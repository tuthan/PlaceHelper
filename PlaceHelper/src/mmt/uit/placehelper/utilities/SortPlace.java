package mmt.uit.placehelper.utilities;

import java.util.Comparator;

import mmt.uit.placehelper.models.Place;

//Class to help sort place by distance
public class SortPlace implements Comparator<Place> {
	public int compare(Place place1, Place place2) {
		// TODO Auto-generated method stub
		double dis1 = place1.getDistance();
		double dis2 = place2.getDistance();
		
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
