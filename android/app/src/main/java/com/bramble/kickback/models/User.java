package com.bramble.kickback.models;

import java.util.ArrayList;

public class User {

    // Singleton instance
    private static User instance;

    // Primary fields
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private String sessionId;
    private boolean temp;
    private boolean online;

    // Data collections, i.e. friends lists
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
        username = "";
        name = "";
        email = "";
        phoneNumber = "";
        sessionId = "";
        temp = false;
        online = false;
    }

    public synchronized String getUsername() {
        return username;
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

    public boolean isOnline() {
        return online;
    }

    public void setUsername(String username) {
        this.username = username;
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
