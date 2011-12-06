package mmt.uit.placehelper.utilities;

import java.util.List;

import mmt.uit.placehelper.models.MyAddress;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import mmt.uit.placehelper.R;

public class LocationAdapter extends ArrayAdapter<MyAddress> {

	Context context;
	int resourceId;
	List<MyAddress> listResult;
	
	public LocationAdapter(Context context, int textViewResourceId, List<MyAddress> objects) {
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
		
		MyAddress place = listResult.get(position);
		if(place != null) {
						
			TextView add = (TextView) view.findViewById(R.id.lc_address);						
			add.setText(place.getAdress());
		}
		
		return view;
	}
}