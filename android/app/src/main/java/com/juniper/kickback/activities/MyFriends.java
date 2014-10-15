package com.juniper.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.junipertech.kickback.R;
import com.juniper.kickback.models.Friend;
import com.juniper.kickback.models.Kickback;
import com.juniper.kickback.util.Globals;
import com.juniper.kickback.util.Util;

import java.util.ArrayList;


public class MyFriends extends Activity {

    ArrayList<Friend> friends = Globals.friends;
    ArrayList<Friend> favorites;
    ArrayList<Kickback> kickback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //dummy arraylists for formatting purposes
        favorites = new ArrayList<Friend>();
        kickback = new ArrayList<Kickback>();

        if(friends.size() == 0)
            Globals.initFriends();

        favorites.add(friends.get(2));
        favorites.add(friends.get(4));
        favorites.add(friends.get(0));
        favorites.add(friends.get(6));

        // methods to be adapted once there are real friends/favorites from server
        addFavoritesToList();
        addFriendsToList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_friends, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_add_friend:
                Intent addFriendsIntent = new Intent(this, AddFriend.class);
                startActivity(addFriendsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addFriendsToList() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.friends_list);

        for(int i = 0; i < friends.size(); i++) {

            Button bt = new Button(this);
            bt.setText(setLabel(friends.get(i).getName()));
            bt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            bt.setBackgroundResource(R.drawable.full_width_selector);
            bt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            bt.setLines(3);
            int padding = Util.dpToPixels(getResources(), 9);
            bt.setPadding(padding, 0, padding, 0);
            layout.addView(bt);


        }
    }

    public void addFavoritesToList() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.favorites_list);

        for(int i = 0; i < favorites.size(); i++) {
            Button bt = new Button(this);
            bt.setText(setLabel(favorites.get(i).getName()));
            bt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            bt.setBackgroundResource(R.drawable.full_width_selector);
            bt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            bt.setLines(3);
            int padding = Util.dpToPixels(getResources(), 9);
            bt.setPadding(padding, 0, padding, 0);
            layout.addView(bt);
        }
    }

    public Spanned setLabel(String name) {
        String username = "<br><font color='#c9c9c9'> username </font>";
        return Html.fromHtml(name + "\n" + "<small>" + username + "</small>");
    }
}
