package com.bramble.kickback.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Griffin on 3/8/2015.
 */
public class UserDataTable {
    public static final String TABLE_USER ="user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESSAGE = "default_message";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD_ENCRYPTED = "password_encrypted";
    public static final String COLUMN_SESSION_ID = "session_id";
    public static final String COLUMN_NOTIFY_FRIEND_REQUEST = "notify_friend_request";
    public static final String COLUMN_NOTIFY_FRIEND_ACTIVITY = "notify_friend_activity";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_USER + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_MESSAGE + " text not null, "
            + COLUMN_EMAIL + " text, "
            + COLUMN_PASSWORD_ENCRYPTED + " text, "
            + COLUMN_SESSION_ID + " text, "
            + COLUMN_NOTIFY_FRIEND_REQUEST + " boolean, "
            + COLUMN_NOTIFY_FRIEND_ACTIVITY + " integer, "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //Conceptual work on a consecutive upgrade system
        /*
            if (newVersion - oldVersion > 1) {
                onUpgrade(SQLiteDatabase database, oldVersion, newVersion - 1)
            }
            // continue with upgrade progress once recursive updates have been resolved
         */

        Log.w(UserDataTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(database);
    }

    public static void setNotificationOnFriendRequest(SQLiteDatabase database, boolean flag) {
        String[] projection = {
                UserDataTable.COLUMN_ID,
        };
        Cursor c = database.query(UserDataTable.TABLE_USER,
                projection,
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            int rowID = c.getInt(c.getColumnIndexOrThrow(UserDataTable.COLUMN_ID));
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserDataTable.COLUMN_NOTIFY_FRIEND_REQUEST, flag);
            String selection = UserDataTable.COLUMN_ID + " LIKE ?";
            String[] selectionArgs = { String.valueOf(rowID) };
            int count = database.update(UserDataTable.TABLE_USER,
                    contentValues,
                    selection,
                    selectionArgs);
        }
        c.close();
    }

    // 0: Any friends Kickback
    // 1: Favorites Kickback
    // 2: Never
    public static void setNotificationOnFriendActivity(SQLiteDatabase database, int flag) {
        String[] projection = {
                UserDataTable.COLUMN_ID,
        };
        Cursor c = database.query(UserDataTable.TABLE_USER,
                projection,
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            int rowID = c.getInt(c.getColumnIndexOrThrow(UserDataTable.COLUMN_ID));
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserDataTable.COLUMN_NOTIFY_FRIEND_ACTIVITY, flag);
            String selection = UserDataTable.COLUMN_ID + " LIKE ?";
            String[] selectionArgs = { String.valueOf(rowID) };
            int count = database.update(UserDataTable.TABLE_USER,
                    contentValues,
                    selection,
                    selectionArgs);
        }
        c.close();
    }

}
