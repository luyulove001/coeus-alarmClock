package net.tatans.coeus.alarm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
	
	public static boolean  isNetworkConnected(Context context){
		if(context != null){
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context//
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworInfo = mConnectivityManager.getActiveNetworkInfo();
			if(mNetworInfo != null){
				return mNetworInfo.isAvailable();
			}
		}
		return false;
	}
}
