package com.group13.hffs.gui;

import oose.group13.hffs.client.WebServices;
import oose.group13.hffs.data.User;
import oose.group13.hffs.transport.SendMessage;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
/**
 * This activity is the start activity for the application, you can log in or sign up
 * @author Aidan, Lucas, Shijian
 *
 */
public class StartActivity extends Activity {

	/** This this is the singleton webservice object that handles all calls to the server. */
	protected static WebServices mWebServices = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		if (mWebServices == null){
			StartupWebServicesTask webStartup = new StartupWebServicesTask();
			webStartup.execute();
		}
	}
	
	/**
	 * Called when the login button is pressed. This launches the LogInActivity
	 * @param view This is not used
	 */
	public void logIn(View view) {
		Intent intent = new Intent(this, LogInActivity.class);
		startActivity(intent);
	}
	
	/**
	 * This is called when the signup button is pressed. This launches the SignUpActivity
	 * @param view
	 */
	public void signUp(View view) {
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
	}
	
	
	/**
	 * This gets called when the application closes
	 */
	@Override 
	public void onDestroy(){
		super.onDestroy();
		Log.e("DEBUG","ONDESTROY");
		if (isFinishing()){
			Log.e("DEBUG","ISFINISHING");
			mWebServices.sendMessage(new SendMessage(new User(), "bye"));
		}
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
