package mmt.uit.placehelper.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.Key;



public class PlaceLocation  implements Parcelable{
		
	
	@Key
	private double lat;
	
	@Key
	private double lng;
	
	//Constructor 
	private PlaceLocation(Parcel in){
		this.setLat(in.readDouble());
		this.setLng(in.readDouble());
				
	}
	
	public PlaceLocation(){
		
	}
	
	public PlaceLocation(double lat, double lng){
		this.setLat(lat);
		this.setLng(lng);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeDouble(getLat());
		dest.writeDouble(getLng());
	}
	
	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	//CREATOR
	public static final Parcelable.Creator<PlaceLocation> CREATOR = new Creator<PlaceLocation>() {
		
		@Override
		public PlaceLocation[] newArray(int size) {
			// TODO Auto-generated method stub
			return new PlaceLocation[size];
		}
		
		@Override
		public PlaceLocation createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new PlaceLocation(source);
		}
	};
	
}
