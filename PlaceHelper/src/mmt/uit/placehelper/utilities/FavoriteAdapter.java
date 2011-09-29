package mmt.uit.placehelper.utilities;

import java.util.List;

import mmt.uit.placehelper.models.FavoriteModel;

import mmt.uit.placehelper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoriteAdapter extends ArrayAdapter<FavoriteModel> {

	Context context;
	int resourceId;
	List<FavoriteModel> array;
	private static double curLng = -1, curLat = -1;
	
	public FavoriteAdapter(Context context, int textViewResourceId,
			List<FavoriteModel> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resourceId = textViewResourceId;
		this.array = objects;
	}
	
	public static void setLng(double lng) {
		curLng = lng;
	}	
	
	public static void setLat(double lat) {
		curLat = lat;
	}	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		if(view == null) {
			LayoutInflater li = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			view = li.inflate(resourceId, null);
		}
		
		FavoriteModel place = array.get(position);
		if(place != null) {
			
			TextView name = (TextView) view.findViewById(R.id.rs_name);
			TextView dis = (TextView) view.findViewById(R.id.rs_distance);	
			TextView add = (TextView) view.findViewById(R.id.rs_address);
			ImageView imgItem = (ImageView)view.findViewById(R.id.rs_img);
			
			String s = array.get(position).getTitle();
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
			
//			if (s.startsWith("Bank")) {
//				imgItem.setImageResource(R.drawable.banks);
//			} else {
//				imgItem.setImageResource(R.drawable.hotel);
//			}
			
			name.setText(place.getTitle());
			
			//ko thay roi vao day, chac la set mac dinh # -1 roi
			if(curLat==-1 && curLng==-1){
				dis.setText("???" + "km");
			}else{
				dis.setText(place.getDistance(curLat, curLng) + "km");
			}
			
			add.setText(place.getAddressLines().toString());
		}
		
		return view;
	}
}
