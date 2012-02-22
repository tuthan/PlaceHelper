package mmt.uit.placehelper.models;

import com.google.api.client.util.Key;

public class Step extends BaseDirection{

	@Key ("html_instructions")
	private String htmlInstruc;
	@Key ("travel_mode")
	private String travelMode;
	@Key
	private Polyline polyline;
	
	
	



	/**
	 * @return the htmlInstruc
	 */
	public String getHtmlInstruc() {
		return htmlInstruc;
	}



	/**
	 * @param htmlInstruc the htmlInstruc to set
	 */
	public void setHtmlInstruc(String htmlInstruc) {
		this.htmlInstruc = htmlInstruc;
	}



	/**
	 * @return the polyline
	 */
	public Polyline getPolyline() {
		return polyline;
	}



	/**
	 * @param polyline the polyline to set
	 */
	public void setPolyline(Polyline polyline) {
		this.polyline = polyline;
	}



	/**
	 * @return the travelMode
	 */
	public String getTravelMode() {
		return travelMode;
	}



	/**
	 * @param travelMode the travelMode to set
	 */
	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}
}
