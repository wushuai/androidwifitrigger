package com.test.macowu.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.test.macowu.MainActivity;
import com.test.macowu.R;
import com.test.macowu.listener.IWifiNameChangeListener;
import com.test.macowu.util.AppUtils;

public class HomeWifiScanService extends Service {

    public static final String TAG = "HomeWifiScanService";

    private HomeWifiScanBinder binder = new HomeWifiScanBinder();
    private IWifiNameChangeListener listener;
    private Notification notification;
    private NotificationManager notificationManager;

    public HomeWifiScanService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notification = new Notification.Builder(this)
                .setContentTitle(getString(R.string.app_notification_title))
                .setContentText(getString(R.string.app_notification_content))
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0))
                .build();
        startForeground(1, notification);

        onWifiNameChanged();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    public class HomeWifiScanBinder extends Binder {

        public HomeWifiScanService getService() {
            return HomeWifiScanService.this;
        }

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive");
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                onWifiNameChanged();
            }
        }
    };

    private void updateNotice() {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notification = new Notification.Builder(this)
                .setContentTitle(getString(R.string.app_notification_title))
                .setContentText(AppUtils.getWifiInfo(this))
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0))
                .build();
        notificationManager.notify(1, notification);
    }

    public void setListener(IWifiNameChangeListener listener) {
        this.listener = listener;

        //update wifiname on listener is set
        onWifiNameChanged();
    }

    private void onWifiNameChanged() {
        if (listener != null) {
            listener.onWifiNameChanged();
            updateNotice();
        }
    }
}
