package com.bramble.kickback.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class LoginService extends Service {

    private String username;
    private String password;

    public class LocalBinder extends Binder {
        public LoginService getService() { return LoginService.this; }
    }

    private final IBinder mBinder = new LocalBinder();

    public void onCreate() {
        super.onCreate();

        username = "";
        password = "";
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
        username = "";
        password = "";
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
