package mmt.uit.placehelper.utilities;

import java.util.List;


import mmt.uit.placehelper.models.PlaceDetail;

import mmt.uit.placehelper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class FavoriteAdapter extends ArrayAdapter<PlaceDetail> {

	Context context;
	int resourceId;
	List<PlaceDetail> array;
	boolean isMultiSl = false;
	
	
	public FavoriteAdapter(Context context, int textViewResourceId,
			List<PlaceDetail> objects, boolean multi) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
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
			
			String s = array.get(position).name;
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
			
	
			name.setText(place.name);
			add.setText(place.address);
			rateBar.setRating(place.rating);
			
		}
		
		return view;
	}
}
