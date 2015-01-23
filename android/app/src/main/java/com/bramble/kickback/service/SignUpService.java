package com.bramble.kickback.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SignUpService extends Service {

    //from sign up credentials
    private String email;
    private String desiredUsername;
    private String desiredPassword;
    private String confirmPassword;

    // from sign up biographical
    private String firstName;
    private String lastName;
    private String birthday;
    private String sex;

    @Override
    public void onCreate() {
        super.onCreate();
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
        return null;
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
