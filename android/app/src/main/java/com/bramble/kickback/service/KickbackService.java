package com.bramble.kickback.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class KickbackService extends Service {

    private NotificationManager notificationManager;
    private final IBinder binder = new KickbackBinder();

    public class KickbackBinder extends Binder {
        KickbackService getService() {
            return KickbackService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
