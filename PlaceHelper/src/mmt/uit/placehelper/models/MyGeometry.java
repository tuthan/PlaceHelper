package mmt.uit.placehelper.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.Key;

public class MyGeometry implements Parcelable{

	@Key
	public PlaceLocation location;
	
	//Constructor 
	private  MyGeometry (Parcel in){
		this.location = in.readParcelable(PlaceLocation.class.getClassLoader());
	}
	
	public MyGeometry(){
		
	}
	
	public MyGeometry (PlaceLocation pl){
		this.location = pl;
	}
	
	public MyGeometry (double lat, double lng){
		this.location.setLat(lat);
		this.location.setLng(lng);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeParcelable(location, flags);
	}
	
	
	public static Parcelable.Creator<MyGeometry> CREATOR = new Creator<MyGeometry>() {
		
		@Override
		public MyGeometry[] newArray(int size) {
			// TODO Auto-generated method stub
			return new MyGeometry[size];
		}
		
		@Override
		public MyGeometry createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new MyGeometry(source);
		}
	};
	
}
