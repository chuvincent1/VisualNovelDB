package com.vndatabase.visualnoveldb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

public class VisualNovelDetailsActivity extends AppCompatActivity {

    VisualNovelDBHelper helper;
    SQLiteDatabase db;
    TextView textName;
    RatingBar ratingBar;
    TextView textGirl;
    TextView textThoughts;

    int dataID;
    String name;
    double rating;
    String girl = "";
    String thoughts;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit:
                Intent intentEdit = new Intent(VisualNovelDetailsActivity.this, VisualNovelEditActivity.class);
                intentEdit.putExtra("dataID", dataID);
                startActivity(intentEdit);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_novel_details);

        Intent intentDetails = getIntent();
        dataID = intentDetails.getIntExtra("dataID", -1);
        helper = new VisualNovelDBHelper(this);
        db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM VISUAL_NOVEL WHERE _id = " + dataID, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            name = cursor.getString(cursor.getColumnIndex("NAME"));
            rating = cursor.getDouble(cursor.getColumnIndex("RATING"));
            girl = cursor.getString(cursor.getColumnIndex("GIRL"));
            thoughts = cursor.getString(cursor.getColumnIndex("THOUGHTS"));
            cursor.moveToNext();
        }

        textName = (TextView) findViewById(R.id.textName);
        textName.setText(name);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        float f = (float) rating;
        ratingBar.setRating(f);
        textGirl = (TextView) findViewById(R.id.textGirl);
        textGirl.setText(girl);
        textThoughts = (TextView) findViewById(R.id.textThoughts);
        textThoughts.setText(thoughts);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, VisualNovelListActivity.class));
    }
}
