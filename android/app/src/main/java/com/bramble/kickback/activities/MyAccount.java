package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.models.User;
import com.bramble.kickback.util.Util;

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
        phoneNumber.setText(Util.formatPhoneNumber(User.getUser().getPhoneNumber()));
        email.setText(User.getUser().getEmail());
    }

    public void myAccountPhonePressed(View view) {

    }

    public void myAccountEmailPressed(View view) {
        AlertDialog.Builder builder = new   AlertDialog.Builder(MyAccount.this);
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Email");
        alertDialog.setMessage("Your email address is used to log in and  recover your account." +
                               " Please make sure your email is correct before confirming.");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        input.setText(User.getUser().getEmail());
        alertDialog.setView(input);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        // code that checks to see if the email is valid or used by another account
                        // if it passes checks make it the new email and close alert dialog

                    }

                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                    }
                });
        alertDialog.show();
    }

    public void myAccountBackButtonPressed(View view) {
        finish();
    }
}
