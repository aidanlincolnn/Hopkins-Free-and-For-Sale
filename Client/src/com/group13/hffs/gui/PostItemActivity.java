package com.group13.hffs.gui;

import java.io.FileNotFoundException;
import java.util.Date;

import oose.group13.hffs.client.PictureHelper;
import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemState;
import oose.group13.hffs.data.ItemTypeUtil;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * This is the activity that allows a user to post an item to the application 
 * @author Aidan, Lucas, Shijian
 *
 */
public class PostItemActivity extends Activity implements ImageResponseDialog.NoticeDialogListener{

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
	ImageView picView;
	Bitmap pic;
	Bitmap rePic;
	EditText title, shortDesc, longDesc;
	Spinner type;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);
        picView = (ImageView) findViewById(R.id.ItemPagePic);
        title = (EditText) findViewById(R.id.PIeditText1);
        shortDesc = (EditText) findViewById(R.id.PIeditText3);
        longDesc = (EditText) findViewById(R.id.PIeditText2);
        setupActionBar();
        addspinner();
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

    /**
     * shows the picture
     */
    public void selectPic() {
	    ImageResponseDialog newFragment = new ImageResponseDialog();
	    newFragment.show(getFragmentManager(), "pics");
    }
    
    /**
     * creates a Temp item and then sets all of the properties before creating a task to send it to the server.
     * @param view This is not used
     */
    public void submit(View view) {
    	if(pic == null)
    	{
    		Toast.makeText(this, "Picture cannot be empty", Toast.LENGTH_LONG).show();
    		return;
    	}
    	if(title.getText().toString() == null ||title.getText().toString().equals("")){
    		Toast.makeText(this, "Invalid Title", Toast.LENGTH_LONG).show();
    		return;
    	}
    	if(longDesc.getText().toString() == null ||longDesc.getText().toString().equals("")){
    		Toast.makeText(this, "Must Fill Out Long Description", Toast.LENGTH_LONG).show();
    		return;
    	}
    	if(shortDesc.getText().toString() == null ||shortDesc.getText().toString().equals("")){
    		Toast.makeText(this, "Must Fill Out Brief Description", Toast.LENGTH_LONG).show();
    		return;
    	}
    	Item temp = new Item();
    	temp.setmAvailability(ItemState.AVAILABLE);
    	Date date = new Date();
    	temp.setmDatePosted(date.toString());
    	temp.setmDescriptionL(longDesc.getText().toString());
    	temp.setmDescriptionS(shortDesc.getText().toString());
    	temp.setmOwnerId(MainMenuActivity.mUser.getUserId());
    	byte[] ba = PictureHelper.picToByte(pic);
    	temp.setmPic(ba);
    	temp.setmTitle(title.getText().toString());
    	int itemType = type.getSelectedItemPosition();
    	temp.setmType(ItemTypeUtil.getItemType(itemType));
    	PostItemTask postTask = new PostItemTask();
    	postTask.execute(temp);
    	finish();
    }
    
    /**
     * Adds a spinner to the view. The spinner is populated with the items in R.array.itemTypes_array
     */
    public void addspinner(){
    	type = (Spinner) findViewById(R.id.PIspinner1);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
    		R.array.itemTypes_array, android.R.layout.simple_spinner_item);
    	// Specify the layout to use when the list of choices appears
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	type.setAdapter(adapter);
    }
    
	/**
	 * Called when the upload picture button is pressed delegates to selectPic()
	 * @param view not Used
	 */
	public void uploadPic(View view) {
		selectPic();
	}
	
	/**
	 * This launches an activity to pick out a picture that you want to associate with this item
	 * @param dialog this is not used
	 */
    @Override
    public void onSelectPicClick(DialogFragment dialog) {
    	Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 0);
    }

    /**
     * This method call the camera activity to capture the picture for an item
     * @param dialog this is not used
     */
    @Override
    public void onTakePicClick(DialogFragment dialog) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, 1);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0: {
			if (resultCode == RESULT_OK) {
		    	Uri targetUri = data.getData();
		    	
				try {
					Matrix matrix = new Matrix();
					matrix.postRotate(90);
					
					Log.d("DEBUG","Image CASE 1");
	    			pic = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
	    			pic = Bitmap.createBitmap(pic, 0, 0, pic.getWidth(), pic.getHeight(), matrix, true);
	    			rePic = Bitmap.createScaledBitmap(pic, picView.getWidth(), picView.getHeight(), false);
	    			picView.setImageBitmap(rePic);
	    		} catch (FileNotFoundException e) {
	    			Log.e("Error", "" + e.getMessage());
	    		} 
			}
			break;
		} 
		case 1: {
			if (resultCode == RESULT_OK) {
					Log.e("DEBUG","Image CASE 2");
					Bundle extras = data.getExtras();
					pic = (Bitmap) extras.get("data");
					picView.setImageBitmap(pic);
					picView.setVisibility(View.VISIBLE);
			}
			break;
		}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, pic);
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (pic != null) );
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		pic = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		picView.setImageBitmap(pic);
		picView.setVisibility(
				savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? 
						ImageView.VISIBLE : ImageView.INVISIBLE
		);
	}
	/**
	 * This class performs the item sending action
	 * @author Aidan, Lucas, Shijian
	 *
	 */
	public class PostItemTask extends AsyncTask<Item, Void, Void> {

		/**
		 * This is run on the background thread
		 * @param Item.. params the item that is being sent to the server
		 */
		@Override
		protected Void doInBackground(Item... params) {
			Item send = params[0];
			send.setitemId(StartActivity.mWebServices.getNewItemId(MainMenuActivity.mUser)+1);
			StartActivity.mWebServices.addItem(MainMenuActivity.mUser, send);
			return null;
		}
	}
}
