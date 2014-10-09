package com.junipertech.kickback.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.junipertech.kickback.R;
import com.junipertech.kickback.models.Friend;
import com.junipertech.kickback.models.Kickback;

import java.util.ArrayList;


public class Friends_Activity extends Activity {

    ArrayList<Friend> friends;
    ArrayList<Friend> favorites;
    ArrayList<Kickback> kickback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        friends = new ArrayList<Friend>();
        favorites = new ArrayList<Friend>();
        kickback = new ArrayList<Kickback>();

        friends.add(new Friend("Bob", "1-911-911-9119", kickback));
        friends.add(new Friend("Jim", "1-802-457-9834", kickback));
        friends.add(new Friend("Nathan Fegard", "1-603-667-7830", kickback));
        friends.add(new Friend("Kelly", "1-420-420-Blaze'em", kickback));
        friends.add(new Friend("JSON", "1-452-735-9384", kickback));
        friends.add(new Friend("Burgle", "1-746-412-9001", kickback));

        favorites.add(friends.get(2));
        favorites.add(friends.get(4));
        favorites.add(friends.get(0));


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
            bt.setText(setLabel(friends.get(i).getName()));
            bt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            bt.setBackgroundResource(R.drawable.full_width_selector);
            bt.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            bt.setLines(3);
            layout.addView(bt);

        }
    }

    public void addFavoritesToList()
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.favorites_list);

        for(int i = 0; i < favorites.size(); i++)
        {
            Button bt = new Button(this);
            bt.setText(setLabel(favorites.get(i).getName()));
            bt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            bt.setBackgroundResource(R.drawable.full_width_selector);
            bt.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            bt.setLines(3);
            layout.addView(bt);
        }
    }

    public Spanned setLabel(String name)
    {
        String username = "<br><font color='#c9c9c9'> username </font>";
        return Html.fromHtml(name+"\n"+"<small>"+username+"</small>");
    }
}
