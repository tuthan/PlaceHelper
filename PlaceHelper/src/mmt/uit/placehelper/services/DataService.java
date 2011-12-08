package mmt.uit.placehelper.services;

import java.util.ArrayList;

import mmt.uit.placehelper.models.FavoriteModel;
import mmt.uit.placehelper.utilities.ConstantsAndKey;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataService {	 	
	    private DatabaseHelper mDbHelper;
	    private SQLiteDatabase mDb;
	    private final Context mCtx;
	    
	    private static final String DATABASE_CREATE = "create table FavoritesTbl (_id integer primary key autoincrement, "
			+ "title text, address text, addresslines text, phone text, lng text, lat text, mapurl text, weburl text);";

	    
	    private static class DatabaseHelper extends SQLiteOpenHelper{

			public DatabaseHelper(Context context){
				super(context, ConstantsAndKey.DATABASE_NAME, null, ConstantsAndKey.DATABASE_VERSION);
			}

			@Override
			public void onCreate(SQLiteDatabase db) {
				// TODO Auto-generated method stub
				db.execSQL(DATABASE_CREATE);
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				// TODO Auto-generated method stub
				
			}
	    }
	    
	    public DataService open() throws SQLException{
	    	mDbHelper = new DatabaseHelper(mCtx);
	    	mDb = mDbHelper.getWritableDatabase();
	    	return this;
	    }
	    
	    public void close(){
	    	mDbHelper.close();
	    }
	    
	    
	    public DataService(Context context){
	    	mCtx = context;
	    }
	    
	  
	    
	    /**
	     * Return a Cursor over the list of all notes in the database
	     * e.g NoteTable has three column: id=KEY_ROWID, title=KEY_TITLE and body=KEY_BODY
	     * Below query is to get all note from this table
	     * @return Cursor over all notes
	     */
	    
	    public Cursor getAllFavorites(){
	    	return mDb.query(ConstantsAndKey.DATABASE_TABLE, new String[]{ConstantsAndKey.KEY_ROWID, ConstantsAndKey.KEY_TITLE, 
	    			ConstantsAndKey.KEY_ADDRESS, ConstantsAndKey.KEY_ADDRESS_LINES, ConstantsAndKey.KEY_PHONENUMBER, ConstantsAndKey.KEY_LNG, 
	    			ConstantsAndKey.KEY_LAT, ConstantsAndKey.KEY_MAPURL, ConstantsAndKey.KEY_WEBURL}, 
	    			null, null, null, null, null);
	    }
	    
	    //OK
	public ArrayList<FavoriteModel> getListFavorites(){
	    	
	    	Cursor mCursor = mDb.query(ConstantsAndKey.DATABASE_TABLE, new String[]{ConstantsAndKey.KEY_ROWID, ConstantsAndKey.KEY_TITLE, ConstantsAndKey.KEY_ADDRESS, 
	    			ConstantsAndKey.KEY_ADDRESS_LINES, ConstantsAndKey.KEY_PHONENUMBER, ConstantsAndKey.KEY_LNG, ConstantsAndKey.KEY_LAT, 
	    			ConstantsAndKey.KEY_MAPURL, ConstantsAndKey.KEY_WEBURL}, 
	    			null, null, null, null, null);
	    	
	    	int rows = mCursor.getCount();
	    	ArrayList<FavoriteModel> mArrayList = new ArrayList<FavoriteModel>();
	    	
	    	if(rows>0){
	    		mCursor.moveToFirst();
		    	
		    	mArrayList.add(new FavoriteModel(mCursor.getInt(0), mCursor.getString(1), mCursor.getString(2), mCursor.getString(3), mCursor.getString(4), mCursor.getString(5), mCursor.getString(6), mCursor.getString(7), mCursor.getString(8)));
		    	
		      for(mCursor.moveToFirst(); mCursor.moveToNext(); mCursor.isAfterLast()) {
		          // The Cursor is now set to the right position
		          mArrayList.add(new FavoriteModel(mCursor.getInt(0), mCursor.getString(1), mCursor.getString(2), mCursor.getString(3), mCursor.getString(4), mCursor.getString(5), mCursor.getString(6), mCursor.getString(7), mCursor.getString(8)));
		      }
	    	}
	    	
	      return mArrayList;
	    }
	    
	    
	    /**
	     * Create a new note using the title and body provided. If the note is
	     * successfully created return the new rowId for that note, otherwise return
	     * a -1 to indicate failure
	     */
	    public long insertFavorite(String title, String address, String addressLines, String phoneNumber, String lng, String lat, String mapUrl, String webUrl){
	    	ContentValues insertedValue = new ContentValues();
	    	insertedValue.put(ConstantsAndKey.KEY_TITLE, title);
	    	insertedValue.put(ConstantsAndKey.KEY_ADDRESS, address);
	    	insertedValue.put(ConstantsAndKey.KEY_ADDRESS_LINES, addressLines);
	    	insertedValue.put(ConstantsAndKey.KEY_PHONENUMBER, phoneNumber);
	    	insertedValue.put(ConstantsAndKey.KEY_LNG, lng);
	    	insertedValue.put(ConstantsAndKey.KEY_LAT, lat);
	    	insertedValue.put(ConstantsAndKey.KEY_MAPURL, mapUrl);
	    	insertedValue.put(ConstantsAndKey.KEY_WEBURL, webUrl);
	    	
	    	return mDb.insert(ConstantsAndKey.DATABASE_TABLE, null, insertedValue);
	    }
	    
	    
	    public void deleteDatabase() {
			mDbHelper.close();
			try {
				mCtx.deleteDatabase("FavoritesTbl");
			} catch (Exception e) {
			}
		}
	    
	    public boolean isExisted(String lat, String lng, String address) {
			Cursor c = mDb.query(ConstantsAndKey.DATABASE_TABLE, new String[] { ConstantsAndKey.KEY_ROWID, ConstantsAndKey.KEY_LNG, ConstantsAndKey.KEY_LAT, ConstantsAndKey.KEY_ADDRESS_LINES},
					ConstantsAndKey.KEY_LNG + " like " + lng + " and " + ConstantsAndKey.KEY_LAT + " like " + lat + " and " + ConstantsAndKey.KEY_ADDRESS_LINES + " like " + "'%" + address + "%'", null, null, null,
					null);
			if(c.moveToFirst() == false) {
				return false;
			}
			else{
				return true;
			}
		}


	    public Cursor getFavoriteById(long id) {

			return mDb.query(ConstantsAndKey.DATABASE_TABLE, new String[] { ConstantsAndKey.KEY_ROWID, ConstantsAndKey.KEY_TITLE,
					ConstantsAndKey.KEY_ADDRESS, ConstantsAndKey.KEY_ADDRESS_LINES, ConstantsAndKey.KEY_PHONENUMBER, ConstantsAndKey.KEY_LNG, ConstantsAndKey.KEY_LAT,
					ConstantsAndKey.KEY_MAPURL, ConstantsAndKey.KEY_WEBURL}, ConstantsAndKey.KEY_ROWID + "=" + id, null, null, null, null);

		}
		
		/**
		 * Return a Cursor over the list of all row of the table which contain the key word
		 * 
		 * @return Cursor over all favorites which contain the key word
		 */
		public Cursor getFavoriteByAddress(String address) {
			return mDb.query(ConstantsAndKey.DATABASE_TABLE, new String[] { ConstantsAndKey.KEY_ROWID, ConstantsAndKey.KEY_TITLE,
					ConstantsAndKey.KEY_ADDRESS, ConstantsAndKey.KEY_ADDRESS_LINES, ConstantsAndKey.KEY_PHONENUMBER, ConstantsAndKey.KEY_LNG, ConstantsAndKey.KEY_LAT,
					ConstantsAndKey.KEY_MAPURL, ConstantsAndKey.KEY_WEBURL},
					ConstantsAndKey.KEY_ADDRESS + " like " + "'%" + address + "%'", null, null, null,
					ConstantsAndKey.KEY_ADDRESS);

		}

	    
	    /**
	     * Delete the note with the given rowId
	     * 
	     * @param rowId id of note to delete
	     * @return true if deleted, false otherwise
	     */
		//ok
	    public boolean deleteFavorite(long rowId) {
	        return mDb.delete(ConstantsAndKey.DATABASE_TABLE, ConstantsAndKey.KEY_ROWID + "=" + rowId, null) > 0;
	    }
	    public boolean deleteFavoriteByTitle(String title) {
	        return mDb.delete(ConstantsAndKey.DATABASE_TABLE, ConstantsAndKey.KEY_TITLE + " like " + title, null) > 0;
	    }
	    public boolean deleteAll() {
	        return mDb.delete(ConstantsAndKey.DATABASE_TABLE, null, null) > 0;
	    }
}
