package mmt.uit.placehelper.activities;


import mmt.uit.placehelper.utilities.CheckConnection;
import mmt.uit.placehelper.utilities.ConstantsAndKey;
import mmt.uit.placehelper.utilities.PointAddressUtil;
import com.google.android.maps.GeoPoint;
import mmt.uit.placehelper.R;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class CategoriesActivity extends Activity {
	
	/** Called when the activity is first created. */
	
	private Button btnRestaurant, btnHotel, btnPlace, btnAtm, btnAirport, btnTaxi, btnGas, btnAttraction;
	ImageButton btnBank;
	boolean gps_enabled = false;
	boolean network_enabled = false;	
	private Location currentLoc=null;
	private String mProviderName;	
	private SharedPreferences mSharePref;
	private Context mContext;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ph_list_categories);
        mContext = this;
        //Get button 
        btnRestaurant = (Button)findViewById(R.id.btnrestaurant);                
        btnHotel = (Button)findViewById(R.id.btnhotel);       
        btnPlace = (Button)findViewById(R.id.btnplace);        
        btnAtm = (Button)findViewById(R.id.btnatm);        
        btnBank = (ImageButton)findViewById(R.id.btnbanks);        
        btnGas = (Button)findViewById(R.id.btngas);               
        btnTaxi = (Button)findViewById(R.id.btntaxi);        
        btnAirport = (Button)findViewById(R.id.btnairport);                  
        btnAttraction = (Button)findViewById(R.id.btnattraction);
                
        //Click Listener
        btnAirport.setOnClickListener(mCatClickListener);
        btnTaxi.setOnClickListener(mCatClickListener);
        btnGas.setOnClickListener(mCatClickListener);
        btnBank.setOnClickListener(mCatClickListener);
        btnAtm.setOnClickListener(mCatClickListener);
        btnPlace.setOnClickListener(mCatClickListener);
        btnHotel.setOnClickListener(mCatClickListener);
        btnRestaurant.setOnClickListener(mCatClickListener);
		btnAttraction.setOnClickListener(mCatClickListener); 
		
		//Get location if from change location activity
		Bundle mBundle = getIntent().getExtras();
		if (mBundle!=null)
		{
			currentLoc = new Location("network");
			currentLoc.setLatitude(mBundle.getDouble(ConstantsAndKey.KEY_LAT));
			currentLoc.setLongitude(mBundle.getDouble(ConstantsAndKey.KEY_LNG));
		}
		if (currentLoc ==null)
			getCurrentLocation();
		
    }
    
    View.OnClickListener mCatClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (btnAirport.isPressed()){
				startSearch(ConstantsAndKey.KEY_AIRPORT);
			}
			
			if(btnAtm.isPressed()){
				startSearch(ConstantsAndKey.KEY_ATM);
			} 
			
			if(btnAttraction.isPressed()){
				startSearch(ConstantsAndKey.KEY_ATTRACTION);
			}
			
			if(btnBank.isPressed()){
				startSearch(ConstantsAndKey.KEY_BANK);
			}
			
			if(btnGas.isPressed()){
				startSearch(ConstantsAndKey.KEY_GAS);
			}

			if(btnHotel.isPressed()){
				startSearch(ConstantsAndKey.KEY_HOTEL);
			}

			if(btnPlace.isPressed()){
				startSearch(ConstantsAndKey.KEY_PLACE);
			}

			if(btnRestaurant.isPressed()){
				startSearch(ConstantsAndKey.KEY_RESTAURANT);
			}

			if(btnTaxi.isPressed()){
				startSearch(ConstantsAndKey.KEY_TAXI);
			}

		}
	};
	
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
			currentAdd = PointAddressUtil.ConvertPointToAddress(point, getBaseContext());
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
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mn_mainscreen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
		case R.id.mn_lstfavorite:
			Intent intent = new Intent(getApplicationContext(), ListFavoriteActivity.class);
			Bundle b = new Bundle();
			if (currentLoc!=null){
				b.putDouble("curlat", currentLoc.getLatitude());			
				b.putDouble("curlon", currentLoc.getLongitude());
			}
			intent.putExtras(b);
			startActivity(intent);
			return true;
		case R.id.mn_changle_location:
			Intent intent2 = new Intent(getApplicationContext(), ChangeLocationActivity.class);
			startActivity(intent2);
			return true;
		case R.id.mn_about:			
			showDialog(ConstantsAndKey.ABOUT);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
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

	
}
