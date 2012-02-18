package mmt.uit.placehelper.activities;

import java.util.ArrayList;
import java.util.List;

import mmt.uit.placehelper.models.PlaceDetail;
import mmt.uit.placehelper.services.FavDataService;
import mmt.uit.placehelper.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListFavoriteActivity extends ListActivity {

	
	private FavoriteAdapter rsAdapter;
	private ListView listFavorites;
	private List<PlaceDetail> favList;
	protected static final int CONTEXTMENU_DELETEITEM = 0;
	protected static final int CONTEXTMENU_DELETEALL = 1;		
	private ImageButton btnMulti, btnDelete, btnDelAll;
	private boolean isMultiSl = false;
	private Context mContext;
	private final int GET_FAV=1;
	private final int DELETE_FAV=2;
	private final int DELETE_ALL = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ph_list_favorite);
		listFavorites = getListView();	
		mContext = this;			
		btnMulti = (ImageButton)findViewById(R.id.btnMultiselect);
		btnMulti.setOnClickListener(toggleCheckBox);
		btnDelete =(ImageButton)findViewById(R.id.btnDelete);
		btnDelete.setOnClickListener(delete);
		btnDelete.setClickable(false);
		btnDelAll =(ImageButton)findViewById(R.id.btnDeleteAll);
		btnDelAll.setOnClickListener(deleteAll);
		GetFavorite gfl = new GetFavorite();
		gfl.execute(GET_FAV);
		
	}
	
	View.OnClickListener toggleCheckBox = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			isMultiSl = !isMultiSl;
			rsAdapter = new FavoriteAdapter(mContext,
					R.layout.ph_favorite_row, favList,isMultiSl);
			setListAdapter(rsAdapter);
			
			
		}
	};
	
	View.OnClickListener deleteAll = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			AlertDialog.Builder alertBox = new AlertDialog.Builder(mContext);
			
			alertBox.setMessage(R.string.fav_del_all);
			//Yes
			alertBox.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {						
					GetFavorite gfl = new GetFavorite();
					gfl.execute(DELETE_ALL);
				}
			});
			//No
			alertBox.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			//Show dialog
			if(listFavorites.getChildCount()!=0)
			alertBox.show();
		}
	};
	
	View.OnClickListener delete = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertBox = new AlertDialog.Builder(mContext);
				alertBox.setMessage(R.string.fav_delete);
				//Yes
				alertBox.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {						
						GetFavorite gfl = new GetFavorite();
						gfl.execute(DELETE_FAV);
					}
				});
				//No
				alertBox.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				//Show dialog
				alertBox.show();
				
			}
		};
		
		

	//Create Task to get favorite list from database 
	private class GetFavorite extends AsyncTask<Integer, Void, List<PlaceDetail>>{

		@Override
		protected List<PlaceDetail> doInBackground(Integer... params) {
			FavDataService dataService;
			List<PlaceDetail> results = new ArrayList<PlaceDetail>();
			dataService = new FavDataService(mContext);
			dataService.open();	
			switch (params[0]){
			case GET_FAV:
				results = dataService.getListFavorites();
				dataService.close();
				break;
				
			case DELETE_FAV:
				int listCount = listFavorites.getChildCount();
				
				for (int i=0; i< listCount; i++){
					CheckBox mCBox = (CheckBox)(View)listFavorites.getChildAt(i).findViewById(R.id.fav_checkbox);
					if (mCBox.isChecked()){
						dataService.deleteFav(favList.get(i).getId());
						Log.v("ph_info", "Deleted " + i);					
						
					}
				}
				results = dataService.getListFavorites();
				dataService.close();
				break;
				
			case DELETE_ALL:
				dataService.deleteAll();
				Log.v("ph_info", "Deleted All ");
			}
			return results;
			
		}
		
		@Override
		protected void onPostExecute(List<PlaceDetail> result) {
			// TODO Auto-generated method stub
			if(result!=null){
			favList =  result;
			rsAdapter = new FavoriteAdapter(mContext,
					R.layout.ph_favorite_row, favList,isMultiSl);
			listFavorites.setAdapter(rsAdapter);
			}
		}
		
	}
	
	

	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		FavoriteModel o = (FavoriteModel) this.getListAdapter().getItem(position);
		
		Bundle b = new Bundle();
		b.putBoolean("fromFv", true);
		b.putDouble("curlon", curLon);
		b.putDouble("curlat", curLat);
		b.putString("title", o.getTitle());
		b.putString("address", o.getAddress());
		b.putString("addressFull", o.getAddressLines());
		b.putString("phone", o.getPhoneNumber());
		b.putString("map", o.getStaticMapUrl());
		b.putString("web", o.getWebUrl());
		b.putString("lng", o.getLng());
		b.putString("lat", o.getLat());
		b.putString("webUrl", o.getWebUrl());
		Intent mIntent = new Intent(getApplicationContext(), DetailPlaceActivity.class);
		mIntent.putExtras(b);
		startActivity(mIntent);
		
	}*/
	
	private class FavoriteAdapter extends ArrayAdapter<PlaceDetail> {

		
		int resourceId;
		List<PlaceDetail> array;
		boolean isMultiSl = false;
		
		
		public FavoriteAdapter(Context context, int textViewResourceId,
				List<PlaceDetail> objects, boolean multi) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
			
			this.resourceId = textViewResourceId;
			this.array = objects;
			this.isMultiSl = multi;
		}
		
			

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {		
			View view = convertView;
			
			if(view == null) {
				LayoutInflater li = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
				view = li.inflate(resourceId, null);
			}
			
			PlaceDetail place = array.get(position);
			if(place != null) {
				if(isMultiSl){
					CheckBox mCb = (CheckBox)view.findViewById(R.id.fav_checkbox);
					mCb.setVisibility(View.VISIBLE);
				}
				TextView name = (TextView) view.findViewById(R.id.fav_name);
				//TextView dis = (TextView) view.findViewById(R.id.fav_distance);	
				TextView add = (TextView) view.findViewById(R.id.fav_address);
				ImageView imgItem = (ImageView)view.findViewById(R.id.fav_img);
				RatingBar rateBar = (RatingBar)view.findViewById(R.id.fav_rate_bar);
				
				
				String s = array.get(position).getName();
				if (s.contains("Bank") || s.contains("bank") || s.contains("Union") || s.contains("Credit")) {
					imgItem.setImageResource(R.drawable.banks);
				}
				else if(s.contains("Plaza") || s.contains("Hotel") || s.contains("Inn") || s.contains("Suites")){
					imgItem.setImageResource(R.drawable.hotel);
				}else if(s.contains("Airport") || s.contains("Flight") ){
					imgItem.setImageResource(R.drawable.airport);
				}else if(s.contains("Taxi") || s.contains("Cab") || s.contains("Limos") || s.contains("Limo")){
					imgItem.setImageResource(R.drawable.taxi);
				}else if(s.contains("ATM")){
					imgItem.setImageResource(R.drawable.atm);
				}
				else {
					imgItem.setImageResource(R.drawable.app_icon);
				}
				
		
				name.setText(place.getName());
				add.setText(place.getAddress());
				rateBar.setRating(place.getRating());
				
			}
			
			return view;
		}
	}

}
