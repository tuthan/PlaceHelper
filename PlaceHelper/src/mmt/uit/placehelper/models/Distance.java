package mmt.uit.placehelper.models;

import com.google.api.client.util.Key;

public class Distance{
	
	@Key ("text")
	private String distanceInText;
	@Key ("value")
	private long distanceValue;
	/**
	 * @return the distanceInText
	 */
	public String getDistanceInText() {
		return distanceInText;
	}
	/**
	 * @param distanceInText the distanceInText to set
	 */
	public void setDistanceInText(String distanceInText) {
		this.distanceInText = distanceInText;
	}
	/**
	 * @return the distanceValue
	 */
	public long getDistanceValue() {
		return distanceValue;
	}
	/**
	 * @param distanceValue the distanceValue to set
	 */
	public void setDistanceValue(long distanceValue) {
		this.distanceValue = distanceValue;
	}
}
