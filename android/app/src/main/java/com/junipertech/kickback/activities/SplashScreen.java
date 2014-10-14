package com.junipertech.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.junipertech.kickback.R;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 1250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences prefs = getSharedPreferences("KickbackPreferences", 0);

        if(prefs.contains("username")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreen.this, Home.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setContentView(R.layout.activity_splash_screen_sign_in);
                }
            }, SPLASH_TIME_OUT);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
     //attempts at using button navigation


    public void cancelSignUpPressed(View v){
        setContentView(R.layout.activity_splash_screen_sign_in);
    }

    public void signInPressed(View v){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void toSignUpPressed(View v){
        setContentView(R.layout.activity_splash_screen_sign_up);
    }

    public void signUpPressed(View v){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}