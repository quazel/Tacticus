package com.junipertech.kickback.models;

import java.util.ArrayList;

public class Friend {

    private String name;
    private String phoneNumber;
    private ArrayList<Kickback> kickbacks;
    private String username = "Username"; //Constant for now
    private boolean isFavorite;

    public Friend(String name, String phoneNumber, ArrayList<Kickback> kickbacks) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.kickbacks = kickbacks;
    }

    public Friend(String name, String phoneNumber, ArrayList<Kickback> kickbacks, boolean fav) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.kickbacks = kickbacks;
        this.isFavorite = fav;
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

    public boolean getIsFavorite(){
        return isFavorite;
    }
}
