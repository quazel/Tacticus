package com.bramble.kickback.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bramble.kickback.R;
import com.bramble.kickback.fragments.KickbackFragment;
import com.bramble.kickback.fragments.KickbackList;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.util.Globals;


public class Home extends Activity {

    private Button theButton;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private KickbackList kickbackList;
    private KickbackFragment kickbackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fm = getFragmentManager();
        kickbackFragment = new KickbackFragment();
        kickbackList = new KickbackList();

        ActionBar actionbar = getActionBar();
        theButton =(Button)findViewById(R.id.kickbackButton);

        ft = getFragmentManager().beginTransaction();
        //ft.setCustomAnimations(android.R.animator, android.R.animator.fade_out);
        ft.add(R.id.home_container, kickbackFragment);
        ft.commit();
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
        ft = getFragmentManager().beginTransaction();
        //ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.home_container, kickbackList);
        ft.commit();
    }

    public void onBusyPressed(View view) {
        ft = getFragmentManager().beginTransaction();
        //ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.home_container, kickbackFragment);
        ft.commit();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit Kickback?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Home.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
