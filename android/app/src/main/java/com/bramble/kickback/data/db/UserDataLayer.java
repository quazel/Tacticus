package com.bramble.kickback.data.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bramble.kickback.KickbackApplication;

public class UserDataLayer {

    private static UserDataLayer instance;

    private SharedPreferences mSharedPreferences;

    private UserDataLayer(Context context) {
        mSharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    public static UserDataLayer getInstance() {
        if (instance == null) {
            Log.d("UserDataLayer", "Not yet initialized! Attempting to use application context!");
            if (KickbackApplication.getInstance() == null) {
                Log.d("UserDataLayer", "Global application has not been initialized. Will not initialize.");
            }
            else {
                initialize(KickbackApplication.getInstance());
            }
        }
        return instance;

    }

    public static void initialize(Context context) {
        instance = new UserDataLayer(context);
    }

}
