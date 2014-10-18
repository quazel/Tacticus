package com.junipertech.kickback.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.junipertech.kickback.R;
import com.junipertech.kickback.adapter.ListViewAdapter;
import com.junipertech.kickback.models.Friend;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.util.Util;
import com.junipertech.kickback.util.Globals;

import java.util.ArrayList;
import java.util.Locale;


public class KickbacksList extends Activity {

    ArrayList<Friend> activeKickbacks;
    ArrayList<Friend> activeFavorites;
    ArrayList<Friend> favorites;
    ArrayList<Kickback> kickback = Globals.kickbacks; //filler empty kickback NO INFO

    //What am I doing
    ListView friendsListView;
    ListView favoritesListView;

    ListViewAdapter friendsAdapter;
    ListViewAdapter favoritesAdapter;

    EditText searchInput;

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
        favorites.add(activeKickbacks.get(3));

        setActiveFavorites(); //Compares the people on the friends list to those who are online

        //Get the list views for friends and favorites
        friendsListView = (ListView)findViewById(R.id.friends_list);
        favoritesListView = (ListView)findViewById(R.id.favorites_list);

        friendsAdapter = new ListViewAdapter(this, activeKickbacks);
        favoritesAdapter = new ListViewAdapter(this, activeFavorites);

        friendsListView.setAdapter(friendsAdapter);
        favoritesListView.setAdapter(favoritesAdapter);

        setListViewHeightBasedOnChildren(friendsListView);
        setListViewHeightBasedOnChildren(favoritesListView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kickback_list, menu);

        initSearchHack(menu);

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


    public void setActiveFavorites() {

        for(Friend favorite : favorites){ //For all given favorites
            if(activeKickbacks.contains(favorite)){ //Check if the favorite is online
                activeFavorites.add(favorite); //If online add them to active Favorites
            }
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
