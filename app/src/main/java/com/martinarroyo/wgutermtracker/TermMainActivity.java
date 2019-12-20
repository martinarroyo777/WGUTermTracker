package com.martinarroyo.wgutermtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import database.join.TermWithCourses;
import logic.AddEditTerm;
import logic.entity.Term;
import view.DeleteDialogFragment;
import view.adapter.TermAdapter;
import view.viewmodel.TermViewModel;

public class TermMainActivity extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener{
    public static final int TERM_ADD_CODE = 1;
    public static final int TERM_MOD_CODE = 2;
    public static final int TERM_DETAIL_CODE = 3;
    TermAdapter termAdapter;
    RecyclerView recyclerView;
    TermViewModel mTermViewModel;
    final List<Term> termsWithCourses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_main_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Set up our recycler view
        termAdapter  = new TermAdapter(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get the ViewModel from ViewModelProvider
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);        // Add an observer for the LiveData object
        mTermViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable final List<Term> terms) {
                // Update the cached copy of the words in the adapter.
                termAdapter.setTerms(terms);
            }
        });

        mTermViewModel.getTermsWithCourses().observe(this, new Observer<List<TermWithCourses>>(){
            public void onChanged(@Nullable final List<TermWithCourses> terms){
                for (TermWithCourses twc: terms){
                    termsWithCourses.add(twc.getTerm());
                }
            }
        });

        // Initialize our floating action button - Add a new Term
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEditTerm.class);
                startActivityForResult(intent,TERM_ADD_CODE);
            }
        });
    }


    /**
     * Gets the response from the delete dialog fragment
     */
    public void getResponse(int response, int position){
        Term term = mTermViewModel.get(position);
        boolean hasCourses = false;
        if (!termsWithCourses.isEmpty()){
            for (Term t : termsWithCourses){
                if (t.getId() == term.getId()){
                    Toast.makeText(this,"Term has courses", Toast.LENGTH_SHORT).show();
                    hasCourses = true;
                    break;
                }
            }
        }
        if (response == 0 && !hasCourses){
            // Delete term from db
            mTermViewModel.delete(mTermViewModel.get(position));
            Toast.makeText(this, "Term deleted successfully", Toast.LENGTH_SHORT).show();
        }
        else if (response == 1){
            //Cancel deletion
            Toast.makeText(this,"Canceled Term Deletion", Toast.LENGTH_SHORT).show();
        }
    }


    // Handle results from AddEditTerm - Add/Update the viewmodel
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // INSERT Term
        if (requestCode == TERM_ADD_CODE && resultCode == RESULT_OK) {
            Term term = data.getParcelableExtra(AddEditTerm.NEW_TERM);
            mTermViewModel.insert(term);
            Toast.makeText(this, "Term Added Successfully", Toast.LENGTH_SHORT).show();
        }
        // UPDATE Term
        else if (requestCode == TERM_MOD_CODE && resultCode == RESULT_OK) {

            Term term = data.getParcelableExtra(AddEditTerm.MOD_TERM);
            mTermViewModel.update(term);
            Toast.makeText(this, "Term updated:\n" + term.toString(),Toast.LENGTH_SHORT).show();
        }


        else {
            Toast.makeText(
                    getApplicationContext(),
                    "No Term was added",
                    Toast.LENGTH_SHORT).show();
        }
    }






    /*  ----------- REMOVAL PENDING -----------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
     */
}
