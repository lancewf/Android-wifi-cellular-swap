package com.finfrock.phoneswap;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WifiSsidTable{
    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table wifissid "
            + "(_id integer primary key autoincrement, "
            + "name text not null);";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
            int newVersion) {
        Log.w(WifiSsidTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS wifissid");
        onCreate(database);
    }
}
