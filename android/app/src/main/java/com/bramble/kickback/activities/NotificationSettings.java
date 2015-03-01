package com.bramble.kickback.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.bramble.kickback.R;

public class NotificationSettings extends Activity {

    private CheckBox friendRequests;

    private CheckBox notifyFriends;
    private CheckBox notifyFavorites;
    private CheckBox notifyNever;

    private CheckBox ledBlink;
    private CheckBox vibrate;
    private CheckBox sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        friendRequests = (CheckBox) findViewById(R.id.friend_request_checkbox);

        notifyFriends = (CheckBox) findViewById(R.id.notify_friends_checkbox);
        notifyFavorites = (CheckBox) findViewById(R.id.notify_favorites_checkbox);
        notifyNever = (CheckBox) findViewById(R.id.notify_never_checkbox);

        ledBlink = (CheckBox) findViewById(R.id.notify_led_blinking_checkbox);
        vibrate = (CheckBox) findViewById(R.id.notify_vibration_checkbox);
        sound = (CheckBox) findViewById(R.id.notify_sound_checkbox);

        // temporary setting correct checkboxes checked
        //default settings represented below
        friendRequests.setChecked(true);
        notifyFriends.setChecked(true);
        ledBlink.setChecked(true);
        vibrate.setChecked(true);
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
