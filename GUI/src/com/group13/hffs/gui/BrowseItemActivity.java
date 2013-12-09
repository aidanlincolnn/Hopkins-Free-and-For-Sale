package com.group13.hffs.gui;

import java.util.ArrayList;

import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemType;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
/**
 * Browse Items Page, can select items to view more details
 * @author aidanfowler
 *
 */
public class BrowseItemActivity extends Activity {
	GridView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	CustomGridViewAdapter customGridAdapter;
	Context mContext;
/**
 * This method will invoke a dialog to specify the type of items you want to see
 */
	public void selectType() {
		    //ViewItemByTypeDialog newFragment = new ViewItemByTypeDialog();
		    //newFragment.show(getFragmentManager(), "types");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_browse);
		mContext = this;
		
		Button type = (Button)findViewById(R.id.ViewByTypeButton);
        type.setOnClickListener(new Button.OnClickListener(){
        	@Override
        	public void onClick(View view) {
        		selectType();
        	}
        });
		
		gridView = (GridView) findViewById(R.id.ItemListGridView);
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.item_unit, gridArray);
		
		gridView.setAdapter(customGridAdapter);
	    gridView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Intent intent = new Intent(BrowseItemActivity.this, ItemPage.class);
	            startActivity(intent);
	        }
	    });
	    gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
	    	public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
	    		Toast.makeText(BrowseItemActivity.this, "Long Description", Toast.LENGTH_LONG).show();
	            return true;
	        }
	    });
	    GetItemsTask itemTask = new GetItemsTask();
	    ArrayList<ItemType> types = new ArrayList<ItemType>();
	    types.add(ItemType.BOOKS);
	    itemTask.execute(types);
	}
	
	/**
	 * This method sets type of items for the checkbox
	 * @param This represents the type being checked
	 */
	public void setItemTypes(ArrayList<ItemType> checked) {
		GetItemsTask itemTask = new GetItemsTask();
		itemTask.execute(checked);
	}
	/**
	 * This class fetch all the items from server to the client. It also constructs the whole grid layout of the client
	 * @author albatross
	 *
	 */
	public class GetItemsTask extends AsyncTask<ArrayList<ItemType>, Void, ArrayList<Item>> {
		@Override
		protected ArrayList<Item> doInBackground(ArrayList<ItemType>... params) {
			// TODO Auto-generated method stub
			ArrayList<ItemType> itemTypes = params[0];
			ArrayList<Item> items = new ArrayList<Item>();
			for(int i = 0; i < itemTypes.size(); i++)
			{
				items.addAll((ArrayList<Item>) StartActivity.mWebServices.getForSaleItems(MainMenuActivity.mUser, itemTypes.get(i)));
			}
			return items;
		}

		@Override
		protected void onPostExecute(ArrayList<Item> items) {
			gridArray = items;
			gridView = (GridView) findViewById(R.id.ItemListGridView);
			customGridAdapter = new CustomGridViewAdapter(mContext, R.layout.item_unit, items);
			
			gridView.setAdapter(customGridAdapter);
		    gridView.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		            Intent intent = new Intent(BrowseItemActivity.this, ItemPage.class);
		            intent.putExtra("Item", gridArray.get(position));
		            Log.e("DEBUG","FIRED ON CLICK LISTENER");
		            startActivity(intent);
		        }
		    });
		    gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
		    	public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
		    		Toast.makeText(BrowseItemActivity.this, "Long Description", Toast.LENGTH_LONG).show();
		            return true;
		        }
		    });
		}
		
	}

}
