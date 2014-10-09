package com.junipertech.kickback.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import com.junipertech.kickback.R;


public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionbar = getActionBar();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId())
        {
            case R.id.action_my_friends:
                Intent friendsIntent = new Intent(this, Friends_Activity.class);
                startActivity(friendsIntent);
                return true;
            case R.id.action_schedule:
                Intent scheduleIntent = new Intent(this, Kickback_Schedule.class);
                startActivity(scheduleIntent);
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, Settings.class);
                startActivity(settingsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*public void kickback()
    {
        Intent intent = new Intent(this, Kickback_List.class);
        startActivity(intent);
    }*/

    public void onToggleClicked(View view)
    {
        boolean on = ((ToggleButton) view).isChecked();

        if(on)
        {
           // Intent kickbackIntent = new Intent(this, Kickback_List.class);
            //startActivity(kickbackIntent);
        }

    }

}
