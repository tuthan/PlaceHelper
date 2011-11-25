                             
package mmt.uit.placehelper.activities;

import java.util.*;
import java.lang.Iterable;
import java.lang.reflect.*;

import mmt.uit.placehelper.models.PlaceModel;
import mmt.uit.placehelper.services.SearchService;
import mmt.uit.placehelper.utilities.Constants;
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
	PlaceModel mPlace;
	
	double curlat, curlon;	
	
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.ph_map_view);
		Bundle b = getIntent().getExtras();
		curlat = b.getDouble("curlat");
		curlon = b.getDouble("curlon");
		
		mapView = (MapView) findViewById(R.id.mapView);
	    mapView.setBuiltInZoomControls(true);
	        
	    mapOverlays = mapView.getOverlays();
	    
	    mapController = mapView.getController(); 
		mapController.setZoom(14);
	    
		
		showCurrentPosition();
		if (b.getBoolean("showall")){
		showAllPlace();
		}
		else{
			showCurrentPlace(b.getDouble(Constants.KEY_LNG),b.getDouble(Constants.KEY_LAT));
		}
			
	}	
	// hien thi vi tri cua nguoi dung qua GPS len map
	private void showCurrentPosition()
	{
		drawable = this.getResources().getDrawable(R.drawable.myplcace);
        itemizedOverlay = new MyOverlay(drawable);
		GeoPoint point = new GeoPoint((int)(curlat * 1e6),(int)(curlon * 1e6));
    	OverlayItem overlayitem = new OverlayItem(point, "", "You're here");	    	
    	mapController.setCenter(point);
    	itemizedOverlay.addOverlay(overlayitem);
    	mapOverlays.add(itemizedOverlay);
	}
	// hien thi vi tri can tim len map
	private void showCurrentPlace(Double lng, Double lat)
	{
		drawable = this.getResources().getDrawable(R.drawable.mark_current);
        itemizedOverlay = new MyOverlay(drawable);        
		GeoPoint point = new GeoPoint((int)( lat* 1e6),(int)(lng * 1e6));
    	OverlayItem overlayitem = new OverlayItem(point, "", "Place here");	    	
    	//mapController.setCenter(point);
    	itemizedOverlay.addOverlay(overlayitem);
    	mapOverlays.add(itemizedOverlay);
    	
    	 
	}
	
	private void showAllPlace()
	{		  
       	 //test hien thi nhieu diem len map
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
