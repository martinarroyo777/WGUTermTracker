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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.martinarroyo.wgutermtracker.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import logic.entity.Term;
import view.viewmodel.TermViewModel;


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
    final List<Term> TERMS = new ArrayList<>();
    TermViewModel mTermViewModel;

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
            this.setTitle("Edit Term");
            term = modIntent.getParcelableExtra(MOD_TERM);
            termTitle.setText(term.getTitle());
            startDate.setText(term.getStartDate());
            endDate.setText(term.getEndDate());
            start = LocalDate.parse(startDate.getText());
            end = LocalDate.parse(endDate.getText());
        }
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        mTermViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable final List<Term> terms) {
                // Update the cached copy of the words in the adapter.
                TERMS.addAll(terms);
            }
        });

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
    private void save(){
        boolean validStartDate = validStartDate(start);
        boolean validEndDate = validEndDate(end);
        boolean validDates = validStartEndDates(start, end);
        if (validStartDate && validEndDate && validDates) {
            this.title = termTitle.getText().toString();
            String termStart = start.toString();
            String termEnd = end.toString();
            Intent intent = new Intent();
            if (update) {
                term.setTitle(this.title);
                term.setStartDate(termStart);
                term.setEndDate(termEnd);
                intent.putExtra(MOD_TERM, term);
            } else {
                intent.putExtra(NEW_TERM, new Term(this.title, termStart, termEnd));
            }
            setResult(RESULT_OK, intent);
            finish();
        } else {
            if (!validStartDate){
                Toast.makeText(this,"Term start date cannot overlap another term", Toast.LENGTH_SHORT).show();
            }
            if (!validEndDate){
                Toast.makeText(this,"Term end date cannot overlap another term", Toast.LENGTH_SHORT).show();
            }
            if (!validDates){
                Toast.makeText(this,"Term start date cannot be after Term end date", Toast.LENGTH_SHORT).show();
            }
        }
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

    /*
        Helper functions for data validation
     */

    /**
     * Helper function to ensure start date does not overlap with another term
     * @param date
     * @return True if given start date is valid; false if not
     */
    private boolean validStartDate(LocalDate date){
        for(Term t: TERMS){
            // If given start date is before or equal to end date of another term
            if(date.isBefore(LocalDate.parse(t.getEndDate())) || date.isEqual(LocalDate.parse(t.getEndDate()))){
                return false;
            }
        }
        return true;
    }

    /**
     * Helper function to ensure end date does not overlap with another term
     * @param date
     * @return True if given end date is valid; false if not
     */
    private boolean validEndDate(LocalDate date){
        for(Term t: TERMS){
            // end date cannot be equal to a start date
            if(date.isEqual(LocalDate.parse(t.getStartDate()))){
                return false;
            }
        }
        return true;
    }

    /**
     * Helper function to ensure start date is before or equal to end date
     * @param start
     * @param end
     * @return True if start is before or equal to end date; false otherwise
     */
    private boolean validStartEndDates(LocalDate start, LocalDate end){
        return start.isBefore(end) || start.isEqual(end);
    }
}
