package com.group13.hffs.gui;

import oose.group13.hffs.client.WebServices;
import oose.group13.hffs.data.User;
import oose.group13.hffs.transport.SendMessage;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
/**
 * start the application 
 * @author aidanfowler
 *
 */
public class StartActivity extends Activity {

	/** This this is the singleton webservice object that handles all calls to the server. */
	protected static WebServices mWebServices;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		StartupWebServicesTask webStartup = new StartupWebServicesTask();
		webStartup.execute();
	}

	/**
	 * Sets up the actionbar menu
	 * @return boolean true if the menu was setup
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	/**
	 * Called when the login button is pressed. This launches the LogInActivity
	 * @param view This is not used
	 */
	public void logIn(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, LogInActivity.class);
		startActivity(intent);
	}
	
	/**
	 * This is called when the signup button is pressed. This launches the SignUpActivity
	 * @param view
	 */
	public void signUp(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
	}
	
	public class StartupWebServicesTask extends AsyncTask<Void, Void, Void> {

		/**
		 * this is called on a different thread. This handles setting up webservices.
		 */
		@Override
		protected Void doInBackground(Void... params) {
			mWebServices = new WebServices();
			mWebServices.setupSocket();
		    SendMessage t = new SendMessage(new User(),"Message: Client Connected To Server");
		    mWebServices.sendMessage(t);
		    t = null;
		    while (t == null){
		    	t = (SendMessage) mWebServices.readMessage();
		    }
			return null;
		}
		
	}

}
