package com.bramble.kickback.receivers;

import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.service.KickbackService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, KickbackService.class);
        context.startService(myIntent);
        new PingTask().execute(context);
    }

    public boolean pingServer(SharedPreferences prefs) {
        try {
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

    public boolean login(SharedPreferences prefs) {
        try {
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

    private class PingTask extends AsyncTask<Context, Void, Void> {

        @Override
        protected Void doInBackground(Context... params) {
            SharedPreferences prefs = params[0].getSharedPreferences("prefs", Context.MODE_PRIVATE);
            if (!pingServer(prefs)) {
                if(!login(prefs)) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.apply();
                }
            }
            return null;
        }

    }

}
