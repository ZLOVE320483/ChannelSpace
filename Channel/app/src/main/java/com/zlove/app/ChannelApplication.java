package com.zlove.app;

import android.app.Application;

import com.zlove.base.util.LogUtil;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;


public class ChannelApplication extends Application {
    
    private static final String LOG_TAG = ChannelApplication.class.getSimpleName();
    private static ChannelApplication instance;
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLog();
        ChannelCookie.getInstance().initCookie(this);
        initException();
    }
    
    public static ChannelApplication getInstance() {
        if (instance == null) {
            LogUtil.e(LOG_TAG, "Application is null");
        }
        return instance;
    }
    
    private void initLog() {
        LogUtil.init(getResources().getBoolean(R.bool.is_debug));
    }
    
    public void initException(){
        if (!getResources().getBoolean(R.bool.is_debug)) {
            ChannelCrashHandler channelCrashHandler = ChannelCrashHandler.getInstance();
            channelCrashHandler.init(this);
        }
    }

}
