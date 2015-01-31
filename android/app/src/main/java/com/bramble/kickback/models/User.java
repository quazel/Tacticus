package com.bramble.kickback.models;

public class User {

    private static User instance;

    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private String sessionId;
    private boolean temp;

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

    public synchronized void setUsername(String username) {
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

}
