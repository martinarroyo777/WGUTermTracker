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

import com.martinarroyo.wgutermtracker.R;

import java.time.LocalDate;
import java.util.Calendar;

import logic.entity.Course;
import logic.entity.Term;

public class AddEditCourse extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Course course; // The course to create based on the input given
    Course course;
    Term term;
    Spinner status_select;
    String status;
    Button cancel;
    Button save;
    TextView startDate;
    DatePickerDialog.OnDateSetListener mDateSetListenerStart;
    LocalDate start;
    TextView endDate;
    DatePickerDialog.OnDateSetListener mDateSetListenerEnd;
    LocalDate end;
    EditText courseTitle;
    EditText courseMentorName;
    EditText courseMentorEmail;
    EditText courseMentorPhone;
    String title;
    /*
    public static final String NEW_COURSE_TITLE = "New Title";
    public static final String NEWCOURSEMENTOR_NAME = "Mentor Name";
    public static final String NEWCOURSEMENTOR_PHONE = "Mentor Phone";
    public static final String NEWCOURSEMENTOR_EMAIL = "Mentor Email";
    public static final String NEW_COURSE_START = "New Start";
    public static final String NEW_COURSE_END = "New End";
    public static final String NEW_COURSE_STATUS = "New Status";
     */
    public static final String NEW_COURSE = "New Course";
    public static final String MOD_COURSE = "Modify Course";
    /*
    public static final String MOD_COURSE_ID = "Mod ID";
    public static final String MOD_COURSE_TITLE = "Mod Title";
    public static final String MODCOURSEMENTOR_NAME = "Mentor Name";
    public static final String MODCOURSEMENTOR_PHONE = "Mentor Phone";
    public static final String MODCOURSEMENTOR_EMAIL = "Mentor Email";
    public static final String MOD_COURSE_START = "Mod Start";
    public static final String MOD_COURSE_END = "Mod End";
    public static final String MOD_COURSE_STATUS = "Mod Status";
     */
    boolean update = false;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_course_dialog);
        term = getIntent().getParcelableExtra("Term");
        // Initialize views and buttons
        courseTitle = (EditText) findViewById(R.id.course_title);
        courseMentorName = (EditText) findViewById(R.id.coursementor_name);
        courseMentorEmail = (EditText) findViewById(R.id.coursementor_email);
        courseMentorPhone = (EditText) findViewById(R.id.coursementor_phone);
        startDate = (TextView) findViewById(R.id.course_startdate);
        endDate = (TextView) findViewById(R.id.course_enddate);
        save = (Button) findViewById(R.id.course_save);
        cancel = (Button) findViewById(R.id.course_cancel);
        status_select = (Spinner) findViewById(R.id.course_status);
        //Initialize Course Status Spinner
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(
                                            this,R.array.course_status,
                                            android.R.layout.simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status_select.setAdapter(statusAdapter);
        // If there is an intent extra, set up the fields to reflect that
        Intent modIntent = getIntent();
        if (modIntent.hasExtra(MOD_COURSE)){
            update = true;
            this.setTitle("Edit Course"); // Change title of screen if we are updating
            course = modIntent.getParcelableExtra(MOD_COURSE);
            courseTitle.setText(course.getTitle());
            courseMentorName.setText(course.getMentor_name());
            courseMentorEmail.setText(course.getMentor_email());
            courseMentorPhone.setText(course.getMentor_phone());
            startDate.setText(course.getStartDate());
            endDate.setText(course.getEndDate());
            status_select.setSelection(statusAdapter.getPosition(course.getStatus()));
            start = LocalDate.parse(startDate.getText());
            end = LocalDate.parse(endDate.getText());
        }

        // Register action listeners
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cancel();
            }
        });
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                save();
            }
        });
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchDatePicker(mDateSetListenerStart);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchDatePicker(mDateSetListenerEnd);
            }
        });
        // Initialize OnDateSetListeners
        mDateSetListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                start = LocalDate.of(year, ++month, day);
                // Set the text for the text view
                startDate.setText(start.toString());
                //Toast.makeText(getApplicationContext(),"Start Date: "+start.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        mDateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                end = LocalDate.of(year,++month,day);
                // Set the text for the text view
                endDate.setText(end.toString());
                //Toast.makeText(getApplicationContext(),"End Date: "+end.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        status_select.setOnItemSelectedListener(this);
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
    //TODO add data validation for fields
    private void save(){

        this.title = courseTitle.getText().toString();
        String courseStart = start.toString();
        String courseEnd = end.toString();
        String mentorName = courseMentorName.getText().toString();
        String mentorEmail = courseMentorEmail.getText().toString();
        String mentorPhone = courseMentorPhone.getText().toString();
        Intent intent = new Intent();
        if (update){
            course.setTitle(this.title);
            course.setStartDate(courseStart);
            course.setEndDate(courseEnd);
            course.setMentor_name(mentorName);
            course.setMentor_email(mentorEmail);
            course.setMentor_phone(mentorPhone);
            course.setStatus(status);
            intent.putExtra(MOD_COURSE, course);
        } else {

            /*
            intent.putExtra(NEW_COURSE_TITLE,this.title);
            intent.putExtra(MODCOURSEMENTOR_NAME,mentorName);
            intent.putExtra(MODCOURSEMENTOR_EMAIL,mentorEmail);
            intent.putExtra(MODCOURSEMENTOR_PHONE,mentorPhone);
            intent.putExtra(NEW_COURSE_START,courseStart);
            intent.putExtra(NEW_COURSE_END,courseEnd);
            intent.putExtra(NEW_COURSE_STATUS,status);
             */
            intent.putExtra(NEW_COURSE,new Course(term.getId(),this.title,mentorName,mentorEmail,mentorPhone,
                    courseStart,courseEnd,status));
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
        this.status = parent.getItemAtPosition(pos).toString();
    }
    public void onNothingSelected(AdapterView<?> parent){
        // TODO figure out what we should have here

    }
}
