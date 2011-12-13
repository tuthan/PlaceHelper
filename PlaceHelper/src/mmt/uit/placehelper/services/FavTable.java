package mmt.uit.placehelper.services;

import mmt.uit.placehelper.utilities.ConstantsAndKey;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FavTable {
	
	//SQL statement 
	private static final String DATABASE_CREATE = "create table " + ConstantsAndKey.DATABASE_TABLE + "( _id text primary key, "
			+ "name text not null, address text not null, phone text,rating real, lng real, lat real, mapurl text, weburl text);";
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(FavTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS todo");
		onCreate(database);
	}
}
