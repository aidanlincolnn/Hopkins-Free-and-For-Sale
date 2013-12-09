package com.group13.hffs.gui;

import oose.group13.hffs.data.User;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
/**
 * activity to sign up for the application
 * @author aidanfowler
 *
 */
public class SignUpActivity extends Activity {

	private EditText mEmail, mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        mEmail = (EditText) findViewById(R.id.SignupEmail);
        mPassword = (EditText) findViewById(R.id.SignupPassword);
        // Show the Up button in the action bar.
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This is called when the enter button is pressed if it is empty nothing happens and it returns
     * If it is not empty then the task to sign up is started
     * @param view This is not used
     */
    public void enter(View view) {
    	String email = mEmail.getText().toString();
    	if(!checkValidLogin(email)) {
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
    public boolean checkValidLogin(String email) {
    	String check = email.substring(email.length() - 8, email.length());
    	if(check.equals("@jhu.edu")) {
    		System.out.println("@jhu.edu");
    		return true;
    	}
		System.out.println(check);
    	return false;
    }
    
    /**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserSignUpTask extends AsyncTask<Void, Void, User> {
		@Override
		protected User doInBackground(Void... params) {
			// Simulate network access.
			//Thread.sleep(2000);
			User user = null;
			try {
				System.out.println(StartActivity.mWebServices);
				System.out.println(" mutha " + mEmail);
				user = StartActivity.mWebServices.addUser(StartActivity.mWebServices.getUserId(), "hello","Doesnt matter", mEmail.getText().toString(), mPassword.getText().toString(), "7375");
			}
			catch (NullPointerException e) {
				System.out.println("Null exception was caught " + e);
			}

			// TODO: register the new account here.
			return user;
		}

		@Override
		protected void onPostExecute(User user) {
			
			MainMenuActivity.mUser = user;
			goToMain();
			finish();
		}

	}
}
