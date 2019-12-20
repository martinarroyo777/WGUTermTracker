package logic;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.martinarroyo.wgutermtracker.CourseDetailActivity;
import com.martinarroyo.wgutermtracker.R;

import java.time.LocalDate;
import java.util.Calendar;

import logic.entity.Assessment;
import logic.entity.Course;

public class AddEditAssessment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Assessment assessment; // Assessment holder for assessments sent for modification
    private Course course;
    EditText mAssessmentTitle;
    String title;
    Spinner mSelectAssessmentType;
    String mAssessmentType;
    Button mCancel;
    Button mSave;
    TextView mDueDate;
    DatePickerDialog.OnDateSetListener mDueDateSetListener;
    LocalDate dueDate;
    public static final String NEW_ASSESSMENT = "New Assessment";
    public static final String MOD_ASSESSMENT = "Modify Assessment";

    boolean update = false;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_assessment_dialog);
        course = getIntent().getParcelableExtra(CourseDetailActivity.COURSE_DETAIL);
        // Initialize views and buttons
        mAssessmentTitle = (EditText) findViewById(R.id.assessment_title);
        mDueDate = (TextView) findViewById(R.id.assessment_duedate);
        mSave = (Button) findViewById(R.id.assessment_save);
        mCancel = (Button) findViewById(R.id.assessment_cancel);
        mSelectAssessmentType = (Spinner) findViewById(R.id.assessment_type);
        //Initialize Course Status Spinner
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                                            this,R.array.assessment_type,
                                            android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectAssessmentType.setAdapter(typeAdapter);
        // If there is an intent extra, set up the fields to reflect that
        Intent modIntent = getIntent();
        if (modIntent.hasExtra(MOD_ASSESSMENT)){
            update = true;
            this.setTitle("Edit Assessment"); // Change title of screen if we are updating
            assessment = modIntent.getParcelableExtra(MOD_ASSESSMENT);
            mAssessmentTitle.setText(assessment.getTitle());
            mDueDate.setText(assessment.getDueDate());
            mSelectAssessmentType.setSelection(typeAdapter.getPosition(assessment.getType()));
            dueDate = LocalDate.parse(mDueDate.getText());
        }

        // Register action listeners
        mCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cancel();
            }
        });
        mSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                save();
            }
        });
        mDueDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchDatePicker(mDueDateSetListener);
            }
        });
        // Initialize OnDateSetListeners
        mDueDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dueDate = LocalDate.of(year, ++month, day);
                // Set the text for the text view
                mDueDate.setText(dueDate.toString());
                //Toast.makeText(getApplicationContext(),"Start Date: "+start.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        mSelectAssessmentType.setOnItemSelectedListener(this);
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

        this.title = mAssessmentTitle.getText().toString();
        String assessmentDueDate = dueDate.toString();
        Intent intent = new Intent();
        if (update){
            assessment.setTitle(this.title);
            assessment.setDueDate(assessmentDueDate);
            assessment.setType(mAssessmentType);
            intent.putExtra(MOD_ASSESSMENT,assessment);
        } else {
            intent.putExtra(NEW_ASSESSMENT, new Assessment(course.getId(),this.title,mAssessmentType,assessmentDueDate));
        }
        setResult(RESULT_OK,intent);
        finish();
    }

    /**
     * Helper function to launch DatePicker
     * @param dateSetListener
     */
    private void launchDatePicker(DatePickerDialog.OnDateSetListener dateSetListener) {
        // Create Calendar object
        Calendar cal = Calendar.getInstance();
        // get year, month and day
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        // Create the DatePickerDialog object
        DatePickerDialog dialog = new DatePickerDialog(this,android.R.style.Widget_Material_DatePicker, dateSetListener,year,month,day);
        // Show the dialog
        dialog.show();
    }

    // Get the user selected status
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        this.mAssessmentType = parent.getItemAtPosition(pos).toString();
    }
    public void onNothingSelected(AdapterView<?> parent){
        // DO NOTHING
    }

}
