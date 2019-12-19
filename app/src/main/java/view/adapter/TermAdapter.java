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

import com.martinarroyo.wgutermtracker.R;
import com.martinarroyo.wgutermtracker.TermDetailActivity;
import com.martinarroyo.wgutermtracker.TermMainActivity;

import java.util.List;

import logic.AddEditTerm;
import logic.entity.Term;
import view.DeleteDialogFragment;


public class TermAdapter extends RecyclerView.Adapter {
    private Context context;
    private int currentPosition;
    // Inner class to display each item in the Recycler view - requires its own layout
    public class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termTitleView;
        private final TextView termDatesView;
        private final Button modTermButton;
        private final Button deleteTerm;
        private TermViewHolder(View itemView) {
            super(itemView);
            ViewGroup viewGroup = (ViewGroup) itemView;
            termTitleView = itemView.findViewById(R.id.term_title);
            termDatesView = itemView.findViewById(R.id.term_dates);
            modTermButton = itemView.findViewById(R.id.mod_term_button);
            deleteTerm = itemView.findViewById(R.id.delete_term_button);
        }
    }
    private final LayoutInflater mInflater;
    private List<Term> mTerms; // Cached copy of Terms

    // Constructor for the Adapter -
    public TermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Override methods for the Adapter class

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_adapter_layout, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        TermViewHolder holder1 = (TermViewHolder) holder;
        if (mTerms != null) {
            // Get an instance of the current item
            final Term current = mTerms.get(position);
            currentPosition = position;
            //Set up the data for each of the views
            holder1.termTitleView.setText(current.getTitle());
            holder1.termDatesView.setText(current.getStartDate() + " - " + current.getEndDate());
            // Set up button actions
            // VIEW Term detail
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(((TermMainActivity)context), TermMainActivity.class);
                    intent.putExtra(TermDetailActivity.TERMDETAIL_TITLE,current.getTitle());
                    intent.putExtra(TermDetailActivity.TERMDETAIL_START,current.getStartDate());
                    intent.putExtra(TermDetailActivity.TERMDETAIL_END,current.getEndDate());
                    intent.putExtra(TermDetailActivity.TERMDETAIL_ID,current.getId());
                    intent.putExtra("Term Adapter Position", currentPosition);
                    ((TermMainActivity)context).startActivityForResult(intent, TermMainActivity.TERM_DETAIL_CODE);
                    //Toast.makeText(context,"Pressed View Term Detail Button", Toast.LENGTH_SHORT).show();
                }
            });
            // MODIFY Term
            holder1.modTermButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(((TermMainActivity)context), AddEditTerm.class);
                    intent.putExtra(AddEditTerm.MOD_TERM_ID,current.getId());
                    intent.putExtra(AddEditTerm.MOD_TERM_TITLE,current.getTitle());
                    intent.putExtra(AddEditTerm.MOD_TERM_START,current.getStartDate());
                    intent.putExtra(AddEditTerm.MOD_TERM_END,current.getEndDate());
                    ((TermMainActivity)context).startActivityForResult(intent, TermMainActivity.TERM_MOD_CODE);
                }
            });

            //DELETE Term
            holder1.deleteTerm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteDialogFragment delete = new DeleteDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Term Position",currentPosition);
                    delete.setArguments(bundle);
                    delete.show(((TermMainActivity)context).getSupportFragmentManager(),"Delete Term?");
                }

            });

        } else {
            // Covers the case of data not being ready yet.
            holder1.termTitleView.setText("No Term");
        }
    }
    // set the data for our list
    public void setTerms(List<Term> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }

    public int getCurrentPosition(){
        return this.currentPosition;
    }

}
