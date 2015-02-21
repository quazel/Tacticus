package com.bramble.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bramble.kickback.R;

public class AddFriendsSearch extends Activity {

    private EditText phoneNumberEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_search);

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
