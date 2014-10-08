package com.junipertech.kickback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Advanced_Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced__settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.advanced__settings, menu);
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

    public void notification_settings(View view)
    {
        // intent = new Intent(this, Notification_Settings.class);
        //startActivity(intent);
    }

    public void geolocation_settings(View view)
    {
        geolocationDialog(this);
    }
    public void review_btn(View view)
    {

    }


    public void geolocationDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);


        //builder.setMessage("Are you sure you would like to logout?");
        builder.setPositiveButton("Enable", null);
        builder.setNegativeButton("Disable", null);
        builder.show();
    }
}
