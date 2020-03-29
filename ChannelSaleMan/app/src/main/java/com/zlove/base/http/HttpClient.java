/*
 * Copyright 2014 HuHoo. All rights reserved.
 * HuHoo PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @HttpClient.java - 2014-5-13
 */

package com.zlove.base.http;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.EncryptUtil;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;


/**
 * HttpClient
 *
 * @author AlanYJM
 */
@SuppressWarnings("deprecation")
public class HttpClient {
    
    public static String HTML5_JSINTERFACE_TYPE_ANDROID = "1";
    protected static final int HTTP_TIME_OUT = 30 * 1000;
    protected static final String PARAMS_CASE_ID = "huhoo_caseid";
    public final static String FILE_UPLOAD_URL = ApplicationUtil.getApplicationContext().getString(R.string.app_name);
    public static String GET_INSTALLEDAPPSINFO = ApplicationUtil.getApplicationContext().getString(R.string.app_name) + "/api/framework/context/json/query/";
    public static final String GET_HUHOO_CUSTOMER_SERVICE = ApplicationUtil.getApplicationContext().getString(R.string.app_name) + "customerservices/load";
    
    public static final String INVITE_CODE_URL = ApplicationUtil.getApplicationContext().getString(R.string.app_name);
    
    protected static final String BASE_URL = ApplicationUtil.getApplicationContext().getString(R.string.test_channel_base_url);
    
    protected static AsyncHttpClient client = null;

    static {
        client = new AsyncHttpClient();
        
        boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;

        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(ApplicationUtil.getApplicationContext());
            proxyPort = android.net.Proxy.getPort(ApplicationUtil.getApplicationContext());
        }

        if (!TextUtils.isEmpty(proxyAddress) && proxyPort != -1) {
            client.setProxy(proxyAddress, proxyPort);
        }
        
    }
    
    public static void get(Context context, String url, RequestParams params,
        AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(HTTP_TIME_OUT);
        Log.i("HttpClient", url + "?" + params);
        client.get(url, params, responseHandler);
    }

    public static void post(Context context, String url, RequestParams params,
        AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(HTTP_TIME_OUT);
        Log.i("HttpClient", url + "?" + params);
        client.post(url, params, responseHandler);
    }

    public static void cancelRequest(Context context) {
        client.cancelRequests(context, true);
    }

    public static void uploadFile(Context context, AsyncHttpResponseHandler handler,
        String path) {
        RequestParams params = new RequestParams();
        File file = new File(path);
        try {
            params.put("file", file);
            post(context, FILE_UPLOAD_URL, params, handler);
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "文件不存在,请重新选择", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        Log.i("HttpUtil", FILE_UPLOAD_URL + "?" + params);
    }
    
    // 获取企业安装的app信息
    public static void getInstalledAppsInfo(Context context,String corpId, AsyncHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("corpId", String.valueOf(corpId));
        get(context, GET_INSTALLEDAPPSINFO, params, responseHandler);
    }
    
    public static String getMD5SignedStr(String key, String signStr) {
        try {
            Log.i("HttpClient", signStr + key);
            return EncryptUtil.getStringMd5(signStr + key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 获取客服列表
     * @param context
     * @param responseHandler
     */
    public static void getCustomerServicers(Context context,AsyncHttpResponseHandler responseHandler){
        Log.i("HttpClient", GET_HUHOO_CUSTOMER_SERVICE);
    	client.get(context, GET_HUHOO_CUSTOMER_SERVICE, responseHandler);
    }
    /**
     * 获取用户是否有企业邀请码
     * @param context
     * @param account
     * @param responseHandler
     */
    public static void getInviteCode(Context context,String account, AsyncHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("username",account);
        get(context, INVITE_CODE_URL, params, responseHandler);
    }
    
    protected static void setClientHeader() {
        client.addHeader("appVersion", ApplicationUtil.getVerName(ApplicationUtil.getApplicationContext()));
        client.addHeader("sysType", "android");
        client.addHeader("sysVersion", ApplicationUtil.getMobilePhoneSysVersion());
        client.addHeader("sessionid", ChannelCookie.getInstance().getSessionId());
        client.addHeader("device_id", ApplicationUtil.getDeviceId());
    }
}
