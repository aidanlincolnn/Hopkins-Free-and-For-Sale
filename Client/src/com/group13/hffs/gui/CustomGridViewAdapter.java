package com.group13.hffs.gui;

import java.util.ArrayList;

import oose.group13.hffs.client.PictureHelper;

import com.group13.hffs.gui.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import oose.group13.hffs.data.Item;
/**
 * Helper for browse items to display the list of items
 * @author Aidan, Lucas, Shijian
 *
 */
public class CustomGridViewAdapter extends ArrayAdapter<Item> {
	Context context;
	int layoutResourceId;
	ArrayList<Item> data = new ArrayList<Item>();
/**
 * Constructor of grid view adapter
 * @param Context for the adapter 
 * @param The layout XML resource
 * @param List of incoming items
 */
	public CustomGridViewAdapter(Context context, int layoutResourceId, ArrayList<Item> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
/**
 * This method set the layout of a grid unit in the grid view
 * @return Returns the unit containing one item
 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.item_title);
			holder.shortDes = (TextView) row.findViewById(R.id.item_description);
			holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		Item item = data.get(position);
		holder.txtTitle.setText(item.getmTitle());
		holder.shortDes.setText(item.getmDescriptionS());
		Bitmap temp = PictureHelper.saveImageFromBytes(item.getmPic());
		temp = Bitmap.createScaledBitmap(temp, 100, 100, false);
		holder.imageItem.setImageBitmap(temp);
		return row;

	}
/**
 * A holder for item info to be used as an icon
 * @author Aidan, Lucas, Shijian
 *
 */
	static class RecordHolder {
		TextView txtTitle;
		ImageView imageItem;
		TextView shortDes;
	}
}