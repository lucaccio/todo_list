package com.example.lukac.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lukac on 01/11/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todo_app_example1.db";
    public static final String DATABASE_TABLE = "todo";
    private static final String CREATE_DB =   "CREATE TABLE " + DATABASE_TABLE + " (" +
            "`id`	INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`title`    TEXT," +
            "`phone`    TEXT," +
            "`notes` TEXT," +
            "`date`	TEXT "  + ")" ;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME ,null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }


}
