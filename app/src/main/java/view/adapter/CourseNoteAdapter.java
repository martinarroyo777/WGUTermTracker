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

import com.martinarroyo.wgutermtracker.NoteDetailActivity;
import com.martinarroyo.wgutermtracker.R;

import java.util.List;

import logic.AddEditCourseNote;
import logic.entity.CourseNote;
import view.DeleteDialogFragment;

public class CourseNoteAdapter extends RecyclerView.Adapter {
    private Context context;

    // Inner class to display each item in the Recycler view - requires its own layout
    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNoteTitleView;
        private final Button mShareNoteButton;
        private final Button mDeleteNoteButton;

        private CourseViewHolder(View itemView) {
            super(itemView);
            ViewGroup viewGroup = (ViewGroup) itemView;
            mNoteTitleView = itemView.findViewById(R.id.note_title);
            mShareNoteButton = itemView.findViewById(R.id.share_note_button);
            mDeleteNoteButton = itemView.findViewById(R.id.delete_note_button);
        }
    }
    private final LayoutInflater mInflater;
    private List<CourseNote> mNotes; // Cached copy of Notes
    // Constructor for the Adapter -
    public CourseNoteAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Override methods for the Adapter class

    @Override
    public CourseNoteAdapter.CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.note_adapter_layout, parent, false);
        return new CourseNoteAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        CourseNoteAdapter.CourseViewHolder holder1 = (CourseNoteAdapter.CourseViewHolder) holder;

        if (mNotes != null) {
            // Get an instance of the current item
            final CourseNote current = mNotes.get(position);
            final int currentPos = position;
            //Set up the data for each of the views
            holder1.mNoteTitleView.setText(current.getTitle());
            // Set up button actions
            // View Course details
            // Set on click listener for adapter - VIEW/MODIFY Note
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(((NoteDetailActivity)context), AddEditCourseNote.class);
                    intent.putExtra(AddEditCourseNote.MOD_NOTE,current);
                    ((NoteDetailActivity)context).startActivityForResult(intent, NoteDetailActivity.NOTE_MOD_CODE);
                }
            });
            // Share Note
            holder1.mShareNoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_SUBJECT, current.getTitle());
                    intent.putExtra(Intent.EXTRA_TEXT, current.getBody());
                    intent.setType("message/rfc822");
                    ((NoteDetailActivity)context).startActivity(Intent.createChooser(intent,"Select an e-mail client"));
                }
            });

            //DELETE Note
            holder1.mDeleteNoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteDialogFragment delete = new DeleteDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(DeleteDialogFragment.ITEM_POS,currentPos);
                    delete.setArguments(bundle);
                    delete.show(((NoteDetailActivity)context).getSupportFragmentManager(),"Delete Note?");
                }

            });

        } else {
            // Covers the case of data not being ready yet.
            holder1.mNoteTitleView.setText("No Note");
        }
    }
    // set the data for our list
    public void setNotes(List<CourseNote> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }


}
