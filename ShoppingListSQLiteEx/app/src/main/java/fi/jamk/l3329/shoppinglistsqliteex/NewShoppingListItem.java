package fi.jamk.l3329.shoppinglistsqliteex;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewShoppingListItem extends DialogFragment {

    // The activity that creates an instance of this dialog fragment must
    // implement this interface in order to receive event callbacks.
    // Each method passes the DialogFragment in case the host needs to query it.
    public interface DialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String name, double count, double price);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    DialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement DialogListener");
        }
    }

    public NewShoppingListItem() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialogView = inflater.inflate(R.layout.fragment_new_item, null);
        builder.setView(dialogView)
                .setTitle("Add new item ")
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // find a name and score
                        EditText editName = (EditText) dialogView.findViewById(R.id.name);
                        String name = editName.getText().toString();
                        EditText editCount = (EditText) dialogView.findViewById(R.id.count);
                        int count = Integer.valueOf(editCount.getText().toString());
                        EditText editPrice = (EditText) dialogView.findViewById(R.id.price);
                        float price = Float.valueOf(editPrice.getText().toString());
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(NewShoppingListItem.this,name,count,price);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(NewShoppingListItem.this);
                    }
                });
        return builder.create();
    }


}
