package logic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.martinarroyo.wgutermtracker.R;


public class AddEditCourseNote extends AppCompatActivity {

    Button mCancelButton;
    Button mSaveButton;
    EditText mNoteTitle;
    EditText mNoteBody;
    String title;
    StringBuilder body;

    public static final String NEW_NOTE_TITLE = "New Title";
    public static final String NEW_NOTE_BODY = "New Body";
    public static final String MOD_NOTE_TITLE = "Mod Title";
    public static final String MOD_NOTE_BODY = "Mod Body";
    public static final String MOD_NOTE_ID = "Mod Note ID";

    private int courseId;
    boolean update = false;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_note_dialog);
        courseId = 1;//getIntent().getIntExtra(CourseDetailActivity.COURSEDETAIL_ID,-1);
        // Initialize views and buttons
        mNoteTitle = (EditText) findViewById(R.id.addnote_title);
        mNoteBody = (EditText) findViewById(R.id.addnote_body);
        mSaveButton = (Button) findViewById(R.id.addnote_save);
        mCancelButton = (Button) findViewById(R.id.addnote_cancel);
        body = new StringBuilder();
        // If there is an intent extra, set up the fields to reflect that
        Intent modIntent = getIntent();
        if (modIntent.hasExtra(MOD_NOTE_ID)){
            update = true;
            this.setTitle("Edit Note"); // Change title of screen if we are updating
            mNoteTitle.setText(modIntent.getStringExtra(MOD_NOTE_TITLE));
            mNoteBody.setText(modIntent.getStringExtra(MOD_NOTE_BODY));
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
        Toast toast = Toast.makeText(getApplicationContext(),"Pressed cancel",Toast.LENGTH_SHORT);
        toast.show();
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
        // Always add the term id
        //intent.putExtra(CourseDetailActivity.COURSEDETAIL_ID,courseId);
        if (update){
            intent.putExtra(MOD_NOTE_TITLE,this.title);
            intent.putExtra(MOD_NOTE_BODY,this.body.toString());
            int id = getIntent().getIntExtra(MOD_NOTE_ID,-1);
            intent.putExtra(MOD_NOTE_ID,id);
        } else {
            intent.putExtra(NEW_NOTE_TITLE,this.title);
            intent.putExtra(NEW_NOTE_BODY,this.body.toString());
        }
        setResult(RESULT_OK,intent);
        finish();
    }


}
