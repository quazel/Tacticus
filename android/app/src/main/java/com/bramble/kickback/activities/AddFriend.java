package com.bramble.kickback.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;

import com.bramble.kickback.R;

public class AddFriend extends TabActivity {
    protected static final View view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Resources resources = getResources();

        //Creates TabHost
        TabHost tabHost = getTabHost();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("add friends");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("friend requests");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("Add Friends");
        tab1.setContent(new Intent(this,AddFriendFromContacts.class));

        tab2.setIndicator("Friend Requests");
        tab2.setContent(new Intent(this,FriendRequests.class));

        //Add the tabs  to the TabHost to display.
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_friend, menu);
        return true;
    }
}