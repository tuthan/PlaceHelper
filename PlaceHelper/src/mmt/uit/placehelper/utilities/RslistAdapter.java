package mmt.uit.placehelper.utilities;

import java.util.List;

import mmt.uit.placehelper.models.Place;
import mmt.uit.placehelper.models.PlaceModel;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import mmt.uit.placehelper.R;

public class RslistAdapter extends ArrayAdapter<Place> {

	Context context;
	int resourceId;
	List<Place> listResult;
	
	public RslistAdapter(Context context, int textViewResourceId, List<Place> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resourceId = textViewResourceId;
		this.listResult = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		if(view == null) {
			LayoutInflater li = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			view = li.inflate(resourceId, null);
		}
		
		Place place = listResult.get(position);
		if(place != null) {
			
			TextView name = (TextView) view.findViewById(R.id.rs_name);
			TextView dis = (TextView) view.findViewById(R.id.rs_distance);	
			TextView add = (TextView) view.findViewById(R.id.rs_address);			
			name.setText(place.name);
			dis.setText(String.valueOf(place.getDistance()) + " km");
			add.setText(place.vicinity);
		}
		
		return view;
	}
}