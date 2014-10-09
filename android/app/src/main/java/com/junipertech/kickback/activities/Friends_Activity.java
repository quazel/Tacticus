package com.junipertech.kickback.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.junipertech.kickback.R;
import com.junipertech.kickback.models.Friend;

import java.util.ArrayList;


public class Friends_Activity extends Activity {

    ArrayList<Friend> friends;
    ArrayList<Friend> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        friends = new ArrayList<Friend>();
        favorites =new ArrayList<Friend>();

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
        LinearLayout layout = (LinearLayout) findViewById(R.id.friends_list);

        for(int i = 0; i < friends.size(); i++)
        {

            Button bt = new Button(this);
            bt.setText(friends.get(i).getName());
            bt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            bt.setBackgroundResource(R.drawable.full_width_selector);
            layout.addView(bt);

        }
    }

    public void addFavoritesToList()
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.favorites_list);

        for(int i = 0; i < favorites.size(); i++)
        {

        }
    }
}
