package mmt.uit.placehelper.models;

import java.util.List;

import com.google.api.client.util.Key;

public class Leg extends BaseDirection{

	@Key ("start_address")
	private String startAddress;
	@Key ("end_address")
	private String endAddress;
	@Key
	private List<Step> steps;
	/**
	 * @return the startAddress
	 */
	public String getStartAddress() {
		return startAddress;
	}
	/**
	 * @param startAddress the startAddress to set
	 */
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	/**
	 * @return the endAddress
	 */
	public String getEndAddress() {
		return endAddress;
	}
	/**
	 * @param endAddress the endAddress to set
	 */
	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	/**
	 * @return the steps
	 */
	public List<Step> getSteps() {
		return steps;
	}
	/**
	 * @param steps the steps to set
	 */
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	
	/**
	 * 
	 * @return String contains html instructions of the direction for this leg
	 * 
	 */
	public String getInstructions(){
		
		StringBuilder sb=new StringBuilder();
		for (Step st:steps){
			sb.append("-->");
			sb.append(st.getHtmlInstruc());
			sb.append("<br>");
		}
		return sb.toString();
	}
}
