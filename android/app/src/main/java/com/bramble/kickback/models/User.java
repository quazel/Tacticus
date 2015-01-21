package com.bramble.kickback.models;

import android.content.ContentValues;

import com.bramble.kickback.networking.ConnectionHandler;

import org.json.JSONException;

import java.io.IOException;

public class User {

    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private String sessionId;

    public User(String username) {
        this.username = username;
        this.name = "Steve King, Jr.";
        this.email = "stevekx86@gmail.com";
        this.phoneNumber = "1 602-752-0045";
    }

    public User(String username, String name, String email, String phoneNumber) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

}
