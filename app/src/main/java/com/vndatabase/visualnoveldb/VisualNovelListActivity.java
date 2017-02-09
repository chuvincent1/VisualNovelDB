package com.vndatabase.visualnoveldb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class VisualNovelListActivity extends AppCompatActivity {

    ListView listViewDB;
    public CursorAdapter cursorAdapter;
    VisualNovelDBHelper helper;
    SQLiteDatabase db;
    Toast t;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add:
                Intent intentCreate = new Intent(VisualNovelListActivity.this, VisualNovelCreateActivity.class);
                startActivity(intentCreate);
                return true;
            case R.id.item_sort_title:
                helper = new VisualNovelDBHelper(this);
                try {
                    db = helper.getReadableDatabase();
                    Cursor cursorTitle = db.query(
                            VisualNovelDBHelper.DB_NAME,
                            new String[]{"_id", VisualNovelDBHelper.NAME_COL, VisualNovelDBHelper.RATING_COL, VisualNovelDBHelper.GIRL_COL, VisualNovelDBHelper.THOUGHTS_COL},
                            null,
                            null,
                            null, null, VisualNovelDBHelper.NAME_COL +" ASC", null
                    );
                    cursorAdapter = new SimpleCursorAdapter(
                            this,
                            android.R.layout.simple_list_item_2,
                            cursorTitle,
                            new String[]{VisualNovelDBHelper.NAME_COL, VisualNovelDBHelper.RATING_COL, VisualNovelDBHelper.GIRL_COL, VisualNovelDBHelper.THOUGHTS_COL},
                            new int[]{android.R.id.text2, android.R.id.text1},
                            0
                    );
                    cursorAdapter.notifyDataSetChanged();
                    listViewDB.setAdapter(cursorAdapter);
                } catch (Exception e) {
                    t = Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG);
                    t.show();
                }
                return true;
            case R.id.item_sort_rating:
                helper = new VisualNovelDBHelper(this);
                try {
                    db = helper.getReadableDatabase();
                    Cursor cursorRating = db.query(
                            VisualNovelDBHelper.DB_NAME,
                            new String[]{"_id", VisualNovelDBHelper.NAME_COL, VisualNovelDBHelper.RATING_COL, VisualNovelDBHelper.GIRL_COL, VisualNovelDBHelper.THOUGHTS_COL},
                            null,
                            null,
                            null, null, VisualNovelDBHelper.RATING_COL +" DESC", null
                    );
                    cursorAdapter = new SimpleCursorAdapter(
                            this,
                            android.R.layout.simple_list_item_2,
                            cursorRating,
                            new String[]{VisualNovelDBHelper.NAME_COL, VisualNovelDBHelper.RATING_COL, VisualNovelDBHelper.GIRL_COL, VisualNovelDBHelper.THOUGHTS_COL},
                            new int[]{android.R.id.text2, android.R.id.text1},
                            0
                    );
                    cursorAdapter.notifyDataSetChanged();
                    listViewDB.setAdapter(cursorAdapter);
                } catch (Exception e) {
                    t = Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG);
                    t.show();
                }
                return true;
            case R.id.item_summary:
                int count = 0;
                double rating = 0.0;
                helper = new VisualNovelDBHelper(this);
                db = helper.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM VISUAL_NOVEL", null);
                cursor.moveToFirst();
                while(cursor.isAfterLast() == false) {
                    count++;
                    rating += cursor.getDouble(cursor.getColumnIndex(VisualNovelDBHelper.RATING_COL));
                    cursor.moveToNext();
                }

                rating = rating/count;
                int entriesAmount = count;
                double averageRating = rating;
                count = 0;
                rating = 0.0;

                Intent intentSummary = new Intent(VisualNovelListActivity.this, SummaryActivity.class);
                intentSummary.putExtra("entriesAmount", entriesAmount);
                intentSummary.putExtra("averageRating", averageRating);
                startActivity(intentSummary);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_novel_list);

        helper = new VisualNovelDBHelper(this);

        listViewDB = (ListView) findViewById(R.id.listViewDB);
        registerForContextMenu(listViewDB);

        try {
            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor cursor = db.query(
                    VisualNovelDBHelper.DB_NAME,
                    new String[]{"_id", VisualNovelDBHelper.NAME_COL, VisualNovelDBHelper.RATING_COL, VisualNovelDBHelper.GIRL_COL, VisualNovelDBHelper.THOUGHTS_COL},
                    null,
                    null,
                    null, null, null
            );

            cursorAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    new String[]{VisualNovelDBHelper.NAME_COL, VisualNovelDBHelper.RATING_COL, VisualNovelDBHelper.GIRL_COL, VisualNovelDBHelper.THOUGHTS_COL},
                    new int[]{android.R.id.text2, android.R.id.text1},
                    0
            );
            cursorAdapter.notifyDataSetChanged();
            listViewDB.setAdapter(cursorAdapter);
        } catch (Exception e) {
            t = Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG);
            t.show();
        }

        listViewDB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(VisualNovelListActivity.this, VisualNovelDetailsActivity.class);
                int dataID = (int) id;
                intent.putExtra("dataID", dataID);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.delete_id:
                helper = new VisualNovelDBHelper(VisualNovelListActivity.this);
                db = helper.getWritableDatabase();
                db.execSQL("DELETE FROM VISUAL_NOVEL WHERE _id = " + info.id);
                // Refreshes activity
                finish();
                overridePendingTransition(0,0);
                startActivity(getIntent());
                overridePendingTransition(0,0);

                cursorAdapter.notifyDataSetChanged();
                listViewDB.setAdapter(cursorAdapter);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
