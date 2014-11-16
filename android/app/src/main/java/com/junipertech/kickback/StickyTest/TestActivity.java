package com.junipertech.kickback.StickyTest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.junipertech.kickback.R;
import com.junipertech.kickback.activities.AddFriend;
import com.junipertech.kickback.models.Friend;
import com.junipertech.kickback.util.Globals;

import java.util.ArrayList;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class TestActivity extends Activity{

    ArrayList<Friend> friends = Globals.friends;

    StickyListHeadersListView stickyList;

    StickyAdapter stickyAdapterThing; //TODO

    EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticky_test);
        getActionBar().setDisplayHomeAsUpEnabled(true);

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
