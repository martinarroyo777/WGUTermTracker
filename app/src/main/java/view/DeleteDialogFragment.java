package view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.martinarroyo.wgutermtracker.R;


public class DeleteDialogFragment extends DialogFragment {
    private DeleteDialogListener listener;
    public static final int DELETE_CONFIRM_CODE = 0;
    public static final int DELETE_CANCEL_CODE = 1;
    private int itemPosition;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the builder class for construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Construct view
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_item_dialog,null);
        TextView message = view.findViewById(R.id.delete_message);
        message.setText("Are you sure you want to delete this item?");

        builder.setMessage("Delete Item?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO insert logic to check if term can be deleted and execute
                        Intent intent = new Intent(getActivity(), getActivity().getClass());
                        // Send confirmation to delete term
                        listener.getResponse(DELETE_CONFIRM_CODE, itemPosition);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.getResponse(DELETE_CANCEL_CODE, itemPosition);
                        Intent intent = new Intent(getActivity(), getActivity().getClass());
                        startActivity(intent);
                    }
                })
                .setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            listener = (DeleteDialogListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement DeleteDialogListener");
        }
    }
    public interface DeleteDialogListener{
        void getResponse(int response, int position);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            this.itemPosition = bundle.getInt("Term Position");
        } else{
            this.itemPosition = -1;
        }
    }
}
