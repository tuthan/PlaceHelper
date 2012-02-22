package mmt.uit.placehelper.models;

import java.util.ArrayList;
import java.util.List;
import com.google.api.client.util.Key;

public class Route {

	@Key
	private List<Leg> legs;
	@Key
	private String summary;
	@Key
	private String copyrights;
	@Key ("overview_polyline")
	private Polyline overviewPolyline;
	@Key
	private ArrayList<String> warnings;
	

	/**
	 * @return the legs
	 */
	public List<Leg> getLegs() {
		return legs;
	}

	/**
	 * @param legs the legs to set
	 */
	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the copyrights
	 */
	public String getCopyrights() {
		return copyrights;
	}

	/**
	 * @param copyrights the copyrights to set
	 */
	public void setCopyrights(String copyrights) {
		this.copyrights = copyrights;
	}

	/**
	 * @return the overviewPolyline
	 */
	public Polyline getOverviewPolyline() {
		return overviewPolyline;
	}

	/**
	 * @param overviewPolyline the overviewPolyline to set
	 */
	public void setOverviewPolyline(Polyline overviewPolyline) {
		this.overviewPolyline = overviewPolyline;
	}

	/**
	 * @return the warnings
	 */
	public ArrayList<String> getWarnings() {
		return warnings;
	}

	/**
	 * @param warnings the warnings to set
	 */
	public void setWarnings(ArrayList<String> warnings) {
		this.warnings = warnings;
	}
}
