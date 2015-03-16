package com.bramble.kickback.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.bramble.kickback.data.UserDatabaseHelper;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.networking.ResponseDeserializer;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Fabric.with(context, new Crashlytics());
        new PingTask().execute(context);
    }

    public boolean pingServer(UserDatabaseHelper databaseHelper) {
        try {
            SQLiteDatabase database = databaseHelper.getReadableDatabase();
            String session = databaseHelper.getSessionID(database);
            String result = new ConnectionHandler().ping(session);
            if (result.startsWith("200:")) {
                result = result.replace("200:", "");
                User.getUser().setSessionId(result);
                return true;
            }
            else {
                User.getUser().setSessionId("");
                return false;
            }
        } catch(IOException e) {
            return false;
        }
    }

    public boolean login(UserDatabaseHelper databaseHelper) {
        try {
            SQLiteDatabase database = databaseHelper.getReadableDatabase();
            String email = databaseHelper.getEmail(database);
            String password = databaseHelper.getPasswordEncrypted(database);
            String result = new ConnectionHandler().login(email, password);
            if (result.startsWith("200:")) {
                result = result.replace("200:", "");
                JSONObject resultJSON = new JSONObject(result);
                ResponseDeserializer.deserializeLogin(result);
                return true;
            }
            else {
                return false;
            }
        } catch (IOException e) {
            return false;
        } catch (JSONException e) {
            return false;
        }
    }

    private class PingTask extends AsyncTask<Context, Void, Void> {

        @Override
        protected Void doInBackground(Context... params) {
            UserDatabaseHelper databaseHelper = new UserDatabaseHelper(params[0]);
            if (!pingServer(databaseHelper)) {
                if(!login(databaseHelper)) {
                    SQLiteDatabase database = databaseHelper.getWritableDatabase();
                    databaseHelper.setEmail(database, "");
                    databaseHelper.setPasswordEncrypted(database, "");
                    databaseHelper.setSessionID(database, "");
                }
            }
            return null;
        }

    }

}
