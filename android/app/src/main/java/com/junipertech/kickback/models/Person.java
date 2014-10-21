package com.junipertech.kickback.models;


public class Person {

    private String name;
    private String phoneNumber;
    private String username;

    public Person(String name, String phoneNumber, String username){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }

    public String getName(){return name;}

    public String getPhoneNumber(){return phoneNumber;}

    public String getUsername(){return  username;}

    public void setName(String name){this.name = name;}

    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

    public void setUsername(String username){this.username = username;}
}
