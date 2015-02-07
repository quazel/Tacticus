package com.bramble.kickback.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class KickbackService extends Service {

    public class LocalBinder extends Binder {
        public KickbackService getService() { return KickbackService.this; }
    }

    private final IBinder mBinder = new LocalBinder();


    @Override
    public void onCreate() {
        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "");
        String password = prefs.getString("password", "");
        if (!pingServer()) {
            login();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public boolean pingServer() {
        try {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            String session = prefs.getString("session", "");
            String result = new ConnectionHandler().ping(session);
            if (result.startsWith("200:")) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("session", result.replace("200:", ""));
                editor.apply();
                return true;
            }
            else {
                return false;
            }
        } catch(IOException e) {
            return false;
        }
    }

    public boolean login() {
        try {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            String email = prefs.getString("email", "");
            String password = prefs.getString("password", "");
            String result = new ConnectionHandler().login(email, password);
            JSONObject resultJSON = new JSONObject(result);
            User user = User.getUser();
            user.setEmail(resultJSON.getString("email"));
            user.setName(resultJSON.getString("name"));
            user.setNickname(resultJSON.getString("nickname"));
            user.setPhoneNumber("phone_number");
            user.setSessionId(resultJSON.getString("session_id"));
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("session", resultJSON.getString("session_id"));
            editor.apply();
            return true;
        } catch (IOException e) {
            return false;
        } catch (JSONException e) {
            return false;
        }
    }

}
