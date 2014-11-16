package com.junipertech.kickback.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.junipertech.kickback.models.Friend;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.models.Person;
import com.junipertech.kickback.models.User;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class Globals {

    public static ArrayList<Friend> friends = new ArrayList<Friend>();
    public static ArrayList<Kickback> kickbacks = new ArrayList<Kickback>();
    public static ArrayList<Person> contacts = new ArrayList<Person>();
    public static User theUser;


    public static void initFriends() {
        ArrayList<Kickback> kickback = new ArrayList<Kickback>();
        friends.add(new Friend("Bob", "1-911-911-9119", kickback,true));
        friends.add(new Friend("Jim", "1-802-457-9834", kickback,true));
        friends.add(new Friend("Nathan Fegard", "1-603-667-7830", kickback,true));
        friends.add(new Friend("Steve King", "1-602-752-0046", kickback,true));
        friends.add(new Friend("Kelly", "1-420-420-Blaze'em", kickback,true));
        friends.add(new Friend("JSON", "1-452-735-9384", kickback,true));
        friends.add(new Friend("Burgle", "1-746-412-9001", kickback,true));
        friends.add(new Friend("Jeremy Adams", "1-480-390-7762", kickback,true));

        friends.add(new Friend("Bob", "1-911-911-9119", kickback));
        friends.add(new Friend("Jim", "1-802-457-9834", kickback));
        friends.add(new Friend("Nathan Fegard", "1-603-667-7830", kickback));
        friends.add(new Friend("Steve King", "1-602-752-0046", kickback));
        friends.add(new Friend("Kelly", "1-420-420-Blaze'em", kickback));
        friends.add(new Friend("JSON", "1-452-735-9384", kickback));
        friends.add(new Friend("Burgle", "1-746-412-9001", kickback));
        friends.add(new Friend("Jeremy Adams", "1-480-390-7762", kickback));

        friends.add(new Friend("Bob", "1-911-911-9119", kickback));
        friends.add(new Friend("Jim", "1-802-457-9834", kickback));
        friends.add(new Friend("Nathan Fegard", "1-603-667-7830", kickback));
        friends.add(new Friend("Steve King", "1-602-752-0046", kickback));
        friends.add(new Friend("Kelly", "1-420-420-Blaze'em", kickback));
        friends.add(new Friend("JSON", "1-452-735-9384", kickback));
        friends.add(new Friend("Burgle", "1-746-412-9001", kickback));
        friends.add(new Friend("Jeremy Adams", "1-480-390-7762", kickback));

        friends.add(new Friend("Bob", "1-911-911-9119", kickback));
        friends.add(new Friend("Jim", "1-802-457-9834", kickback));
        friends.add(new Friend("Nathan Fegard", "1-603-667-7830", kickback));
        friends.add(new Friend("Steve King", "1-602-752-0046", kickback));
        friends.add(new Friend("Kelly", "1-420-420-Blaze'em", kickback));
        friends.add(new Friend("JSON", "1-452-735-9384", kickback));
        friends.add(new Friend("Burgle", "1-746-412-9001", kickback));
        friends.add(new Friend("Jeremy Adams", "1-480-390-7762", kickback));


    }

    public static void addKickback(Kickback k) {
        kickbacks.add(k);
    }

    public static void loginUser(String username, String password) {
        theUser = new User(username, "somedude@email.com", "0-000-0000");
    }

    public static void createUser(String username, String email, String password){
        theUser = new User(username, email, "0-000-000-0000");
    }

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
