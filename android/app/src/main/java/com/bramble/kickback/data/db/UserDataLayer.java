package com.bramble.kickback.data.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bramble.kickback.KickbackApplication;

public class UserDataLayer {

    private static UserDataLayer instance;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private boolean isWriting;

    private final String EMAIL_KEY = "email";
    private final String PASSWORD_KEY = "password";
    private final String SESSION_ID_KEY = "session_id";

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

    @SuppressLint("CommitPrefEdits")
    public void startWriting () {
        isWriting = true;
        mEditor = mSharedPreferences.edit();
    }

    public void writeChanges() {
        isWriting = false;
        mEditor.apply();
    }

    private void assertWriting() {
        if (!isWriting) {
            throw new IllegalStateException("UserDataLayer is not writing!");
        }
    }

    public void writeEmail(String email) {
        assertWriting();
        mEditor.putString(EMAIL_KEY, email);
    }

    public void writePassword(String password) {
        assertWriting();
        mEditor.putString(PASSWORD_KEY, password);
    }

    public void writeSessionID(String sessionID) {
        assertWriting();
        mEditor.putString(SESSION_ID_KEY, sessionID);
    }

    public String getEmail() {
        return mSharedPreferences.getString(EMAIL_KEY, "");
    }

    public String getPassword() {
        return mSharedPreferences.getString(PASSWORD_KEY, "");
    }

    public String getSessionID() {
        return mSharedPreferences.getString(SESSION_ID_KEY, "");
    }

}
