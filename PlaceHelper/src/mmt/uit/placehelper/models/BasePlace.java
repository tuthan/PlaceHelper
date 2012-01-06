package mmt.uit.placehelper.models;

import mmt.uit.placehelper.utilities.ConstantsAndKey;
import com.google.api.client.util.Key;

public class BasePlace {
	@Key
	protected MyGeometry geometry;
	
	@Key
	protected String id;
	
	@Key
	protected String name;
	
	@Key
	protected String reference;
	
	@Key
	protected String vicinity;

	@Key
	protected float rating;
	
	protected double distance;
	
	protected boolean favorite;
			
	public BasePlace()
	{
		
	}
		
	//Calculate distance with given lat and lng
	public void setDistance(double lat, double lng) {
		//Haversine formula
		double deltaLat = Math.toRadians(Math.abs(this.geometry.location.getLat() - lat));
		double deltaLng = Math.toRadians(Math.abs(this.geometry.location.getLng() - lng));
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + 
				   Math.cos(Math.toRadians(this.geometry.location.getLat())) * Math.cos(Math.toRadians(lat)) * 
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
		return name + " - " + id + " - " + vicinity + " - " + rating;
	}
	


	/**
	 * @return the geometry
	 */
	public MyGeometry getGeometry() {
		return geometry;
	}

	/**
	 * @param geometry the geometry to set
	 */
	public void setGeometry(MyGeometry geometry) {
		this.geometry = geometry;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the vicinity
	 */
	public String getVicinity() {
		return vicinity;
	}

	/**
	 * @param vicinity the vicinity to set
	 */
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}


	/**
	 * @return the isFavorite
	 */
	public boolean isFavorite() {
		return favorite;
	}

	/**
	 * @param isFavorite the isFavorite to set
	 */
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	/**
	 * @return the rating
	 */
	public float getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}
}
