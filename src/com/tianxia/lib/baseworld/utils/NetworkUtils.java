package com.tianxia.lib.baseworld.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkUtils {

	public static final int NETWORN_NONE = 0;
	public static final int NETWORN_WIFI = 1;
	public static final int NETWORN_MOBILE = 2;

	public static int getNetworkState(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// Wifi
		// State state =
		// connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
		// .getState();
		// if (state == State.CONNECTED || state == State.CONNECTING) {
		// return NETWORN_WIFI;
		// }
		NetworkInfo mMobileNetworkInfo = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (mMobileNetworkInfo != null && mMobileNetworkInfo.isAvailable()
				&& mMobileNetworkInfo.isConnected()) {
			return NETWORN_WIFI;
		}

		// 3G
		// state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
		// .getState();
		// if (state == State.CONNECTED || state == State.CONNECTING) {
		// return NETWORN_MOBILE;
		// }
		mMobileNetworkInfo = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mMobileNetworkInfo != null && mMobileNetworkInfo.isAvailable()
				&& mMobileNetworkInfo.isConnected()) {
			return NETWORN_WIFI;
		}

		return NETWORN_NONE;
	}
}
