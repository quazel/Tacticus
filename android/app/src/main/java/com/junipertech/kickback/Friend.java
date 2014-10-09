package com.junipertech.kickback;

import java.util.ArrayList;

public class Friend {

    private String name;
    private String phoneNumber;
    private ArrayList<Kickback> kickbacks;

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

}