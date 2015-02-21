package com.bramble.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bramble.kickback.R;

public class AddFriendsSearch extends Activity {

    private EditText phoneNumberEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_search);

        phoneNumberEdittext = (EditText) findViewById(R.id.search_edittext);
    }

    public void searchBackButtonPressed(View view) {
        Intent intent = new Intent(AddFriendsSearch.this, Main.class);
        startActivity(intent);
        finish();
    }

    public void searchPhoneNumberPressed(View view) {

    }
}
