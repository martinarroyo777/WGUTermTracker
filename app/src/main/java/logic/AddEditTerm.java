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

import com.martinarroyo.wgutermtracker.R;

import java.time.LocalDate;
import java.util.Calendar;

import logic.entity.Term;


/**
 * Fragment to handle entering a new Term or modifying an existing one
 */
public class AddEditTerm extends AppCompatActivity {
    Term term; // The term to create based on the input given
    Button cancel;
    Button save;
    TextView startDate;
    DatePickerDialog.OnDateSetListener mDateSetListenerStart;
    LocalDate start;
    TextView endDate;
    DatePickerDialog.OnDateSetListener mDateSetListenerEnd;
    LocalDate end;
    public EditText termTitle;
    String title;

    public static final String NEW_TERM = "New Term";
    public static final String MOD_TERM = "Modify Term";

    boolean update = false;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_term_dialog);
        // Initialize views and buttons
        termTitle = (EditText) findViewById(R.id.term_title);
        startDate = (TextView) findViewById(R.id.startdate);
        endDate = (TextView) findViewById(R.id.enddate);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        // If there is an intent extra, set up the fields to reflect that
        Intent modIntent = getIntent();
        if (modIntent.hasExtra(MOD_TERM)){
            update = true;
            term = modIntent.getParcelableExtra(MOD_TERM);
            termTitle.setText(term.getTitle());
            startDate.setText(term.getStartDate());
            endDate.setText(term.getEndDate());
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

        this.title = termTitle.getText().toString();
        String termStart = start.toString();
        String termEnd = end.toString();
        Intent intent = new Intent();
        if (update){
            term.setTitle(this.title);
            term.setStartDate(termStart);
            term.setEndDate(termEnd);
            intent.putExtra(MOD_TERM,term);
        } else {
            intent.putExtra(NEW_TERM,new Term(this.title,termStart,termEnd));
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
