package mmt.uit.placehelper.activities;

import java.util.List;

import mmt.uit.placehelper.models.FavoriteModel;
import mmt.uit.placehelper.services.DataService;
import mmt.uit.placehelper.utilities.FavoriteAdapter;


import mmt.uit.placehelper.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ListFavoriteActivity extends ListActivity {

	private DataService dataService;
	private FavoriteAdapter rsAdapter;
	private ListView listFavorites;
	private List<FavoriteModel> favoriteModel;
	protected static final int CONTEXTMENU_DELETEITEM = 0;
	protected static final int CONTEXTMENU_DELETEALL = 1;
	FavoriteModel favContexted;
	double curLat, curLon; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_favorite);

		listFavorites = getListView();
		registerForContextMenu(listFavorites);
		listFavorites.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

					@Override
					public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
						// TODO Auto-generated method stub
						menu.setHeaderIcon(R.drawable.del36);
						menu.setHeaderTitle("XĂ³a danh má»¥c yĂªu thĂ­ch");
						menu.add(0, CONTEXTMENU_DELETEITEM, 0, "XĂ³a má»¥c Ä‘Æ°Æ¡c chá»�n");
						menu.add(1, CONTEXTMENU_DELETEALL, 1, "XĂ³a táº¥t cáº£");
						
					}
				});

		// OK
		dataService = new DataService(getApplicationContext());
		dataService.open();
		Bundle bGet = getIntent().getExtras();
		curLat = bGet.getDouble("curlat");
		curLon = bGet.getDouble("curlon");		
		FavoriteAdapter.setLat(curLat);
		FavoriteAdapter.setLng(curLon);		
		favoriteModel = dataService.getListFavorites();
		rsAdapter = new FavoriteAdapter(getApplicationContext(),
				R.layout.ph_favorite_row, favoriteModel);
		setListAdapter(rsAdapter);
	}

	@Override
	public boolean onContextItemSelected(MenuItem aItem) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) aItem.getMenuInfo();
		
		AlertDialog.Builder alertBox = new AlertDialog.Builder(ListFavoriteActivity.this);
		
		/* Switch on the ID of the item, to get what the user selected. */
		switch (aItem.getItemId()) {
		case CONTEXTMENU_DELETEITEM:
			/* Get the selected item out of the Adapter by its position. */
			favContexted = (FavoriteModel) listFavorites.getAdapter().getItem(menuInfo.position);
			alertBox.setMessage("Báº¡n cĂ³ muá»‘n xĂ³a má»¥c nĂ y khĂ´ng?");
			//Yes
			alertBox.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
//					Toast.makeText(getApplicationContext(),"Yes clicked", Toast.LENGTH_SHORT).show();
					try {
						/* Remove it from the list. */
						dataService.deleteFavorite(favContexted.getPlaceId());
						refreshFavListItems();
						Toast.makeText(getApplicationContext(),"Ä�Ă£ xĂ³a", Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						Log.i("Yes delete"," error "+ e.toString());
						}
				}
			});
			
			//No
			alertBox.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							Toast.makeText(getApplicationContext(),"Ä�Ă£ bá»� qua", Toast.LENGTH_SHORT).show();
						}
					});
			alertBox.show();
			
			return true; /* true means: "we handled the event". */

			
			
		case CONTEXTMENU_DELETEALL:
			alertBox.setMessage("Báº¡n cĂ³ cháº¯c lĂ  muá»‘n xĂ³a táº¥t cáº£?");
			alertBox.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
//					Toast.makeText(getApplicationContext(),"Yes clicked", Toast.LENGTH_SHORT).show();
					try {
						/* Remove all items from the list. */
						dataService.deleteAll();
						refreshFavListItems();
						Toast.makeText(getApplicationContext(),"Ä�Ă£ xĂ³a táº¥t cáº£", Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						Log.i("Yes delete"," error "+ e.toString());
						}
				}
			});
			
			//No
			alertBox.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							Toast.makeText(getApplicationContext(),"Ä�Ă£ bá»� qua", Toast.LENGTH_SHORT).show();
						}
					});
			alertBox.show();
			
			return true; /* true means: "we handled the event". */
		}
		return false;
	}

	private void refreshFavListItems() {
		favoriteModel = dataService.getListFavorites();
		setListAdapter(new FavoriteAdapter(getApplicationContext(),
				R.layout.ph_favorite_row, favoriteModel));
	}

	@Override
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
		
	}
}
