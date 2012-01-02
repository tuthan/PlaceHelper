package mmt.uit.placehelper.activities;



import java.util.Collections;
import java.util.List;

import mmt.uit.placehelper.models.Place;
import mmt.uit.placehelper.models.PlaceLocation;
import mmt.uit.placehelper.models.PlacesList;
import mmt.uit.placehelper.services.FavDataService;
import mmt.uit.placehelper.services.SearchPlace;
import mmt.uit.placehelper.utilities.ConstantsAndKey;
import mmt.uit.placehelper.utilities.SortPlace;

import mmt.uit.placehelper.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultActivity extends ListActivity {
	

	private ImageButton btnSearch;
	private ImageButton btnShowAllMap;
	private TextView txtCurAdd, txtrs;
	private String keyWord;
    private RslistAdapter rsAdapter;       
    private PlaceLocation curLoc = new PlaceLocation();
    private PlacesList lsPlace = null;
    private Context mContext;
    private ProgressBar search_progress;
      
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        mContext = getApplicationContext();
        setContentView(R.layout.ph_list_results);                
        txtrs = (TextView)findViewById(R.id.txt_result);
        search_progress = (ProgressBar)findViewById(R.id.rs_progress);
        
        //Get value from ListCategoriesActivity
        Bundle b = getIntent().getExtras();
        keyWord = b.getString("search");               
        curLoc.lat= b.getDouble("curlat");
        curLoc.lng = b.getDouble("curlng");        
        
        //Task to get place result from google place service
        FindPlace fp = new FindPlace(keyWord);
        fp.execute(curLoc);      
        
    }
	
	
	
	//Create Task to make request and get places list
	private class FindPlace extends AsyncTask<PlaceLocation,Void, PlacesList>{
		private String keyword;//keyword to search 
		
		public FindPlace(String keyword){
			
			this.keyword = keyword;
		}
		@Override
		protected PlacesList doInBackground(PlaceLocation... params) {		
					
			PlacesList pl=null; //initialize list result
			pl = SearchPlace.getPlaceList(params[0].lat, params[0].lng,keyword);
			return pl; //Return list Place				
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			search_progress.setVisibility(View.VISIBLE);
			txtrs.setText(getResources().getText(R.string.seact_load));
			
		}
		//Display result to the screen after finish getting place list
		@Override
		protected void onPostExecute(PlacesList result) {
			if (result==null){
				Toast.makeText(mContext, getResources().getText(R.string.seact_connect_error), Toast.LENGTH_SHORT).show();
				txtrs.setText(getResources().getText(R.string.seact_connect_error));
				return;
			}
			if (result!=null && result.status.contentEquals(ConstantsAndKey.STATUS_OK)){			
				FavDataService dataSrv = new FavDataService(mContext);
				dataSrv.open();
				txtrs.setText(getResources().getText(R.string.seact_result)+ keyWord);
				lsPlace = result;				
				Collections.sort(lsPlace.results, new SortPlace());
				for (int i=0;i<lsPlace.results.size();i++){
					if (dataSrv.isExisted(lsPlace.results.get(i).id)){
						lsPlace.results.get(i).setIsFavorite(true);
					}
					else {
						lsPlace.results.get(i).setIsFavorite(false);
					}
				}
				rsAdapter = new RslistAdapter(mContext, R.layout.ph_result_row,  lsPlace.results);
				setListAdapter(rsAdapter);
				dataSrv.close();
			}
			else
				if (result.status.contentEquals(ConstantsAndKey.NO_RESULT))
					//Toast.makeText(getApplicationContext(), getResources().getText(R.string.seact_nors), Toast.LENGTH_SHORT).show();
				{									
					txtrs.setText(getResources().getText(R.string.seact_nors) + keyWord);
				}
				else 
					Toast.makeText(mContext, getResources().getText(R.string.seact_rq_error), Toast.LENGTH_SHORT).show();
			
			search_progress.setVisibility(View.GONE);
			
		}
	}
	
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Intent mIntent = new Intent(this, DetailPlaceActivity.class);
		Place pl = lsPlace.results.get(position);
		Bundle mBundle = new Bundle();
		mBundle.putParcelable("place", pl);
		mBundle.putParcelable(ConstantsAndKey.KEY_CURLOC, curLoc);
		mIntent.putExtras(mBundle);
		startActivity(mIntent);
		
	}
	
	 @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater mInflater = getMenuInflater();
		mInflater.inflate(R.menu.mn_search_result, menu);
		return true;
	}
	 
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()){
		 case R.id.mn_all_map:
			 	Intent mIntent = new Intent(this, ViewOnMapActivity.class);				
				Bundle mBundle = new Bundle();
				mBundle.putBoolean(ConstantsAndKey.KEY_SHOW_ALL, true);
				mBundle.putParcelable(ConstantsAndKey.KEY_LST_PLACES, lsPlace);
				mBundle.putParcelable(ConstantsAndKey.KEY_CURLOC, curLoc);
				mIntent.putExtras(mBundle);
				startActivity(mIntent);
		 default:
				return super.onOptionsItemSelected(item);
		 }
	 }
	
	public class RslistAdapter extends ArrayAdapter<Place> {

		
		int resourceId;
		List<Place> array;
		
		
		
		public RslistAdapter(Context context, int textViewResourceId,
				List<Place> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
			
			this.resourceId = textViewResourceId;
			this.array = objects;
			
		}
		
			

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {		
			View view = convertView;
			
			if(view == null) {
				LayoutInflater li = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
				view = li.inflate(resourceId, null);
			}
			
			Place place = array.get(position);
			if(place != null) {
				
				TextView name = (TextView) view.findViewById(R.id.rs_name);					
				name.setText(place.name);
				
				TextView address = (TextView)view.findViewById(R.id.rs_address);
				address.setText(place.vicinity);
				
				TextView distance = (TextView)view.findViewById(R.id.rs_distance);
				distance.setText(String.valueOf(place.getDistance()));
				ImageView img = (ImageView)view.findViewById(R.id.rs_infavorite);
				if(place.getIsFavorite()){				
				img.setImageResource(R.drawable.ic_rsfavorite);
				}
				else img.setImageResource(R.drawable.ic_no_rsfavorite);
			}
			
			return view;
		}
	}
	
	
}

