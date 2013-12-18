package com.group13.hffs.gui;

import java.util.ArrayList;

import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.OfferState;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
/**
 * This class provides the adapter to construct the expandable list view for offers
 * @author Aidan, Lucas, Shijian
 *
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private Activity mActivity;
	private ArrayList<Object> mChildItems;
	private LayoutInflater mInflater;
	private ArrayList<String> mParentItems;
	private ArrayList<Offer> mChild;
	/**
	 * Constructor takes in an arraylist that represents the category titles and then the children that will be displayed in those categories
	 * @param parents Category titles when clicked this will expand to show the children
	 * @param children the children that are under each category
	 */
	public ExpandableListAdapter(ArrayList<String> parents, ArrayList<Object> children) {
		mParentItems = parents;
		mChildItems = children;
	}
	
	/**
	 * Sets the inflater to used for the different views that are in the expandable list
	 * Also sets the base activity context with the activity param
	 * @param inflater from the activity that calls this so that it can create the views needed
	 * @param activity base activity used for different functions
	 */
	public void setInflater(LayoutInflater inflater, Activity activity) {
		mInflater = inflater;
		mActivity = activity;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * This actually makes the view that represents the child item.
	 * This creates a textview that holds the name of the items
	 * @param groupPosition the category or group that this view is in
	 * @param childPosition the position of the child in the group.
	 */
	public View getChildView(int groupPosition, final int childPosition,
		boolean isLastChild, View convertView, ViewGroup parent) {
		mChild = (ArrayList<Offer>) mChildItems.get(groupPosition);
		
		TextView textView = null;
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.offer_view, null);
		}
		
		textView = (TextView) convertView.findViewById(R.id.offerDescription);
		textView.setText("" + mChild.get(childPosition).getmItemName());
		
		// changes the color of the text based on the state of the offer
		if (mChild.get(childPosition).getmState()==OfferState.COUNTERED){ 
			textView.setTextColor(Color.parseColor("#9B870C"));
		}
		else if (mChild.get(childPosition).getmState()==OfferState.OPEN){
			textView.setTextColor(Color.parseColor("#33CC00"));
		}
		else if (mChild.get(childPosition).getmState()==OfferState.DECLINED){
			textView.setTextColor(Color.RED);
		}
		else if (mChild.get(childPosition).getmState()==OfferState.ACCEPTED){
			textView.setTextColor(Color.BLUE);
		}
		
		convertView.setOnClickListener(new OnClickListener() {
			private Offer mOffer = mChild.get(childPosition);
			@Override
			public void onClick(View view) {
				System.out.println("Clicked");
				Intent intent = new Intent(mActivity, OfferActivity.class);
				intent.putExtra("offer", mOffer);
				mActivity.startActivity(intent);
			}
		});
		return convertView;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Returns the total number of children in the group
	 * @param groupPosition the group that it is finding the count for
	 * @param the number of children int he group.
	 */
	public int getChildrenCount(int groupPosition) {
		return ((ArrayList<Offer>) mChildItems.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	/**
	 * gets the number of groups
	 * @return number of groups
	 */
	public int getGroupCount() {
		return mParentItems.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	/**
	 * creates the views for the categories. This is what you click in order to expand the item.
	 * @param groupPosition the Category
	 * @param isExpanded if true then it shows all of the children views.
	 */
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.offer_category_view, null);
		}
		((CheckedTextView) convertView).setText(mParentItems.get(groupPosition));
		((CheckedTextView) convertView).setChecked(isExpanded);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
