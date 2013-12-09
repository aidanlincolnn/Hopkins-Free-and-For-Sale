package com.group13.hffs.gui;

import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.OfferState;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
/**
 * An activity containing all info of an item selected from item list, allows making offer to this item
 * @author albatross
 *
 */
public class ItemPage extends Activity {

	private Item mItem;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_page, menu);
        return true;
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
			toSend.setOfferId(StartActivity.mWebServices.getOfferId(MainMenuActivity.mUser));
			StartActivity.mWebServices.sendOffer(MainMenuActivity.mUser, toSend);
			return null;
		}
    	
    }
}
