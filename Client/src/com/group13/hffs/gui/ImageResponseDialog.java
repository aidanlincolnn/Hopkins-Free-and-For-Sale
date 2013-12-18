package com.group13.hffs.gui;

import com.group13.hffs.gui.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
/**
 * This class provides a dialog for the upload picture button, deciding if the picture is selected from gallery or taken directly from camera
 * @author Aidan, Lucas, Shijian
 *
 */
public class ImageResponseDialog extends DialogFragment {
	/**
	 * This interface acts like a listener to give feedback to its parent activity
	 * @author albatross
	 */
	public interface NoticeDialogListener {
		/**
		 * Select a picture
		 * @param dialog
		 * Dialog being acted on
		 */
        public void onSelectPicClick(DialogFragment dialog);
        /**
         * Take a picture
         * @param dialog
         * Dialog being acted on
         */
        public void onTakePicClick(DialogFragment dialog);
    }
    
    NoticeDialogListener mListener;
    /**
     * This method interacts with the parent activity
     * @param Parent activity of this dialog
     */
    @Override
    public void onAttach(Activity parent) {
        super.onAttach(parent);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) parent;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(parent.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	/**
	 * Initialize the features and functions of the dialog
	 */
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title);
        builder.setItems(R.array.picSelect_array, new DialogInterface.OnClickListener(){
        		   public void onClick(DialogInterface dialog, int which) {
        			   if (which == 0){
        				   mListener.onSelectPicClick(ImageResponseDialog.this);
        			   }
        			   else if(which == 1){
        				   mListener.onTakePicClick(ImageResponseDialog.this);
        			   }
        			   else if(which == 2){
        				   onCancel(dialog);
        			   }
                   }
        	   });
        return builder.create();
    }
}