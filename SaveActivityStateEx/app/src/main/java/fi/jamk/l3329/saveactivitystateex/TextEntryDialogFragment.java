package fi.jamk.l3329.saveactivitystateex;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class TextEntryDialogFragment extends DialogFragment {
    public interface TextEntryDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog, String text);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    TextEntryDialogListener mListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mListener = (TextEntryDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement TextEntryDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final  View dialogView = inflater.inflate(R.layout.fragment_text_entry_dialog, null);

        builder.setView(dialogView)
                .setTitle("Give a new text")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        EditText editText = (EditText) dialogView.findViewById(R.id.editText);
                        String text = editText.getText().toString();

                        mListener.onDialogPositiveClick(TextEntryDialogFragment.this, text);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        mListener.onDialogNegativeClick(TextEntryDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
