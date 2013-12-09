package com.group13.hffs.gui;

import java.util.ArrayList;

import oose.group13.hffs.data.ItemType;
import oose.group13.hffs.data.ItemTypeUtil;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * This class represents the dialog when user wants to view items of some certain type(s) 
 * @author albatross
 *
 */
public class ViewItemByTypeDialog extends DialogFragment {
	/**
	 * Array of item types
	 */
	private int[] checkedTypes = new int[6];
	private ViewItemByTypeDialog mContext;
	/**
	 * Initialize the features and functions of the dialog
	 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	mContext = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.view_item_by_type);
        builder.setMultiChoiceItems(R.array.itemTypes_array, null,
                      new DialogInterface.OnMultiChoiceClickListener() {
        			@Override
        			public void onClick(DialogInterface dialog, int which,
        					boolean isChecked) {
        				if (isChecked) {
        					// If the user checked the item, add it to the selected items
        					checkedTypes[which] = 1;
        				} else {
        					// Else, if the item is already in the array, remove it 
        					checkedTypes[which] = 0;
        				}
        			}
        		});
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   ArrayList<ItemType> types = new ArrayList<ItemType>();
                	   for(int i = 0; i < checkedTypes.length; i++) {
                		   if(checkedTypes[i] == 1 )
                		   {
                			   types.add(ItemTypeUtil.getItemType(i));
                		   }
                	   }
                	   ((BrowseItemActivity) mContext.getActivity()).setItemTypes(types);
                   }
               });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   onCancel(dialog);
                   }
               });
        return builder.create();
    }
}