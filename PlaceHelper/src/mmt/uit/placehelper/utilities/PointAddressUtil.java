package mmt.uit.placehelper.utilities;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mmt.uit.placehelper.models.MyAddress;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.maps.GeoPoint;

public class PointAddressUtil {
	public static String ConvertPointToAddress(GeoPoint mPoint, Context mContext) throws UnknownHostException {   
	    String address = "";
	    Geocoder geoCoder = new Geocoder(
	        mContext, Locale.getDefault());
	    try {
	      List<Address> addresses = geoCoder.getFromLocation(
	        mPoint.getLatitudeE6()  / 1E6, 
	        mPoint.getLongitudeE6() / 1E6, 1);
	 
	      if (addresses.size() > 0) {
	        for (int index = 0; 
		index < addresses.get(0).getMaxAddressLineIndex(); index++)
	          address += addresses.get(0).getAddressLine(index) + " ";
	      }
	    }
	    catch (IOException e) {        
	      e.printStackTrace();
	    }   
	    
	    return address;
	  } 
	public static List<MyAddress> getAdress(String name, Context mContext){
		
		List<MyAddress> lstAd = new ArrayList<MyAddress>();
	    Geocoder geoCoder = new Geocoder(
	        mContext, Locale.getDefault());
	    try {
	    List<Address> addresses = geoCoder.getFromLocationName(name, 10);
	    if (!addresses.isEmpty()){
	    	for (Address ad:addresses){
	    		MyAddress myAd= new MyAddress();	    		
	    		String address=ad.getAddressLine(0);
	    		for (int i=1;i<ad.getMaxAddressLineIndex();i++){
	    			address += " - " + ad.getAddressLine(i);
	    		}
	    		myAd.setAdress(address);
	    		myAd.setLat(ad.getLatitude());
	    		myAd.setLng(ad.getLongitude());
	    		lstAd.add(myAd);
	    	}
	    	}
	    
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	    return lstAd;
	}
}
