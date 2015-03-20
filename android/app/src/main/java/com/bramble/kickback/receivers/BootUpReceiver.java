package com.bramble.kickback.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.bramble.kickback.data.db.UserDataLayer;
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

    public boolean pingServer(String sessionID) {
        try {
            String result = new ConnectionHandler().ping(sessionID);
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

    public boolean login(String email, String password) {
        try {
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
            UserDataLayer dataLayer = UserDataLayer.getInstance();
            String sessionID = dataLayer.getSessionID();
            if (!pingServer(sessionID)) {
                String email = dataLayer.getEmail();
                String password = dataLayer.getPassword();
                if(!login(email, password)) {
                    dataLayer.startWriting();
                    dataLayer.writeEmail("");
                    dataLayer.writePassword("");
                    dataLayer.writeSessionID("");
                    dataLayer.writeChanges();
                }
            }
            return null;
        }

    }

}
