package com.group13.hffs.gui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * This class represents a dialog when user wants to make an offer
 * @author albatross
 *
 */
public class MakeOfferDialog extends DialogFragment {
	private EditText price;
	/**
	 * This method initialize the layout and functions of the dialog
	 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_make_offer, null);
        price = (EditText) view.findViewById(R.id.offerPay);
        builder.setView(inflater.inflate(R.layout.dialog_make_offer, null))
        	   .setMessage(R.string.dialog_make_offer)
               .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   if(price.getText().toString() != null)
                	   {
                		   ((ItemPage)getActivity()).sendOffer(Integer.parseInt(price.getText().toString()));
                	   }
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                   }
               });
        return builder.create();
    }
}
