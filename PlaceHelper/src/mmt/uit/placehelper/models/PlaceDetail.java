package mmt.uit.placehelper.models;

import mmt.uit.placehelper.utilities.MyLocation;

import com.google.api.client.util.Key;

public class PlaceDetail {


	@Key("formatted_address")
	public String address;
	
	@Key
	public String id;
	
	@Key("international_phone_number")
	public String phone;
	
	@Key
	public String name;
	
	@Key
	public float rating;
	
	@Key
	public String url;
	
	@Key
	public MyGeometry geometry;
	@Key 
	public String website;
	
	public PlaceDetail(){
		
	}
	public PlaceDetail(String id, String name, 
			 String address, String phone, float rating, double lng, double lat, String url, String weburl) {
			 PlaceLocation pl = new PlaceLocation(lat, lng);
			 this.id = id;
			 this.name = name;
			 this.address = address;
			 this.rating = rating;
			 this.url = url;			 
			 this.phone = phone;
			 this.geometry = new MyGeometry(pl);
			 this.website = weburl;
			 }
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return address + id + name + phone + rating + url + website;
	}

	
}

