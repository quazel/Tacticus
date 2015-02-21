package com.bramble.kickback.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.adapters.AddFriendSearchResultsAdapter;
import com.bramble.kickback.models.RemoteUser;

import java.util.ArrayList;

public class AddFriendsSearch extends Activity {

    private EditText phoneNumberEdittext;
    private ListView resultsList;
    private AddFriendSearchResultsAdapter resultsAdapter;
    private ArrayList<RemoteUser> remoteUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_search);

        resultsList = (ListView) findViewById(R.id.search_results_list);
        resultsAdapter = new AddFriendSearchResultsAdapter(this, remoteUsers);
        resultsList.setAdapter(resultsAdapter);

        phoneNumberEdittext = (EditText) findViewById(R.id.search_edittext);
        phoneNumberEdittext.requestFocus();
        phoneNumberEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //search();
                }
                return false;
            }
        });
    }

    public void searchBackButtonPressed(View view) {
        finish();
    }

    public void searchPhoneNumberPressed(View view) {
        //search();
    }

}
