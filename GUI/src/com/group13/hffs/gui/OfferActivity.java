package com.group13.hffs.gui;


import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.OfferState;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * activity to view an individual offer 
 * @author aidanfowler
 *
 */
public class OfferActivity extends Activity{

	private static Offer mOffer;
	private EditText mCounterPrice;
	private TextView mDesc, mPrice, mUser;
	@Override
	/**
	 * creates the activity and sets the content
	 * @param savedInstanceState this isn't used
	 */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Bundle extras = getIntent().getExtras();
        mOffer = (Offer) extras.get("offer");
        if(mOffer == null)
        {
        	finish();
        }
        mDesc = (TextView) findViewById(R.id.ItemDescription);
        mPrice = (TextView) findViewById(R.id.Price);
        mUser = (TextView) findViewById(R.id.OtherParty);
        mCounterPrice = (EditText) findViewById(R.id.CouterEditText);
        
        mDesc.setText("" + mOffer.getmItemName());
        mPrice.setText("" + mOffer.getmPrice());
        mUser.setText("" + mOffer.getmSender());
    }
	
	/**
	 * Called when the accept button is pressed
	 * @param view View of the button that is being pressed? This is not used
	 */
	public void accept(View view) {
		Offer toSend = createOfferToSend();
		toSend.setmState(OfferState.ACCEPTED);
		SendOffer task = new SendOffer();
		task.execute(toSend);
		finish();
	}
	
	/**
	 * Called with the onClick for the android button reject
	 * @param view This is not used
	 */
	public void reject(View view) {
		Offer toSend = createOfferToSend();
		toSend.setmState(OfferState.DECLINED);
		SendOffer task = new SendOffer();
		task.execute(toSend);
		finish();
	}
	
	/**
	 * Called when the button Counter is pressed.
	 * @param view This is not used
	 */
	public void counter(View view) {
		if(mCounterPrice.toString() == null) {
			Toast.makeText(this, "Enter Counter Offer", Toast.LENGTH_LONG).show();
		}
		else {
			Offer toSend = createOfferToSend();
			toSend.setmPrice(Integer.parseInt(mCounterPrice.toString()));
			SendOffer task = new SendOffer();
			task.execute(toSend, toSend);
			finish();
		}
	}
	
	/**
	 * Creates a clone of the offer that is currently being used. 
	 * @return Offer clone of the offer that is currently being observed
	 */
	public Offer createOfferToSend() {
		Offer send = new Offer();
		send.setOfferId(mOffer.getOfferId());
		send.setmItem(mOffer.getmItem());
		send.setmItemName(mOffer.getmItemName());
		send.setmPrice(mOffer.getmPrice());
		send.setmReceiver(mOffer.getmSender());
		send.setmSender(MainMenuActivity.mUser.getUserId());
		send.setmState(mOffer.getmState());
		return send;
	}
	
	public class SendOffer extends AsyncTask<Offer, Void, Void> {

		/**
		 * This is run on a different thread than the main UI thread. This is used for all of the network calls.
		 * gets a new offer Id and adds it to the offer then sends the offer to the server.
		 * @param params The offer that is being sent
		 */
		@Override
		protected Void doInBackground(Offer... params) {
			// TODO Auto-generated method stub
			Offer send = params[0];
			if(params.length > 1)
			{
				send.setmItem(StartActivity.mWebServices.getOfferId(MainMenuActivity.mUser));
			}
			StartActivity.mWebServices.sendOffer(MainMenuActivity.mUser, send);
			return null;
		}
	}
	
}
