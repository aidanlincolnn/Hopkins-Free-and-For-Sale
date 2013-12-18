package com.group13.hffs.gui;

import oose.group13.hffs.data.User;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This activity allows a user to change the information for their account
 * @author Aidan, Lucas, Shijian
 *
 */
public class AccountActivity extends Activity {

	private TextView name, email;
	private EditText uName, phone, password;
	protected static User mUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		name = (TextView)findViewById(R.id.accountName);
		uName = (EditText)findViewById(R.id.accountUName);
		email = (TextView)findViewById(R.id.accountEmail);
		phone = (EditText)findViewById(R.id.accountPhone);
		password = (EditText)findViewById(R.id.accountPW);
		mUser = MainMenuActivity.mUser;
		name.setText(mUser.getmName());
		email.setText(mUser.getmEmail());
		uName.setText(mUser.getmUserName());
		password.setText(mUser.getmPassword());
		phone.setText(mUser.getmPhoneNumber());
	}

	
	/**
	 * The method to apply changes upon your user name, password and phone. Note that the name and email can't be changed since they've been fixed when you signed up.
	 * @param view
	 * Not used
	 */
	public void makeChange(View view){
		if (uName.getText().toString() == null || uName.getText().toString().equals("")) {
			uName.setError("Can't be blank");
		}
		else if (password.getText().toString() == null || password.getText().toString().equals("")) {
			password.setError("Must have password");
		}
		else if (phone.getText().toString() == null || phone.getText().toString().length() != 10 ) {
			phone.setError("Please provide your phone number");
		}
		else{
		ChangeInfoTask task = new ChangeInfoTask();
		task.execute();
		}
		
	}

	/**
	 * This subclass does the actual changing of account information
	 * @author Aidan, Lucas, Shijian
	 *
	 */
	public class ChangeInfoTask extends AsyncTask<Void, Void, User>{

		@Override
		protected User doInBackground(Void... params) {
			User user = null;
			try {
				user = StartActivity.mWebServices.addUser(mUser.getUserId(), uName.getText().toString(), mUser.getmName(), mUser.getmEmail(), password.getText().toString(), phone.getText().toString(), false);
			}
			catch (NullPointerException e) {
				Log.e("Error caught", "One of the text fields was null" + e.getMessage());
			}
			
			return user;
		}
		
		@Override
		protected void onPostExecute(User user) {
			if (user != null){
				MainMenuActivity.mUser = user;
			}
			finish();
			
		}
		
	}
}
