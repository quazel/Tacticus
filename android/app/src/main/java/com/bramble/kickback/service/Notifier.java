package com.bramble.kickback.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.activities.Home;
import com.bramble.kickback.util.Globals;

public class Notifier extends Service {

    public class LocalBinder extends Binder {
        public Notifier getService() { return Notifier.this; }
    }

    private final IBinder mBinder = new LocalBinder();

    public void onCreate() {
        Toast.makeText(this, "Service has started", Toast.LENGTH_LONG).show();
    }

    final private Handler callThisInAnHour = new Handler();


    /*public void ping() {

        // this returns the next hour of tasks
        @Override
        @SuppressWarnings("deprecation")
        public void onReceive(Context context, Intent intent) {
            NotificationManager mNM;
            mNM = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
            // Set the icon, scrolling text and timestamp
            Notification notification = new Notification(R.drawable.ic_launcher, "Test Kickback Notification",
                    System.currentTimeMillis());
            // The PendingIntent to launch our activity if the user selects this notification
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, Home.class), 0);
            // Set the info for the views that show in the notification panel.
            notification.setLatestEventInfo(context, intent.getStringExtra("title"), intent.getStringExtra("description"), contentIntent);
            // Send the notification.
            // We use a layout id because it is a unique number. We use it later to cancel.
            mNM.notify(R.string.Kickback, notification);
        }

        callThisInAnHour.postDelayed(new Runnable() {
            @Override
            public void run() { ping(); }
        }, 3540*1000L); // this is an hour - 1 minute
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
