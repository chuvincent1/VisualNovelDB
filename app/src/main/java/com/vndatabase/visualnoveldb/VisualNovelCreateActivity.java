package com.vndatabase.visualnoveldb;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class VisualNovelCreateActivity extends AppCompatActivity {

    EditText editVisualNovel;
    RatingBar ratingBar;
    EditText editBestGirl;
    EditText editThoughts;
    VisualNovelDBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_novel_create);
    }

    public void onClickAdd(View view) {
        editVisualNovel = (EditText) findViewById(R.id.editVisualNovel);
        editBestGirl = (EditText) findViewById(R.id.editBestGirl);
        editThoughts = (EditText) findViewById(R.id.editThoughts);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        String name = editVisualNovel.getText().toString();
        String girl = editBestGirl.getText().toString();
        String thoughts = editThoughts.getText().toString();

        helper = new VisualNovelDBHelper(this);
        db = helper.getReadableDatabase();
        helper.insertVisualNovel(db, name, ratingBar.getRating(), girl, thoughts);

        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, VisualNovelListActivity.class));
    }
}
