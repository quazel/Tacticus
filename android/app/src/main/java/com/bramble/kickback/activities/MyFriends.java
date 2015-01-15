package com.bramble.kickback.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.bramble.kickback.R;
import com.bramble.kickback.adapter.StickyAdapter;
import com.bramble.kickback.models.Friend;
import com.bramble.kickback.util.Globals;

import java.util.ArrayList;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MyFriends extends Activity{

    ArrayList<Friend> friends = Globals.friends;

    StickyListHeadersListView stickyList;

    StickyAdapter stickyAdapterThing;

    EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.friends_color)));


        if(friends.size() == 0) {
            Globals.initFriends();
        }

        stickyList = (StickyListHeadersListView)findViewById(R.id.list);

        stickyAdapterThing = new StickyAdapter(this,friends);

        stickyList.setAdapter(stickyAdapterThing);

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

            default:
                return super.onOptionsItemSelected(item);
        }
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
                stickyAdapterThing.filter(text);

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