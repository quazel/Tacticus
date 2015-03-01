package com.bramble.kickback.contacts;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;

import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.Person;
import com.bramble.kickback.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactLayer {

    private static ContactLayer instance;
    private ContentResolver mContentResolver;
    private HashMap<String, Person> contacts;

    private ContactLayer(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
        contacts = new HashMap<String, Person>();
        Cursor cur = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    // get the phone number
                    Cursor pCur = mContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String phoneUnformatted = phone.replaceAll("[^\\d]", "");
                        if (phoneUnformatted.length() < 10) {
                            continue;
                        }
                        if (phoneUnformatted.length() < 11) {
                            phoneUnformatted = User.getUser().getCountryCode() + phoneUnformatted;
                        }
                        contacts.put(phoneUnformatted, new Person(name, phone));
                    }
                    pCur.close();
                }
            }
        }
        cur.close();
    }

    public static void initialize(ContentResolver contentResolver) {
        instance = new ContactLayer(contentResolver);
    }

    public static ContactLayer getInstance() {
        if (instance == null) {
            throw new IllegalStateException();
        }
        else {
            return instance;
        }
    }

    public void createContact(Friend friend) {
        ArrayList <ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        friend.getName()).build());
        ops.add(ContentProviderOperation.
                newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, friend.getPhoneNumber())
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());
        try {
            mContentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    public void removeContact(Friend friend) {
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(friend.getPhoneNumber()));
        String[] mPhoneNumberProjection = { ContactsContract.PhoneLookup.LOOKUP_KEY, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME };
        Cursor cur = mContentResolver.query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                mContentResolver.delete(uri, null, null);
            }
        } finally {
            if (cur != null)
                cur.close();
        }
    }

    // lol hack
    public void updateContact(Friend friend) {
        removeContact(friend);
        createContact(friend);
    }

    public boolean contactExists(Friend friend) {
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(friend.getPhoneNumber()));
        String[] mPhoneNumberProjection = { ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME };
        Cursor cur = mContentResolver.query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }

    public HashMap<String, Person> getContacts() {
        return contacts;
    }

}
