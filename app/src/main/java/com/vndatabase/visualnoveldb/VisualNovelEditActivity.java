package com.vndatabase.visualnoveldb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class VisualNovelEditActivity extends AppCompatActivity {

    VisualNovelDBHelper helper;
    SQLiteDatabase db;
    EditText editVisualNovel;
    RatingBar ratingBar;
    EditText editBestGirl;
    EditText editThoughts;

    int dataID;
    String name;
    double rating;
    String girl;
    String thoughts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_novel_edit);

        Intent intentEdit = getIntent();
        dataID = intentEdit.getIntExtra("dataID", -1);
        helper = new VisualNovelDBHelper(this);
        db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM VISUAL_NOVEL WHERE _id = " + dataID, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            name = cursor.getString(cursor.getColumnIndex("NAME"));
            rating = cursor.getDouble(cursor.getColumnIndex("RATING"));
            girl = cursor.getString(cursor.getColumnIndex("GIRL"));
            thoughts = cursor.getString(cursor.getColumnIndex("THOUGHTS"));
            cursor.moveToNext();
        }

        editVisualNovel = (EditText) findViewById(R.id.editVisualNovel);
        editVisualNovel.setText(name);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        float f = (float) rating;
        ratingBar.setRating(f);
        editBestGirl = (EditText) findViewById(R.id.editBestGirl);
        editBestGirl.setText(girl);
        editThoughts = (EditText) findViewById(R.id.editThoughts);
        editThoughts.setText(thoughts);
    }

    public void onClickEdit(View view) {
        helper = new VisualNovelDBHelper(this);
        db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        editVisualNovel = (EditText) findViewById(R.id.editVisualNovel);
        String editedVisualNovel = editVisualNovel.getText().toString();
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        editBestGirl = (EditText) findViewById(R.id.editBestGirl);
        String editedGirl = editBestGirl.getText().toString();
        editThoughts = (EditText) findViewById(R.id.editThoughts);
        String editedThoughts = editThoughts.getText().toString();

        cv.put(VisualNovelDBHelper.NAME_COL, editedVisualNovel);
        cv.put(VisualNovelDBHelper.RATING_COL, ratingBar.getRating());
        cv.put(VisualNovelDBHelper.GIRL_COL, editedGirl);
        cv.put(VisualNovelDBHelper.THOUGHTS_COL, editedThoughts);
        String whereClause = "_id = ?";
        db.update(VisualNovelDBHelper.DB_NAME, cv, whereClause, new String[]{String.valueOf(dataID)});

        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);

        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, VisualNovelListActivity.class));
    }
}

