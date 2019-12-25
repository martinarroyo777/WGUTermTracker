package com.martinarroyo.wgutermtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import logic.AddEditNotification;
import logic.entity.Assessment;
import logic.entity.AssessmentNotification;
import view.DeleteDialogFragment;
import view.adapter.NotificationAdapter;
import view.viewmodel.NotificationViewModel;

public class AssessmentDetailActivity extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener{
    public static Assessment assessment;
    public static final int NOTIFICATION_ADD_CODE = 21;
    public static final int NOTIFICATION_MOD_CODE = 22;
    public static final int ASSESSMENT_DETAIL_CODE = 20;
    public static final String ASSESSMENT_DETAIL = "Assessment Detail";
    NotificationAdapter adapter;
    RecyclerView recyclerView;
    NotificationViewModel mNotificationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail_layout);
        /*
            Set up Assessment details - Top of Screen
         */
        TextView mAssessmentTitle = findViewById(R.id.assessmentdetail_title);
        TextView mAssDueDate = findViewById(R.id.assessmentdetail_date);
        Intent assessmentDetail = getIntent();
        if (assessmentDetail.hasExtra(ASSESSMENT_DETAIL)){
            assessment = assessmentDetail.getParcelableExtra(ASSESSMENT_DETAIL);
            mAssessmentTitle.setText(assessment.getTitle());
            mAssDueDate.setText("DUE DATE: " + assessment.getDueDate());
        }

        // Set up our recycler view
        recyclerView = findViewById(R.id.assessmentdetail_recycleview);
        adapter = new NotificationAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNotificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);        // Add an observer for the LiveData object
        mNotificationViewModel.getAllNotifications(assessment.getId()).observe(this, new Observer<List<AssessmentNotification>>() {
            @Override
            public void onChanged(List<AssessmentNotification> notifications) {
                adapter.setNotifications(notifications);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_addnotification);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), AddEditNotification.class);
                    intent.putExtra("assessment",assessment);
                    startActivityForResult(intent,NOTIFICATION_ADD_CODE);
                //Toast.makeText(getApplicationContext(),"Clicked addNotification button",Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Override the onActivityResult method here to have it insert data from the AddEditTerm if everything is okay
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // INSERT Notification
        if (requestCode == NOTIFICATION_ADD_CODE && resultCode == RESULT_OK) {
            // Inflate our notification
            AssessmentNotification notification = data.getParcelableExtra(AddEditNotification.NEW_NOTIFICATION);
            mNotificationViewModel.insert(notification);
            Toast.makeText(this, "Notification Added Successfully", Toast.LENGTH_SHORT).show();
        }
        // UPDATE Notification
        else if (requestCode == NOTIFICATION_MOD_CODE && resultCode == RESULT_OK) {
            // Inflate notification
            AssessmentNotification notification = data.getParcelableExtra(AddEditNotification.MOD_NOTIFICATION);
            mNotificationViewModel.update(notification);
            Toast.makeText(this, "Notification Updated Successfully",Toast.LENGTH_SHORT).show();

        }


        else {
            Toast.makeText(
                    getApplicationContext(),
                    "No Notification was added",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Gets the response from the delete dialog fragment
     */
    public void getResponse(int response, int position){
        AssessmentNotification notification = mNotificationViewModel.get(position);
        if (response == 0){
            // Delete term from db
            mNotificationViewModel.delete(notification);
            //TODO add code to cancel alarms
            Toast.makeText(this, "Notification deleted successfully",  Toast.LENGTH_SHORT).show();
        }
        else if (response == 1){
            //Cancel deletion
            Toast.makeText(this,"Canceled Notification Deletion", Toast.LENGTH_SHORT).show();
        }
    }
}
