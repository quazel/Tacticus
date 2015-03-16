package com.bramble.kickback.data;

import android.content.SharedPreferences;

public class UserPreferencesLayer {

    private static UserPreferencesLayer instance;

    private SharedPreferences preferences;

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

}
