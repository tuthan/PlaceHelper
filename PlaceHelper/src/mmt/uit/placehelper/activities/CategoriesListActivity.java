package mmt.uit.placehelper.activities;

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import mmt.uit.placehelper.R;
import mmt.uit.placehelper.models.Child;
import mmt.uit.placehelper.models.MainGroup;
import mmt.uit.placehelper.models.MyAddress;
import mmt.uit.placehelper.utilities.CatParser;
import mmt.uit.placehelper.utilities.CheckConnection;
import mmt.uit.placehelper.utilities.ConstantsAndKey;
import mmt.uit.placehelper.utilities.ExpListAdapter;
import mmt.uit.placehelper.utilities.LocationAdapter;
import mmt.uit.placehelper.utilities.LocationHelper;
import mmt.uit.placehelper.utilities.LocationHelper.LocationResult;
import mmt.uit.placehelper.utilities.PointAddressUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class CategoriesListActivity extends Activity {	
	private List<MainGroup> lsGroup = new ArrayList<MainGroup>();    
	private ExpListAdapter adapter;
	private ExpandableListView listView ;
	private TextView cur_add;
	private ProgressBar loc_progress;
	private Context mContext;
	boolean gps_enabled = false;
	boolean network_enabled = false;	
	private Location currentLoc=null;
	private String mProviderName;	
	private SharedPreferences mSharePref;
	private boolean hasLocation=false;	
	private LocationHelper locHelper;
	private GetLocation getLoc = new GetLocation();;
	private String lang = "en";
	Dialog customLoc;
	private static final int FAVORITE_GROUP = 900;
	private static final int SETTING_GROUP = 800;
	private static final int CHILD_ADD = 701;
	private static final int MAX_AGE = 2*60*1000;//Max age of location cache
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
    	super.onCreate(savedInstanceState);
    	Configuration mConfig = new Configuration(getResources().getConfiguration());
    	mSharePref = PreferenceManager.getDefaultSharedPreferences(mContext);
    	if(mSharePref!=null){
    	lang = mSharePref.getString(getResources().getString(R.string.prefkey_lang), getResources().getStringArray(R.array.arr_lang_value)[0]);    	
    	mConfig.locale = new Locale(lang);
    	getResources().updateConfiguration(mConfig, getResources().getDisplayMetrics());
    	}
    	
        setContentView(R.layout.ph_catlist);
        
        // Retrive the ExpandableListView from the layout
        listView = (ExpandableListView) findViewById(R.id.explist);
        listView.setDividerHeight(0);
        cur_add = (TextView)findViewById(R.id.cur_address);
        loc_progress = (ProgressBar)findViewById(R.id.loc_progress);
        //Call task to create category list.
        CreateCat cc = new CreateCat();
        cc.execute();
        // Collapse all groups that are not clicked        
        listView.setOnGroupExpandListener(new OnGroupExpandListener() {
        	public void onGroupExpand(int groupPosition) {
        		int len = adapter.getGroupCount();
        	    for (int i = 0; i < len; i++) {
        	    	if (i!=groupPosition || lsGroup.get(i).getChild().size()==0) {
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
            	switch(lsGroup.get(groupPos).getChild().get(childPos).getId()){
            	case CHILD_ADD:
            		showDialog(ConstantsAndKey.ADD_CAT);
            		return true;
            	default:
            		int tmpId = getResources().getIdentifier("mmt.uit.placehelper:"+lsGroup.get(groupPos).getChild().get(childPos).getName(), null, null);
                	int imgId = getResources().getIdentifier("mmt.uit.placehelper:"+lsGroup.get(groupPos).getChild().get(childPos).getImgID(), null, null);
                	if (lsGroup.get(groupPos).getChild().get(childPos).getId()<702||lsGroup.get(groupPos).getChild().get(childPos).getId()>=800){
                		startSearch(getResources().getString(tmpId),imgId,
                				lsGroup.get(groupPos).getChild().get(childPos).getTypes());            	
                	}
                	else {
                		startSearch(lsGroup.get(groupPos).getChild().get(childPos).getName(),imgId,
                				lsGroup.get(groupPos).getChild().get(childPos).getTypes());   
                	}
                	return true;
            	}            	               
                
            }
        });
        
        listView.setOnGroupClickListener(new OnGroupClickListener()
        {
            
            @Override
            public boolean onGroupClick(ExpandableListView expLv, View v, int groupPos, long id)
            {
            	            	
            	if(lsGroup.get(groupPos).getId()==FAVORITE_GROUP){
            		Intent intent = new Intent(getApplicationContext(), ListFavoriteActivity.class);        			
        			startActivity(intent);
        			return true;
            	}
            	if(lsGroup.get(groupPos).getId()==SETTING_GROUP){
            		Intent intSetting = new Intent(getApplicationContext(),SettingsActivity.class);
        			startActivity(intSetting);
            		return true;
            	}
            	if(lsGroup.get(groupPos).getChild().size()==0){
            		int tmpId = getResources().getIdentifier("mmt.uit.placehelper:"+lsGroup.get(groupPos).getName(), null, null);
            		int imgId = getResources().getIdentifier("mmt.uit.placehelper:"+lsGroup.get(groupPos).getImgID(), null, null);
                	startSearch(getResources().getString(tmpId),imgId,lsGroup.get(groupPos).getTypes());
                	return true;
                }
                
                return false;
            }
        });

        locHelper = new LocationHelper();
        if (locHelper.isDisable(mContext)){
        	showDialog(ConstantsAndKey.CHECK_SETTING);
        }
        else {
        locHelper.getLocation(mContext, locResult);        
        getLoc.execute(mContext);
        }

        
    }
    
    @Override
    protected void onStop() {
    	
    	super.onStop();
    	//When activity stop also stop update location and stop task too.
    	/*locHelper.stopLocationUpdates();
    	getLoc.cancel(true);*/
    }
    
    @Override
    protected void onPause() {    	
    	/*locHelper.stopLocationUpdates();
    	getLoc.cancel(true);*/
    	super.onPause();
    }
    
    @Override
    protected void onResume() {    	
    	super.onResume();
    }
    
    
    
	public LocationResult locResult = new LocationResult() {
		
		@Override
		public void gotLocation(Location location) {
			if (location!=null){
			currentLoc = new Location(location);
			hasLocation = true;
			}
		}
	};
    
    private void startSearch(String place, int img, String types){
		Log.v("ph_info", "Start Search");
		if (CheckConnection.checkInternet(mContext)){
		if (currentLoc!=null){
			
		Bundle b = new Bundle();					
		b.putString("search", place);		
			b.putDouble("curlat", currentLoc.getLatitude());			
			b.putDouble("curlng", currentLoc.getLongitude());
			b.putInt("img", img);
			b.putString("types", types);
			
		Intent mIntent = new Intent(getApplicationContext(),
				SearchResultActivity.class);
		mIntent.putExtras(b);
		startActivity(mIntent);
		}
		
		else {
			if (locHelper.isDisable(mContext)){
	        	showDialog(ConstantsAndKey.CHECK_SETTING);
	        }
	        else {
	        locHelper.getLocation(mContext, locResult);
	        getLoc = new GetLocation();
	        getLoc.execute(mContext);
	        }
		}
		}
		else 
			Toast.makeText(mContext, R.string.error_network, Toast.LENGTH_LONG);
		}	
    //Get location 
    private Location getLocation() {
		LocationManager locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		Location lastestLoc;
		criteria.setAccuracy(Criteria.ACCURACY_FINE);		
		MyLocationListener listener = new MyLocationListener();
		mProviderName = locMgr.getBestProvider(criteria, true);		
		if (mProviderName == null) {
			showDialog(ConstantsAndKey.CHECK_SETTING);			
			return null;
		} 
		
			lastestLoc = locMgr.getLastKnownLocation(mProviderName);
			if (lastestLoc !=null && (System.currentTimeMillis() - lastestLoc.getTime() < MAX_AGE) ){
				return lastestLoc;	
				}
				else {
				locMgr.requestLocationUpdates(mProviderName, 0, 0, listener);
				
			}
			
			return lastestLoc;
		
	}
    
    //Create menu
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {		
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
			/*Intent intent2 = new Intent(getApplicationContext(), ChangeLocationActivity.class);
			startActivity(intent2);*/
			showDialog(ConstantsAndKey.CUSTOM_LOC);			
			return true;
		case R.id.mn_about:			
			showDialog(ConstantsAndKey.ABOUT);
			return true;
		case R.id.setting:
			Intent intSetting = new Intent(getApplicationContext(),SettingsActivity.class);
			startActivity(intSetting);
			return true;
		case R.id.mn_clear:
			File file = new File(Environment.getExternalStorageDirectory()+"/categories.xml");
			return file.delete();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
    
    @SuppressWarnings("static-access")
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
            .setIcon(R.drawable.default_icon)
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
		case ConstantsAndKey.CUSTOM_LOC:
				customLoc = new Dialog(mContext);
				customLoc.requestWindowFeature(Window.FEATURE_NO_TITLE);
				customLoc.setContentView(R.layout.ph_change_location);				
				customLoc.setCancelable(true);				
				ImageButton btnSearch = (ImageButton)customLoc.findViewById(R.id.btnSearch);							        	            
		        btnSearch.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						TextView textView = (AutoCompleteTextView)CategoriesListActivity.this.customLoc.findViewById(R.id.searchLocation);	
						
						final List<MyAddress> lstAd = PointAddressUtil.getAdress(textView.getText().toString(),getApplicationContext());
						if (lstAd !=null){
							ListView addressList = (ListView)CategoriesListActivity.this.customLoc.findViewById(android.R.id.list);
							ListAdapter mAdapter = new LocationAdapter(getApplicationContext(), R.layout.ph_row_location, lstAd);
							addressList.setAdapter(mAdapter);
							addressList.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> av,
										View v, int pos, long id) {
									currentLoc = new Location(LocationManager.NETWORK_PROVIDER);
									currentLoc.setLatitude(lstAd.get(pos).getLat());
									currentLoc.setLongitude(lstAd.get(pos).getLng());
									GeoPoint point = new GeoPoint(
						   			          (int) (currentLoc.getLatitude() * 1E6), 
						   			          (int) (currentLoc.getLongitude() * 1E6));
						   			String currentAdd = mContext.getResources().getString(R.string.near);	
						   			try {
						   			currentAdd += PointAddressUtil.ConvertPointToAddress(point, mContext);
						   			}
						   			catch (UnknownHostException ex){
						   				cur_add.setText(R.string.error_network);
						   				return;
						   			}
							   		if (currentAdd!=null){
							   			cur_add.setText(currentAdd);
							   		}
						          CategoriesListActivity.this.customLoc.dismiss();						             
								}
							});
						}						
						 
						        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

					}
				});
				return customLoc;
		case ConstantsAndKey.ADD_CAT:
			LayoutInflater factory = LayoutInflater.from(mContext);
            final View textEntryView = factory.inflate(R.layout.ph_add_cat, null);
            return new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_child_add)
                .setTitle(R.string.cat_add_title)
                .setView(textEntryView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
    
                        // Add new category and write to xml file
                    	EditText edTxt = (EditText)textEntryView.findViewById(R.id.name_edit);
                    	if (edTxt.getText()!=null){
                    		int lastId = lsGroup.get(6).getChild().get(lsGroup.get(6).getChild().size()-1).getId();
                    		Child ch = new Child();
                    		ch.setId(++lastId);
                    		ch.setImgID("drawable/ic_child_user");
                    		ch.setName(edTxt.getText().toString());
                    		ch.setTypes("establishment");
                    		lsGroup.get(6).addChild(ch);
                    		adapter.notifyDataSetChanged();
                    		CatParser.writeXml(lsGroup);
                    	}
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                       dialog.dismiss();
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
			

		}

		@Override
		public void onProviderEnabled(String provider) {
			

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			

		}
	}
    
    //Task to parse xml and create main screen
    private class CreateCat extends AsyncTask<Void, Void, List<MainGroup>>{

		@Override
		protected List<MainGroup> doInBackground(Void... params) {			
			CatParser dp = new CatParser(mContext);
	    	List<MainGroup> results = dp.parse();
	    	return results;
		}
		
		@Override
		protected void onPostExecute(List<MainGroup> result) {
			
			if (result !=null){
				lsGroup = result;				
				adapter = new ExpListAdapter(mContext, lsGroup);
		        listView.setAdapter(adapter);		        		       
			}
		}
    	
    }
    
    private class GetLocation extends AsyncTask<Context, Void, Void>{
    	

         protected void onPreExecute()
         {
             
             loc_progress.setVisibility(View.VISIBLE);
             cur_add.setText(R.string.get_cur_add);
         }

         @Override 
         protected Void doInBackground(Context... params)
         {
             //Wait 15 seconds to see if we can get a location from either network or GPS, otherwise stop
             Long t = Calendar.getInstance().getTimeInMillis();
             while (!hasLocation && Calendar.getInstance().getTimeInMillis() - t < 30000) {
                 try {
                     Thread.sleep(30000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             };
             return null;
         }

         protected void onPostExecute(final Void unused)
         {
             
             if (currentLoc != null)
             {
            	 loc_progress.setVisibility(View.GONE);
            	 GeoPoint point = new GeoPoint(
   			          (int) (currentLoc.getLatitude() * 1E6), 
   			          (int) (currentLoc.getLongitude() * 1E6));
   			String currentAdd = mContext.getResources().getString(R.string.near);	
   			try {
   			currentAdd += PointAddressUtil.ConvertPointToAddress(point, mContext);
   			}
   			catch (UnknownHostException ex){
   				cur_add.setText(R.string.error_network);
   				return;
   			}
	   		if (currentAdd!=null){
	   			cur_add.setText(currentAdd);
	   		}
             }
             else
             {
                 cur_add.setText(R.string.err_address);
                 loc_progress.setVisibility(View.GONE);
             }
         }

    	
    }
}