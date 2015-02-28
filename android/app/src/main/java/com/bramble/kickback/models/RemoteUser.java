package com.bramble.kickback.models;

public class RemoteUser extends Person {

    private String nickname;
    private String email;
    private String sex;
    private boolean isFriend;
    private boolean isContact;

    public RemoteUser(String nickname, String name, String email, String phoneNumber, String sex, boolean isFriend, boolean isContact) {
        super(name, phoneNumber);
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.isFriend = isFriend;
        this.isContact = isContact;
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

    public void setFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }

}
