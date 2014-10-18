package com.junipertech.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.junipertech.kickback.R;
import com.junipertech.kickback.adapter.ListViewAdapter;
import com.junipertech.kickback.models.Friend;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.util.Globals;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Daniel on 10/13/2014.
 */
public class MyFriends extends Activity {
    ArrayList<Friend> friends = Globals.friends;
    ArrayList<Friend> favorites;
    ArrayList<Kickback> kickback;

    //What am I doing
    ListView friendsListView;
    ListView favoritesListView;

    ListViewAdapter friendsAdapter;
    ListViewAdapter favoritesAdapter;

    EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
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
        friendsListView = (ListView)findViewById(R.id.friends_list);
        favoritesListView = (ListView)findViewById(R.id.favorites_list);

        friendsAdapter = new ListViewAdapter(this, friends);
        favoritesAdapter = new ListViewAdapter(this, favorites);

        friendsListView.setAdapter(friendsAdapter);
        favoritesListView.setAdapter(favoritesAdapter);

        setListViewHeightBasedOnChildren(friendsListView);
        setListViewHeightBasedOnChildren(favoritesListView);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_friends, menu);

        initSearchHack(menu);

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
            //TODO Lets autofocus the edit text when they click on the search button

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

    private void initSearchHack(Menu menu){
        MenuItem searchMenuItem = menu.findItem(R.id.action_search_friend);
        searchMenuItem.expandActionView();
        searchInput = (EditText)findViewById(R.id.txt_search);
        searchMenuItem.collapseActionView();

        searchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = searchInput.getText().toString().toLowerCase(Locale.getDefault());
                friendsAdapter.filter(text);
                favoritesAdapter.filter(text);
                setListViewHeightBasedOnChildren(friendsListView);
                setListViewHeightBasedOnChildren(favoritesListView);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }

}
