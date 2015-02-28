package com.bramble.kickback.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.bramble.kickback.R;
import com.bramble.kickback.contacts.ContactLayer;

public class AddFriendsContacts extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_contacts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ContactLayer.initialize(getContentResolver());
    }

    public void contactsBackButtonPressed(View view) {
        finish();
    }
}
