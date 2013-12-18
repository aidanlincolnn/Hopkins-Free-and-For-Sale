package com.group13.hffs.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * This activity sends an email containing corresponding passwords to users who pressed recover password button
 * @author Aidan, Lucas, Shijian
 *
 */
public class RecoverPasswordActivity extends Activity {
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private ForgotPasswordTask mRecoverTask = null;
	private String mEmail;
	private EditText mEmailView;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_recover_password);
        
        mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
        mEmailView = (EditText) findViewById(R.id.Recover);
        mEmailView.setText(mEmail);
        
        findViewById(R.id.RPbutton1).setOnClickListener(
        		new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						sendEmail();
					}
				});
    }
	
	public void sendEmail(){
		if (mRecoverTask != null){
			return;
		}
		
		mEmailView.setError(null);
		mEmail = mEmailView.getText().toString();
		
		Log.e("DEBUG","EMAIL: "+ mEmail);
		boolean cancel = false;
		View focusView = null;
		
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@jhu.edu")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		
		if (cancel){
			// There was an error; dont attempt to send the recovery email
			focusView.requestFocus();
		}else {
			mRecoverTask = new ForgotPasswordTask();
			mRecoverTask.execute(mEmail);
			
		}
		
	}
	
	/**
	 * Launch the login screen after sending email
	 */
	public void enter(){
		//Do something in response to button
		Intent intent = new Intent(this,LogInActivity.class);
		startActivity(intent);
	}

	public class ForgotPasswordTask extends AsyncTask<String,Void,Void>{

		@Override
		protected Void doInBackground(String... arg0) {
			StartActivity.mWebServices.recoverPassword(arg0[0]);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void v){
			enter();
			
		}
	}
}
