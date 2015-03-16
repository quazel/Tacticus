package com.bramble.kickback.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Griffin on 3/8/2015.
 */
public class UserDataTable {
    public static final String TABLE_USER ="user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESSAGE ="Let's Kickback";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_USER + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_MESSAGE
            + " text not null);";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(UserDataTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(database);
    }
}
