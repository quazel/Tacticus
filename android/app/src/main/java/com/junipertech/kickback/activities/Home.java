package com.junipertech.kickback.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.junipertech.kickback.R;
import com.junipertech.kickback.util.Globals;


public class Home extends Activity {

    private Button theButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ugly hack
        if(!Globals.isOnline) {
            setContentView(R.layout.activity_home);
        }
        else if (Globals.isOnline) {
            setContentView(R.layout.activity_home_online);
        }
        ActionBar actionbar = getActionBar();
        theButton =(Button)findViewById(R.id.kickbackButton);
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
                Intent friendsIntent = new Intent(this, MyFriends.class);
                startActivity(friendsIntent);
                return true;
            case R.id.action_schedule:
                Intent scheduleIntent = new Intent(this, KickbacksSchedule.class);
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

    public void onKickbackPressed(View view) {
        if(Globals.isOnline) {
            setContentView(R.layout.activity_home_online);
        }
        else if(!Globals.isOnline) {
            Globals.goOnline();
            setContentView(R.layout.activity_home_online);
            theButton.setText("test text");
        }
    }

    public void onBusyPressed(View view) {
        Globals.goOffline();
        setContentView(R.layout.activity_home);
    }
}
