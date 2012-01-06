package mmt.uit.placehelper.models;

import com.google.api.client.util.Key;

public class PlaceDetailRs {

	@Key
	private PlaceDetail result;
	
	@Key
	private String status;

	/**
	 * @return the result
	 */
	public PlaceDetail getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(PlaceDetail result) {
		this.result = result;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
