package com.bramble.kickback.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.bramble.kickback.R;

public class NotificationSettings extends Activity {

    private CheckBox notifyFriends;
    private CheckBox notifyFavorites;
    private CheckBox notifyNever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        notifyFriends = (CheckBox) findViewById(R.id.notify_friends_checkbox);
        notifyFavorites = (CheckBox) findViewById(R.id.notify_favorites_checkbox);
        notifyNever = (CheckBox) findViewById(R.id.notify_never_checkbox);


    }

    public void notifyFriendsPressed(View view) {
        notifyFriends.setChecked(true);
        notifyFavorites.setChecked(false);
        notifyNever.setChecked(false);
    }

    public void notifyFavoritesPressed(View view) {
        notifyFriends.setChecked(false);
        notifyFavorites.setChecked(true);
        notifyNever.setChecked(false);
    }

    public void notifyNeverPressed(View view) {
        notifyFriends.setChecked(false);
        notifyFavorites.setChecked(false);
        notifyNever.setChecked(true);
    }

    public void notificationSettingsBackButtonPressed(View view) {
        finish();
    }
}
