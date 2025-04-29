package com.project.financ.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqlite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "financ_base.db";
    private static final int DATABASE_VERSION = 1;

    public Sqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE usuario_tk (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user TEXT, " +
                "token TEXT, " + "token_refresh TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario_tk");
        onCreate(db);
    }


}
