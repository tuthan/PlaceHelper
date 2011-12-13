package mmt.uit.placehelper.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.Key;



public class PlaceLocation  implements Parcelable{
		
	
	@Key
	public double lat;
	
	@Key
	public double lng;
	
	//Constructor 
	private PlaceLocation(Parcel in){
		this.lat = in.readDouble();
		this.lng = in.readDouble();
				
	}
	
	public PlaceLocation(){
		
	}
	
	public PlaceLocation(double lat, double lng){
		this.lat = lat;
		this.lng = lng;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeDouble(lat);
		dest.writeDouble(lng);
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
