package mmt.uit.placehelper.activities;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;

import mmt.uit.placehelper.R;
import mmt.uit.placehelper.models.MainGroup;
import mmt.uit.placehelper.utilities.CatParser;
import mmt.uit.placehelper.utilities.CheckConnection;
import mmt.uit.placehelper.utilities.ConstantsAndKey;
import mmt.uit.placehelper.utilities.ExpListAdapter;
import mmt.uit.placehelper.utilities.PointAddressUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class CategoriesListActivity extends Activity {	
	private List<MainGroup> lsGroup = new ArrayList<MainGroup>();    
	private ExpListAdapter adapter;
	private ExpandableListView listView ;
	private Context mContext;
	boolean gps_enabled = false;
	boolean network_enabled = false;	
	private Location currentLoc=null;
	private String mProviderName;	
	private SharedPreferences mSharePref;	
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.ph_catlist);
        
        // Retrive the ExpandableListView from the layout
        listView = (ExpandableListView) findViewById(R.id.explist);
        listView.setDividerHeight(0);
        
        CreateCat cc = new CreateCat();
        cc.execute();
        // Collapse all groups that are not clicked        
        listView.setOnGroupExpandListener(new OnGroupExpandListener() {
        	public void onGroupExpand(int groupPosition) {
        		int len = adapter.getGroupCount();
        	    for (int i = 0; i < len; i++) {
        	    	if (i != groupPosition || i == 2 || i == 6) {
        	    		listView.collapseGroup(i);
        	        }
        	    }
        	    
        	}
        });

        listView.setOnChildClickListener(new OnChildClickListener()
        {
            
            @Override
            public boolean onChildClick(ExpandableListView expLv, View v, int groupPos, int childPos, long id)
            {
                startSearch(getResources().getString(lsGroup.get(groupPos).getChild().get(childPos).getName()));
                return true;
            }
        });
        
        listView.setOnGroupClickListener(new OnGroupClickListener()
        {
            
            @Override
            public boolean onGroupClick(ExpandableListView expLv, View v, int groupPos, long id)
            {
            	if(lsGroup.get(groupPos).getId()==700){
            		Intent intent = new Intent(getApplicationContext(), ListFavoriteActivity.class);        			
        			startActivity(intent);
        			return true;
            	}
            	if(lsGroup.get(groupPos).getChild().size()==0){
                	startSearch(getResources().getString(lsGroup.get(groupPos).getName()));
                	return true;
                }
                
                return false;
            }
        });


        
    }
    
    private void startSearch(String place){
		Log.v("ph_info", "Start Search");
		if (CheckConnection.checkInternet(mContext)){
		if (currentLoc!=null){
		String currentAdd;			
		Bundle b = new Bundle();					
		b.putString("search", place);		
			b.putDouble("curlat", currentLoc.getLatitude());			
			b.putDouble("curlng", currentLoc.getLongitude());		
			GeoPoint point = new GeoPoint(
			          (int) (currentLoc.getLatitude() * 1E6), 
			          (int) (currentLoc.getLongitude() * 1E6));
			currentAdd = PointAddressUtil.ConvertPointToAddress(point, mContext);
		if (currentAdd!=null){
			b.putString("currentadd", currentAdd);
		}
		Intent mIntent = new Intent(getApplicationContext(),
				SearchResultActivity.class);
		mIntent.putExtras(b);
		startActivity(mIntent);
		}
		else
			getCurrentLocation();
		}
		else 
			Toast.makeText(mContext, "Please check you network setting", Toast.LENGTH_LONG);
		}	
    //Get location 
    private void getLocation() {
		LocationManager locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		Location lastestLoc;
		criteria.setAccuracy(Criteria.ACCURACY_FINE);		
		MyLocationListener listener = new MyLocationListener();
		mProviderName = locMgr.getBestProvider(criteria, true);		
		if (mProviderName == null) {
			showDialog(ConstantsAndKey.CHECK_SETTING);
			currentLoc = null;
			
		} 
		else
		{
			lastestLoc = locMgr.getLastKnownLocation(mProviderName);
			if (lastestLoc !=null && (System.currentTimeMillis() - lastestLoc.getTime() < 36000000) ){
				currentLoc = lastestLoc;	
				}
				else {
				locMgr.requestLocationUpdates(mProviderName, 0, 0, listener);
				showDialog(ConstantsAndKey.WAIT_MSG);
				new Thread(new Runnable() {
					public void run() {
						Looper.prepare();
						long start = System.currentTimeMillis();
						while ((currentLoc == null)
								&& (System.currentTimeMillis() < (start + 60000))) {
						}
						dismissDialog(ConstantsAndKey.WAIT_MSG);
						if (currentLoc == null) {
							showDialog(ConstantsAndKey.LOC_NOTFOUND);
							
						}
						Looper.loop();
					}
				}).start();
				if (("network".equals(mProviderName)) && (currentLoc == null)) {
					locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
							0, listener);
				}
			}
		}
	}
    
    
    private void getCurrentLocation(){
		//mSharePref = getSharedPreferences(Constants.PREF_NAME,0);
		
		try {
			/*if (mSharePref!=null)
				showDialog(Constants.RESTORE_LOC);
			else*/
			getLocation();
		}
		catch (Exception e) {
				Log.v(ConstantsAndKey.TAG_EXCEPTION, e.toString());
			
			}
	}
    
    protected Dialog onCreateDialog(int id) {

		switch (id) {
		case ConstantsAndKey.CHECK_SETTING:
			return new AlertDialog.Builder(this).setTitle(R.string.alert).setMessage(
					R.string.gps_off)
					.setPositiveButton(R.string.setting,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Intent callGPSSettingIntent = new Intent(
											android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(callGPSSettingIntent);
								}
							}).setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									
								}
							}).create();
		case ConstantsAndKey.WAIT_MSG:
			return new ProgressDialog(this).show(this,getResources().getText(R.string.wait_plz),getResources().getText(R.string.get_location), true,true);
		case ConstantsAndKey.LOC_NOTFOUND:
			return new AlertDialog.Builder(this)
					.setMessage(
							R.string.get_loca_error)
					.setNegativeButton(R.string.ok, null).show();
		case ConstantsAndKey.RESTORE_LOC: 
			return new AlertDialog.Builder(this)
            .setIcon(R.drawable.app_icon)
            .setTitle(R.string.ask_save)
            .setPositiveButton("CĂ³", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	currentLoc = new Location("network");
                    currentLoc.setLatitude(mSharePref.getFloat(ConstantsAndKey.KEY_LAT, 0));
                    currentLoc.setLongitude(mSharePref.getFloat(ConstantsAndKey.KEY_LNG, 0));
                }
            })
            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    try{
                    	getLocation();
                    }
                    catch (Exception e){
                    	Log.v(ConstantsAndKey.TAG_EXCEPTION, e.toString());
                    }
                }
            })
            .create();
			
		case ConstantsAndKey.ABOUT:
            return new AlertDialog.Builder(this)
                .setIcon(R.drawable.about)
                .setTitle(R.string.about_title)
                .setMessage(R.string.about_msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
    
                        dismissDialog(ConstantsAndKey.ABOUT);
                    }
                })
                
                .create();
		}
		return null;
	}
    
    class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {

			if (location != null) {
				currentLoc = location;
								
			}

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}
    
    //Task to parse xml and create main screen
    private class CreateCat extends AsyncTask<Void, Void, List<MainGroup>>{

		@Override
		protected List<MainGroup> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			CatParser dp = new CatParser(mContext);
	    	List<MainGroup> results = dp.parse();
	    	return results;
		}
		
		@Override
		protected void onPostExecute(List<MainGroup> result) {
			// TODO Auto-generated method stub
			if (result !=null){
				lsGroup = result;
				adapter = new ExpListAdapter(mContext, lsGroup);
		        listView.setAdapter(adapter);
			}
		}
    	
    }
    
}