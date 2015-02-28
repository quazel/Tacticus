package com.bramble.kickback.models;

import com.bramble.kickback.contacts.ContactLayer;

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

    // Next appropriate time
    private int callMe;

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
        onlineFriends = new ArrayList<Friend>();
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

    public synchronized int getCallMe() {
        return callMe;
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

    public synchronized void setCallMe(int callMe) {
        this.callMe = callMe;
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
            ContactLayer.getInstance().createContact(friend);
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

    public synchronized void clearUser() {
        nickname = "";
        name = "";
        phoneNumber = "";
        sessionId = "";
        temp = false;
        online = false;
        callMe = 0;

        friends.clear();
        onlineFriends.clear();
    }

}
