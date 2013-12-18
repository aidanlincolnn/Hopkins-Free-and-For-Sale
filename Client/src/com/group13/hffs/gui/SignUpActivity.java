package com.group13.hffs.gui;

import oose.group13.hffs.data.User;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/**
 * This activity allows a user to sign up for the application
 * @author Aidan, Lucas, Shijian
 *
 */
public class SignUpActivity extends Activity {

	private EditText mEmail, mPassword, mCPassword, mName, mUserName, mPhone;
	private String email, password, cPassword, name, userName, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        mEmail = (EditText) findViewById(R.id.SignupEmail);
        mCPassword = (EditText) findViewById(R.id.SignupCPassword);
        mPassword = (EditText) findViewById(R.id.SignupPassword);
        mName = (EditText) findViewById(R.id.SignupName);
        mUserName = (EditText) findViewById(R.id.SignupUsername);
        mPhone = (EditText) findViewById(R.id.SignupPhone);
        // Show the Up button in the action bar.
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	getActionBar().setDisplayHomeAsUpEnabled(false);
        }
        
    }

    /**
     * This is called when the enter button is pressed if it is empty nothing happens and it returns
     * If it is not empty then the task to sign up is started
     * @param view This is not used
     */
    public void enter(View view) {
    	email = mEmail.getText().toString();
    	password = mPassword.getText().toString();
    	cPassword = mCPassword.getText().toString();
    	name = mName.getText().toString();
    	userName = mUserName.getText().toString();
    	phone = mPhone.getText().toString();
    	if(!checkValidLogin(email, password, cPassword,name,userName,phone)) {
    		return;
    	}
    	UserSignUpTask task = new UserSignUpTask();
    	task.execute();
    }
    
    /**
     * Launches the MainMenuActivity
     */
    public void goToMain() {
    	Intent intent = new Intent(this, MainMenuActivity.class);
    	startActivity(intent);
    }
    
    
    /**
     * Checks to see if the login is valid
     * @param email the Email address that was entered
     * @return true if it is valid false if not.
     */
    public boolean checkValidLogin(String email, String password, String cPassword, String name, String userName, String phone) {
    	try{
	    	String check = email.substring(email.length() - 8, email.length());
	    	if(check.equals("@jhu.edu") && (password.equals(cPassword)) && !(name.equals("") && !(userName.equals("") && !(phone.equals(""))))) {
	    		return true;
	    	}
    	}
    	catch(StringIndexOutOfBoundsException e){
    		Log.e("ERROR","Input is invalid");
    	}
    	Toast.makeText(this, "You Must Fill Out All Fields", Toast.LENGTH_LONG).show();
    	return false;
    }
    
    /**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserSignUpTask extends AsyncTask<Void, Void, User> {
		@Override
		protected User doInBackground(Void... params) {
			User user = null;
			try {
				System.out.println(StartActivity.mWebServices);
				System.out.println(" mutha " + mEmail);
				user = StartActivity.mWebServices.addUser(StartActivity.mWebServices.getNewUserId()+1, userName, name, email, password, phone,true);
			}
			catch (NullPointerException e) {
				Log.e("Error", "One of the text fields was null" + e);
			}
			return user;
		}

		@Override
		protected void onPostExecute(User user) {
			if (user != null){
				MainMenuActivity.mUser = user;
				goToMain();
				finish();
			}
			else {
				Toast.makeText(SignUpActivity.this, "There is already an account with that email", Toast.LENGTH_LONG).show();
				Log.d("DEBUG","THERE IS ALREADY AN ACCOUNT WITH THAT EMAIL");
			}
		}

	}
}
