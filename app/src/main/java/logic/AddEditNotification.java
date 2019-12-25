package logic;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.martinarroyo.wgutermtracker.AssessmentDetailActivity;
import com.martinarroyo.wgutermtracker.R;

import java.time.LocalDate;
import java.util.Calendar;

import logic.entity.Assessment;
import logic.entity.AssessmentNotification;


public class AddEditNotification extends AppCompatActivity {

    Button mCancelButton;
    Button mSaveButton;
    EditText mNotificationTitle;
    EditText mNotificationMessage;
    TextView mNotificationDate;
    DatePickerDialog.OnDateSetListener mDateSetListenerStart;
    LocalDate mDate;
    String title;
    StringBuilder body;

    public static final String NEW_NOTIFICATION = "New Notification";
    public static final String MOD_NOTIFICATION = "Modify Notification";

    Assessment assessment;
    AssessmentNotification notification;
    boolean update = false;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_notification_dialog);
        assessment = getIntent().getParcelableExtra(AssessmentDetailActivity.ASSESSMENT_DETAIL);
        // Initialize views and buttons
        mNotificationTitle = (EditText) findViewById(R.id.addnotification_title);
        mNotificationMessage = (EditText) findViewById(R.id.addnotification_message);
        mNotificationDate = findViewById(R.id.notification_date);
        mSaveButton = (Button) findViewById(R.id.addnote_save);
        mCancelButton = (Button) findViewById(R.id.addnote_cancel);
        body = new StringBuilder();
        // If there is an intent extra, set up the fields to reflect that
        Intent modIntent = getIntent();
        if (modIntent.hasExtra(MOD_NOTIFICATION)){
            update = true;
            this.setTitle("Edit Notification"); // Change title of screen if we are updating
            notification = modIntent.getParcelableExtra(MOD_NOTIFICATION);
            mNotificationTitle.setText(notification.getTitle());
            mNotificationMessage.setText(notification.getMessage());
            mNotificationDate.setText(notification.getDate());
            mDate = LocalDate.parse(mNotificationDate.getText());
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
        mNotificationDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchDatePicker(mDateSetListenerStart);
            }
        });
        // Initialize OnDateSetListeners
        mDateSetListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mDate = LocalDate.of(year, ++month, day);
                // Set the text for the text view
                mNotificationDate.setText(mDate.toString());
                //Toast.makeText(getApplicationContext(),"Start Date: "+start.toString(), Toast.LENGTH_SHORT).show();
            }
        };

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
        this.title = mNotificationTitle.getText().toString();
        this.body.append(mNotificationMessage.getText().toString());
        Intent intent = new Intent();
        if (update){
            notification.setTitle(this.title);
            notification.setMessage(this.body.toString());
            notification.setDate(this.mDate.toString());
            intent.putExtra(MOD_NOTIFICATION,notification);
        } else {
            intent.putExtra(NEW_NOTIFICATION,new AssessmentNotification(assessment.getId(),this.title,this.body.toString(),mDate.toString()));
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

}
