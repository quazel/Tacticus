package com.bramble.kickback.models;

import java.util.ArrayList;

public class Person {

    private String name;
    private ArrayList<String> phoneNumbers;
    private String username;

    public Person(String name, ArrayList<String> phoneNumbers) {
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPhoneNumbers() {
        return new ArrayList<String>(phoneNumbers);
    }

    public String getUsername() {
        return  username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
