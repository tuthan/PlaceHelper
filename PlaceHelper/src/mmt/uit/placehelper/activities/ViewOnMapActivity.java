                             
package mmt.uit.placehelper.activities;

import java.net.UnknownHostException;
import java.util.*;

import mmt.uit.placehelper.models.Place;
import mmt.uit.placehelper.models.PlaceLocation;
import mmt.uit.placehelper.models.PlacesList;
import mmt.uit.placehelper.utilities.ConstantsAndKey;
import mmt.uit.placehelper.utilities.CustomOverlay;
import mmt.uit.placehelper.utilities.PointAddressUtil;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
	CustomOverlay itemizedOverlay;
	PlacesList lsplaces;
	MapController mapController;
	Place place;
	boolean showall;
	
	double curlat, curlon;	
	private PlaceLocation curLoc;
	private Handler handler;
	private Bundle mBundle;
	
	@Override
	protected void onCreate(Bundle b) {
		// TODO Auto-generated method stub
		super.onCreate(b);
		setContentView(R.layout.ph_map_view);
		mBundle = getIntent().getExtras();
		curLoc = mBundle.getParcelable(ConstantsAndKey.KEY_CURLOC);		
		showall = mBundle.getBoolean(ConstantsAndKey.KEY_SHOW_ALL);
		
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
		
		ShowMark shMark = new ShowMark();
		shMark.execute();
			
	}	
	
	//Show current location on map
	private void showCurrent(PlaceLocation curloc)
	{
		drawable = this.getResources().getDrawable(R.drawable.marker_current);
        itemizedOverlay = new CustomOverlay(drawable, mapView);        
		GeoPoint point = new GeoPoint((int)( curloc.getLat()* 1e6),(int)(curloc.getLng() * 1e6));
		GeoPoint curPoint = new GeoPoint(
		          (int) (curloc.getLat() * 1E6), 
		          (int) (curloc.getLng() * 1E6));
		try {
		String currentAdd = PointAddressUtil.ConvertPointToAddress(curPoint, getBaseContext());
		OverlayItem overlayitem = new OverlayItem(point, "You Here", currentAdd);	    	
    	mapController.setCenter(point);
    	itemizedOverlay.addOverlay(overlayitem);
    	mapOverlays.add(itemizedOverlay);
		}
		catch (UnknownHostException e){
			e.printStackTrace();
		}
    	
    	
	}
	
	// Show a place on map
	private void showALocation(Place pl)
	{
		if(pl.isFavorite()){
			drawable = this.getResources().getDrawable(R.drawable.marker_fav);
		}
		else{
		drawable = this.getResources().getDrawable(R.drawable.marker_normal);
		}
        itemizedOverlay = new CustomOverlay(drawable,mapView);        
		GeoPoint point = new GeoPoint((int)( pl.getGeometry().getLocation().getLat()* 1e6),(int)(pl.getGeometry().getLocation().getLng() * 1e6));
    	OverlayItem overlayitem = new OverlayItem(point, pl.getName(), pl.getVicinity());	    	    	
    	itemizedOverlay.addOverlay(overlayitem);
    	mapOverlays.add(itemizedOverlay);
    	
	}
	
	private void showAllPlace(PlacesList places)
	{		  
       	 if (lsplaces !=null){
      	  
      	 for(Place pl:lsplaces.getResults()){
      		 showALocation(pl);
      	 }
       	 }
    	 
	}
	
	@Override
    protected boolean isRouteDisplayed() {
        return false;
    }
	
	private class ShowMark extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			showCurrent(curLoc);
			if(showall){
				lsplaces = mBundle.getParcelable(ConstantsAndKey.KEY_LST_PLACES);
				showAllPlace(lsplaces);
			}
			else{	
				place = mBundle.getParcelable("place");
				showALocation(place);
			}
		}
	}
	
}
