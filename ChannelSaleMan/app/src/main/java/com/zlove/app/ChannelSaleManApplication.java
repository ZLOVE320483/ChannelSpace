package com.zlove.app;

import android.app.Application;

import com.zlove.base.util.LogUtil;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;

public class ChannelSaleManApplication extends Application {

    private static final String LOG_TAG = ChannelSaleManApplication.class.getSimpleName();
    private static ChannelSaleManApplication instance;
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLog();
        ChannelCookie.getInstance().initCookie(this);
    }
    
    public static ChannelSaleManApplication getInstance() {
        if (instance == null) {
            LogUtil.e(LOG_TAG, "Application is null");
        }
        return instance;
    }

    private void initLog() {
        LogUtil.init(getResources().getBoolean(R.bool.is_debug));
    }
}
