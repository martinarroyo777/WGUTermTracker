package com.martinarroyo.wgutermtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import logic.entity.Term;

public class TermDetailActivity extends AppCompatActivity {
    private Term term;
    public static final int COURSE_ADD_CODE = 4;
    public static final int COURSE_MOD_CODE = 5;
    public static final int COURSE_DETAIL_CODE = 6;
    public static final String TERM_DETAIL = "Term Detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail_layout);
        /*
            Set up Term details - Top of Screen
         */
        TextView mTermTitle = findViewById(R.id.termdetail_title);
        TextView mTermDates = findViewById(R.id.termdetail_dates);
        Intent termDetail = getIntent();
        if (termDetail.hasExtra(TERM_DETAIL)){
            term = termDetail.getParcelableExtra(TERM_DETAIL);
            mTermTitle.setText(term.getTitle());
            mTermDates.setText("START: " + term.getStartDate() + "\n\nEND: " + term.getEndDate());
        }

        FloatingActionButton fab = findViewById(R.id.fab_addcourse);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Jizz in my pants", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /* ---- REMOVAL PENDING -------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

     */
}
