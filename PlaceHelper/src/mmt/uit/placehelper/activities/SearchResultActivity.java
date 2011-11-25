package mmt.uit.placehelper.activities;



import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mmt.uit.placehelper.models.PlaceModel;
import mmt.uit.placehelper.services.SearchService;
import mmt.uit.placehelper.utilities.PlaceAdapter;
import mmt.uit.placehelper.utilities.SortPlace;

import mmt.uit.placehelper.R;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
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
	private TextView txtCurAdd;
	private String searchWhat;
	private Double curLat, curLon;
    private SearchService searchSrv;
    private PlaceAdapter rsAdapter;
    private Thread parseThread;
    private EditText edtTxtSearch;
    
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.ph_list_results); 
        
        //Get View
        edtTxtSearch = (EditText) findViewById(R.id.edtSearchTxt);
        txtCurAdd = (TextView)findViewById(R.id.txtCurAdd);
        btnSearch = (ImageButton)findViewById(R.id.rs_btSearch);
        btnShowAllMap = (ImageButton)findViewById(R.id.rs_btShowAllMap);
        //Get value from ListCategoriesActivity
        Bundle b = getIntent().getExtras();
        searchWhat = b.getString("search");        
        edtTxtSearch.setText(searchWhat);
        //Add listener
        btnSearch.setOnClickListener(mClickListener);
        btnShowAllMap.setOnClickListener(mClickListener);
        //Process Search Result
        txtCurAdd.setText("Gần " + b.getString("currentadd"));
        curLat = b.getDouble("curlat");
        curLon = b.getDouble("curlon");   
        searchSrv = new SearchService(searchWhat, curLat, curLon);
        rsAdapter = new PlaceAdapter(getApplicationContext(), R.layout.ph_result_row, SearchService.placeModel);
        setListAdapter(rsAdapter);
        setProgressBarIndeterminateVisibility(true);
        parseData();
        
        
    }
	
	View.OnClickListener mClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(btnSearch.isPressed()){
			setProgressBarIndeterminateVisibility(true);
			searchSrv = new SearchService(edtTxtSearch.getText().toString(), curLat, curLon);
			parseData();
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
	};
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		Bundle b = new Bundle();
		b.putBoolean("fromFv", false);
		b.putInt("posittion", position);
		b.putDouble("curlon", curLon);
		b.putDouble("curlat", curLat);
		Intent mIntent = new Intent(getApplicationContext(), DetailPlaceActivity.class);
		mIntent.putExtras(b);
		startActivity(mIntent);
	}
	
	
	
	private void parseData() {
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
	}
					
	final Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what){
			case 1: 
				setProgressBarIndeterminateVisibility(false);
				Collections.sort(SearchService.placeModel,new SortPlace());
				rsAdapter.notifyDataSetChanged();				
				break;
			}
		}
	};
		
}

