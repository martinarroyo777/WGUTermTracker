package view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.martinarroyo.wgutermtracker.R;
import com.martinarroyo.wgutermtracker.TermDetailActivity;

import java.util.List;

import logic.AddEditCourse;
import logic.entity.Course;
import view.DeleteDialogFragment;

public class CourseAdapter extends RecyclerView.Adapter {
    private Context context;

    // Inner class to display each item in the Recycler view - requires its own layout
    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseTitleView;
        private final TextView courseDatesView;
        private final TextView courseStatusView;
        private final Button modCourseButton;
        private final Button viewNotes;
        private final Button deleteCourse;

        private CourseViewHolder(View itemView) {
            super(itemView);
            ViewGroup viewGroup = (ViewGroup) itemView;
            courseTitleView = itemView.findViewById(R.id.course_title);
            courseDatesView = itemView.findViewById(R.id.course_dates);
            courseStatusView = itemView.findViewById(R.id.course_status);
            modCourseButton = itemView.findViewById(R.id.mod_course_button);
            viewNotes = itemView.findViewById(R.id.view_notes_button);
            deleteCourse = itemView.findViewById(R.id.delete_course_button);
        }
    }
    private final LayoutInflater mInflater;
    private List<Course> mCourses; // Cached copy of Courses
    // Constructor for the Adapter -
    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Override methods for the Adapter class

    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_adapter_layout, parent, false);
        return new CourseAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        CourseAdapter.CourseViewHolder holder1 = (CourseAdapter.CourseViewHolder) holder;

        if (mCourses != null) {
            // Get an instance of the current item
            final Course current = mCourses.get(position);
            final int currentPos = position;
            //Set up the data for each of the views
            holder1.courseTitleView.setText(current.getTitle());
            holder1.courseDatesView.setText(current.getStartDate() + " - " + current.getEndDate());
            holder1.courseStatusView.setText(current.getStatus());
            // Set up button actions
            // View Course details
            // Set on click listener for adapter
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(((TermDetailActivity)context),current.toString(),Toast.LENGTH_SHORT).show();
                    /*
                    Intent intent = new Intent(((TermDetailActivity)context), CourseDetailActivity.class);
                    intent.putExtra(CourseDetailActivity.COURSEDETAIL_TITLE,current.getTitle());
                    intent.putExtra(CourseDetailActivity.COURSEDETAIL_START,current.getStartDate());
                    intent.putExtra(CourseDetailActivity.COURSEDETAIL_END,current.getEndDate());
                    //input course mentor info
                    intent.putExtra(CourseDetailActivity.COURSEDETAIL_MENTORNAME,current.getMentor_name());
                    intent.putExtra(CourseDetailActivity.COURSEDETAIL_MENTOREMAIL,current.getMentor_email());
                    intent.putExtra(CourseDetailActivity.COURSEDETAIL_MENTORPHONE,current.getMentor_phone());
                    //course status
                    intent.putExtra(CourseDetailActivity.COURSEDETAIL_STATUS,current.getStatus());
                    //course id
                    intent.putExtra(CourseDetailActivity.COURSEDETAIL_ID,current.getId());
                    ((TermDetailActivity)context).startActivityForResult(intent, TermDetailActivity.COURSE_DETAIL_CODE);
                     */

                }
            });
            // MODIFY Course
            holder1.modCourseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(((TermDetailActivity)context), AddEditCourse.class);
                    /*
                    intent.putExtra(AddEditCourse.MOD_COURSE_ID,current.getId());
                    intent.putExtra(AddEditCourse.MOD_COURSE_TITLE,current.getTitle());
                    intent.putExtra(AddEditCourse.MODCOURSEMENTOR_NAME,current.getMentor_name());
                    intent.putExtra(AddEditCourse.MODCOURSEMENTOR_PHONE,current.getMentor_phone());
                    intent.putExtra(AddEditCourse.MODCOURSEMENTOR_EMAIL,current.getMentor_email());
                    intent.putExtra(AddEditCourse.MOD_COURSE_START,current.getStartDate());
                    intent.putExtra(AddEditCourse.MOD_COURSE_END,current.getEndDate());
                     */
                    intent.putExtra(AddEditCourse.MOD_COURSE,current);
                    ((TermDetailActivity)context).startActivityForResult(intent, TermDetailActivity.COURSE_MOD_CODE);
                }
            });
            // VIEW Course Notes
            holder1.viewNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO need to create new activity to show course notes
                   // Intent intent = new Intent(((TermDetailActivity)context), NoteDetailActivity.class);
                   // intent.putExtra(NoteDetailActivity.COURSEDETAIL_ID,current.getId());
                   // ((TermDetailActivity)context).startActivityForResult(intent, NoteDetailActivity.NOTE_MOD_CODE);
                    Toast.makeText(context,"Pressed View Course Notes Button", Toast.LENGTH_SHORT).show();
                }

            });

            //DELETE Course
            holder1.deleteCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteDialogFragment delete = new DeleteDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Course position",currentPos);
                    delete.setArguments(bundle);
                    delete.show(((TermDetailActivity)context).getSupportFragmentManager(),"Delete Course?");
                }

            });

        } else {
            // Covers the case of data not being ready yet.
            holder1.courseTitleView.setText("No Course");
        }
    }
    // set the data for our list
    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }


}
