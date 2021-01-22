package com.midnightworks.testapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class Utils {

	public final static String DATE_PATTERN = "yyyy-MM-dd";

	public static boolean isNetworkConnected(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) return false;
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnected();
	}

	public static int getPathIdFromURL(String url) {
		String[] resultArray = url.split("/");
		return Integer.parseInt(resultArray[resultArray.length - 1]);
	}

}
