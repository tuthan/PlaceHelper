package mmt.uit.placehelper.models;

import com.google.api.client.util.Key;

public class Duration {
	@Key ("text")
	private String durationInText;
	@Key ("value")
	private long durationValue;
	/**
	 * @return the durationInText
	 */
	public String getDurationInText() {
		return durationInText;
	}
	/**
	 * @param durationInText the durationInText to set
	 */
	public void setDurationInText(String durationInText) {
		this.durationInText = durationInText;
	}
	/**
	 * @return the durationValue
	 */
	public long getDurationValue() {
		return durationValue;
	}
	/**
	 * @param durationValue the durationValue to set
	 */
	public void setDurationValue(long durationValue) {
		this.durationValue = durationValue;
	}
	
}
