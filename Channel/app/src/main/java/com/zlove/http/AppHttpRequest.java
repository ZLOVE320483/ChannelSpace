package com.zlove.http;

import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpClient;
import com.zlove.base.http.RequestParams;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.channel.R;


public class AppHttpRequest extends HttpClient {
    
    public static final String BASE_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url);

    public static final String UPDATE_APP_VERSION_URL = BASE_URL + "common/checkAppVersion";
    public static final String GET_STATION_MESSAGE_URL = BASE_URL + "message/getSysMessageList";
    
    public static void updateAppVersionRequest(AsyncHttpResponseHandler responseHandler) {
        setClientHeader();

        RequestParams params = new RequestParams();
        params.put("version_code", ApplicationUtil.getVersionCode(ApplicationUtil.getApplicationContext()));
        post(ApplicationUtil.getApplicationContext(), UPDATE_APP_VERSION_URL, params, responseHandler);
    }
    
    public static void getStateMessageListRequest(String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();

        RequestParams params = new RequestParams();
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        post(ApplicationUtil.getApplicationContext(), GET_STATION_MESSAGE_URL, params, responseHandler);
    }
    
    
}
