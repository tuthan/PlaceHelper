package mmt.uit.placehelper.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.Key;

public class PlacesList implements Parcelable{

	@Key
	private String status;

	
	
	@Key
	private List<Place> results;
	
	public PlacesList(){
		
	}
	
	public PlacesList(Parcel in){
		setStatus(in.readString());
		setResults(new ArrayList<Place>()); //Initialize list
		in.readTypedList(getResults(), Place.CREATOR);
	}
	
	@Override
	public int describeContents() {
	
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flag) {
		dest.writeString(getStatus());
		dest.writeTypedList(getResults());
		
	}
	
	 /**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the results
	 */
	public List<Place> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<Place> results) {
		this.results = results;
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
