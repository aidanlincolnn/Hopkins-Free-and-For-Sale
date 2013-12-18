package com.group13.hffs.gui;

import java.util.ArrayList;

import oose.group13.hffs.data.User;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
/**
 * This activity gets and display offers for a user
 * @author Aidan, Lucas, Shijian
 *
 */
public class ViewOffersActivity extends ExpandableListActivity{
	
	/** this is the list of the parent items or the category headers */
	private ArrayList<String> mParentItems = new ArrayList<String>();
	/** this is an arraylist of objects that will hold ArrayList<Offer> objects. */
	private ArrayList<Object> mChildItems = new ArrayList<Object>();
	/** The adapter used for the expandable list view */
	private ExpandableListAdapter mAdapter;
	/** the actual expandable list view that uses the adapter to populate the view */
	private ExpandableListView mListView;
	
	@Override
	/**
	 * Constructor of this activity
	 * @param Bundle savedInstanceState we are not useing this.
	 */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offers);
 
        mListView = getExpandableListView();
        mListView.setDividerHeight(2);
        mListView.setGroupIndicator(null);
        mListView.setClickable(true);
        
        mParentItems.add("Loading");
        mParentItems.add("Loading");
        mParentItems.add("Loading");
        setupActionBar();
        
        mAdapter = new ExpandableListAdapter(mParentItems, mChildItems);
        
        mAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        
        mListView.setAdapter(mAdapter);
        mListView.setOnChildClickListener(this);
    	UserOffersTask task = new UserOffersTask(this);
    	
    	task.execute(MainMenuActivity.mUser);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	/**
	 * Used to create the actionbar for the menu and the back button
	 */
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	/**
	 * This method adds the parent text to the extended list view
	 */
    public void setGroupParents() 
    {
    	mParentItems.clear();
        mParentItems.add("Sent Offers");
        mParentItems.add("Received Offers");
        mParentItems.add("Accepted");
    }
    
    public class UserOffersTask extends AsyncTask<User, Void, User> {

    	private Activity callingActivity;
    	/**
    	 * This is the constructor for this task. This is used to get the 
    	 * context that is needed for the ExpandableListViewAdapter. 
    	 * @param cxt context of the activity that is calling this.
    	 */
    	public UserOffersTask(Activity cxt) {
    		callingActivity = cxt;
    	}
    	
		@Override
		/**
		 * doInBackground method this is what is actually done on another thread.
		 * @param User... params this is the user that is currently calling this method
		 * @return returns the user along with the objects that is stored on the server.
		 */
		protected User doInBackground(User... params) {
			User u = StartActivity.mWebServices.getOffers(params[0]);
			return u;
		}
    	
		/**
		 * Called with the return of doInBackground as a parameter after it is done
		 * @param User user the user object that holds all the offers for that user.
		 */
		@Override
		protected void onPostExecute(User user) {
			mParentItems.clear();
			setGroupParents();
			mChildItems.clear();
			mChildItems.add(user.getBuyOffers());
			mChildItems.add(user.getSellOffers());
			mChildItems.add(user.getCompletedOffers());
			mAdapter = new ExpandableListAdapter(mParentItems, mChildItems);
	        
	        mAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), callingActivity);
	        mListView.setAdapter(mAdapter);
		}
    }
}
