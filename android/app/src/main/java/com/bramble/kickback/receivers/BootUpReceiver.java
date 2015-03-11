package com.bramble.kickback.receivers;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.bramble.kickback.data.UserDataTable;
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

    public String pingServer(String session) {
        try {
            String result = new ConnectionHandler().ping(session);
            if (result.startsWith("200:")) {
                return result.replace("200:", "");
            }
            else {
                return null;
            }
        } catch(IOException e) {
            return null;
        }
    }

    public boolean login(String email, String password) {
        try {
            String result = new ConnectionHandler().login(email, password);
            if (result.startsWith("200:")) {
                result = result.replace("200:", "");
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
            UserDatabaseHelper helper = new UserDatabaseHelper(params[0]);
            SQLiteDatabase db = helper.getReadableDatabase();
            String[] projection = {
                    UserDataTable.COLUMN_ID,
                    //UserDataTable.COLUMN_EMAIL,
                    //UserDataTable.COLUMN_PASSWORD_ENCRYPTED,
                    //UserDataTable.COLUMN_SESSION_ID
            };
            Cursor c = db.query(UserDataTable.TABLE_USER,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                int rowID = c.getInt(c.getColumnIndexOrThrow(UserDataTable.COLUMN_ID));
                String email = c.getString(c.getColumnIndexOrThrow(UserDataTable.COLUMN_EMAIL));
                String password_encrypted = c.getString(c.getColumnIndexOrThrow(UserDataTable.COLUMN_PASSWORD_ENCRYPTED));
                String session = c.getString(c.getColumnIndexOrThrow(UserDataTable.COLUMN_SESSION_ID));
                String result = pingServer(session);
                if (result == null) {
                    if (!login(email, password_encrypted)) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(UserDataTable.COLUMN_EMAIL, "");
                        contentValues.put(UserDataTable.COLUMN_PASSWORD_ENCRYPTED, "");
                        contentValues.put(UserDataTable.COLUMN_SESSION_ID, "");
                        String selection = UserDataTable.COLUMN_ID + " LIKE ?";
                        String[] selectionArgs = { String.valueOf(rowID) };
                        int count = db.update(UserDataTable.TABLE_USER,
                                contentValues,
                                selection,
                                selectionArgs);
                    }
                }
            }
            c.close();
            return null;
        }

    }

}
