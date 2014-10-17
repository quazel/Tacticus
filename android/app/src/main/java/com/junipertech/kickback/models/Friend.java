package com.junipertech.kickback.models;

import java.util.ArrayList;

public class Friend {

    private String name;
    private String phoneNumber;
    private ArrayList<Kickback> kickbacks;
    private String username = "Username"; //Constant for now

    public Friend(String name, String phoneNumber, ArrayList<Kickback> kickbacks) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.kickbacks = kickbacks;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername(){
        return username;
    }
}
