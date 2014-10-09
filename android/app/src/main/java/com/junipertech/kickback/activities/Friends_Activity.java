package com.junipertech.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.junipertech.kickback.R;

import java.util.ArrayList;


public class Friends_Activity extends Activity {

    ArrayList<String> friends;
    ArrayList<String> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        addFavoritesToList();
        addFriendsToList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.friends, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_add_friend:

                return true;
            case R.id.action_search:

                return true;
        }
        return true;
    }

    public void addFriendsToList()
    {

    }

    public void addFavoritesToList()
    {

    }
}
