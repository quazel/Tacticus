package com.bramble.kickback.models;

public class RemoteUser {

    private String nickname;
    private String name;
    private String email;
    private String phoneNumber;
    private String sex;
    private boolean isFriend;

    public RemoteUser(String nickname, String name, String email, String phoneNumber, String sex, boolean isFriend) {
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.isFriend = isFriend;
    }

    public String getNickname() {
        return nickname;
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

    public String getSex() {
        return sex;
    }

    public boolean isFriend() {
        return isFriend;
    }

}
