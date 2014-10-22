package com.junipertech.kickback.util;

import com.junipertech.kickback.models.Friend;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.models.User;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class Globals {

    public static ArrayList<Friend> friends = new ArrayList<Friend>();
    public static ArrayList<Kickback> kickbacks = new ArrayList<Kickback>();
    public static User theUser;



    public static void initFriends() {
        ArrayList<Kickback> kickback = new ArrayList<Kickback>();
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

}
