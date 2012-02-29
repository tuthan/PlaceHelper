package mmt.uit.placehelper.utilities;

import java.util.Comparator;

import mmt.uit.placehelper.models.Place;

//Class to help sort place by Rating 
public class SortByRate implements Comparator<Place> {
	public int compare(Place place1, Place place2) {		
		float rate1 = place1.getRating();
		float rate2 = place2.getRating();
		
		if(rate1 > rate2)
			return 1;
		else {
			if(rate1 < rate2)
				return -1;
			else
				return 0;
		}		
	}
	
	
}
