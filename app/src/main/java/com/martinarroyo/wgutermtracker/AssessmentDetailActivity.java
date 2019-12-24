package com.martinarroyo.wgutermtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import logic.entity.Assessment;
import logic.entity.Course;
import view.DeleteDialogFragment;
import view.adapter.CourseAdapter;
import view.viewmodel.CourseViewModel;

public class AssessmentDetailActivity extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener{
    public static Assessment assessment;
    public static final int NOTIFICATION_ADD_CODE = 21;
    public static final int NOTIFICATION_MOD_CODE = 22;
    public static final int ASSESSMENT_DETAIL_CODE = 20;
    public static final String ASSESSMENT_DETAIL = "Assessment Detail";
    CourseAdapter adapter;
    RecyclerView recyclerView;
    CourseViewModel mCourseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail_layout);
        /*
            Set up Assessment details - Top of Screen
         */
        TextView mAssessmentTitle = findViewById(R.id.assessmentdetail_title);
        TextView mAssDueDate = findViewById(R.id.assessmentdetail_date);
        Intent assessmentDetail = getIntent();
        if (assessmentDetail.hasExtra(ASSESSMENT_DETAIL)){
            assessment = assessmentDetail.getParcelableExtra(ASSESSMENT_DETAIL);
            mAssessmentTitle.setText(assessment.getTitle());
            mAssDueDate.setText("DUE DATE: " + assessment.getDueDate());
        }

        // Set up our recycler view
        recyclerView = findViewById(R.id.assessmentdetail_recycleview);
        adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /* TODO Create view model for notifications
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);        // Add an observer for the LiveData object
        mCourseViewModel.getAllCourses(term.getId()).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourses(courses);
            }
        });

         */
        FloatingActionButton fab = findViewById(R.id.fab_addcourse);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Intent intent = new Intent(getApplicationContext(), AddEditCourse.class);
                    intent.putExtra("Term",term);
                    startActivityForResult(intent,COURSE_ADD_CODE);

                 */
                Toast.makeText(getApplicationContext(),"Clicked addNotification button",Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Override the onActivityResult method here to have it insert data from the AddEditTerm if everything is okay
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // INSERT Notification
        if (requestCode == NOTIFICATION_ADD_CODE && resultCode == RESULT_OK) {
            // Inflate our course
            //Course course = data.getParcelableExtra(AddEditCourse.NEW_COURSE);
            //mCourseViewModel.insert(course);
            Toast.makeText(this, "Notification Added Successfully", Toast.LENGTH_SHORT).show();
        }
        // UPDATE Notification
        else if (requestCode == NOTIFICATION_MOD_CODE && resultCode == RESULT_OK) {
            // Inflate course
            //Course course = data.getParcelableExtra(AddEditCourse.MOD_COURSE);
            //mCourseViewModel.update(course);
            Toast.makeText(this, "Notification Updated Successfully",Toast.LENGTH_SHORT).show();

        }


        else {
            Toast.makeText(
                    getApplicationContext(),
                    "No Notification was added",
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
            Toast.makeText(this, "Notification deleted successfully",  Toast.LENGTH_SHORT).show();
        }
        else if (response == 1){
            //Cancel deletion
            Toast.makeText(this,"Canceled Notification Deletion", Toast.LENGTH_SHORT).show();
        }
    }
}
