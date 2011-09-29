package mmt.uit.placehelper.models;

public class FavoriteModel {
	private int placeId;
	private String titleNoFormatting;
	private double earthRadius = 6371;
	private String address, staticMapUrl, addressLines, phoneNumber, lat, lng, webUrl;

	 public FavoriteModel(int placeId, String titleNoFormatting, 
	 String address, String addressLines,  
	 String phoneNumber, String lng, String lat, String staticMapUrl, String webUrl) {
	 super();
	 this.placeId = placeId;
	 this.titleNoFormatting = titleNoFormatting;
	 this.address = address;
	 this.staticMapUrl = staticMapUrl;
	 this.addressLines = addressLines;
	 this.phoneNumber = phoneNumber;
	 this.lat = lat;
	 this.lng = lng;
	 this.webUrl = webUrl;
	 }
	 
	 public FavoriteModel(String lat, String lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public int getPlaceId() {
		return placeId;
	}



	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}



	public void setTitle(String title) {
			this.titleNoFormatting = title;
		}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getTitle() {
		return titleNoFormatting;
	}

	public double getEarthRadius() {
		return earthRadius;
	}

	public void setEarthRadius(double earthRadius) {
		this.earthRadius = earthRadius;
	}

	public String getAddressLines() {
		return addressLines;
	}

	public void setAddressLines(String addressLines) {
		this.addressLines = addressLines;
	}

	public String getDistance(double lat2, double lng2) {
		// Haversine formula
		double lat1 = Double.parseDouble(this.lat);
		double lng1 = Double.parseDouble(this.lng);
		double distance1 = -1;

		double deltaLat = Math.toRadians(Math.abs(lat1 - lat2));
		double deltaLng = Math.toRadians(Math.abs(lng1 - lng2));
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(deltaLng / 2)
				* Math.sin(deltaLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		distance1 = round((earthRadius * c), 3);
		return "" + distance1;
		// nho xly trong tr.h ko lay dc vitri hien tai thi tra ve ???
	}

	public double round(double value, int fractionDigits) {
		double d = Math.pow(10, fractionDigits);
		return (Math.round(value * d) / d);
	}

	public void setStaticMapUrl(String staticMapUrl) {
		this.staticMapUrl = staticMapUrl;
	}

	public String getStaticMapUrl() {
		return staticMapUrl;
	}
	public boolean equals(Object obj){
		if(obj == null) return false;
		if(obj instanceof FavoriteModel){
			FavoriteModel qg = (FavoriteModel)obj;
			return this.titleNoFormatting.equals(qg.titleNoFormatting);
		}
		else
			return false;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
}
