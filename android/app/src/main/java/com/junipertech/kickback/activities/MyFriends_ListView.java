package com.junipertech.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.junipertech.kickback.R;
import com.junipertech.kickback.models.Friend;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.util.Globals;

import java.util.ArrayList;

/**
 * Created by Daniel on 10/13/2014.
 */
public class MyFriends_ListView extends Activity {
    ArrayList<Friend> friends = Globals.friends;
    ArrayList<Friend> favorites;
    ArrayList<Kickback> kickback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends_listview);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //dummy arraylists for formatting purposes
        favorites = new ArrayList<Friend>();
        kickback = new ArrayList<Kickback>();

        if(friends.size() == 0) {
            Globals.initFriends();

        }

        favorites.add(friends.get(2));
        favorites.add(friends.get(4));
        favorites.add(friends.get(0));
        favorites.add(friends.get(6));


        //Get the list views for friends and favorites
        ListView friendsListView = (ListView)findViewById(R.id.friends_list);
        ListView favoritesListView = (ListView)findViewById(R.id.favorites_list);

        final ArrayAdapter<Friend> friendsAdapter = new ArrayAdapter<Friend>(this, R.layout.list_item, R.id.friend_thing, friends);
        ArrayAdapter<Friend> favoritesAdapter = new ArrayAdapter<Friend>(this, R.layout.list_item, R.id.friend_thing, favorites);

        EditText searchInput = (EditText)findViewById(R.id.txt_search); //Find the EditText that will take input from the user

        friendsListView.setAdapter(friendsAdapter);
        favoritesListView.setAdapter(favoritesAdapter);
        setListViewHeightBasedOnChildren(friendsListView);
        setListViewHeightBasedOnChildren(favoritesListView);

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

    //http://stackoverflow.com/questions/18367522/android-list-view-inside-a-scroll-view
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() ));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
