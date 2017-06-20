package com.example.olgacoll.sifu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by olgacoll on 19/6/17.
 */

public class DBConfig extends SQLiteOpenHelper {

    String createSQL = "CREATE TABLE settings (active BOOL)";
    String insertSQL = "INSERT INTO settings (active) VALUES (1)";

    public DBConfig(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.createSQL);
        db.execSQL(this.insertSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS settings");
        db.execSQL(this.createSQL);
    }
}