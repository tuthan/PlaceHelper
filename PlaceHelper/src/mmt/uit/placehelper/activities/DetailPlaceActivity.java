package mmt.uit.placehelper.activities;

import java.net.UnknownHostException;

import com.google.android.maps.GeoPoint;

import mmt.uit.placehelper.models.Direction;
import mmt.uit.placehelper.models.Place;
import mmt.uit.placehelper.models.PlaceDetail;
import mmt.uit.placehelper.models.PlaceDetailRs;
import mmt.uit.placehelper.models.PlaceLocation;
import mmt.uit.placehelper.services.FavDataService;
import mmt.uit.placehelper.services.SearchPlace;
import mmt.uit.placehelper.utilities.ConstantsAndKey;
import mmt.uit.placehelper.utilities.PointAddressUtil;
import mmt.uit.placehelper.R;
import mmt.uit.placehelper.R.id;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DetailPlaceActivity extends Activity {
	
	//Button Bar
	private ImageButton btnCall, btnWeb, btnEmail, btnMap, btnFavorite; 
	//Textview
	private TextView txtPlaceName, txtAddress, txtPhone,txtWebsite,txtDistance,txtTime,txtDirection;
	//Image View
	private ImageView add_fav;
	private RatingBar ratBar;	
	private PlaceDetail plDetail;	
	private Place place;
	private Bundle mBundle;
	private String lang = "en";
	private int img;
	private SharedPreferences mSharePref;
	private PlaceLocation curLoc;
	private Context mContext;
	private Spinner spDirMode;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ph_detail_place);
		//Get instance from view
		btnWeb = (ImageButton)findViewById(id.btnFacebook);
		btnCall = (ImageButton) findViewById(R.id.btnCall);
		btnEmail = (ImageButton) findViewById(R.id.btnEmail);
		btnMap = (ImageButton) findViewById(R.id.btnMap);
		btnFavorite = (ImageButton) findViewById(R.id.btnFavorite);
		txtPlaceName = (TextView)findViewById(R.id.de_place_name);
		txtAddress = (TextView)findViewById(R.id.de_address);
		txtPhone = (TextView)findViewById(R.id.de_phone);
		txtWebsite = (TextView)findViewById(R.id.de_website);
		txtDistance = (TextView)findViewById(R.id.dt_distance);
		txtTime = (TextView)findViewById(R.id.dt_time);
		txtDirection = (TextView)findViewById(R.id.dt_guide);
		ratBar = (RatingBar)findViewById(R.id.de_rate_bar);
		add_fav = (ImageView)findViewById(R.id.de_add_fav);
		btnFavorite.setOnClickListener(mClickListener);
		btnMap.setOnClickListener(mClickListener);
		spDirMode = (Spinner)findViewById(R.id.de_dir_mode);
		mContext = this;
		mBundle = getIntent().getExtras();
		if(mBundle.getBoolean(ConstantsAndKey.KEY_FROM_FAV)){
			plDetail = mBundle.getParcelable(ConstantsAndKey.KEY_PL_DETAIL);
			setDetail(plDetail);
		}
		else
		{
		place = mBundle.getParcelable("place");
		img = mBundle.getInt("img");
		curLoc = mBundle.getParcelable(ConstantsAndKey.KEY_CURLOC);
		mSharePref = PreferenceManager.getDefaultSharedPreferences(this);
    	if(mSharePref!=null){
    	lang = mSharePref.getString(getResources().getString(R.string.prefkey_lang), getResources().getStringArray(R.array.arr_lang_value)[0]);    	
    	}
		GetDetail gd = new GetDetail(lang);
		gd.execute(place);
		}
						
		}
	
	
		private class MyOnItemSelectListener implements OnItemSelectedListener{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				GetDirection getDir = new GetDirection();
				getDir.execute(plDetail.getAddress(),parent.getItemAtPosition(pos).toString().toLowerCase());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// nothing
				
			}
			
		}
	
		//Create Task to make request and get places detail
		private class GetDetail extends AsyncTask<Place,Void, PlaceDetailRs>{
			private String lang;
			
			
			public GetDetail(String lang){
				this.lang = lang;
			}
			
			
			@Override
			protected PlaceDetailRs doInBackground(Place... params) {
				// TODO Auto-generated method stub
								
				PlaceDetailRs pd=null; //initialize place detail result						
				pd = SearchPlace.getDetail(params[0].getReference(),lang);				 				
				return pd;				
			}
			
			@Override
			protected void onPostExecute(PlaceDetailRs result) {
				// TODO Auto-generated method stub
				if (result!=null && result.getStatus().contentEquals(ConstantsAndKey.STATUS_OK)){	
					plDetail = result.getResult();
					setDetail(plDetail);					
					GetDirection getDir = new GetDirection();
					getDir.execute(result.getResult().getAddress(),"driving");
					spDirMode.setOnItemSelectedListener(new MyOnItemSelectListener());
											
				}
				else
					{
					Toast.makeText(getApplicationContext(), getResources().getText(R.string.seact_rq_error), Toast.LENGTH_SHORT).show();
					}				
			}
		}
		
		//Create Task to make request and get places detail
				private class GetDirection extends AsyncTask<String,Void, Direction>{
					
					/**
					 * @param params[0] destination address, params[1] is direction mode (driving, walking or bicycling)
					 */
					@Override
					protected Direction doInBackground(String... params) {
						Direction direct = new Direction();	
						 GeoPoint point = new GeoPoint(
			   			          (int) (curLoc.getLat() * 1E6), 
			   			          (int) (curLoc.getLng() * 1E6));
						 String currentAdd = "";
						 try {
					   			currentAdd = PointAddressUtil.ConvertPointToAddress(point, mContext);
					   			}
					   			catch (UnknownHostException ex){
					   				ex.printStackTrace();
					   			}
						direct = SearchPlace.getDirection(currentAdd, params[0], params[1]);						
						
						try {
							Thread.sleep(3000);//Thread pause for 3s 
						} catch (InterruptedException e) {							
							e.printStackTrace();
						}
						return direct;				
					}
					
					@Override
					protected void onPostExecute(Direction result) {
						
						
						if(result !=null&&result.getStatus().equalsIgnoreCase(ConstantsAndKey.STATUS_OK)){
							txtDistance.setText(getResources().getText(R.string.de_distance)+" " + result.getRoutes().get(0).getLegs().get(0).getDistance().getDistanceInText());
							txtTime.setText(getResources().getText(R.string.de_time)+" " + result.getRoutes().get(0).getLegs().get(0).getDuration().getDurationInText());
							txtDirection.setMovementMethod(new ScrollingMovementMethod());
							txtDirection.setText(Html.fromHtml(result.getRoutes().get(0).getLegs().get(0).getInstructions()));
						}
						
						else {
							txtDirection.setText(R.string.de_dir_not_found);
						}
					}
				}
		
		private void setDetail (PlaceDetail item){							
			txtPlaceName.setText(item.getName());
			txtAddress.setText(item.getAddress());
			ratBar.setRating(item.getRating());
			txtPhone.setText(item.getPhone());
			txtWebsite.setText(item.getWebsite());					
			if (item.isFavorite()){
				add_fav.setImageResource(R.drawable.ic_rsfavorite);
			}
			else {
				add_fav.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						FavDataService dataService = new FavDataService(getApplicationContext());
						dataService.open();
						
						if(dataService.isExisted(plDetail.getId())){
							Toast.makeText(getApplicationContext(), "Existed", Toast.LENGTH_LONG).show();
							dataService.close();
						}else{
							dataService.createFav(plDetail.getId(), plDetail.getName(), plDetail.getAddress(), plDetail.getPhone(), plDetail.getRating(), plDetail.getGeometry().location.getLng(), plDetail.getGeometry().location.getLat(), plDetail.getUrl(), plDetail.getWebsite(), img);
							dataService.close();
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.de_success), Toast.LENGTH_LONG).show();
							add_fav.setImageResource(R.drawable.ic_rsfavorite);
							add_fav.setClickable(false);
						}
					}
				});
			}			
		}
		private class WebInfoClient extends WebViewClient {
			@Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);
		        view.setClickable(false);
		        return true;
		    }
			
			
		}	
		

	
	//Button Click Listener
		private View.OnClickListener mClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Button Web
				
				//Button Call
				/*if(btnCall.isPressed()){
					try {
					   	Intent intent = new Intent(Intent.ACTION_CALL);
					   	String phoneNumber=null;
					   	if (fromFv==false){
		            	phoneNumber = "tel: " + place.getPhoneNumbers().get(0).getNumber();
					   	}
					   	else {
					   		if (b.getString("phone")!=null){
					   		phoneNumber = "tel: " + b.getString("phone");
					   		}
					   	}
		            	intent.setData(Uri.parse(phoneNumber));
		            	startActivity(intent);
						}
						catch (Exception e) {
		            	Toast.makeText(getApplicationContext(), 
		            			"KhĂ´ng gá»�i Ä‘Æ°á»£c", Toast.LENGTH_SHORT).show();
						}
					}*/
				//Button Email
				//Call extra email application to send mail
				/*if (btnEmail.isPressed()){
					final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);                
	                emailIntent.setType("plain/text");           
	                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "friends@domainl.com");         
	                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ä�á»‹a Ä‘iá»ƒm hay"); 
	                if(fromFv==false){
	                	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Xem nĂ¨, chá»— nĂ y hay láº¯m "+ place.getUrl());
					}
					else 
					{
						emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Xem nĂ¨, chá»— nĂ y hay láº¯m "+ b.getString("webUrl"));
					}
	                 
	                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
				}*/
				//Button Map
				if(btnMap.isPressed()){
					Intent mMapIntent = new Intent(getApplicationContext(), ViewOnMapActivity.class);
					mBundle.putBoolean(ConstantsAndKey.KEY_SHOW_ALL, false);
					mMapIntent.putExtras(mBundle);
					startActivity(mMapIntent);
				}
				//Button Favorite
				if(btnFavorite.isPressed()){
					FavDataService dataService = new FavDataService(getApplicationContext());
					dataService.open();
					
					if(dataService.isExisted(plDetail.getId())){
						Toast.makeText(getApplicationContext(), "Existed", Toast.LENGTH_LONG).show();
						dataService.close();
					}else{
						dataService.createFav(plDetail.getId(), plDetail.getName(), plDetail.getAddress(), plDetail.getPhone(), plDetail.getRating(), plDetail.getGeometry().location.getLng(), plDetail.getGeometry().location.getLat(), plDetail.getUrl(), plDetail.getWebsite(),img);
						dataService.close();
						Toast.makeText(getApplicationContext(), "Successed", Toast.LENGTH_LONG).show();
						add_fav.setImageResource(R.drawable.ic_rsfavorite);
					}
				}
				}
			
		};
		
		
		//Menu creation
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	// TODO Auto-generated method stub    	
	    	MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.mn_detail, menu);
			return true;
	    }
	    
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			switch (item.getItemId())
			{
			case R.id.mn_home:
				Intent mIntent = new Intent(DetailPlaceActivity.this,CategoriesListActivity.class);				
				Bundle mBundle = new Bundle();
                //mBundle.putDouble(ConstantsAndKey.KEY_LAT,curLat);
                //mBundle.putDouble(ConstantsAndKey.KEY_LNG, curLng);
                mIntent.putExtras(mBundle);
                DetailPlaceActivity.this.startActivity(mIntent);
				DetailPlaceActivity.this.finish();
				return true;
			case R.id.mn_lstfavorite:
				Intent intent = new Intent(getApplicationContext(), ListFavoriteActivity.class);
				/*Bundle mBundle1 = new Bundle();
					mBundle1.putDouble("curlat", curLat);			
					mBundle1.putDouble("curlon", curLon);
				intent.putExtras(mBundle1);*/
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
			
		}
}
