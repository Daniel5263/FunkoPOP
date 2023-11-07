package com.example.funkopop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private FunkoPOPDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Uri contentUri = FunkoPOPContentProvider.CONTENT_URI;

    Button buttonInsert;
    EditText editTextPopName;
    EditText editTextPopNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new FunkoPOPDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        buttonInsert = findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int popID = Integer.parseInt(editTextID.getText().toString());
                String popName = editTextPopName.getText().toString();
                int popNumber = Integer.parseInt(editTextPopNumber.getText().toString());

                // Call a method to insert the FunkoPOP element into the database
                insertFunkoPOP(popID, popName, popNumber, popType, fandom, On, ultimate, price);
            }
        });

        Button deleteButton = findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call a method to delete the selected FunkoPOP element
                long id = // Retrieve the selected FunkoPOP item's ID
                        deleteFunkoPOP(id);
            }
        });

        Button updateButton = findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from UI elements (e.g., EditText fields)
                String popName = editTextPopName.getText().toString();
                int popNumber = Integer.parseInt(editTextPopNumber.getText().toString());
                // Retrieve other FunkoPOP data
                long id = // Retrieve the ID of the FunkoPOP item to update

                        // Call a method to update the FunkoPOP element
                        updateFunkoPOP(id, popName, popNumber, /* Other data */);
            }
        });

        Button browseButton = findViewById(R.id.buttonBrowse);
        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call a method to browse FunkoPOP elements
                browseFunkoPOPs();
            }
        });
    }

    public long insertFunkoPOP(String popName, int popNumber, String popType, String fandom, boolean on, String ultimate, double price) {
        ContentValues values = new ContentValues();
        values.put("POP_NAME", popName);
        values.put("POP_NUMBER", popNumber);
        values.put("POP_TYPE", popType);
        values.put("FANDOM", fandom);
        values.put("On", on);
        values.put("ULTIMATE", ultimate);
        values.put("PRICE", price);

        Uri newUri = getContentResolver().insert(contentUri, values);

        return db.insert("FunkoPOP", null, values);
    }

    public int deleteFunkoPOP(long id) {
        Uri deleteUri = ContentUris.withAppendedId(contentUri, id);
        int rowsDeleted = getContentResolver().delete(deleteUri, null, null);

        String selection = "_ID = ?";
        String[] selectionArgs = { String.valueOf(id) };

        return db.delete("FunkoPOP", selection, selectionArgs);
    }

    public int updateFunkoPOP(long id, String popName, int popNumber, String popType, String fandom, boolean on, String ultimate, double price) {
        Uri updateUri = ContentUris.withAppendedId(contentUri, id);
        ContentValues values = new ContentValues();
        values.put("POP_NAME", popName);
        values.put("POP_NUMBER", popNumber);
        values.put("POP_TYPE", popType);
        values.put("FANDOM", fandom);
        values.put("On", on);
        values.put("ULTIMATE", ultimate);
        values.put("PRICE", price);

        int rowsUpdated = getContentResolver().update(updateUri, values, null, null);

        String selection = "_ID = ?";
        String[] selectionArgs = { String.valueOf(id) };

        return db.update("FunkoPOP", values, selection, selectionArgs);
    }

    public Cursor browseFunkoPOPs() {
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);

        String[] projection = {
                "_ID",
                "POP_NAME",
                "POP_NUMBER",
                "POP_TYPE",
                "FANDOM",
                "On",
                "ULTIMATE",
                "PRICE"
        };

        return db.query("FunkoPOP", projection, null, null, null, null, null);
    }


}