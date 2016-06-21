package com.example.fingerprintdbmaker;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	static final String DB = "/storage/emulated/0/"  + "point11.db";
    static final int DB_VERSION = 1;
    static final String CREATE_TABLE = "create table mytable (_id integer primary key autoincrement, " + "bssid not null, level integer not null)";
    static final String DROP_TABLE = "drop table mytable;";
  
    public MySQLiteOpenHelper(Context c) {
        super(c, DB, null, DB_VERSION);
    }
    
    public void onCreate(SQLiteDatabase db) {
    	System.out.println("maked table");
        db.execSQL(CREATE_TABLE);
    }
    
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

}
 