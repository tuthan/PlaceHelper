package mmt.uit.placehelper.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Place extends BasePlace implements Parcelable{

		//Constructor
		public Place(Parcel in){
			readFromParcel(in);			
		}
		
		public Place(){
			
		}
		
	
		@Override
		public int describeContents() {
			
			return 0;
		}
	
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			
			dest.writeString(id);
			dest.writeString(name);
			dest.writeString(reference);
			dest.writeString(vicinity);
			dest.writeDouble(distance);
			dest.writeParcelable(geometry, flags);
			dest.writeByte((byte)(favorite?1:0));
			dest.writeFloat(rating);
		}
		
		private void readFromParcel (Parcel in){
			
			this.id = in.readString();
			this.name = in.readString();
			this.reference = in.readString();
			this.vicinity = in.readString();
			this.distance = in.readDouble();
			this.geometry = in.readParcelable(MyGeometry.class.getClassLoader());
			this.favorite = in.readByte()==1;
			this.rating = in.readFloat();
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
