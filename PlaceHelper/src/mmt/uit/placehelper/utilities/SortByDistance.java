package mmt.uit.placehelper.utilities;

import java.util.Comparator;

import mmt.uit.placehelper.models.Place;

//Class to help sort place by distance
public class SortByDistance implements Comparator<Place> {
	public int compare(Place place1, Place place2) {
		
		float dis1 = place1.getRating();
		float dis2 = place2.getRating();
		
		if(dis1 > dis2)
			return 1;
		else {
			if(dis1 < dis2)
				return -1;
			else
				return 0;
		}		
	}
	
	
}
