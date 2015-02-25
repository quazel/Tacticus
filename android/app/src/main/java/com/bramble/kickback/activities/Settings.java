package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.bramble.kickback.R;

public class Settings extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void settingsBackButtonPressed(View view) {
        finish();
    }

    public void userLogoutPressed(View view) {
        AlertDialog.Builder builder = new   AlertDialog.Builder(Settings.this);
        AlertDialog alertDialog = builder.create();
        alertDialog.setMessage("Are you sure you wish to logout?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        Intent intent = new Intent(Settings.this, Main.class);
                        intent.putExtra("finish", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        startActivity(intent);
                        finish();
                    }

                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                    }
                });
        alertDialog.show();
    }
}

