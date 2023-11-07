package com.example.funkopop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FunkoPOPDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FunkoPOPDatabase";
    private static final int DATABASE_VERSION = 1;
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_FUNKOPOP_TABLE = "CREATE TABLE FunkoPOP (" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "POP_NAME TEXT NOT NULL, " +
                "POP_NUMBER INTEGER NOT NULL, " +
                "POP_TYPE TEXT, " +
                "FANDOM TEXT, " +
                "ON BOOLEAN, " +
                "ULTIMATE TEXT, " +
                "PRICE REAL);";

        db.execSQL(SQL_CREATE_FUNKOPOP_TABLE);    }
    public FunkoPOPDatabaseHelper(Context context) {
        super(context, "FunkoPOP.db", null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

