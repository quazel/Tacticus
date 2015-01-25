package com.bramble.kickback.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SignUpService extends Service {

    //from sign up credentials
    private String email;
    private String desiredUsername;
    private String desiredPassword;
    private String confirmPassword;
    private String phoneNumber;

    // from sign up biographical
    private String firstName;
    private String lastName;
    private String birthday;
    private String sex;

    public class LocalBinder extends Binder {
        public SignUpService getService() { return SignUpService.this; }
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        //creds
        email = "";
        desiredUsername = "";
        desiredPassword = "";
        confirmPassword = "";
        phoneNumber = "";
        //bio
        firstName = "";
        lastName = "";
        birthday = "";
        sex = "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    public void clear() {
        email = "";
        desiredUsername = "";
        desiredPassword = "";
        confirmPassword = "";
        phoneNumber = "";

        firstName = "";
        lastName = "";
        birthday = "";
        sex = "";
    }

    // Getters
    public String getEmail() {
        return this.email;
    }

    public String getDesiredUsername() {
        return this.desiredUsername;
    }

    public String getDesiredPassword() {
        return this.desiredPassword;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getSex() {
        return this.sex;
    }

    // Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDesiredUsername(String desiredUsername) {
        this.desiredUsername = desiredUsername;
    }

    public void setDesiredPassword(String desiredPassword) {
        this.desiredPassword = desiredPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
