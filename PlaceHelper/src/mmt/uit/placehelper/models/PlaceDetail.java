package mmt.uit.placehelper.models;

import mmt.uit.placehelper.R;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.Key;

public class PlaceDetail extends BasePlace implements Parcelable{


	@Key("formatted_address")
	private String address;
	
	@Key("international_phone_number")
	private String phone;
	
	@Key
	private String url;
	
	@Key
	private String website;
	
	private int typeImg = R.drawable.default_icon;
	
	public PlaceDetail (Parcel in){
		readFromParcel(in);
	}
	public PlaceDetail(){
		
	}
	
	public PlaceDetail(String id, String name, 
			 String address, String phone, float rating, double lng, double lat, String url, String weburl, int img) {
			 PlaceLocation pl = new PlaceLocation(lat, lng);
			 this.id = id;
			 this.name = name;
			 this.address = address;
			 this.phone = phone;
			 this.rating = rating;
			 this.geometry = new MyGeometry(pl);			 
			 this.url =url;
			 this.website = weburl;
			 this.typeImg = img;
			 }
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(address);
		dest.writeString(phone);
		dest.writeDouble(distance);
		dest.writeParcelable(geometry, flags);
		dest.writeByte((byte)(favorite?1:0));
		dest.writeFloat(rating);
		dest.writeString(url);
		dest.writeString(website);
		dest.writeInt(typeImg);
		
	}
	
	private void readFromParcel (Parcel in){
		
		this.id = in.readString();
		this.name = in.readString();
		this.address = in.readString();
		this.phone = in.readString();
		this.distance = in.readDouble();
		this.geometry = in.readParcelable(MyGeometry.class.getClassLoader());
		this.favorite = in.readByte()==1;
		this.rating = in.readFloat();
		this.url = in.readString();
		this.website = in.readString();
		this.typeImg = in.readInt();
	}
	
    public static final Parcelable.Creator<PlaceDetail> CREATOR = new Parcelable.Creator<PlaceDetail>() {
    	public PlaceDetail createFromParcel(Parcel source) {
    		return new PlaceDetail(source);
    	}
    	
    	public PlaceDetail[] newArray(int size) {
    		return new PlaceDetail[size];
    	}
	};
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getAddress() +" - "+ getName() +" - "+ getPhone() +" - "+ getRating() +" - "+ getUrl() +" - "+ getWebsite();
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @return the typeImg
	 */
	public int getTypeImg() {
		return typeImg;
	}
	/**
	 * @param typeImg the typeImg to set
	 */
	public void setTypeImg(int typeImg) {
		this.typeImg = typeImg;
	}

	
}

