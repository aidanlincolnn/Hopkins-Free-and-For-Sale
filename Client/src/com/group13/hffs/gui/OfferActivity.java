package com.group13.hffs.gui;


import java.util.ArrayList;
import java.util.List;

import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemState;
import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.OfferState;
import oose.group13.hffs.data.User;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * This activity allows a user to view an individual offer 
 * @author Aidan, Lucas, Shijian
 *
 */
public class OfferActivity extends Activity{

	private static Offer mOffer;
	private EditText mCounterPrice;
	private TextView mDesc, mUser1, mUser2,message;
	private Button mAccept, mReject, mCounter;
	private Item item;
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
        
        
        // One User For Seller One For Buyer
        mUser1 = (TextView) findViewById(R.id.OtherParty1);
        mUser2 = (TextView) findViewById(R.id.OtherParty2);
        
        mCounterPrice = (EditText) findViewById(R.id.CounterEditText);
        //Message to Display for Competed Transactions
        message = (TextView) findViewById(R.id.EndMessage);
 
        mAccept = (Button) findViewById(R.id.AcceptOffer);
        mReject = (Button) findViewById(R.id.RejectOffer);
        mCounter = (Button) findViewById(R.id.CounterOffer);

        //Get the item related to the offer
        GetItemTask getItem = new GetItemTask();
        getItem.execute(mOffer.getmItem());
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Log.e("ERROR","INTERRUPTED EXCEPTION MAKING THREAD SLEEP");
		}
        
        //Set transaction complete message and item information
        if (mOffer.getmState() == OfferState.ACCEPTED){
        	mDesc.setText("Transaction Completed\n\n"+"Item: " + item.getmTitle() +"\nShort: "+item.getmDescriptionS()+ "\nLong: "+item.getmDescriptionL()+"\n\nPrice: $" + mOffer.getmPrice());
        	message.setText("Please Contact Seller/Buyer to Set Up Exchange");
        }
        //Just use the item name for sent / received offers
        else{
        	mDesc.setText("Offer For Item: " + mOffer.getmItemName() + "\n\nPrice: $" + mOffer.getmPrice());
        }
        
        //Get the users and set the text fields to their respective usernames and information
        SetUserTask getUser = new SetUserTask();
        hideButtons();
        getUser.execute(mOffer.getmReceiver(),mOffer.getmSender());
    }
	
	/**
	 * Hide the buttons for all but received offers that are not delcined
	 */
	public void hideButtons() {
		if (mOffer.getmState() == OfferState.DECLINED 
				|| mOffer.getmState() == OfferState.ACCEPTED 
				|| mOffer.getmReceiver() != MainMenuActivity.mUser.getUserId()) {
			mAccept.setVisibility(View.GONE);
			mReject.setVisibility(View.GONE);
			mCounter.setVisibility(View.GONE);
			mCounterPrice.setVisibility(View.GONE);
		}
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
		Boolean send = true;
	
		Offer toSend = createOfferToSend();
		Offer toClose = createOfferToSend();
		
		try{
			toSend.setmPrice(Integer.parseInt(mCounterPrice.getText().toString()));
		}
		catch(Exception e){
			Log.d("DEBUG","PRICE WAS NOT VALID");
			Toast.makeText(this, "Counter Offer Value Must Be An Integer", Toast.LENGTH_LONG).show();
			send = false;
		}
		if (send){
			RemoveOffer removeTask = new RemoveOffer();
			removeTask.execute(toClose);
			toSend.setmState(OfferState.COUNTERED);
			SendOffer task = new SendOffer();
			task.execute(toSend);
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
			
			Offer send = params[0];
			//Set the item state to sold if the offer is accepted.
			if (send.getmState() == OfferState.ACCEPTED){
				item.setmAvailability(ItemState.SOLD);
				StartActivity.mWebServices.addItem(MainMenuActivity.mUser, item);
			}
			else if (send.getmState() == OfferState.DECLINED){
				send.setmReceiver(mOffer.getmReceiver());
				send.setmSender(mOffer.getmSender());
			}
			else if (send.getmState() == OfferState.COUNTERED){
				send.setOfferId(StartActivity.mWebServices.getNewOfferId(MainMenuActivity.mUser));
			}
			
			StartActivity.mWebServices.sendOffer(MainMenuActivity.mUser, send);
			return null;
		}
	}
	
	public class RemoveOffer extends AsyncTask<Offer, Void, Void> {

		/**
		 * This is run on a different thread than the main UI thread. This is used for all of the network calls.
		 * gets a new offer Id and adds it to the offer then sends the offer to the server.
		 * @param params The offer that is being sent
		 */
		@Override
		protected Void doInBackground(Offer... params) {
			Offer send = params[0];
			StartActivity.mWebServices.removeOffer(MainMenuActivity.mUser, send);
			return null;
		}
	}
	
	public class SetUserTask extends AsyncTask<Long, Void, List<User>> {

		/**
		 * Makes a call to the server in order to get the user that corresponds to the user Id
		 * @param Long user id
		 * @return User that corresponds to the user id
		 */
		@Override
		protected List<User> doInBackground(Long... params) {
			List<User> u1u2 = new ArrayList<User>(2);
			u1u2.add(StartActivity.mWebServices.getUserById(params[0]));
			u1u2.add(StartActivity.mWebServices.getUserById(params[1]));
			return u1u2;
		}
		
		/**
		 * Correctly changes the user text to have the username not the id
		 * @param User that is returned from doInBackground
		 */
		@Override
		protected void onPostExecute(List<User> user) {
			String text = "";
			String text2 = "";
			
			if (user.get(0).getUserId() == item.getmOwnerId()){
				text = "Seller: " + user.get(0).getmUserName();
				text2 = "Buyer: " + user.get(1).getmUserName();
			}
			else {
				text = "Buyer: " + user.get(0).getmUserName();
				text2 = "Seller: " + user.get(1).getmUserName();
			}
			if (mOffer.getmState() == OfferState.ACCEPTED)
			{
				text += "\n" + "Name: " + user.get(0).getmName() + "\n" + "Email: " + user.get(0).getmEmail() + "\n" + "Phone: " + user.get(0).getmPhoneNumber();
				text2 += "\n" + "Name: " + user.get(1).getmName() + "\n" + "Email: " + user.get(1).getmEmail() + "\n" + "Phone: " + user.get(1).getmPhoneNumber();
			}
			if (mOffer.getmState() == OfferState.DECLINED)
			{
				text2 += "\n\n" + "Offer Rejected";
			}
			if (mOffer.getmState() == OfferState.COUNTERED)
			{
				text2 += "\n\n" + "Offer Countered";
			}
			mUser1.setText(text);
			mUser2.setText(text2);
		}
		
	}
	
	public class GetItemTask extends AsyncTask<Long, Void, Void> {

		/**
		 * Makes a call to the server in order to get the user that corresponds to the user Id
		 * @param Long user id
		 * @return User that corresponds to the user id
		 */
		@Override
		protected Void doInBackground(Long... params) {
			Item gotItem =  StartActivity.mWebServices.getItemById(params[0]);
			item = gotItem;
			return null;
		}
		
	}
}
