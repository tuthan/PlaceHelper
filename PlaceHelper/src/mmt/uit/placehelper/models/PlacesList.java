package mmt.uit.placehelper.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.Key;

public class PlacesList implements Parcelable{

	@Key
	public String status;

	
	
	@Key
	public List<Place> results;
	
	public PlacesList(){
		
	}
	
	public PlacesList(Parcel in){
		status = in.readString();
		results = new ArrayList<Place>(); //Initialize list
		in.readTypedList(results, Place.CREATOR);
	}
	
	@Override
	public int describeContents() {
	
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flag) {
		dest.writeString(status);
		dest.writeTypedList(results);
		
	}
	
	 public static final Parcelable.Creator<PlacesList> CREATOR = new Parcelable.Creator<PlacesList>() {
	    	public PlacesList createFromParcel(Parcel source) {
	    		return new PlacesList(source);
	    	}
	    	
	    	public PlacesList[] newArray(int size) {
	    		return new PlacesList[size];
	    	}
		};

}
