package com.martinarroyo.wgutermtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import logic.AddEditCourseNote;
import logic.entity.Course;
import logic.entity.CourseNote;
import view.DeleteDialogFragment;
import view.adapter.CourseNoteAdapter;
import view.viewmodel.NoteViewModel;



public class NoteDetailActivity extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener{
    private Course course; // course that note is attached to
    private NoteViewModel mNoteViewModel; // Instance member for Course ViewModel
    public static final int NOTE_ADD_CODE = 9;
    public static final int NOTE_MOD_CODE = 10;
    public static final String COURSE_DETAIL= "Course Detail";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Course Notes");
        setContentView(R.layout.note_detail_layout);
        // Get id from course detail/adapter
        Intent courseDetail = getIntent();
        if (courseDetail.hasExtra(COURSE_DETAIL)){
            course = courseDetail.getParcelableExtra(COURSE_DETAIL);
        }
        // Set up our recycler view
        recyclerView = findViewById(R.id.note_recyclerView);
        final CourseNoteAdapter adapter = new CourseNoteAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get the ViewModel from ViewModelProvider
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);        // Add an observer for the LiveData object
        mNoteViewModel.getAllNotes(course.getId()).observe(this, new Observer<List<CourseNote>>() {
            @Override
            public void onChanged(List<CourseNote> notes) {
                adapter.setNotes(notes);
            }
        });

        /*
         * Initializing the floating action button
         */
        FloatingActionButton fab = findViewById(R.id.note_fab);
        fab.setImageResource(R.drawable.ic_add_circle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start AddEditAssessment for a result
                Intent intent = new Intent(NoteDetailActivity.this, AddEditCourseNote.class);
                intent.putExtra(COURSE_DETAIL,course);
                startActivityForResult(intent, NOTE_ADD_CODE);
            }
        });

    }

    /**
     * Gets the response from the delete dialog fragment
     */
    public void getResponse(int response, int position){
        if (response == 0){
            // Delete term from db
            CourseNote note = mNoteViewModel.get(position);
            //Toast.makeText(this,"The term position is " + position, Toast.LENGTH_SHORT).show();
            mNoteViewModel.delete(note);
            Toast.makeText(this,"Note deleted successfully",Toast.LENGTH_SHORT).show();
        } else if (response == 1){
            Toast.makeText(this,"Canceled Note Deletion", Toast.LENGTH_SHORT).show();
        }
    }

    // Override the onActivityResult method here to have it insert data from the AddEditTerm if everything is okay
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // INSERT CourseNote
        if (requestCode == NOTE_ADD_CODE && resultCode == RESULT_OK) {
            // Get data from intent
            CourseNote note = data.getParcelableExtra(AddEditCourseNote.NEW_NOTE);
            // Insert note into viewmodel
            mNoteViewModel.insert(note);
            Toast.makeText(this, "Note Added Successfully" , Toast.LENGTH_SHORT).show();
        }
        // UPDATE Assessment
        else if (requestCode == NOTE_MOD_CODE && resultCode == RESULT_OK) {
            // Get data from intent and store in variables
            CourseNote note = data.getParcelableExtra(AddEditCourseNote.MOD_NOTE);
           // Update view model
            mNoteViewModel.update(note);
            Toast.makeText(this, "Note Updated Successfully",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    "No Note was added",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
