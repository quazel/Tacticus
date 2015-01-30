package com.bramble.kickback.activities;

import android.app.Activity;;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import com.bramble.kickback.R;
import com.bramble.kickback.util.Globals;


public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 1250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("on create");
        setContentView(R.layout.activity_splash_screen);
        Globals.readContacts(getContentResolver());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent accountPortalIntent = new Intent(SplashScreen.this, AccountPortal.class);
                System.out.println("Starting account portal");
                startActivity(accountPortalIntent);
                System.out.println("started account portal");
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
