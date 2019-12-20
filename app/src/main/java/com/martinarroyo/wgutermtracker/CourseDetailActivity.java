package com.martinarroyo.wgutermtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import logic.AddEditAssessment;
import logic.entity.Assessment;
import logic.entity.Course;
import view.DeleteDialogFragment;
import view.adapter.AssessmentAdapter;
import view.viewmodel.AssessmentViewModel;


public class CourseDetailActivity extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener{

    private AssessmentViewModel mAssessmentViewModel; // Instance member for Course ViewModel
    public static final int ASSESSMENT_ADD_CODE = 7;
    public static final int ASSESSMENT_MOD_CODE = 8;
    public static final String COURSE_DETAIL = "Course Detail";
    RecyclerView recyclerView;
    Course course; // The current course

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail_layout);
        /*
            Set up Course details
         */
        TextView mCourseTitle = findViewById(R.id.coursedetail_title);
        TextView mCourseStartDate = findViewById(R.id.coursedetail_startdate);
        TextView mCourseEndDate = findViewById(R.id.coursedetail_enddate);
        TextView mCourseStatus = findViewById(R.id.coursedetail_status);
        TextView mCourseMentorName = findViewById(R.id.coursedetail_mentorname);
        TextView mCourseMentorEmail = findViewById(R.id.coursedetail_mentoremail);
        TextView mCourseMentorPhone = findViewById(R.id.coursedetail_phone);
        Button mViewNotes = findViewById(R.id.coursedetail_notes);
        Intent courseDetail = getIntent();
        if (courseDetail.hasExtra(COURSE_DETAIL)){
            course = courseDetail.getParcelableExtra(COURSE_DETAIL);
            mCourseTitle.setText(course.getTitle());
            mCourseStartDate.setText("START: " + course.getStartDate());
            mCourseEndDate.setText( "END: " + course.getEndDate());
            mCourseStatus.setText("STATUS: " + course.getStatus());
            mCourseMentorName.setText(course.getMentor_name());
            mCourseMentorEmail.setText(course.getMentor_email());
            mCourseMentorPhone.setText(course.getMentor_phone());

        }

        mViewNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetailActivity.this,NoteDetailActivity.class);
                //intent.putExtra(NoteDetailActivity.COURSEDETAIL_ID,courseId);
                startActivity(intent);
            }
        });

        // Set up our recycler view
        recyclerView = findViewById(R.id.coursedetail_recycleview);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get the ViewModel from ViewModelProvider
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);        // Add an observer for the LiveData object
        mAssessmentViewModel.getAllAssessments(course.getId()).observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setAssessments(assessments);
            }
        });

        /*
         * Initializing the floating action button
         */
        FloatingActionButton fab = findViewById(R.id.fab_addassessment);
        fab.setImageResource(R.drawable.ic_add_circle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start AddEditAssessment for a result
                Intent intent = new Intent(CourseDetailActivity.this, AddEditAssessment.class);
                intent.putExtra(COURSE_DETAIL,course);
                startActivityForResult(intent, ASSESSMENT_ADD_CODE);
            }
        });

    }

    /**
     * Gets the response from the delete dialog fragment
     */
    public void getResponse(int response, int position){
        if (response == 0){
            // Delete term from db
            Assessment assessment = mAssessmentViewModel.get(position);
            mAssessmentViewModel.delete(assessment);
            Toast.makeText(this,"Assessment deleted successfully",Toast.LENGTH_SHORT).show();
        } else if (response == 1){
            Toast.makeText(this,"Canceled Assessment Deletion", Toast.LENGTH_SHORT).show();
        }
    }

    // Override the onActivityResult method here to have it insert data from the AddEditTerm if everything is okay
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // INSERT Assessment
        if (requestCode == ASSESSMENT_ADD_CODE && resultCode == RESULT_OK) {
            // Inflate our Assessment
            Assessment assessment = data.getParcelableExtra(AddEditAssessment.NEW_ASSESSMENT);
            // Insert assessment into view model
            mAssessmentViewModel.insert(assessment);
            Toast.makeText(this, "Assessment Added Successfully", Toast.LENGTH_SHORT).show();
        }
        // UPDATE Assessment
        else if (requestCode == ASSESSMENT_MOD_CODE && resultCode == RESULT_OK) {
            Assessment assessment = data.getParcelableExtra(AddEditAssessment.MOD_ASSESSMENT);
           // Update view model
            mAssessmentViewModel.update(assessment);
            Toast.makeText(this, "Assessment Updated Successfully",Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(
                    getApplicationContext(),
                    "No Assessment was added",
                    Toast.LENGTH_SHORT).show();
        }
    }



}
