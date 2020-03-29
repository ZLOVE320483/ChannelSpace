package com.zlove.http;

import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpClient;
import com.zlove.base.http.RequestParams;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.channel.R;


public class ProjectHttpRequest extends HttpClient {
    
    public static final String BASE_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url);
    public static final String REQUEST_PROJECT_LIST_URL = BASE_URL + "house/getHouseList";
    public static final String REQUEST_PROJECT_DETAIL_URL = BASE_URL + "house/getHouseInfo";
    public static final String REQUEST_PROJECT_NEWS_LIST_URL = BASE_URL + "house/getHouseNewsList";
    public static final String REQUEST_PROJECT_RULE_LIST_URL = BASE_URL + "house/getHouseRuleList";
    public static final String REQUEST_PROJECT_RULE_DETAIL_URL = BASE_URL + "house/getHouseRuleInfo";
    public static final String REQUEST_PROJECT_PARAMS_URL = BASE_URL + "house/getHouseParams";
    public static final String REQUEST_PROJECT_GET_REPORTED_CUSTOMER_URL = BASE_URL + "house/getRecommendedList";
    public static final String REQUEST_POJECT_SEARCH_BY_CODE = BASE_URL + "house/searchHouseByCode";
    public static final String REQUEST_SELECT_PROJECT_URL = BASE_URL + "house/selectHouseList";
    public static final String REQUEST_SELECT_PROJECT_SALEMAN_URL = BASE_URL + "house/getHouseSalesmanList";
    
    public static void requestProjectList(String keyword, String city_id, String house_type, 
                                            String property_type, String price_min, String price_max, 
                                            String page_index, String page_size, String area_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("keyword", keyword);
        params.put("city_id", city_id);
        params.put("house_type", house_type);
        params.put("property_type", property_type);
        params.put("price_min", price_min);
        params.put("price_max", price_max);
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        params.put("area_id", area_id);
        
        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_LIST_URL, params, responseHandler);
    }
    
    public static void requestProjectDetail(String house_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("house_id", house_id);

        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_DETAIL_URL, params, responseHandler);
    }
    
    public static void requestProjectNewsList(String house_id, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
		setClientHeader();
		RequestParams params = new RequestParams();
        params.put("house_id", house_id);
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_NEWS_LIST_URL, params, responseHandler);
	}
    
    public static void requestProjectRuleList(String house_id, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
		setClientHeader();
		RequestParams params = new RequestParams();
        params.put("house_id", house_id);
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_RULE_LIST_URL, params, responseHandler);
	}
    
    public static void requestProjectRuleDetail(String house_rule_id, AsyncHttpResponseHandler responseHandler) {
		setClientHeader();
		RequestParams params = new RequestParams();
        params.put("house_rule_id", house_rule_id);
        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_RULE_DETAIL_URL, params, responseHandler);
	}
    
    public static void requestProjectParams(String house_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("house_id", house_id);

        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_PARAMS_URL, params, responseHandler);
    }
    
    public static void requestProjectCustomerReportedList(String house_id, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("house_id", house_id);
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        post(ApplicationUtil.getApplicationContext(), REQUEST_PROJECT_GET_REPORTED_CUSTOMER_URL, params, responseHandler);
    }
    
    public static void addProjectByCode(String code, AsyncHttpResponseHandler responseHandler) {
		setClientHeader();
		RequestParams params = new RequestParams();
		params.put("code", code);
        post(ApplicationUtil.getApplicationContext(), REQUEST_POJECT_SEARCH_BY_CODE, params, responseHandler);
	}
    
    public static void requestSelectProjectList(String city_id, String house_type, String property_type, String price_min, String price_max,
        String area_id, String client_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("city_id", city_id);
        params.put("house_type", house_type);
        params.put("property_type", property_type);
        params.put("price_min", price_min);
        params.put("price_max", price_max);
        params.put("area_id", area_id);
        params.put("client_id", client_id);

        post(ApplicationUtil.getApplicationContext(), REQUEST_SELECT_PROJECT_URL, params, responseHandler);
    }
    
    public static void requestSelectProjectSaleManList(String house_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("house_id", house_id);
        post(ApplicationUtil.getApplicationContext(), REQUEST_SELECT_PROJECT_SALEMAN_URL, params, responseHandler);
    }
}
