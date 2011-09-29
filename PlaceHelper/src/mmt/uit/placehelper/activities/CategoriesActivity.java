package mmt.uit.placehelper.activities;


import mmt.uit.placehelper.utilities.Constants;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class CategoriesActivity extends Activity {
	
	/** Called when the activity is first created. */
	
	private Button btnRestaurant, btnHotel, btnPlace, btnBank, btnAtm, btnAirport, btnTaxi, btnGas, btnAttraction;
	boolean gps_enabled = false;
	boolean network_enabled = false;	
	private Location currentLoc=null;
	private String mProviderName;	
	private SharedPreferences mSharePref;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_categories);
        //Get button 
        btnRestaurant = (Button)findViewById(R.id.btnrestaurant);                
        btnHotel = (Button)findViewById(R.id.btnhotel);       
        btnPlace = (Button)findViewById(R.id.btnplace);        
        btnAtm = (Button)findViewById(R.id.btnatm);        
        btnBank = (Button)findViewById(R.id.btnbanks);        
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
			currentLoc.setLatitude(mBundle.getDouble(Constants.KEY_LAT));
			currentLoc.setLongitude(mBundle.getDouble(Constants.KEY_LNG));
		}
		if (currentLoc ==null)
			getCurrentLocation();
		
    }
    
    View.OnClickListener mCatClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (btnAirport.isPressed()){
				startSearch(Constants.KEY_AIRPORT);
			}
			
			if(btnAtm.isPressed()){
				startSearch(Constants.KEY_ATM);
			} 
			
			if(btnAttraction.isPressed()){
				startSearch(Constants.KEY_ATTRACTION);
			}
			
			if(btnBank.isPressed()){
				startSearch(Constants.KEY_BANK);
			}
			
			if(btnGas.isPressed()){
				startSearch(Constants.KEY_GAS);
			}

			if(btnHotel.isPressed()){
				startSearch(Constants.KEY_HOTEL);
			}

			if(btnPlace.isPressed()){
				startSearch(Constants.KEY_PLACE);
			}

			if(btnRestaurant.isPressed()){
				startSearch(Constants.KEY_RESTAURANT);
			}

			if(btnTaxi.isPressed()){
				startSearch(Constants.KEY_TAXI);
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
				Log.v(Constants.TAG_EXCEPTION, e.toString());
			
			}
	}
	
	private void startSearch(String place){
		if (currentLoc!=null){
		String currentAdd;			
		Bundle b = new Bundle();					
		b.putString("search", place);		
			b.putDouble("curlat", currentLoc.getLatitude());			
			b.putDouble("curlon", currentLoc.getLongitude());		
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
		case R.id.about:			
			showDialog(Constants.ABOUT);
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
			showDialog(Constants.CHECK_SETTING);
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
				showDialog(Constants.WAIT_MSG);
				new Thread(new Runnable() {
					public void run() {
						Looper.prepare();
						long start = System.currentTimeMillis();
						while ((currentLoc == null)
								&& (System.currentTimeMillis() < (start + 60000))) {
						}
						dismissDialog(Constants.WAIT_MSG);
						if (currentLoc == null) {
							showDialog(Constants.LOC_NOTFOUND);
							
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
		case Constants.CHECK_SETTING:
			return new AlertDialog.Builder(this).setTitle("Alert").setMessage(
					"GPS đã bị tắt")
					.setPositiveButton("Thiết lập",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Intent callGPSSettingIntent = new Intent(
											android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(callGPSSettingIntent);
								}
							}).setNegativeButton("Bỏ qua",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									
								}
							}).create();
		case Constants.WAIT_MSG:
			return new ProgressDialog(this).show(this, "Vui lòng chờ...", "Đang lấy vị trí hiện tại ", true,true);
		case Constants.LOC_NOTFOUND:
			return new AlertDialog.Builder(this)
					.setMessage(
							"Đã có lỗi xảy ra khi xác định vị trí, bạn vui lòng thử lại")
					.setNegativeButton("Đóng", null).show();
		case Constants.RESTORE_LOC: 
			return new AlertDialog.Builder(this)
            .setIcon(R.drawable.app_icon)
            .setTitle("Do you want use saved location")
            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	currentLoc = new Location("network");
                    currentLoc.setLatitude(mSharePref.getFloat(Constants.KEY_LAT, 0));
                    currentLoc.setLongitude(mSharePref.getFloat(Constants.KEY_LNG, 0));
                }
            })
            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    try{
                    	getLocation();
                    }
                    catch (Exception e){
                    	Log.v(Constants.TAG_EXCEPTION, e.toString());
                    }
                }
            })
            .create();
			
		case Constants.ABOUT:
            return new AlertDialog.Builder(this)
                .setIcon(R.drawable.about)
                .setTitle("Thông Tin PlaceHelper")
                .setMessage("ĐHCNTT - Khoa MMT&TT - Lớp MMT02 \n " +
                		"Các thành viên: \n Võ Trung Hưng - 07520164 \n " +
                		"Lương Vĩnh Thảo - 07520499 \n " +
                		"Lê Thanh Hải - 07520466 \n " +
                		"Đỗ Huy Hưng -7520167")
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
    
                        dismissDialog(Constants.ABOUT);
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
