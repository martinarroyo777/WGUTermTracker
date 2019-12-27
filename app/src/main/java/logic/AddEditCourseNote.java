package logic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.martinarroyo.wgutermtracker.CourseDetailActivity;
import com.martinarroyo.wgutermtracker.R;

import logic.entity.Course;
import logic.entity.CourseNote;


public class AddEditCourseNote extends AppCompatActivity {

    Button mCancelButton;
    Button mSaveButton;
    EditText mNoteTitle;
    EditText mNoteBody;
    String title;
    StringBuilder body;

    public static final String NEW_NOTE = "New Note";
    public static final String MOD_NOTE = "Modify Note";

    Course course;
    CourseNote note;
    boolean update = false;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setTitle("Add Note");
        setContentView(R.layout.add_note_dialog);
        course = getIntent().getParcelableExtra(CourseDetailActivity.COURSE_DETAIL);
        // Initialize views and buttons
        mNoteTitle = (EditText) findViewById(R.id.addnote_title);
        mNoteBody = (EditText) findViewById(R.id.addnote_body);
        mSaveButton = new Button(this);//(Button) findViewById(R.id.addnote_save);
        mCancelButton = new Button(this); //(Button) findViewById(R.id.addnote_cancel);
        body = new StringBuilder();
        /*
            Dynamically generate add buttons to view
         */
        mCancelButton.setText(R.string.addmodterm_cancel);
        mSaveButton.setText(R.string.addmodcourse_save);
        //LinearLayout linearLayout = (LinearLayout) View.inflate(this,R.layout.add_note_dialog,null);
        LinearLayout buttonHolder = findViewById(R.id.addnote_buttonholder);
        //setContentView(linearLayout);
        buttonHolder.addView(mCancelButton);
        buttonHolder.addView(mSaveButton);
        // If there is an intent extra, set up the fields to reflect that
        Intent modIntent = getIntent();
        if (modIntent.hasExtra(MOD_NOTE)){
            update = true;
            this.setTitle("Edit Note"); // Change title of screen if we are updating
            note = modIntent.getParcelableExtra(MOD_NOTE);
            mNoteTitle.setText(note.getTitle());
            mNoteBody.setText(note.getBody());
        }

        // Register action listeners
        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cancel();
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                save();
            }
        });

    }


    /**
     * Functionality for the Cancel button
     */
    private void cancel(){
        //Toast toast = Toast.makeText(getApplicationContext(),"Pressed cancel",Toast.LENGTH_SHORT);
        //toast.show();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();

    }

    /**
     * Functionality for the Save Button
     */
    private void save(){
        this.title = mNoteTitle.getText().toString();
        this.body.append(mNoteBody.getText().toString());
        Intent intent = new Intent();
        if (update){
            note.setTitle(this.title);
            note.setBody(this.body.toString());
            intent.putExtra(MOD_NOTE,note);
        } else {
            intent.putExtra(NEW_NOTE,new CourseNote(course.getId(),this.title,this.body.toString()));
        }
        setResult(RESULT_OK,intent);
        finish();
    }


}
