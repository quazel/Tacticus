package com.bramble.kickback.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.bramble.kickback.networking.ConnectionHandler;

import java.io.IOException;

public class KickbackService extends Service {

    public class LocalBinder extends Binder {
        public KickbackService getService() { return KickbackService.this; }
    }

    private final IBinder mBinder = new LocalBinder();


    @Override
    public void onCreate() {
        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "");
        String password = prefs.getString("password", "");
        if (pingServer()) {

        }
        else {

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public boolean pingServer() {
        try {
            String session = getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("session", "");
            String result = new ConnectionHandler().ping(session);
            return result.startsWith("200:");
        } catch(IOException e) {
            return false;
        }
    }

    public boolean login() {
        try {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            String email = prefs.getString("email", "");
            String password = prefs.getString("password", "");
            String result = new ConnectionHandler().login(email, password);
            return result.startsWith("200:");
        } catch (IOException e) {
            return false;
        }
    }

}
