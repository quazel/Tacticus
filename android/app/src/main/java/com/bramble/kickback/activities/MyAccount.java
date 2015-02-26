package com.bramble.kickback.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.models.User;

public class MyAccount extends Activity {

    private TextView nickname;
    private TextView phoneNumber;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nickname = (TextView) findViewById(R.id.my_account_nickname_textview);
        phoneNumber = (TextView) findViewById(R.id.my_account_phone_textview);
        email = (TextView) findViewById(R.id.my_account_email_textview);

        nickname.setText(User.getUser().getNickname());
        phoneNumber.setText(User.getUser().getPhoneNumber());
        email.setText(User.getUser().getEmail());
    }

    public void myAccountBackButtonPressed(View view) {
        finish();
    }
}
