package com.tianxia.lib.baseworld.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class NetworkUtils {

    public static final int NETWORN_NONE = 0;
    public static final int NETWORN_WIFI = 1;
    public static final int NETWORN_MOBILE = 2;

    public static int getNetworkState(Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Wifi
        State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(state == State.CONNECTED||state == State.CONNECTING){
            return NETWORN_WIFI;
        }

        //3G
        try {
        	state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            if(state == State.CONNECTED||state == State.CONNECTING){
                return NETWORN_MOBILE;
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
        return NETWORN_NONE;
    }
}
