package com.zlove.http;

import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpClient;
import com.zlove.base.http.RequestParams;
import com.zlove.base.util.ApplicationUtil;

public class ProjectHttpRequest extends HttpClient {
	
    public static final String REQUEST_PROJECT_LIST_URL = BASE_URL + "house/getHouseList";
	public static final String REQUEST_PROJECT_PARAMS_URL = BASE_URL + "house/getHouseParams";
    public static final String REQUEST_PROJECT_RULE_DETAIL_URL = BASE_URL + "house/getHouseRuleInfo";
    public static final String REQUEST_PROJECT_RULE_LIST_URL = BASE_URL + "house/getHouseRuleList";
    public static final String REQUEST_PROJECT_INFO_URL = BASE_URL + "house/getHouseInfo";
    public static final String REQUEST_PROJECT_NEWS_LIST_URL = BASE_URL + "house/getHouseNewsList";
    
    public static void requestProjectList(String keyword, String city_id, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("keyword", "");
        params.put("city_id", city_id);
        params.put("page_index", page_index);
        params.put("page_size", page_size);

        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_LIST_URL, params, responseHandler);
    }
    
    public static void requestProjectInfo(String house_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("house_id", house_id);

        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_INFO_URL, params, responseHandler);
    }
    
	public static void requestProjectParams(String house_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("house_id", house_id);

        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_PARAMS_URL, params, responseHandler);
    }
	
	 public static void requestProjectRuleDetail(String house_rule_id, AsyncHttpResponseHandler responseHandler) {
			setClientHeader();
			RequestParams params = new RequestParams();
	        params.put("house_rule_id", house_rule_id);
	        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_RULE_DETAIL_URL, params, responseHandler);
	}
	 
	 public static void requestProjectRuleList(String house_id, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
			setClientHeader();
			RequestParams params = new RequestParams();
	        params.put("house_id", house_id);
	        params.put("page_index", page_index);
	        params.put("page_size", page_size);
	        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_RULE_LIST_URL, params, responseHandler);
	}
	 
	 public static void requestProjectNewsList(String house_id, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
	        setClientHeader();
	        RequestParams params = new RequestParams();
	        params.put("house_id", house_id);
	        params.put("page_index", page_index);
	        params.put("page_size", page_size);
	        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_NEWS_LIST_URL, params, responseHandler);
	    }
}
