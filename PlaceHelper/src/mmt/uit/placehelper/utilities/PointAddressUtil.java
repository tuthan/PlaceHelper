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
		MyAddress myAd= new MyAddress();
		String address="";
		List<MyAddress> lstAd = new ArrayList<MyAddress>();
	    Geocoder geoCoder = new Geocoder(
	        mContext, Locale.getDefault());
	    try {
	    List<Address> addresses = geoCoder.getFromLocationName(name, 5);
	    if (!addresses.isEmpty()){
	    	for (int i =0; i<addresses.size();i++){
	    		myAd.setLat(addresses.get(i).getLatitude());
	    		myAd.setLng(addresses.get(i).getLongitude());
	    		for (int index = 0; 
	    		index < addresses.get(i).getMaxAddressLineIndex(); index++)
	    	          address += addresses.get(i).getAddressLine(index) + " ";
	    		myAd.setAdress(address);
	    	      }
	    		lstAd.add(myAd);
	    	}
	    
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	    return lstAd;
	}
}
