package com.test.macowu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.test.macowu.MainActivity;
import com.test.macowu.service.HomeWifiScanService;
import com.test.macowu.util.AppUtils;

/**
 *
 * Created by Mr. Wu on 2015/10/7.
 */
public class NetwokReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent serviceIntent = new Intent(context, HomeWifiScanService.class);
        context.startService(serviceIntent);
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            String wifiName = AppUtils.getWifiInfo(context);
            if (MainActivity.HOME_WIFI_NAME.equals(wifiName)) {
                AppUtils.startApp(context);
            }
        }
    }
}
