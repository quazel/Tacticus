package com.bramble.kickback.data;

import android.content.SharedPreferences;

public class UserPreferencesLayer {

    private static UserPreferencesLayer instance;

    private SharedPreferences preferences;

    private final String NOTIFICATION_ON_FRIEND_REQUEST = "friend_request_notification";
    private final String NOTICIATION_ON_FRIEND_ACTIVITY = "friend_activity_notification";
    private final String NOTIFIERS = "notifiers";


    private UserPreferencesLayer(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public static void initialize(SharedPreferences preferences) {
        if (instance == null) {
            instance = new UserPreferencesLayer(preferences);
        }
    }

    public static UserPreferencesLayer getInstance() {
        if (instance == null) {
            throw new IllegalStateException("UserPreferencesLayer not initialized!");
        }
        return instance;
    }

    public void setNotificationOnFriendRequest(boolean flag) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(NOTIFICATION_ON_FRIEND_REQUEST, flag);
        editor.apply();
    }

    // 0: Any friends Kickback
    // 1: Favorites Kickback
    // 2: Never
    public void setNotificationOnFriendActivity(int flag) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(NOTICIATION_ON_FRIEND_ACTIVITY, flag);
        editor.apply();
    }

    // Flag values:
    // 0b001: LED blinking
    // 0b010: Vibration
    // 0b100: Sound
    public void setNotifiers(int flag) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(NOTIFIERS, flag);
        editor.apply();
    }

    public boolean getNotificationOnFriendRequest() {
        return preferences.getBoolean(NOTIFICATION_ON_FRIEND_REQUEST, false);
    }

    // 0: Any friends Kickback
    // 1: Favorites Kickback
    // 2: Never
    public int getNoticationOnFriendActivity() {
        return preferences.getInt(NOTICIATION_ON_FRIEND_ACTIVITY, 1);
    }

    // Flag values:
    // 0b001: LED blinking
    // 0b010: Vibration
    // 0b100: Sound
    public int getNotifiers() {
        return preferences.getInt(NOTIFIERS, 0b101);
    }

}
