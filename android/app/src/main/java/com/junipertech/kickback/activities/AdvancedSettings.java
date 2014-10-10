package com.junipertech.kickback.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.junipertech.kickback.R;


public class AdvancedSettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // formats phone number in account settings
        String state = "enabled"; //dummy variable
        formatGeolocationButton(state);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.advanced_settings, menu);


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
        Intent intent = new Intent(this, NotificationSettings.class);
        startActivity(intent);
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

    public void formatGeolocationButton(String s)
    {
        // formats phone number in account settings
        Button geoButton = (Button) findViewById(R.id.account_advanced_geolocation);
        String firstGeo = (String)geoButton.getText();

        String nextGeo = "<br><font color='#c9c9c9'>"+s+"</font>";
        geoButton.setText(Html.fromHtml(firstGeo + "\n" + "<small>" + nextGeo + "</small>"));;
    }
}
