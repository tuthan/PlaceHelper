package mmt.uit.placehelper.models;

import com.google.api.client.util.Key;

public class PlaceDetail extends BasePlace {


	@Key("formatted_address")
	private String address;
	
	@Key("international_phone_number")
	private String phone;
	
	@Key
	private String url;
	
	@Key
	private String website;
	
	public PlaceDetail(){
		
	}
	public PlaceDetail(String id, String name, 
			 String address, String phone, float rating, double lng, double lat, String url, String weburl) {
			 PlaceLocation pl = new PlaceLocation(lat, lng);
			 this.id = id;
			 this.name = name;
			 this.address = address;
			 this.phone = phone;
			 this.rating = rating;
			 this.geometry = new MyGeometry();
			 this.geometry.location = pl;
			 this.url =url;
			 this.website = weburl;
			 }
	
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

	
}

