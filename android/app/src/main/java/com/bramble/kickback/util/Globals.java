package com.bramble.kickback.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.Kickback;
import com.bramble.kickback.models.Person;
import com.bramble.kickback.models.User;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class Globals {

    public static ArrayList<Kickback> kickbacks = new ArrayList<Kickback>();
    public static ArrayList<Person> contacts = new ArrayList<Person>();

    public static void initKickBacks(){
        kickbacks.add(new Kickback(new DateTime(),new DateTime(),"Hati"));
        kickbacks.add(new Kickback(new DateTime().plusDays(1),new DateTime().plusDays(1),"CHIPS!"));
        kickbacks.add(new Kickback(new DateTime().plusDays(1),new DateTime().plusDays(1),"Nicaragua"));
        kickbacks.add(new Kickback(new DateTime().plusDays(1),new DateTime().plusDays(1),"Chip Factory"));
        kickbacks.add(new Kickback(new DateTime().plusDays(2),new DateTime().plusDays(2),"Nigeria"));
        kickbacks.add(new Kickback(new DateTime().plusDays(3),new DateTime().plusDays(3),"Sierra Leone"));
        kickbacks.add(new Kickback(new DateTime().plusDays(4),new DateTime().plusDays(4),"New Guinea"));
        kickbacks.add(new Kickback(new DateTime().plusDays(5),new DateTime().plusDays(5),"South Africa"));
        kickbacks.add(new Kickback(new DateTime().plusDays(6),new DateTime().plusDays(6),"Main"));
        kickbacks.add(new Kickback(new DateTime().plusDays(7),new DateTime().plusDays(7),"New York"));
        kickbacks.add(new Kickback(new DateTime().plusDays(8),new DateTime().plusDays(8),"Bolivia"));
        kickbacks.add(new Kickback(new DateTime().plusDays(9),new DateTime().plusDays(9),"Youtube"));
        kickbacks.add(new Kickback(new DateTime().plusDays(10),new DateTime().plusDays(10),"My House"));
        kickbacks.add(new Kickback(new DateTime().plusDays(11),new DateTime().plusDays(11),"Chips"));
        kickbacks.add(new Kickback(new DateTime().plusDays(12),new DateTime().plusDays(12),"CHIPS!"));

    }

    public static void addKickback(Kickback k) {
        kickbacks.add(k);
    }


    //needs work
    public static void readContacts(ContentResolver cr) {
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
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
    }

}
