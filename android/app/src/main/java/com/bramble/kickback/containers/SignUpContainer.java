package com.bramble.kickback.containers;

public class SignUpContainer {

    private static SignUpContainer instance;

    private String desiredUsername;
    private String desiredEmail;
    private String desiredPhoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String birthdate;
    private String sex;

    private SignUpContainer() {
        desiredUsername = "";
        desiredEmail = "";
        desiredPhoneNumber = "";
        password = "";
        firstName = "";
        lastName = "";
        birthdate = "";
        sex = "";
    }

    public static SignUpContainer getInstance() {
        if (instance == null) {
            instance = new SignUpContainer();
        }
        return instance;
    }

    public String getDesiredUsername() {
        return desiredUsername;
    }

    public String getDesiredEmail() {
        return desiredEmail;
    }

    public String getDesiredPhoneNumber() {
        return desiredPhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setDesiredUsername(String username) {
        desiredUsername = username;
    }

    public void setDesiredEmail(String email) {
        desiredEmail = email;
    }

    public void setDesiredPhoneNumber(String phoneNumber) {
        desiredPhoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
