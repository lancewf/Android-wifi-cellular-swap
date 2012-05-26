package com.finfrock.phoneswap;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WifiSsidDbAdapter
{
 // Database fields
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    private static final String DB_TABLE = "wifissid";
    private Context context;
    private SQLiteDatabase db;
    private WifiSsidDatabaseHelper dbHelper;

    public WifiSsidDbAdapter(Context context) {
        this.context = context;
    }

    public WifiSsidDbAdapter open() throws SQLException {
        dbHelper = new WifiSsidDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long createWifiSsid(String name) {
        ContentValues values = createContentValues(name);

        return db.insert(DB_TABLE, null, values);
    }

    public boolean updateWifiSsid(long rowId, String name) {
        ContentValues values = createContentValues(name);

        return db.update(DB_TABLE, values, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean deleteWifiSsid(long rowId) {
        return db.delete(DB_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllWifiSsids() {
        return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_NAME }, null, null, null, null, null);
    }
    
    public List<String> getAllWifiSsids(){
        List<String> wifiSsids = new ArrayList<String>();
        Cursor cursor = fetchAllWifiSsids();
        
        if (cursor != null && cursor.moveToFirst()) {
            do{
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
                wifiSsids.add(name);
            }while(cursor.moveToNext());
            
            cursor.close();
        }
        return wifiSsids;
    }

    public Cursor fetchWifiSsid(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, DB_TABLE, new String[] { KEY_ROWID,
                KEY_NAME }, KEY_ROWID + "="
                + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    private ContentValues createContentValues(String name) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        return values;
    }
}
