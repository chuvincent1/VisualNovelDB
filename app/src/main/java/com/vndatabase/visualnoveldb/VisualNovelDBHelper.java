package com.vndatabase.visualnoveldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VisualNovelDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "VISUAL_NOVEL";
    public static final int DB_VERSION = 1;
    public static final String NAME_COL = "NAME";
    public static final String RATING_COL = "RATING";
    public static final String GIRL_COL = "GIRL";
    public static final String THOUGHTS_COL = "THOUGHTS";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + DB_NAME +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NAME_COL + " TEXT, " +
                        RATING_COL + " DECIMAL(2,1), " +
                        GIRL_COL + " TEXT, " +
                        THOUGHTS_COL + " TEXT " + ");"
        );

        //insertVisualNovel(db, "Visual Novel Name", 1, "Best Girl", "And here are some of my thoughts.");
        //insertVisualNovel(db, "Apples and Pie", 3, "Best Girl", "And here are some of my thoughts.");
        //insertVisualNovel(db, "Bob the Builder", 2, "Best Girl", "And here are some of my thoughts.");
        //insertVisualNovel(db, "Cats and Dogs", 4, "Best Girl", "And here are some of my thoughts.");
        //insertVisualNovel(db, "Songs of Fire and Ice", 5, "Best Girl", "And here are some of my thoughts.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public VisualNovelDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void insertVisualNovel(SQLiteDatabase db, String name, double rating, String girl, String thoughts) {
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(RATING_COL, rating);
        values.put(GIRL_COL, girl);
        values.put(THOUGHTS_COL, thoughts);
        db.insert(DB_NAME, null, values);
    }

    public void updateVisualNovel(SQLiteDatabase db, int id, String name, double rating, String girl, String thoughts) {
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put(NAME_COL, name);
        values.put(RATING_COL, rating);
        values.put(GIRL_COL, girl);
        values.put(THOUGHTS_COL, thoughts);
        db.insert(DB_NAME, null, values);
    }

    private SQLiteDatabase db;
    private VisualNovelDBHelper dbHelper;

    public long insertVisualNovel(String name, double rating, String girl, String thoughts) {
        ContentValues cv = new ContentValues();
        cv.put(NAME_COL, name);
        cv.put(RATING_COL, rating);
        cv.put(GIRL_COL, girl);
        cv.put(THOUGHTS_COL, thoughts);
        this.openWritableDB();
        long rowID = db.insert(DB_NAME,  null, cv);
        this.closeDB();

        return rowID;
    }
    public void deleteVisualNovel(int id) {
        db.delete(DB_NAME, "_id = " + id, new String[] {String.valueOf(id)});
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null) {
            db.close();
        }
    }
}
