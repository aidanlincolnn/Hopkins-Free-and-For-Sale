package oose.group13.hffs.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
/**
 * class to serialize and deserialize bitmaps 
 * @author aidanfowler
 *
 */
public class PictureHelper {
	
	/**
	 * Convert a jpg to a byte array
	 * @param path the path to the picture on the phone
	 * @return the image in a byte array
	 */
	public static byte[] picToByte(Bitmap bitmap){
		if (bitmap == null){
			return null;
		}
		//Bitmap bitmap = BitmapFactory.decodeFile("path");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
		byte[] ba = baos.toByteArray();
		Log.e("DEBUG","SIZE OF Bite array: "+ ba.length);
		try {
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ba;
		
	}
	
	/**
	 * Generate a bitmap from a byte array
	 * @param imageInBytes the image in a byte array
	 * @return the image as a bitmap
	 */
	public static Bitmap saveImageFromBytes(byte[] imageInBytes){
		//Bitmap bitmap = 
		if (imageInBytes == null){
			return null;
		}
		return BitmapFactory.decodeByteArray(imageInBytes, 0,imageInBytes.length);
		//Resources res = context.getResources();
		//return new BitmapDrawable(res, bitmap);
	}

}
