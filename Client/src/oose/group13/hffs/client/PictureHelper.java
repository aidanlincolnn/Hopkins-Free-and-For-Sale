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
	 * Convert a bitmap to a byte array
	 * @param path the path to the picture on the phone
	 * @return the image in a byte array
	 */
	public static byte[] picToByte(Bitmap bitmap){
		if (bitmap == null){
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
		byte[] ba = baos.toByteArray();
		try {
			baos.close();
		} catch (IOException e) {
			Log.e("Error", "Error closing output stream" + e.getMessage());
			return null;
		}
		return ba;
		
	}
	
	/**
	 * Generate a bitmap from a byte array
	 * @param imageInBytes the image in a byte array
	 * @return the image as a bitmap
	 */
	public static Bitmap saveImageFromBytes(byte[] imageInBytes){
		if (imageInBytes == null){
			return null;
		}
		return BitmapFactory.decodeByteArray(imageInBytes, 0,imageInBytes.length);
	}

}
