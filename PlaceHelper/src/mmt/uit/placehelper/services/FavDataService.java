package mmt.uit.placehelper.services;

import java.util.ArrayList;

import mmt.uit.placehelper.models.PlaceDetail;
import mmt.uit.placehelper.utilities.ConstantsAndKey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FavDataService {
	private Context mContext;
	private SQLiteDatabase mDb;
	private FavDatabaseHelper mDbHelper;
	
	public FavDataService(Context context){
		this.mContext = context;
	}
	
	
	public FavDataService open() throws SQLException {
		mDbHelper = new FavDatabaseHelper(mContext);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		mDbHelper.close();
	}
	
	private ContentValues createContentValue (String id, String name, String address, String phone, float rating, double lng, double lat, String url, String weburl, int img){
		ContentValues values = new ContentValues();
		values.put(ConstantsAndKey.KEY_ID, id);
		values.put(ConstantsAndKey.KEY_NAME, name);
		values.put(ConstantsAndKey.KEY_ADDRESS, address);
		values.put(ConstantsAndKey.KEY_PHONENUMBER, phone);
		values.put(ConstantsAndKey.KEY_RATE,rating);
		values.put(ConstantsAndKey.KEY_LNG, lng);
		values.put(ConstantsAndKey.KEY_LAT, lat);
		values.put(ConstantsAndKey.KEY_URL, url);
		values.put(ConstantsAndKey.KEY_WEBURL, weburl);		
		values.put(ConstantsAndKey.KEY_TYPEIMG, img);	
		return values;
		
	}
	
	//Create new favorite row, return rowID. Return -1 if there's error
	public long createFav (String id, String name, String address, String phone,float rating, double lng, double lat, String url, String weburl, int img){
		ContentValues values = createContentValue(id, name, address, phone, rating, lng, lat, url, weburl, img);
		return mDb.insert(ConstantsAndKey.DATABASE_TABLE, null, values);
	}
	
	//Delete favorite
	
	public boolean deleteFav (String id){
		return mDb.delete(ConstantsAndKey.DATABASE_TABLE, ConstantsAndKey.KEY_ID +"='" + id + "'", null) >0;
	}
	
	//Delete All Favorite
	public boolean deleteAll() {
	        return mDb.delete(ConstantsAndKey.DATABASE_TABLE, null, null) > 0;
	    }
	 
	 
	public Cursor getFavoriteById(String id) {

		return mDb.query(ConstantsAndKey.DATABASE_TABLE, new String[] { ConstantsAndKey.KEY_ID, ConstantsAndKey.KEY_NAME,
				ConstantsAndKey.KEY_ADDRESS, ConstantsAndKey.KEY_PHONENUMBER, ConstantsAndKey.KEY_RATE, ConstantsAndKey.KEY_LNG, ConstantsAndKey.KEY_LAT,
				ConstantsAndKey.KEY_URL, ConstantsAndKey.KEY_WEBURL, ConstantsAndKey.KEY_TYPEIMG}, ConstantsAndKey.KEY_ID + "='" + id +"'", null, null, null, null);

	}
	
	/**
	 * Return a Cursor over the list of all row of the table which contain the key word
	 * 
	 * @return Cursor over all favorites which contain the key word
	 */
	public Cursor getFavoriteByAddress(String address) {
		return mDb.query(ConstantsAndKey.DATABASE_TABLE, new String[] { ConstantsAndKey.KEY_ID, ConstantsAndKey.KEY_NAME,
				ConstantsAndKey.KEY_ADDRESS, ConstantsAndKey.KEY_PHONENUMBER, ConstantsAndKey.KEY_LNG, ConstantsAndKey.KEY_LAT,
				ConstantsAndKey.KEY_URL, ConstantsAndKey.KEY_WEBURL, ConstantsAndKey.KEY_TYPEIMG},
				ConstantsAndKey.KEY_ADDRESS + " like " + "'%" + address + "%'", null, null, null,
				ConstantsAndKey.KEY_ADDRESS);

	}
	
	//get All Favorite
	public Cursor getAllFavorites(){
    	return mDb.query(ConstantsAndKey.DATABASE_TABLE, new String[]{ConstantsAndKey.KEY_ID, ConstantsAndKey.KEY_NAME, 
    			ConstantsAndKey.KEY_ADDRESS, ConstantsAndKey.KEY_PHONENUMBER, ConstantsAndKey.KEY_RATE, ConstantsAndKey.KEY_LNG, 
    			ConstantsAndKey.KEY_LAT, ConstantsAndKey.KEY_URL, ConstantsAndKey.KEY_WEBURL,ConstantsAndKey.KEY_TYPEIMG}, 
    			null, null, null, null, null);
    }
	
	
	public ArrayList<PlaceDetail> getListFavorites(){
    	
    	Cursor mCursor = getAllFavorites();
    	
    	int rows = mCursor.getCount();
    	ArrayList<PlaceDetail> mArrayList = new ArrayList<PlaceDetail>();
    	
    	if(rows>0){
    		mCursor.moveToFirst();
	    	
	    	mArrayList.add(new PlaceDetail(mCursor.getString(0), mCursor.getString(1), mCursor.getString(2), mCursor.getString(3), mCursor.getFloat(4), mCursor.getDouble(5), mCursor.getDouble(6), mCursor.getString(7), mCursor.getString(8), mCursor.getInt(9)));
	    	
	      for(mCursor.moveToFirst(); mCursor.moveToNext(); mCursor.isAfterLast()) {
	          // The Cursor is now set to the right position
	    	  mArrayList.add(new PlaceDetail(mCursor.getString(0), mCursor.getString(1), mCursor.getString(2), mCursor.getString(3), mCursor.getFloat(4), mCursor.getDouble(5), mCursor.getDouble(6), mCursor.getString(7), mCursor.getString(8),mCursor.getInt(9)));
	      }
    	}
    	
    	mCursor.close();
      return mArrayList;
    }
	
	public boolean isExisted(String id) {
		Cursor c = getFavoriteById(id);
		if(c.moveToFirst() == false) {
			c.close();
			return false;
		}
		else{
			c.close();
			return true;
		}
		}


}
