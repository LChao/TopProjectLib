package com.tianxia.lib.baseworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.tianxia.lib.baseworld.utils.NetworkUtils;

public class BaseBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            BaseApplication.mNetWorkState = NetworkUtils.getNetworkState(context);
        }

    }

}
