package mmt.uit.placehelper.models;

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
	public String website;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return address + id + name + phone + rating + url + website;
	}

	
}

