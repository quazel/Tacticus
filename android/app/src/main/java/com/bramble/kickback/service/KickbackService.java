package com.bramble.kickback.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class KickbackService extends Service {

    public class LocalBinder extends Binder {
        public KickbackService getService() { return KickbackService.this; }
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
