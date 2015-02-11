package com.bramble.kickback.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class KickbackService extends Service {

    public static final int MSG_REFRESH_HOME = 0;
    private Messenger messenger;

    public class LocalBinder extends Binder {
        public KickbackService getService() { return KickbackService.this; }
    }

    private final IBinder mBinder = new LocalBinder();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            messenger = (Messenger) extras.get("messenger");
        }
        return mBinder;
    }


    public void startPolling() {
        Toast.makeText(this, "Started", Toast.LENGTH_LONG).show();
        Message message = Message.obtain();
        message.what = MSG_REFRESH_HOME;
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopPolling() {
        Toast.makeText(this, "Stopped", Toast.LENGTH_LONG).show();
    }


}
