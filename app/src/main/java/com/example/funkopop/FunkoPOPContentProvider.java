package com.example.funkopop;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class FunkoPOPContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.funkopop";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath("data").build();
    private static final int DATA = 1;

    private static final String DATABASE_NAME = "funkopop.db";
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "FunkoPOP";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "pop_name";
    public static final String COLUMN_NUMBER = "pop_number";
    public static final String COLUMN_TYPE = "pop_type";
    public static final String COLUMN_FANDOM = "fandom";
    public static final String COLUMN_ON = "on";
    public static final String COLUMN_ULTIMATE = "ultimate";
    public static final String COLUMN_PRICE = "price";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_NUMBER + " integer not null, "
            + COLUMN_TYPE + " text not null, "
            + COLUMN_FANDOM + " text not null, "
            + COLUMN_ON + " real not null, "
            + COLUMN_ULTIMATE + " text not null, "
            + COLUMN_PRICE + " real not null)";

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

        sUriMatcher.addURI(AUTHORITY, "data", DATA);
    }


    @Override
    public boolean onCreate() {
        SQLiteOpenHelper dbHelper = new FunkoPOPDatabaseHelper(getContext());
        database.execSQL(DATABASE_CREATE);
        database = dbHelper.getWritableDatabase();
        return (database != null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = database.insert(TABLE_NAME, "", values);
        if (rowID > 0) {
            Uri insertedUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(insertedUri, null);
            return insertedUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        switch (sUriMatcher.match(uri)) {
            case ALL_ITEMS:
                count = database.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case ALL_ITEMS:
                count = database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case SINGLE_ITEM:
                queryBuilder.appendWhere("_ID=" + uri.getLastPathSegment());
                break;
            default:
        }

        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_ITEMS:
                return "vnd.android.cursor.dir/vnd.com.example.funkopop.funkopop";
            case SINGLE_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.funkopop.funkopop";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}