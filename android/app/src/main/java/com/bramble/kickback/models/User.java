package com.bramble.kickback.models;

public class User {

    private static User instance;

    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private String sessionId;
    private boolean temp;
    private boolean online;

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

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isTemp() {
        return temp;
    }

    public boolean isOnline() {
        return online;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

}
