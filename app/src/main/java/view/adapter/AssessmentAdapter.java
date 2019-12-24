package view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.martinarroyo.wgutermtracker.AssessmentDetailActivity;
import com.martinarroyo.wgutermtracker.CourseDetailActivity;
import com.martinarroyo.wgutermtracker.R;

import java.util.List;

import logic.entity.Assessment;
import view.DeleteDialogFragment;

public class AssessmentAdapter extends RecyclerView.Adapter {
    private Context context;

    // Inner class to display each item in the Recycler view - requires its own layout
    public class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView mAssessmentTitleView;
        private final TextView mDueDateView;
        private final TextView mAssessmentTypeView;
        private final Button mDeleteAssessment;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            ViewGroup viewGroup = (ViewGroup) itemView;
            mAssessmentTitleView = viewGroup.findViewById(R.id.assessment_title_adapter);
            mDueDateView = viewGroup.findViewById(R.id.assessment_duedate_adapter);
            mAssessmentTypeView = viewGroup.findViewById(R.id.assessment_type_adapter);
            mDeleteAssessment = viewGroup.findViewById(R.id.delete_assessment_button);
        }
    }
    private final LayoutInflater mInflater;
    private List<Assessment> mAssessments; // Cached copy of Courses

    // Constructor for the Adapter -
    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Override methods for the Adapter class

    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_adapter_layout, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        AssessmentAdapter.AssessmentViewHolder holder1 = (AssessmentAdapter.AssessmentViewHolder) holder;
        if (mAssessments != null) {
            // Get an instance of the current item
            final Assessment current = mAssessments.get(position);
            //Set up the data for each of the views
            holder1.mAssessmentTitleView.setText(current.getTitle());
            holder1.mDueDateView.setText(current.getDueDate());
            holder1.mAssessmentTypeView.setText(current.getType());
            // Set up button actions
            // View Assessment details
            // Set on click listener for adapter - View Assessment Detail
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /* DONT DELETE - CODE FOR MOD ASSESSMENT
                    Intent intent = new Intent(((CourseDetailActivity)context), AddEditAssessment.class);
                    intent.putExtra(AddEditAssessment.MOD_ASSESSMENT,current);
                    ((CourseDetailActivity)context).startActivityForResult(intent, CourseDetailActivity.ASSESSMENT_MOD_CODE);
                     */
                    Intent intent = new Intent((CourseDetailActivity)context,AssessmentDetailActivity.class);
                    intent.putExtra(AssessmentDetailActivity.ASSESSMENT_DETAIL,current);
                    ((CourseDetailActivity)context).startActivityForResult(intent,AssessmentDetailActivity.ASSESSMENT_DETAIL_CODE);
                }
            });

            //DELETE Assessment
            holder1.mDeleteAssessment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteDialogFragment delete = new DeleteDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(DeleteDialogFragment.ITEM_POS,position);
                    delete.setArguments(bundle);
                    delete.show(((CourseDetailActivity)context).getSupportFragmentManager(),"Delete Assessment?");
                }

            });

        } else {
            // Covers the case of data not being ready yet.
            holder1.mAssessmentTitleView.setText("No Assessments");
        }

    }
    // set the data for our list
    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }


}
