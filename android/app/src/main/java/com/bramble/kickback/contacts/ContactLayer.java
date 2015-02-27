package com.bramble.kickback.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.Person;

import java.util.ArrayList;
import java.util.List;

public class ContactLayer {

    private ContentResolver mContentResolver;
    private List<Person> contacts;


    public ContactLayer(ContentResolver cr) {
        mContentResolver = cr;
        contacts = new ArrayList<Person>();
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
                    ArrayList<String> phoneNumbers = new ArrayList<String>();
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNumbers.add(phone);
                    }
                    contacts.add(new Person(name, phoneNumbers));
                    pCur.close();
                }
            }
        }
        cur.close();
    }


}
