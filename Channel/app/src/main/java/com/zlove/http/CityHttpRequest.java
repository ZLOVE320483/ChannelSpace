
package com.zlove.http;

import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpClient;
import com.zlove.base.http.RequestParams;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.channel.R;

public class CityHttpRequest extends HttpClient {

    public static final String BASE_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url);

    public static final String GET_COMBINE_CITY_LIST_URL = BASE_URL + "city/getCombineCityList";
    public static final String GET_COMBINE_AREA_LIST_URL = BASE_URL + "city/getAreaList";
    public static final String SET_CITY_URL = BASE_URL + "city/setCity";

    public static void getCombineCityList(AsyncHttpResponseHandler responseHandler) {
        setClientHeader();

        RequestParams params = new RequestParams();
        post(ApplicationUtil.getApplicationContext(), GET_COMBINE_CITY_LIST_URL, params, responseHandler);
    }

    public static void getAreaList(String city_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();

        RequestParams params = new RequestParams();
        params.put("city_id", city_id);
        post(ApplicationUtil.getApplicationContext(), GET_COMBINE_AREA_LIST_URL, params, responseHandler);
    }
    
    public static void setCityRequest(String city_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();

        RequestParams params = new RequestParams();
        params.put("city_id", city_id);
        post(ApplicationUtil.getApplicationContext(), SET_CITY_URL, params, responseHandler);
    }

}
