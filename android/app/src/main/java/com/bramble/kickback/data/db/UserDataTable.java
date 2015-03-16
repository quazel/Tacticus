package com.bramble.kickback.data.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Griffin on 3/8/2015.
 */
public class UserDataTable {
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESSAGE = "default_message";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD_ENCRYPTED = "password_encrypted";
    public static final String COLUMN_SESSION_ID = "session_id";
    public static final String COLUMN_NOTIFY_FRIEND_REQUEST = "notify_friend_request";
    public static final String COLUMN_NOTIFY_FRIEND_ACTIVITY = "notify_friend_activity";
    public static final String COLUMN_NOTIFIERS = "notifiers";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_USER + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_MESSAGE + " text not null, "
            + COLUMN_EMAIL + " text, "
            + COLUMN_PASSWORD_ENCRYPTED + " text, "
            + COLUMN_SESSION_ID + " text, "
            + COLUMN_NOTIFY_FRIEND_REQUEST + " integer, "
            + COLUMN_NOTIFY_FRIEND_ACTIVITY + " integer, "
            + COLUMN_NOTIFIERS + " integer"
            + ");";

    private static final String DATABASE_INSERT_DEFAULT_ROW = "insert into "
            + TABLE_USER + "("
            + COLUMN_MESSAGE + ", "
            + COLUMN_EMAIL + ", "
            + COLUMN_PASSWORD_ENCRYPTED + ", "
            + COLUMN_SESSION_ID + ", "
            + COLUMN_NOTIFY_FRIEND_REQUEST + ", "
            + COLUMN_NOTIFY_FRIEND_ACTIVITY + ", "
            + COLUMN_NOTIFIERS
            + ")"
            + " values (\"\", \"\", \"\", \"\", 1, 1, 5)";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_INSERT_DEFAULT_ROW);
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

}
