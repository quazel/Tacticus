package com.bramble.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

import com.bramble.kickback.R;
import com.bramble.kickback.models.User;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 1250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (!User.getUser().getNickname().equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent accountPortalIntent = new Intent(SplashScreen.this, Main.class);
                    startActivity(accountPortalIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent accountPortalIntent = new Intent(SplashScreen.this, AccountPortal.class);
                    startActivity(accountPortalIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

}
