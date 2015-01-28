package com.bramble.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bramble.kickback.R;

public class AccountPortal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_portal);
    }

    // LOGIN PATH
    // when the login button is pressed
    public void toSignInPressed(View v){
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    // SIGN UP PATH
    // when the sign up button is pressed
    public void toSignUpPressed(View v){
        Intent intent = new Intent(this, SignUpCredentials.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
