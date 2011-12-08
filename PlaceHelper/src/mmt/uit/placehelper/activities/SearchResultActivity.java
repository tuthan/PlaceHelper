package mmt.uit.placehelper.activities;



import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mmt.uit.placehelper.models.PlaceModel;
import mmt.uit.placehelper.models.PlacesList;
import mmt.uit.placehelper.services.SearchPlace;
import mmt.uit.placehelper.services.SearchService;
import mmt.uit.placehelper.utilities.ConstantsAndKey;
import mmt.uit.placehelper.utilities.PlaceAdapter;
import mmt.uit.placehelper.utilities.RslistAdapter;
import mmt.uit.placehelper.utilities.SortPlace;

import mmt.uit.placehelper.R;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultActivity extends ListActivity {
	

	private ImageButton btnSearch;
	private ImageButton btnShowAllMap;
	private TextView txtCurAdd, txtrs;
	private String keyWord;
	private Double curLat, curLon;
    //private SearchService searchSrv;
    private RslistAdapter rsAdapter;
    //private Thread searchThread; //Make another thread to search 
    private EditText edtTxtSearch;
    private Location curLoc = new Location("GPS");
    private PlacesList lsPlace;
    
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.ph_list_results); 
        
        txtrs = (TextView)findViewById(R.id.txt_result);
        
        //Get value from ListCategoriesActivity
        Bundle b = getIntent().getExtras();
        keyWord = b.getString("search");        
        
        //Add listener
        //btnSearch.setOnClickListener(mClickListener);
        //btnShowAllMap.setOnClickListener(mClickListener);
        //Process Search Result
        
        /*curLat = b.getDouble("curlat");
        curLon = b.getDouble("curlon");*/
        curLoc.setLatitude(b.getDouble("curlat"));
        curLoc.setLongitude(b.getDouble("curlon"));
        //searchSrv = new SearchService(searchWhat, curLat, curLon);
        //rsAdapter = new PlaceAdapter(getApplicationContext(), R.layout.ph_result_row, SearchService.placeModel);
        //setListAdapter(rsAdapter);
        FindPlace fp = new FindPlace(keyWord);
        fp.execute(curLoc);
        //setProgressBarIndeterminateVisibility(true);
        
    }
	
	//Create Task to make request and get places list
	private class FindPlace extends AsyncTask<Location,Void, PlacesList>{
		private String keyword;//keyword to search 
		
		public FindPlace(String keyword){
			
			this.keyword = keyword;
		}
		@Override
		protected PlacesList doInBackground(Location... params) {
			// TODO Auto-generated method stub
					
			PlacesList pl=null; //initialize list result
			pl = SearchPlace.getPlaceList(params[0].getLatitude(), params[0].getLongitude(),keyword);
			return pl;				
		}
		
		@Override
		protected void onPostExecute(PlacesList result) {
			// TODO Auto-generated method stub
			if (result!=null && result.status.contentEquals(ConstantsAndKey.STATUS_OK)){			
				txtrs.setText(getResources().getText(R.string.seact_result)+ keyWord);
				lsPlace = result;
				Collections.sort(lsPlace.results, new SortPlace());
				rsAdapter = new RslistAdapter(getApplicationContext(), R.layout.ph_result_row, lsPlace.results);
				setListAdapter(rsAdapter);			
			}
			else
				if (result.status.contentEquals(ConstantsAndKey.NO_RESULT))
					//Toast.makeText(getApplicationContext(), getResources().getText(R.string.seact_nors), Toast.LENGTH_SHORT).show();
				{									
					txtrs.setText(getResources().getText(R.string.seact_nors) + keyWord);
				}
				else 
					Toast.makeText(getApplicationContext(), getResources().getText(R.string.seact_rq_error), Toast.LENGTH_SHORT).show();
		}
	}
	
	/*View.OnClickListener mClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(btnSearch.isPressed()){
			setProgressBarIndeterminateVisibility(true);
			//searchSrv = new SearchService(edtTxtSearch.getText().toString(), curLat, curLon);
			//parseData();
			}
			if(btnShowAllMap.isPressed()){
				Bundle b = new Bundle();
				Intent mIntent = new Intent(getApplicationContext(), ViewOnMapActivity.class);
				b.putBoolean("showall", true);
				b.putDouble("curlon", curLon);
				b.putDouble("curlat", curLat);
				mIntent.putExtras(b);
				startActivity(mIntent);
			}
		}
	};*/
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Intent mIntent = new Intent(this, DetailPlaceActivity.class);
		/*Bundle mBundle = new Bundle();
		mBundle.putParcelable("place", lsPlace.results.get(position));*/
		mIntent.putExtra("place", lsPlace.results.get(position));
		startActivity(mIntent);
		
	}
	
	
	/*private void getResult(){
		searchThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
	}*/
	
	/*private void parseData() {
		parseThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				searchSrv.parseData(0);
				if (SearchService.placeModel.size()==8){
				searchSrv.parseData(8);
				}
				if (SearchService.placeModel.size()==16){
				searchSrv.parseData(16);
				}	
				if (SearchService.placeModel.size()==24){
					searchSrv.parseData(24);
					}
				mHandler.sendMessage(mHandler.obtainMessage(searchSrv.getState()));				
			}
		});
		parseThread.start();
	}*/
					
	/*final Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what){
			case 1: 
				setProgressBarIndeterminateVisibility(false);
				Collections.sort(SearchService.placeModel,new SortPlace());
				rsAdapter.notifyDataSetChanged();				
				break;
			}
		}
	};*/
		
}

