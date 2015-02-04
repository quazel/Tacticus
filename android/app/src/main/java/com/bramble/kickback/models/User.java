package com.bramble.kickback.models;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class User {

    // Singleton instance
    private static User instance;

    // Primary fields
    private String nickname;
    private String name;
    private String email;
    private String phoneNumber;
    private String sessionId;
    private boolean temp;
    private boolean online;

    // Data collections, i.e. friends lists and kickbacks
    private ArrayList<Friend> friends;
    private ArrayList<Friend> onlineFriends;
    private ArrayList<Kickback> plans;

    // Lazy initialization and getting of User instance. Synchronized so that we can ensure that
    // user is not created by two concurrent threads.
    public static synchronized User getUser() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    private User() {
        nickname = "";
        name = "";
        email = "";
        phoneNumber = "";
        sessionId = "";
        temp = false;
        online = false;

        friends = new ArrayList<Friend>();
        ArrayList<Kickback> kickback = new ArrayList<Kickback>();
        friends.add(new Friend("starfox29","Bob", "1-911-911-9119", kickback, true));
        friends.add(new Friend("TheDude","Jim", "1-802-457-9834", kickback, true));
        friends.add(new Friend("punther","Nathan Fegard", "1-603-667-7830", kickback, true));
        friends.add(new Friend("stevex86","Steve King", "1-602-752-0046", kickback, true));
        friends.add(new Friend("bitch_face","Kelly", "1-420-420-Blaze'em", kickback, true));
        friends.add(new Friend("parsePro","JSON", "1-452-735-9384", kickback, true));
        friends.add(new Friend("hamBurgle","Burgle", "1-746-412-9001", kickback, true));
        friends.add(new Friend("gay","Jeremy Adams", "1-480-390-7762", kickback, true));

        friends.add(new Friend("starfox29","Bob", "1-911-911-9119", kickback));
        friends.add(new Friend("TheDude","Jim", "1-802-457-9834", kickback));
        friends.add(new Friend("punther","Nathan Fegard", "1-603-667-7830", kickback));
        friends.add(new Friend("stevex86","Steve King", "1-602-752-0046", kickback));
        friends.add(new Friend("bitch_face","Kelly", "1-420-420-Blaze'em", kickback));
        friends.add(new Friend("parsePro","JSON", "1-452-735-9384", kickback));
        friends.add(new Friend("hamBurgle","Burgle", "1-746-412-9001", kickback));
        friends.add(new Friend("gay","Jeremy Adams", "1-480-390-7762", kickback));

        plans = new ArrayList<Kickback>();
        plans.add(new Kickback(new DateTime(),new DateTime(),"Hati"));
        plans.add(new Kickback(new DateTime().plusDays(1),new DateTime().plusDays(1),"CHIPS!"));
        plans.add(new Kickback(new DateTime().plusDays(1),new DateTime().plusDays(1),"Nicaragua"));
        plans.add(new Kickback(new DateTime().plusDays(1),new DateTime().plusDays(1),"Chip Factory"));
        plans.add(new Kickback(new DateTime().plusDays(2),new DateTime().plusDays(2),"Nigeria"));
        plans.add(new Kickback(new DateTime().plusDays(3),new DateTime().plusDays(3),"Sierra Leone"));
        plans.add(new Kickback(new DateTime().plusDays(4),new DateTime().plusDays(4),"New Guinea"));
        plans.add(new Kickback(new DateTime().plusDays(5),new DateTime().plusDays(5),"South Africa"));
        plans.add(new Kickback(new DateTime().plusDays(6),new DateTime().plusDays(6),"Main"));
        plans.add(new Kickback(new DateTime().plusDays(7),new DateTime().plusDays(7),"New York"));
        plans.add(new Kickback(new DateTime().plusDays(8),new DateTime().plusDays(8),"Bolivia"));
        plans.add(new Kickback(new DateTime().plusDays(9),new DateTime().plusDays(9),"Youtube"));
        plans.add(new Kickback(new DateTime().plusDays(10),new DateTime().plusDays(10),"My House"));
        plans.add(new Kickback(new DateTime().plusDays(11),new DateTime().plusDays(11),"Chips"));
        plans.add(new Kickback(new DateTime().plusDays(12),new DateTime().plusDays(12),"CHIPS!"));

        onlineFriends = friends;
    }

    public synchronized String getNickname() {
        return nickname;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized String getEmail() {
        return email;
    }

    public synchronized String getPhoneNumber() {
        return phoneNumber;
    }

    public synchronized String getSessionId() {
        return sessionId;
    }

    public synchronized boolean isTemp() {
        return temp;
    }

    public synchronized void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized void setEmail(String email) {
        this.email = email;
    }

    public synchronized void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public synchronized void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public synchronized void setTemp(boolean temp) {
        this.temp = temp;
    }

    public synchronized void setOnline(boolean online) {
        this.online = online;
    }

    public synchronized ArrayList<Friend> getFriends() {
        return friends;
    }

    public synchronized ArrayList<Friend> getOnlineFriends() {
        return onlineFriends;
    }

    public synchronized void addFriend(Friend friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
        }
    }

    public synchronized void removeFriend(Friend friend) {
        if (friends.contains(friend)) {
            friends.remove(friend);
            if (onlineFriends.contains(friend)) {
                onlineFriends.remove(friend);
            }
        }
    }

    public synchronized void addKickback(Kickback kickback) {
        if (!plans.contains(kickback)) {
            plans.add(kickback);
        }
    }

    public synchronized void removeKickback(Kickback kickback) {
        if (plans.contains(kickback)) {
            plans.remove(kickback);
        }
    }

    public synchronized ArrayList<Kickback> getPlans() {
        return plans;
    }

}
