package com.bramble.kickback.receivers;

import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;

import com.bramble.kickback.service.KickbackBootUpService;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, KickbackBootUpService.class);
        context.startService(myIntent);
    }

}
