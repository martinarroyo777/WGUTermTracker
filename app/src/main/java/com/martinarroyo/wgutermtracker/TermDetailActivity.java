package com.martinarroyo.wgutermtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import logic.AddEditCourse;
import logic.entity.Course;
import logic.entity.Term;
import view.DeleteDialogFragment;
import view.adapter.CourseAdapter;
import view.viewmodel.CourseViewModel;

public class TermDetailActivity extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener{
    public static Term term;
    public static final int COURSE_ADD_CODE = 4;
    public static final int COURSE_MOD_CODE = 5;
    public static final int COURSE_DETAIL_CODE = 6;
    public static final String TERM_DETAIL = "Term Detail";
    CourseAdapter adapter;
    RecyclerView recyclerView;
    CourseViewModel mCourseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Term Detail/Course Overview");
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

        // Set up our recycler view
        recyclerView = findViewById(R.id.termdetail_recycleview);
        adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get the ViewModel from ViewModelProvider
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);        // Add an observer for the LiveData object
        mCourseViewModel.getAllCourses(term.getId()).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourses(courses);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_addcourse);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddEditCourse.class);
                    intent.putExtra("Term",term);
                    startActivityForResult(intent,COURSE_ADD_CODE);
            }
        });
    }
    // Override the onActivityResult method here to have it insert data from the AddEditTerm if everything is okay
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // INSERT Course
        if (requestCode == COURSE_ADD_CODE && resultCode == RESULT_OK) {
            // Inflate our course
            Course course = data.getParcelableExtra(AddEditCourse.NEW_COURSE);
            mCourseViewModel.insert(course);
            Toast.makeText(this, "Course Added Successfully", Toast.LENGTH_SHORT).show();
        }
        // UPDATE Course
        else if (requestCode == COURSE_MOD_CODE && resultCode == RESULT_OK) {
            // Inflate course
            Course course = data.getParcelableExtra(AddEditCourse.MOD_COURSE);
            mCourseViewModel.update(course);
            Toast.makeText(this, "Course Updated Successfully",Toast.LENGTH_SHORT).show();

        }


        else {
            Toast.makeText(
                    getApplicationContext(),
                    "No Term was added",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Gets the response from the delete dialog fragment
     */
    public void getResponse(int response, int position){
        Course course = mCourseViewModel.get(position);
        if (response == 0){
            // Delete term from db
            mCourseViewModel.delete(course);
            Toast.makeText(this, "Course deleted successfully",  Toast.LENGTH_SHORT).show();
        }
        else if (response == 1){
            //Cancel deletion
            Toast.makeText(this,"Canceled Course Deletion", Toast.LENGTH_SHORT).show();
        }
    }
}
