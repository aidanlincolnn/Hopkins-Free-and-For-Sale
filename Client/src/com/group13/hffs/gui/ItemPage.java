package com.group13.hffs.gui;

import oose.group13.hffs.client.PictureHelper;
import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.OfferState;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * This activity contains all info for an item selected from item list, allows user to make an offer for the item
 * @author Aidan, Lucas, Shijian
 *
 */
public class ItemPage extends Activity {

	private Item mItem;
/**
 * This method fires a dialog if user wants to make an offer, 
 */
    public void confirm() {
	    MakeOfferDialog newFragment = new MakeOfferDialog();
	    newFragment.show(getFragmentManager(), "confirm");
    }
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page);
        Button offer = (Button)findViewById(R.id.ItemPageOfferButton);
        offer.setOnClickListener(new Button.OnClickListener(){
        	@Override
        	public void onClick(View view) {
        		confirm();
        	}
        });
        
        Bundle extra = getIntent().getExtras();
        mItem = (Item) extra.get("Item");
        ImageView image = (ImageView) findViewById(R.id.ItemPagePic);
        TextView title = (TextView) findViewById(R.id.ItemPageTitle);
        TextView desc = (TextView) findViewById(R.id.ItemPageLongText);
        TextView date = (TextView) findViewById(R.id.ItemPageDatePosted);
        
        if (mItem.getmPic()!= null){
        	image.setImageBitmap(PictureHelper.saveImageFromBytes((mItem.getmPic())));
        }
        if (mItem.getmTitle() != null)
        {
        	title.setText(mItem.getmTitle());
        }
        if (mItem.getmDescriptionL() != null)
        {
        	desc.setText(mItem.getmDescriptionL());
        }
        if (mItem.getmDatePosted() != null)
        {
        	date.setText(mItem.getmDatePosted());
        }
    }
    
    public void sendOffer(int price) {
    	if(price < 0) {
    		return;
    	}
    	Offer toSend = new Offer(0, price, mItem.getitemId(), mItem.getmTitle(),
    			MainMenuActivity.mUser.getUserId(), mItem.getmOwnerId(), OfferState.OPEN);
    	OfferTask sendOffer = new OfferTask();
    	sendOffer.execute(toSend);
    }
    
    public class OfferTask extends AsyncTask<Offer, Void, Void> {

		@Override
		protected Void doInBackground(Offer... params) {
			Offer toSend = params[0];
			toSend.setOfferId(StartActivity.mWebServices.getNewOfferId(MainMenuActivity.mUser));
			Log.d("DEBUG","SENDING OFFER:"+toSend.getmItemName()+ " "+ toSend.getmSender()+ " " +toSend.getmReceiver());
			StartActivity.mWebServices.sendOffer(MainMenuActivity.mUser, toSend);
			return null;
		}
    	
    }
}
