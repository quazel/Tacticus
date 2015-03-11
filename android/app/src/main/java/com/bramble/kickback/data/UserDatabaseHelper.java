package com.bramble.kickback.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Griffin on 3/8/2015.
 */
public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="usertable.db";
    public static final int DATABASE_VERSION = 1;

    public UserDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Method called on first creation of database
    @Override
    public void onCreate(SQLiteDatabase db) {
        UserDataTable.onCreate(db);
    }

    //method called when database is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        UserDataTable.onUpgrade(db, oldVersion, newVersion);
    }

}
