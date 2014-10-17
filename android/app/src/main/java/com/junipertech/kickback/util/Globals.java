package com.junipertech.kickback.util;

import com.junipertech.kickback.models.Friend;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.models.User;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class Globals {

    public static ArrayList<Friend> friends = new ArrayList<Friend>();
    public static ArrayList<Kickback> kickbacks = new ArrayList<Kickback>();
    public static User theUser = new User("joeschmoe", "somedude@email.com", "0-000-0000");

    public static void initKickbacks() {
        kickbacks.add(new Kickback(new DateTime(2014,10,9,5,30,0,0),
                new DateTime(2014,10,9,8,0,0,0),"Tempe, AZ"));
        kickbacks.add(new Kickback(new DateTime(2014,10,10,6,30,0,0),
                new DateTime(2014,10,10,9,0,0,0),"Woodstock, VT"));
        kickbacks.add(new Kickback(new DateTime(2014,10,10,12,30,0,0),
                new DateTime(2014,10,10,16,0,0,0),"San Francisco, CA"));
        kickbacks.add(new Kickback(new DateTime(2014,10,13,5,30,0,0),
                new DateTime(2014,10,13,8,0,0,0),"New York City, NY"));
        kickbacks.add(new Kickback(new DateTime(2014,10,15,15,30,0,0),
                new DateTime(2014,10,15,18,0,0,0),"Boston, MA"));
        kickbacks.add(new Kickback(new DateTime(2014,10,20,18,30,0,0),
                new DateTime(2014,10,20,22,0,0,0),"Seattle, OR"));
    }

    public static void initFriends() {
        ArrayList<Kickback> kickback = new ArrayList<Kickback>();
        friends.add(new Friend("Bob", "1-911-911-9119", kickback));
        friends.add(new Friend("Jim", "1-802-457-9834", kickback));
        friends.add(new Friend("Nathan Fegard", "1-603-667-7830", kickback));
        friends.add(new Friend("Kelly", "1-420-420-Blaze'em", kickback));
        friends.add(new Friend("JSON", "1-452-735-9384", kickback));
        friends.add(new Friend("Burgle", "1-746-412-9001", kickback));
        friends.add(new Friend("Jeremy Adams", "1-480-390-7762", kickback));
    }

    public static void addKickback(Kickback k)
    {
        kickbacks.add(k);
    }

}
