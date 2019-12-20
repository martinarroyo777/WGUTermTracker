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

import logic.Note;
import view.AddEditNote;
import view.DeleteDialogFragment;
import view.adapters.NoteAdapter;
import view.viewmodels.NoteViewModel;

public class NoteDetailActivity extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener{

    private NoteViewModel mNoteViewModel; // Instance member for Course ViewModel
    public static final int NOTE_ADD_CODE = 9;
    public static final int NOTE_MOD_CODE = 10;
    public static final String NOTEDETAIL_ID = "ID";
    public static final String NOTEDETAIL_TITLE = "Title";
    public static final String NOTEDETAIL_BODY = "Body";
    public static final String COURSEDETAIL_ID = "Course ID";
    public static final String COURSEDETAIL_END = "End";
    RecyclerView recyclerView;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        // Get id from course detail/adapter
        Intent courseDetail = getIntent();
        if (courseDetail.hasExtra(COURSEDETAIL_ID)){
            courseId = courseDetail.getIntExtra(COURSEDETAIL_ID,-1);
        }

        // Set up our recycler view
        recyclerView = findViewById(R.id.note_recyclerView);
        final NoteAdapter adapter = new NoteAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get the ViewModel from ViewModelProvider
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);        // Add an observer for the LiveData object
        mNoteViewModel.getAllNotes(courseId).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
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
                Intent intent = new Intent(NoteDetailActivity.this, AddEditNote.class);
                intent.putExtra(COURSEDETAIL_ID,courseId);
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
            //Toast.makeText(this,"The term position is " + position, Toast.LENGTH_SHORT).show();
            mNoteViewModel.delete(mNoteViewModel.get(position));
            Toast.makeText(this,"Note deleted successfully",Toast.LENGTH_SHORT).show();
        } else if (response == 1){
            Toast.makeText(this,"Canceled Note Deletion", Toast.LENGTH_SHORT).show();
        }
    }

    // Override the onActivityResult method here to have it insert data from the AddEditTerm if everything is okay
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // INSERT Assessment
        if (requestCode == NOTE_ADD_CODE && resultCode == RESULT_OK) {
            // Get data from intent
            String title = data.getStringExtra(AddEditNote.NEW_NOTE_TITLE);
            String body = data.getStringExtra(AddEditNote.NEW_NOTE_BODY);
            // Inflate our Note
            Note note = new Note(courseId,title,body);
            // Insert note into viewmodel
            mNoteViewModel.insert(note);
            Toast.makeText(this, "Note Added Successfully" , Toast.LENGTH_SHORT).show();
        }
        // UPDATE Assessment
        else if (requestCode == NOTE_MOD_CODE && resultCode == RESULT_OK) {
            // Get data from intent and store in variables
            String title = data.getStringExtra(AddEditNote.MOD_NOTE_TITLE);
            String body = data.getStringExtra(AddEditNote.MOD_NOTE_BODY);
            int id = data.getIntExtra(AddEditNote.MOD_NOTE_ID,-1);
            // Inflate note
            Note note = new Note(courseId,title,body);
           // Set note id
            note.setId(id);
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
