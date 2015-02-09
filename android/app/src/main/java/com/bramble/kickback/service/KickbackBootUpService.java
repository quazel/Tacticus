package com.bramble.kickback.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;

import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class KickbackBootUpService extends Service {

    public class LocalBinder extends Binder {
        public KickbackBootUpService getService() { return KickbackBootUpService.this; }
    }

    private final IBinder mBinder = new LocalBinder();


    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }



}
