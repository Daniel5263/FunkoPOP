package com.example.funkopop;

import android.net.Uri;
import android.provider.BaseColumns;

public class FunkoPOPContract {
    public static final String AUTHORITY = "com.example.funkopop";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String FUNKOPOP_PATH = "funkopops";
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(FUNKOPOP_PATH).build();

    public static final class FunkoPOPEntry implements BaseColumns {
        public static final String TABLE_NAME = "funkopop";
        public static final String COLUMN_POP_NAME = "POP_NAME";
        public static final String COLUMN_POP_NUMBER = "POP_NUMBER";
        public static final String COLUMN_POP_TYPE = "POP_TYPE";
        public static final String COLUMN_FANDOM = "FANDOM";
        public static final String COLUMN_ON = "On";
        public static final String COLUMN_ULTIMATE = "ULTIMATE";
        public static final String COLUMN_PRICE = "PRICE";
    }
}