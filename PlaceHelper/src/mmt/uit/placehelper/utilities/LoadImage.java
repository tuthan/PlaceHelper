package mmt.uit.placehelper.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LoadImage {
	
	public static final Bitmap downloadImage(String imageUrl){
        URL myUrl =null;  
        Bitmap mBitmap = null;
        try {
             myUrl= new URL(imageUrl);
        } catch (MalformedURLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();             
        }
        try {
             HttpURLConnection conn= (HttpURLConnection)myUrl.openConnection();
             conn.setDoInput(true);
             conn.connect();
             //int length = conn.getContentLength();
             InputStream is = conn.getInputStream();
             mBitmap= BitmapFactory.decodeStream(is);             
             
        } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();             
        }
        
        return mBitmap;
	}
}
