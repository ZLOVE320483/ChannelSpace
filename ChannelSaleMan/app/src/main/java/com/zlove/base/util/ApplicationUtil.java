package com.zlove.base.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import com.zlove.app.ChannelSaleManApplication;

public class ApplicationUtil {
	
	public static Context getApplicationContext() {
        return ChannelSaleManApplication.getInstance();
    }
	
	public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
    
    public static int getVersionCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }
    
    public static String getMobilePhoneSysVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) ApplicationUtil.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
