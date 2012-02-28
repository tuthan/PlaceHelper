package mmt.uit.placehelper.utilities;

import java.util.Comparator;

import mmt.uit.placehelper.models.Place;

//Class to help sort place by Rating 
public class SortByRate implements Comparator<Place> {
	public int compare(Place place1, Place place2) {		
		double rate1 = place1.getDistance();
		double rate2 = place2.getDistance();
		
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
