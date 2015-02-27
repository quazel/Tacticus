package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;

import java.io.IOException;

public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void myAccountButtonPressed(View view) {
        Intent intent = new Intent(Settings.this, MyAccount.class);
        startActivity(intent);
    }

    public void notificationSettingsButtonPressed(View view) {
        Intent intent = new Intent(Settings.this, NotificationSettings.class);
        startActivity(intent);
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
                        new LogoutTask().execute();
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

    private class LogoutTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                String response = new ConnectionHandler().logout(User.getUser().getSessionId());
                return response.startsWith("200:") || response.startsWith("400:");
            }
            catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                User.getUser().clearUser();
                Intent intent = new Intent(Settings.this, Main.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(Settings.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

