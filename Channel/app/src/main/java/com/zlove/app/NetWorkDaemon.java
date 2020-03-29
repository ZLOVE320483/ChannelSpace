
package com.zlove.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.LogUtil;
import com.zlove.base.util.NetworkUtil;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;

public class NetWorkDaemon {

    private static final String LOG_TAG = NetWorkDaemon.class.getSimpleName();

    private Context context;

    // Connectivity State
    private ConnectivityManager connectivityMgr;

    private ConnectivityReceiver connectivityReceiver;

    public NetWorkDaemon() {
        context = ApplicationUtil.getApplicationContext();

        // NetWorkState
        connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityReceiver = new ConnectivityReceiver();
        context.registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * Release Daemon Thread.
     */
    public void unregisterReceiver() {
        try {
            context.unregisterReceiver(connectivityReceiver);
        } catch (Exception e) {
        }
    }

    private class ConnectivityReceiver extends BroadcastReceiver {

        public ConnectivityReceiver() {
            super();
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo newInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (newInfo != null) {
                LogUtil.d(LOG_TAG, "Connectivity event: " + newInfo.getState().toString() + " " + newInfo.getTypeName());
            }

            NetworkInfo aInfo = connectivityMgr.getActiveNetworkInfo();
            if (aInfo != null) {
                LogUtil.d(LOG_TAG, "Active: " + aInfo.getState().toString() + " " + aInfo.getTypeName());
            }

            if (!ChannelCookie.getInstance().isLoginPass()) {
                LogUtil.i(LOG_TAG, "login was not passed");
                return;
            }

            if (!NetworkUtil.isNetworkConnected(ApplicationUtil.getApplicationContext())) {
                Toast.makeText(context, R.string.net_break_tips, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
