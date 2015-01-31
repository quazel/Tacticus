package com.bramble.kickback.models;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class Friend {

    private String name;
    private String phoneNumber;
    private String username;
    private boolean isFavorite;
    private ArrayList<Kickback> plans;

    public Friend(String name, String username, String phoneNumber, ArrayList<Kickback> plans) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.plans = plans;
    }

    public Friend(String name, String username, String phoneNumber, ArrayList<Kickback> plans, boolean fav) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.plans = plans;
        this.isFavorite = fav;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername(){
        return username;
    }

    public boolean getIsFavorite(){
        return isFavorite;
    }

    /*public void makeContact(Context context, String phoneNumber) {
        if(contactExists(context, phoneNumber)) {
            ContentValues values = new ContentValues();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, 001);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, this.phoneNumber);
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM);
            values.put(ContactsContract.CommonDataKinds.Phone.LABEL, this.name);
            Uri dataUri = getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
    }*/

    public boolean contactExists(Context context, String number) {
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = { ContactsContract.PhoneLookup._ID,
                                            ContactsContract.PhoneLookup.NUMBER,
                                            ContactsContract.PhoneLookup.DISPLAY_NAME };
        Cursor cur = context.getContentResolver().query(lookupUri,mPhoneNumberProjection, null, null, null);
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
}
