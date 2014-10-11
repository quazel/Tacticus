package com.junipertech.kickback.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.junipertech.kickback.R;
import com.junipertech.kickback.models.Friend;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.util.Util;
import com.junipertech.kickback.util.Globals;

import java.util.ArrayList;


public class KickbacksList extends Activity {

    ArrayList<Friend> activeKickbacks;
    ArrayList<Friend> activeFavorites;
    ArrayList<Friend> favorites;
    ArrayList<Kickback> kickback = Globals.kickbacks; //filler empty kickback NO INFO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kickback_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //dummy arraylists for formatting purposes
        activeKickbacks = new ArrayList<Friend>();
        activeFavorites = new ArrayList<Friend>();
        favorites = new ArrayList<Friend>();

        //Dummy Info
        activeKickbacks.add(new Friend("Steven Smith", "555-888-1234", kickback));
        activeKickbacks.add(new Friend("Bob Billy", "555-123-5645", kickback));
        activeKickbacks.add(new Friend("Tyler Glecker", "555-123-5567", kickback));
        activeKickbacks.add(new Friend("Hogger Imba", "1-800-55-1234", kickback));
        activeKickbacks.add(new Friend("Dora Explorer", "555-321-9563", kickback));
        activeKickbacks.add(new Friend("Nasa Kabrel", "111-111-1111", kickback));
        activeKickbacks.add(new Friend("Hope Mcgee", "230-444-1523", kickback));
        //Dummy Favorites
        favorites.add(activeKickbacks.get(0));
        favorites.add(activeKickbacks.get(1));
        favorites.add(activeKickbacks.get(2));
        activeFavorites.add(activeKickbacks.get(0));

        setActiveFavorites(); //Compares the people on the friends list to those who are online


        // methods to be adapted once there are real friends/favorites from server

        //I created the addToList method because the only difference between addin the favorites and others is simply which Layout and ArrayList used
        //Also in the future if we allow users to make categories this would make adding them cleaner
        addToList(activeFavorites,(LinearLayout) findViewById(R.id.friends_list));
        addToList(activeKickbacks,(LinearLayout) findViewById(R.id.friends_list));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kickback_list, menu);
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

    public void addToList(ArrayList<Friend> people, LinearLayout layout) {

        for(int i = 0; i < people.size(); i++) {
            Button bt = new Button(this);
            bt.setText(setLabel(people.get(i).getName()));
            bt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
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

    public void setActiveFavorites() {

        for(Friend favorite : favorites){ //For all given favorites
            if(activeKickbacks.contains(favorite)){ //Check if the favorite is online
                activeFavorites.add(favorite); //If online add them to active Favorites
            }
        }

    }
}
