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
        // Favorites
        friends = new ArrayList<Friend>();
        friends.add(new Friend("starfox29","Bob", "1-911-911-9119", true, false));
        friends.add(new Friend("TheDude","Jim", "1-802-457-9834", true, false));
        friends.add(new Friend("punther","Nathan Fegard", "1-603-667-7830", true, false));
        friends.add(new Friend("stevex86","Steve King", "1-602-752-0045", true, false));
        friends.add(new Friend("bitch_face","Kelly", "1-420-420-Blaze'em", true, false));
        friends.add(new Friend("parsePro","JSON", "1-452-735-9384", true, false));
        friends.add(new Friend("hamBurgle","Burgle", "1-746-412-9001", true, false));
        friends.add(new Friend("gay","Jeremy Adams", "1-480-390-7762", true, false));
        // Friends
        friends.add(new Friend("punther","Nathan Fegard", "1-603-667-7830"));
        friends.add(new Friend("stevex86","Steve King", "1-602-752-0045"));
        friends.add(new Friend("bitch_face","Kelly", "1-420-420-Blaze'em"));
        friends.add(new Friend("parsePro","JSON", "1-452-735-9384"));
        friends.add(new Friend("hamBurgle","Burgle", "1-746-412-9001"));
        // Hidden
        friends.add(new Friend("starfox29","Bob", "1-911-911-9119", false, true));
        friends.add(new Friend("TheDude","Jim", "1-802-457-9834", false, true));
        friends.add(new Friend("gay","Jeremy Adams", "1-480-390-7762", false, true));

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

}
