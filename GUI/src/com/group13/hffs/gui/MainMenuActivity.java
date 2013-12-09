package com.group13.hffs.gui;

import oose.group13.hffs.data.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
/**
 * Activity which displays the main menu with the post item, browse items, and browse offers buttons
 * @author aidanfowler
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
     * creates the menu and the back button in the action bar
     * @param menu to add things to
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    /**
     * Moves the user back to the LogInActivity
     * @param view This is not used
     */
	public void signOut(View view) {
	    // Do something in response to button

		Intent intent = new Intent(this, LogInActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Launched when the post Item button is pressed
	 * @param view This is the button that is pressed. 
	 */
	public void post(View view) {
	    // Do something in response to button
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
	 * Launches the Browse Item activity
	 * @param view view that is the button that has the onClick that launches this method.
	 */
	public void browseItem(View view) {
		Intent intent = new Intent(this, BrowseItemActivity.class);
		startActivity(intent);
	}
}
