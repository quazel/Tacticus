package com.bramble.kickback.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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


    /*
        USER CREDENTIALS
     */

    public void setEmail(SQLiteDatabase database, String email) {
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
            contentValues.put(UserDataTable.COLUMN_EMAIL, email);
            String selection = UserDataTable.COLUMN_ID + " LIKE ?";
            String[] selectionArgs = { String.valueOf(rowID) };
            int count = database.update(UserDataTable.TABLE_USER,
                    contentValues,
                    selection,
                    selectionArgs);
        }
        c.close();
    }

    public void setPasswordEncrypted(SQLiteDatabase database, String passwordEncrypted) {
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
            contentValues.put(UserDataTable.COLUMN_PASSWORD_ENCRYPTED, passwordEncrypted);
            String selection = UserDataTable.COLUMN_ID + " LIKE ?";
            String[] selectionArgs = { String.valueOf(rowID) };
            int count = database.update(UserDataTable.TABLE_USER,
                    contentValues,
                    selection,
                    selectionArgs);
        }
        c.close();
    }

    public void setSessionID(SQLiteDatabase database, String sessionID) {
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
            contentValues.put(UserDataTable.COLUMN_SESSION_ID, sessionID);
            String selection = UserDataTable.COLUMN_ID + " LIKE ?";
            String[] selectionArgs = { String.valueOf(rowID) };
            int count = database.update(UserDataTable.TABLE_USER,
                    contentValues,
                    selection,
                    selectionArgs);
        }
        c.close();
    }

    public String getEmail(SQLiteDatabase database) {
        String[] projection = {
                UserDataTable.COLUMN_EMAIL
        };
        Cursor c = database.query(UserDataTable.TABLE_USER,
                projection,
                null,
                null,
                null,
                null,
                null);
        String toReturn = "";
        c.moveToFirst();
        if (c.getCount() > 0) {
            toReturn = c.getString(c.getColumnIndexOrThrow(UserDataTable.COLUMN_EMAIL));
        }
        c.close();
        return toReturn;
    }

    public String getPasswordEncrypted(SQLiteDatabase database) {
        String[] projection = {
                UserDataTable.COLUMN_PASSWORD_ENCRYPTED
        };
        Cursor c = database.query(UserDataTable.TABLE_USER,
                projection,
                null,
                null,
                null,
                null,
                null);
        String toReturn = "";
        c.moveToFirst();
        if (c.getCount() > 0) {
            toReturn = c.getString(c.getColumnIndexOrThrow(UserDataTable.COLUMN_PASSWORD_ENCRYPTED));
        }
        c.close();
        return toReturn;
    }

    public String getSessionID(SQLiteDatabase database) {
        String[] projection = {
                UserDataTable.COLUMN_SESSION_ID
        };
        Cursor c = database.query(UserDataTable.TABLE_USER,
                projection,
                null,
                null,
                null,
                null,
                null);
        String toReturn = "";
        c.moveToFirst();
        if (c.getCount() > 0) {
            toReturn = c.getString(c.getColumnIndexOrThrow(UserDataTable.COLUMN_SESSION_ID));
        }
        c.close();
        return toReturn;
    }


    /*
        NOTIFICATION SETTINGS
     */

    public void setNotificationOnFriendRequest(SQLiteDatabase database, boolean flag) {
        int writeFlag = flag ? 1 : 0;
        String[] projection = {
                UserDataTable.COLUMN_ID
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
            contentValues.put(UserDataTable.COLUMN_NOTIFY_FRIEND_REQUEST, writeFlag);
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
    public void setNotificationOnFriendActivity(SQLiteDatabase database, int flag) {
        String[] projection = {
                UserDataTable.COLUMN_ID
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

    // Flag values:
    // 0b1: LED blinking
    // 0b10: Vibration
    // 0b100: Sound
    public void setNotifiers(SQLiteDatabase database, int flags) {
        String[] projection = {
                UserDataTable.COLUMN_ID
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
            contentValues.put(UserDataTable.COLUMN_NOTIFIERS, flags);
            String selection = UserDataTable.COLUMN_ID + " LIKE ?";
            String[] selectionArgs = { String.valueOf(rowID) };
            int count = database.update(UserDataTable.TABLE_USER,
                    contentValues,
                    selection,
                    selectionArgs);
        }
        c.close();
    }

    public boolean getNotificationOnFriendRequest(SQLiteDatabase database) {
        String[] projection = {
                UserDataTable.COLUMN_NOTIFY_FRIEND_REQUEST
        };
        Cursor c = database.query(UserDataTable.TABLE_USER,
                projection,
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        boolean flag = false;
        if (c.getCount() > 0) {
            flag = c.getInt(c.getColumnIndexOrThrow(UserDataTable.COLUMN_NOTIFY_FRIEND_REQUEST)) > 0;
        }
        c.close();
        return flag;
    }

    public int getNotificationOnFriendActivity(SQLiteDatabase database) {
        String[] projection = {
                UserDataTable.COLUMN_NOTIFY_FRIEND_ACTIVITY
        };
        Cursor c = database.query(UserDataTable.TABLE_USER,
                projection,
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        int flag = 0;
        if (c.getCount() > 0) {
            flag = c.getInt(c.getColumnIndexOrThrow(UserDataTable.COLUMN_NOTIFY_FRIEND_ACTIVITY));
        }
        c.close();
        return flag;
    }

    public int getNotifiers(SQLiteDatabase database) {
        String[] projection = {
                UserDataTable.COLUMN_NOTIFIERS
        };
        Cursor c = database.query(UserDataTable.TABLE_USER,
                projection,
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        int flag = 0;
        if (c.getCount() > 0) {
            flag = c.getInt(c.getColumnIndexOrThrow(UserDataTable.COLUMN_NOTIFIERS));
        }
        c.close();
        return flag;
    }

}
