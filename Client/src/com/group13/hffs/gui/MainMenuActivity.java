package com.group13.hffs.gui;

import oose.group13.hffs.data.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * This activity displays the main menu with the post item, browse items, and browse offers buttons
 * @author Aidan, Lucas, Shijian
 *
 */
public class MainMenuActivity extends Activity {

	protected static User mUser;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    /**
     * Moves the user back to the LogInActivity
     * @param view This is not used
     */
	public void signOut(View view) {
		Intent intent = new Intent(this, LogInActivity.class);
		startActivity(intent);
		finish();
	}
	
	/**
	 * Launched when the post Item button is pressed
	 * @param view This is the button that is pressed. 
	 */
	public void post(View view) {
		Intent intent = new Intent(this, PostItemActivity.class);
		startActivity(intent);
	}
    
	/**
	 * This is launched when the view offers button is pressed.
	 * @param view This is the button that is pressed and its view. This is not used
	 */
	public void viewOffers(View view) {
		Intent intent = new Intent(this, ViewOffersActivity.class);
		startActivity(intent);
	}
	
	/**
	 * This is launched when the browse items button is pressed.
	 * @param view view that is the button that has the onClick that launches this method.
	 */
	public void browseItem(View view) {
		Intent intent = new Intent(this, BrowseItemActivity.class);
		startActivity(intent);
	}
	
	/**
	 * This is launched when the manage account button is pressed.
	 * @param view The button that's pressed.
	 */
	public void accountManagement(View view){
		Intent intent = new Intent(this, AccountActivity.class);
		startActivity(intent);
	}
}
