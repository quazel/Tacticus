package com.bramble.kickback.activities;

import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
        startActivity(intent);
        finish();
    }

    // SIGN UP PATH
    // when the sign up button is pressed
    public void toSignUpPressed(View v){
        Intent intent = new Intent(this, SignUpCredentials.class);
        startActivity(intent);
        finish();
    }

    /*
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you wish to exit without logging in or signing up?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AccountPortal.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    */
}
