package com.bramble.kickback.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bramble.kickback.R;

public class AddFriendsRequests extends Activity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_requests);
    }

    public void requestsBackButtonPressed(View view) {
        finish();
    }
}
