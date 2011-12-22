                             
package mmt.uit.placehelper.activities;

import java.util.*;

import mmt.uit.placehelper.models.Place;
import mmt.uit.placehelper.models.PlaceLocation;
import mmt.uit.placehelper.models.PlaceModel;
import mmt.uit.placehelper.services.SearchService;
import mmt.uit.placehelper.utilities.ConstantsAndKey;
import mmt.uit.placehelper.utilities.MyOverlay;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import mmt.uit.placehelper.R;

public class ViewOnMapActivity extends MapActivity
{
	/** Called when the activity is first created. */
	LinearLayout linearLayout;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	MyOverlay itemizedOverlay;
	List<PlaceModel > placemodels;
	MapController mapController;
	Place place;
	
	double curlat, curlon;	
	private PlaceLocation curLoc;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle b) {
		// TODO Auto-generated method stub
		super.onCreate(b);
		setContentView(R.layout.ph_map_view);
		Bundle mBundle = getIntent().getExtras();
		curLoc = mBundle.getParcelable(ConstantsAndKey.KEY_CURLOC);
		place = mBundle.getParcelable("place");
		mapView = (MapView) findViewById(R.id.mapView);
	    mapView.setBuiltInZoomControls(true);
	        
	    mapOverlays = mapView.getOverlays();
	    
	    mapController = mapView.getController(); 
		mapController.setZoom(14);
	    
		
		/*showCurrentPosition();
		if (b.getBoolean("showall")){
		showAllPlace();
		}
		else{
			showCurrentPlace(b.getDouble(ConstantsAndKey.KEY_LNG),b.getDouble(ConstantsAndKey.KEY_LAT));
		}*/
		
		showCurrent(curLoc);
		showALocation(place.geometry.location);
			
	}	
	
	//Show current location on map
	private void showCurrent(PlaceLocation curloc)
	{
		drawable = this.getResources().getDrawable(R.drawable.marker_current);
        itemizedOverlay = new MyOverlay(drawable);        
		GeoPoint point = new GeoPoint((int)( curloc.lat* 1e6),(int)(curloc.lng * 1e6));
    	OverlayItem overlayitem = new OverlayItem(point, "", "your here");	    	
    	mapController.setCenter(point);
    	itemizedOverlay.addOverlay(overlayitem);
    	mapOverlays.add(itemizedOverlay);
    	
	}
	
	// Show a place on map
	private void showALocation(PlaceLocation ploc)
	{
		if(place.getIsFavorite()){
			drawable = this.getResources().getDrawable(R.drawable.marker_fav);
		}
		else{
		drawable = this.getResources().getDrawable(R.drawable.marker_normal);
		}
        itemizedOverlay = new MyOverlay(drawable);        
		GeoPoint point = new GeoPoint((int)( ploc.lat* 1e6),(int)(ploc.lng * 1e6));
    	OverlayItem overlayitem = new OverlayItem(point, "", "Place");	    	    	
    	itemizedOverlay.addOverlay(overlayitem);
    	mapOverlays.add(itemizedOverlay);
    	
	}
	
	private void showAllPlace()
	{		  
       	 
      	  placemodels = SearchService.placeModel;
      	  int max = placemodels.size();
      	  if (max >10) max=10;
        for (int j=0;j<max;j++)
        { 
        	GeoPoint point = new GeoPoint((int)( placemodels.get(j).getLat()* 1e6),(int)(placemodels.get(j).getLng() * 1e6));
            OverlayItem overlayitem = new OverlayItem(point, "", "");
            
            drawable = this.getResources().getDrawable(this.getResources().getIdentifier("mark"+j, "drawable", "mmt.uit.placehelper"));
        	itemizedOverlay = new MyOverlay(drawable);
        	itemizedOverlay.addOverlay(overlayitem);
        	mapOverlays.add(itemizedOverlay);
        }
               
    	
        
        
    	 
	}
	
	@Override
    protected boolean isRouteDisplayed() {
        return false;
    }
	
}
