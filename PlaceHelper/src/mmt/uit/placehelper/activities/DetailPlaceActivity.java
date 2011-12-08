package mmt.uit.placehelper.activities;

import java.util.Collections;

import mmt.uit.placehelper.MainActivity;
import mmt.uit.placehelper.models.FavoriteModel;
import mmt.uit.placehelper.models.Place;
import mmt.uit.placehelper.models.PlaceDetail;
import mmt.uit.placehelper.models.PlaceDetailRs;
import mmt.uit.placehelper.models.PlaceModel;
import mmt.uit.placehelper.models.PlacesList;
import mmt.uit.placehelper.services.DataService;
import mmt.uit.placehelper.services.SearchPlace;
import mmt.uit.placehelper.services.SearchService;
import mmt.uit.placehelper.utilities.ConstantsAndKey;
import mmt.uit.placehelper.utilities.LoadImage;
import mmt.uit.placehelper.utilities.MyLocation;
import mmt.uit.placehelper.utilities.RslistAdapter;
import mmt.uit.placehelper.utilities.SortPlace;

import mmt.uit.placehelper.R;
import mmt.uit.placehelper.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DetailPlaceActivity extends Activity {
	
	//Button Bar
	private ImageButton btnCall, btnWeb, btnEmail, btnMap, btnFavorite; 
	//Textview
	private TextView txtPlaceName, txtAddress, txtPhone,txtWebsite;
	//Image View
	private ImageView imgMap;
	//other
	private Bitmap mBitmap;
	private RatingBar ratBar;
	private WebView mWebView;
	private PlaceModel place;
	private double curLon, curLat, lng,lat;
	private int placeId;
	private Boolean fromFv;
	private DataService dataService;
	private Bundle b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ph_detail_place2);
	//Get instance from view
		btnWeb = (ImageButton)findViewById(id.btnFacebook);
		btnCall = (ImageButton) findViewById(R.id.btnCall);
		btnEmail = (ImageButton) findViewById(R.id.btnEmail);
		btnMap = (ImageButton) findViewById(R.id.btnMap);
		btnFavorite = (ImageButton) findViewById(R.id.btnFavorite);		
		/*txtPlaceName = (TextView)findViewById(R.id.de_place_name);
		txtPhone = (TextView)findViewById(R.id.de_phone);
		txtWebsite = (TextView)findViewById(R.id.de_website);
		ratBar = (RatingBar)findViewById(R.id.de_rate_bar);*/
		//txtAddressFull = (TextView)findViewById(R.id.address_full);		
		//txtPhoneNumber = (TextView)findViewById(R.id.phone_number);
		//txtDistance = (TextView)findViewById(R.id.distance);
		//txtAddress = (TextView)findViewById(R.id.de_address);
		mWebView = (WebView)findViewById(R.id.webview);
		mWebView.setWebViewClient(new WebInfoClient());
		//Set Listener for button		
		/*btnWeb.setOnClickListener(mClickListener);
		btnCall.setOnClickListener(mClickListener);
		btnEmail.setOnClickListener(mClickListener);
		btnMap.setOnClickListener(mClickListener);
		btnFavorite.setOnClickListener(mClickListener);*/
		
		Bundle mBundle = getIntent().getExtras();
		Place pl = mBundle.getParcelable("place");
		GetDetail gd = new GetDetail();
		gd.execute(pl);
		/*b = getIntent().getExtras();
		fromFv = b.getBoolean("fromFv");
		if (fromFv == false){
		placeId = b.getInt("posittion");
		curLat = b.getDouble("curlat");
		curLon = b.getDouble("curlon");
		place = SearchService.placeModel.get(placeId);
		lng = place.getLng();
		lat = place.getLat();
	//Load Place Map
		mBitmap = LoadImage.downloadImage(place.getStaticMapUrl());
		if (mBitmap!=null)
		imgMap.setImageBitmap(mBitmap);
	//Load data to view
		txtPlaceName.setText(place.getTitle());
		txtAddress.setText(place.getAddressLines().get(0).toString());
		String address = "";
    	for (String s : place.getAddressLines()) {
    		address += s + " ";
    	}
		//txtAddressFull.setText("Ä�ia chá»‰: " + address);
		//if(place.getPhoneNumbers()!=null){
		//	txtPhoneNumber.setText("Ä�iá»‡n thoáº¡i: "+ place.getPhoneNumbers().get(0).getNumber());
		//}
		
		//txtDistance.setText("Khoáº£ng cĂ¡ch: " + place.getDistance() + " km");
		}
		else {
			curLat = b.getDouble("curlat");
			curLon = b.getDouble("curlon");
			lng = Double.parseDouble(b.getString("lng"));
			lat = Double.parseDouble(b.getString("lat"));
		//Load Place Map
			mBitmap = LoadImage.downloadImage(b.getString("map"));
			if (mBitmap!=null)
			imgMap.setImageBitmap(mBitmap);
		//Load data to view
			txtPlaceName.setText(b.getString("title"));
			txtAddress.setText(b.getString("address"));
			//txtAddressFull.setText("Ä�ia chá»‰: " + b.getString("addressFull"));
			//if (b.getString("phone")!=null){
			//txtPhoneNumber.setText("Ä�iá»‡n thoáº¡i: "+ b.getString("phone"));
			//}
			FavoriteModel fv = new FavoriteModel(b.getString("lat"), b.getString("lng"));
			//txtDistance.setText("Khoáº£ng cĂ¡ch: " + fv.getDistance(curLat, curLon) + " km");
			//txtDistance.setText("Distance: " + place.getDistance() + " km");
*/		}
	
		//Create Task to make request and get places detail
		private class GetDetail extends AsyncTask<Place,Void, PlaceDetailRs>{
			private String keyword;//keyword to search 
			
			
			@Override
			protected PlaceDetailRs doInBackground(Place... params) {
				// TODO Auto-generated method stub
								
				PlaceDetailRs pd=null; //initialize place detail result				
				pd = SearchPlace.getDetail(params[0].reference);
				return pd;				
			}
			
			@Override
			protected void onPostExecute(PlaceDetailRs result) {
				// TODO Auto-generated method stub
				if (result!=null && result.status.contentEquals(ConstantsAndKey.STATUS_OK)){	
					/*txtPlaceName.setText(result.result.name);
					txtAddress.setText(result.result.address);
					ratBar.setRating(result.result.rating);
					txtPhone.setText(result.result.phone);
					txtWebsite.setText(result.result.website);*/
					mWebView.getSettings().setJavaScriptEnabled(true);			
					mWebView.getSettings().setDomStorageEnabled(true);	
					
					String reviewURL = result.result.url + "&view=feature&mcsrc=google_reviews&num=10&start=0";
					mWebView.loadUrl(reviewURL);
					
							
				}
				else
					Toast.makeText(getApplicationContext(), getResources().getText(R.string.seact_rq_error), Toast.LENGTH_SHORT).show();
			}
		}
		
		private class WebInfoClient extends WebViewClient {
			@Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);
		        view.setClickable(false);
		        return true;
		    }
			
			/*@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
			}*/
		}	
		
	/*	class MyJavaScriptInterface  
		{  
		    
		    public void showHTML(String html)  
		    {  
		        Log.v("html", html);
		    }  
		}  */
}
	
	
	
	//Button Click Listener
		/*private View.OnClickListener mClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Button Web
				if (btnWeb.isPressed()){
					Bundle mBundle = new Bundle();
					if(fromFv==false){
						mBundle.putString("url", place.getUrl());
					}
					else 
					{
						mBundle.putString("url", b.getString("webUrl"));
					}
				Intent mWebIntent = new Intent(getApplicationContext(), WebInfoActivity.class);
				mWebIntent.putExtras(mBundle);
				startActivity(mWebIntent);
				}
				//Button Call
				if(btnCall.isPressed()){
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
					}
				//Button Email
				//Call extra email application to send mail
				if (btnEmail.isPressed()){
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
				}
				//Button Map
				if(btnMap.isPressed()){
					Bundle mBundle = new Bundle();
					mBundle.putBoolean("showall", false);
					mBundle.putDouble("curlon", curLon);
					mBundle.putDouble("curlat", curLat);
					mBundle.putDouble(ConstantsAndKey.KEY_LAT, lat);
					mBundle.putDouble(ConstantsAndKey.KEY_LNG, lng);
					Intent mMapIntent = new Intent(getApplicationContext(), ViewOnMapActivity.class);
					mMapIntent.putExtras(mBundle);
					startActivity(mMapIntent);
				}
				//Button Favorite
				if(btnFavorite.isPressed()){
					String pPhone=null;
					if (fromFv==true)
					{Toast.makeText(getApplicationContext(), "Ä�Ă£ cĂ³ trong má»¥c yĂªu thĂ­ch", Toast.LENGTH_LONG).show();
					}
					else {
					String pName = place.getTitle();
					String pMapUrl = place.getStaticMapUrl();
					if (place.getPhoneNumbers()!=null)
					{
						pPhone = place.getPhoneNumbers().get(0).getNumber();
					}
					String pAddress = place.getAddressLines().get(0).toString();
					String lng = ""+place.getLng();
					String lat = ""+place.getLat();
					String address = "";
					String webUrl = place.getUrl();
					for (String s : place.getAddressLines()) {
						address += s + " ";
					}
					dataService = new DataService(getApplicationContext());
					dataService.open();
					
					if(dataService.isExisted(lat, lng, address)){
						Toast.makeText(getApplicationContext(), "Ä�Ă£ cĂ³ trong má»¥c yĂªu thĂ­ch", Toast.LENGTH_LONG).show();
					}else{
						dataService.insertFavorite(pName, pAddress,	address, pPhone, lng, lat, pMapUrl, webUrl);
						Toast.makeText(getApplicationContext(), "ThĂªm thĂ nh cĂ´ng", Toast.LENGTH_LONG).show();
					}
				}
				}
			}
		};*/
		
		
		//Menu creation
	    /*@Override
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
				Intent mIntent = new Intent(DetailPlaceActivity.this,CategoriesActivity.class);				
				Bundle mBundle = new Bundle();
                mBundle.putDouble(ConstantsAndKey.KEY_LAT,curLat);
                mBundle.putDouble(ConstantsAndKey.KEY_LNG, curLon);
                mIntent.putExtras(mBundle);
                DetailPlaceActivity.this.startActivity(mIntent);
				DetailPlaceActivity.this.finish();
				return true;
			case R.id.mn_lstfavorite:
				Intent intent = new Intent(getApplicationContext(), ListFavoriteActivity.class);
				Bundle mBundle1 = new Bundle();
					mBundle1.putDouble("curlat", curLat);			
					mBundle1.putDouble("curlon", curLon);
				intent.putExtras(mBundle1);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
			
		}*/
//}
