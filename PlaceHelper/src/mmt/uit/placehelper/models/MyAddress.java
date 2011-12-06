package mmt.uit.placehelper.models;

public class MyAddress {
	private String adress;
	private Double lng, lat;
	
	public String getAdress (){
		return this.adress;
	}
	
	public void setAdress(String address){
		this.adress = address;
	}
	
	public Double getLng (){
		return this.lng;
	}
	
	public void setLng(Double lng){
		this.lng = lng;
	}
	
	public Double getLat (){
		return this.lat;
	}
	
	public void setLat(Double lat){
		this.lat = lat;
	}
}
