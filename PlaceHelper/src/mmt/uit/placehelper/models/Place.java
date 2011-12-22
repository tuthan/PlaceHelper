package mmt.uit.placehelper.models;


import mmt.uit.placehelper.utilities.ConstantsAndKey;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.Key;
public class Place implements Parcelable{
	
	@Key
	public MyGeometry geometry;
	
	@Key
	public String id;
	
	@Key
	public String name;
	
	@Key
	public String reference;
	
	@Key
	public String vicinity;

	private double distance;
	
	private boolean isFavorite;
	
	//Constructor
	private Place(Parcel in){
		readFromParcel(in);
		
	}
	
	public Place()
	{
		
	}
	
	//Set-get 
	public void setIsFavorite(boolean isFav){
		this.isFavorite = isFav;
	}
	
	public boolean getIsFavorite(){
		return this.isFavorite;
	}
	
	public void setDistance(double lat, double lng) {
		//Haversine formula
		double deltaLat = Math.toRadians(Math.abs(this.geometry.location.lat - lat));
		double deltaLng = Math.toRadians(Math.abs(this.geometry.location.lng - lng));
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + 
				   Math.cos(Math.toRadians(this.geometry.location.lat)) * Math.cos(Math.toRadians(lat)) * 
				   Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);
		double c =  2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		this.distance = ConstantsAndKey.EARTH_RADIUS * c;
	}
	
	public double getDistance(){
		return round (distance,3);
	}
	
	public void setDistance(double dis){
		distance = dis;
	}
	
	//Function to round a double
	public double round( double value, int fractionDigits)
	{
	           double d = Math.pow( 10, fractionDigits ) ;

	           return( Math.round( value * d ) / d ) ;
	}
	@Override
	public String toString() {
		return name + " - " + id + " - " + vicinity + " - " + reference;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(reference);
		dest.writeString(vicinity);
		dest.writeDouble(distance);
		dest.writeParcelable(geometry, flags);
		dest.writeByte((byte)(isFavorite?1:0));
	}
	
	private void readFromParcel (Parcel in){
		
		this.id = in.readString();
		this.name = in.readString();
		this.reference = in.readString();
		this.vicinity = in.readString();
		this.distance = in.readDouble();
		this.geometry = in.readParcelable(MyGeometry.class.getClassLoader());
		this.isFavorite = in.readByte()==1;
	}
	
    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
    	public Place createFromParcel(Parcel source) {
    		return new Place(source);
    	}
    	
    	public Place[] newArray(int size) {
    		return new Place[size];
    	}
	};


}
