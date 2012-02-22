package mmt.uit.placehelper.models;

import com.google.api.client.util.Key;

public class BaseDirection {
	
	@Key ("start_location")
	private PlaceLocation startLocation;
	@Key ("end_location")
	private PlaceLocation endLocation;
	@Key
	private Distance distance;
	@Key
	private Duration duration;
	
	

	/**
	 * @return the endLocation
	 */
	public PlaceLocation getEndLocation() {
		return endLocation;
	}

	/**
	 * @param endLocation the endLocation to set
	 */
	public void setEndLocation(PlaceLocation endLocation) {
		this.endLocation = endLocation;
	}

	/**
	 * @return the startLocation
	 */
	public PlaceLocation getStartLocation() {
		return startLocation;
	}

	/**
	 * @param startLocation the startLocation to set
	 */
	public void setStartLocation(PlaceLocation startLocation) {
		this.startLocation = startLocation;
	}

	/**
	 * @return the distance
	 */
	public Distance getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	/**
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
}

