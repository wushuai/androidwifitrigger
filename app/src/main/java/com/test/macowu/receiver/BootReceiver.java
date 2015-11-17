package com.test.macowu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.test.macowu.util.AppUtils;

/**
 *
 * Created by Mr. Wu on 2015/10/6.
 */
public class BootReceiver extends BroadcastReceiver {

    public static final String TAG = "MacowuBootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive");
        String action = intent.getAction();
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            AppUtils.startApp(context);
        }
    }
}
