package mmt.uit.placehelper.models;

import java.util.List;
import android.telephony.PhoneNumberUtils;

public class PlaceModel {
	private int placeId;
	private String titleNoFormatting, city, country, region;
	private double lat, lng, distance = -1, earthRadius = 6371;
	private String url, staticMapUrl;
	private List<String> addressLines;
	private List<PhoneNumber> phones;		
	
	public void setPlaceId(int storeId) {
		this.placeId = storeId;
	}
	public int getPlaceId() {
		return placeId;
	}
	
	public void setTitle(String title) {
		this.titleNoFormatting = title;
	}
	public String getTitle() {
		return titleNoFormatting;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity() {
		return city;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry() {
		return country;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegion() {
		return region;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}	
	public double getLat() {
		return lat;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}	
	public double getLng() {
		return lng;
	}
	
	public void setAddressLines(List<String> addressLines) {
		this.addressLines = addressLines;
	}
	public List<String> getAddressLines() {
		return addressLines;
	}
	
	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phones = phoneNumbers;
	}
	public List<PhoneNumber> getPhoneNumbers() {
		return phones;
	}
	
	public void setDistance(double lat, double lng) {
		//Haversine formula
		double deltaLat = Math.toRadians(Math.abs(this.lat - lat));
		double deltaLng = Math.toRadians(Math.abs(this.lng - lng));
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + 
				   Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(lat)) * 
				   Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);
		double c =  2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		this.distance = earthRadius * c;
	}
	public double getDistance() {
		return round(distance, 3);
	}
	
	public double round( double value, int fractionDigits)
	{
	           double d = Math.pow( 10, fractionDigits ) ;

	           return( Math.round( value * d ) / d ) ;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	
	public void setStaticMapUrl(String staticMapUrl) {
		this.staticMapUrl = staticMapUrl;
	}
	public String getStaticMapUrl() {
		return staticMapUrl;
	}
}
