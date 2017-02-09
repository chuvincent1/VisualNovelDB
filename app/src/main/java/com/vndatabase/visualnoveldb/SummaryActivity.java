package com.vndatabase.visualnoveldb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    TextView textEntries;
    TextView textRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        textEntries = (TextView) findViewById(R.id.textEntries);
        textRating = (TextView) findViewById(R.id.textRating);

        Intent intentSummary = getIntent();
        int entriesAmount = intentSummary.getIntExtra("entriesAmount", -1);
        double averageRating = intentSummary.getDoubleExtra("averageRating", -1.0);

        textEntries.setText("Total Visual Novel Entries: " + entriesAmount);
        textRating.setText("Visual Novel Average Rating: " + averageRating);
    }
}
