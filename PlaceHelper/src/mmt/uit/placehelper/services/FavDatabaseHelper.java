package mmt.uit.placehelper.services;

import mmt.uit.placehelper.utilities.ConstantsAndKey;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavDatabaseHelper extends SQLiteOpenHelper{

	
	
	public FavDatabaseHelper(Context context) {
		super(context, ConstantsAndKey.DATABASE_NAME, null, ConstantsAndKey.DATABASE_VERSION);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		FavTable.onCreate(db);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
		FavTable.onUpgrade(db, oldVersion, newVersion);
		
	}

}
