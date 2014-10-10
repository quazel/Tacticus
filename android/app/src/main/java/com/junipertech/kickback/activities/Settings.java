package com.junipertech.kickback.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.junipertech.kickback.R;


public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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

    public void openAccountSettings(View view) {
        Intent intent = new Intent(this, AccountSettings.class);
        startActivity(intent);
    }

    public void openPrivacySettings(View view) {
        Intent intent = new Intent(this, PrivacySettings.class);
        startActivity(intent);
    }

    public void openAdvancedSettings(View view) {
        Intent intent = new Intent(this, AdvancedSettings.class);
        startActivity(intent);
    }

    public void openMoreInfo(View view) {
        Intent intent = new Intent(this, MoreInformation.class);
        startActivity(intent);
    }

    public void logoutUser(View view) {
        logoutDialog(this);
    }

    public void logoutDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);


        builder.setMessage("Are you sure you would like to logout?");
        builder.setPositiveButton("Yes", null);
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
