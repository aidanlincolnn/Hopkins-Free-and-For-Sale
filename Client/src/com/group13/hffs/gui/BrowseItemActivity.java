package com.group13.hffs.gui;

import java.util.ArrayList;

import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemType;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
/**
 * This activity allows users to browse Items, they can select a specific items to view more details
 * @author Aidan, Lucas, Shijian
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
		    ViewItemByTypeDialog newFragment = new ViewItemByTypeDialog();
		    newFragment.show(getFragmentManager(), "types");
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
	    ArrayList<ItemType> types = new ArrayList<ItemType>();
	    types.add(ItemType.BOOKS);
	    types.add(ItemType.CLOTHES);
	    types.add(ItemType.FURNITURE);
	    types.add(ItemType.ELECTRONICS);
	    types.add(ItemType.GAMES);
	    types.add(ItemType.MISC);
	    setItemTypes(types);
	}
	
	/**
	 * This method sets type of items for the checkbox
	 * @param This represents the type being checked
	 */
	@SuppressWarnings("unchecked")
	public void setItemTypes(ArrayList<ItemType> checked) {
		GetItemsTask itemTask = new GetItemsTask();
		itemTask.execute(checked);
	}
	/**
	 * This class fetch all the items from server to the client. It also constructs the whole grid layout of the client
	 * @author Aidan, Lucas, Shijian
	 *
	 */
	public class GetItemsTask extends AsyncTask<ArrayList<ItemType>, Void, ArrayList<Item>> {
		@Override
		protected ArrayList<Item> doInBackground(ArrayList<ItemType>... params) {
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
			customGridAdapter = new CustomGridViewAdapter(mContext, R.layout.item_unit, gridArray);
			
			gridView.setAdapter(customGridAdapter);
		    gridView.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		            Intent intent = new Intent(getBaseContext(), ItemPage.class);
		            intent.putExtra("Item", gridArray.get(position));
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
