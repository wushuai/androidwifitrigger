package com.test.macowu.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import com.test.macowu.MainActivity;

/**
 * Created by macowu on 15/10/5.
 */
public class AppUtils {

    public static String getWifiInfo(Context context) {
        String wifiInfo = "wifi not connected";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                wifiInfo = getWifiName(context);
            }
        }
        return wifiInfo;
    }

    private static String getWifiName(Context context) {
        String wifiInfo = "wifi not connected";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                wifiInfo = escapeWifiName(connectionInfo.getSSID());
            }
        }
        return wifiInfo;
    }

    private static String escapeWifiName(String wifiName) {
        if (TextUtils.isEmpty(wifiName)) return "";
        if (Build.VERSION.SDK_INT >= 17 && wifiName.startsWith("\"") && wifiName.endsWith("\"")) {
            wifiName = wifiName.substring(1, wifiName.length() - 1);
        }
        return wifiName;
    }

    public static void startApp(Context context){
        Intent appIntent = new Intent(context, MainActivity.class);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(appIntent);
    }

    public static void startXiaomiApp(Context context){
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.xiaomi.smarthome");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
