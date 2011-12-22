package mmt.uit.placehelper.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnection {

	public static boolean checkInternet(Context mContext){
		ConnectivityManager mConMgr = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConMgr != null){
			NetworkInfo ni = mConMgr.getActiveNetworkInfo();
			if (ni != null){
				if(!ni.isConnected()){
					return false;
				}
				if (!ni.isAvailable()){
					return false;
				}
				return true;
			}
			else return false;
		}
		else return false;
	}
}
