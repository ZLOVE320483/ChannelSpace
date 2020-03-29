package com.zlove.http;

import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpClient;
import com.zlove.base.http.RequestParams;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.channel.R;


public class ClientHttpRequest extends HttpClient {
    
    public static final String BASE_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url);
    public static final String ADD_NEW_CLIENT_URL = BASE_URL + "client/addNewClient";
    public static final String GET_CLIENT_LIST_URL = BASE_URL + "client/getClientList";
    public static final String RECOMMEND_CLIENT_URL = BASE_URL + "client/recommendClient";
    public static final String UPDATE_CLIENT_URL = BASE_URL + "client/updateClient";
    public static final String GET_CLIENT_INFO_URL = BASE_URL + "client/getClientInfo";
    public static final String ADD_CLIENT_TRACE_URL = BASE_URL + "client/addClientTraceLog";
    public static final String GET_CLIENT_TRACE_LIST_URL = BASE_URL + "client/getClientTraceList";
    public static final String EXTEND_RECOMMEND_TIME_URL = BASE_URL + "client/extendRecommendTime";
    public static final String SET_REVISIT_TIME_URL = BASE_URL + "client/setRevisitTime";
    public static final String GET_CLIENT_RECOMMEND_HOUSE_LIST_URL = BASE_URL + "client/getRecommendedHousesList";
    public static final String UPDATE_RECOMMEND_STATUS = BASE_URL + "clinet/updateRecommendStatus";
    public static final String RECOMMEND_BY_CLIENT_ID_URL = BASE_URL + "client/recommendByClientId";
    public static final String SELECT_CLIENT_LIST_URL = BASE_URL + "client/selectClientList";
    public static final String EXTEND_VISIT_TIME_URL = BASE_URL + "client/extendVisitTime";
    public static final String RECOMMEND_VISIT_URL = BASE_URL + "client/clientVisitRemind";
    
    public static void getClientListRequest(String keyword, String category_id, String house_type, 
                                            String property_type, String page_index, String page_size, String status, String is_disabled, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("keyword", keyword);
        params.put("category_id", category_id);
        params.put("house_type", house_type);
        params.put("property_type", property_type);
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        params.put("status", status);
        params.put("is_disabled", is_disabled);

        post(ApplicationUtil.getApplicationContext(), GET_CLIENT_LIST_URL, params, responseHandler);
    }
    
    public static void addNewClientRequest(String name, String phone, String category_id, String location_ids, 
                                            String intent_price_min, String intent_price_max, String intent_area, 
                                            String house_types, String property_type, String gender, String client_desc, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("phone", phone);
        params.put("category_id", category_id);
        params.put("intent_location_ids", location_ids);
        params.put("intent_price_min", intent_price_min);
        params.put("intent_price_max", intent_price_max);
        params.put("intent_area", intent_area);
        params.put("house_types", house_types);
        params.put("property_types", property_type);
        params.put("gender", gender);
        params.put("client_desc", client_desc);
        
        post(ApplicationUtil.getApplicationContext(), ADD_NEW_CLIENT_URL, params, responseHandler);
    }
    
    public static void recommeClientRequest(String name, String phone, String category_id, String location_ids, String intent_price_min,
        String intent_price_max, String intent_area, String house_types, String property_type,
        String house_id, String salesman_id, String client_desc, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("phone", phone);
        params.put("category_id", category_id);
        params.put("intent_location_ids", location_ids);
        params.put("intent_price_min", intent_price_min);
        params.put("intent_price_max", intent_price_max);
        params.put("intent_area", intent_area);
        params.put("house_types", house_types);
        params.put("property_types", property_type);
        params.put("house_id", house_id);
        params.put("salesman_id", salesman_id);
        params.put("client_desc", client_desc);

        post(ApplicationUtil.getApplicationContext(), RECOMMEND_CLIENT_URL, params, responseHandler);
    }
    
    public static void updateClientRequest(String name, String category_id, String location_ids, String intent_price_min,
        String intent_price_max, String intent_area, String house_types, String property_type, 
        String gender, String client_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("category_id", category_id);
        params.put("intent_location_ids", location_ids);
        params.put("intent_price_min", intent_price_min);
        params.put("intent_price_max", intent_price_max);
        params.put("intent_area", intent_area);
        params.put("house_types", house_types);
        params.put("property_types", property_type);
        params.put("gender", gender);
        params.put("client_id", client_id);

        post(ApplicationUtil.getApplicationContext(), UPDATE_CLIENT_URL, params, responseHandler);
    }
    
    public static void getClientInfoRequest(String client_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);

        post(ApplicationUtil.getApplicationContext(), GET_CLIENT_INFO_URL, params, responseHandler);
    }
    
    public static void addClientTraceRequest(String client_id, String house_id, String content, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        params.put("content", content);

        post(ApplicationUtil.getApplicationContext(), ADD_CLIENT_TRACE_URL, params, responseHandler);
    }
    
    public static void getClientTraceListRequest(String client_id, String house_id, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        params.put("page_index", page_index);
        params.put("page_size", page_size);

        post(ApplicationUtil.getApplicationContext(), GET_CLIENT_TRACE_LIST_URL, params, responseHandler);
    }
    
    public static void extendRecommendTimeRequest(String client_id, String house_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);

        post(ApplicationUtil.getApplicationContext(), EXTEND_RECOMMEND_TIME_URL, params, responseHandler);
    }
    
    public static void extendVisitTimeRequest(String client_id, String house_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);

        post(ApplicationUtil.getApplicationContext(), EXTEND_VISIT_TIME_URL, params, responseHandler);
    }
    
    public static void setRevisitTimeRequest(String client_id, String house_id, String revisit_time, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        params.put("revisit_time", revisit_time);

        post(ApplicationUtil.getApplicationContext(), SET_REVISIT_TIME_URL, params, responseHandler);
    }
    
    public static void getClientRecommendHouseListRequest(String client_id, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("page_index", page_index);
        params.put("page_size", page_size);

        post(ApplicationUtil.getApplicationContext(), GET_CLIENT_RECOMMEND_HOUSE_LIST_URL, params, responseHandler);
    }
    
    public static void updateRecommendStatusRequest(String client_id, String status, String house_id, AsyncHttpResponseHandler responseHandler) {
    	setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("status", status);
        params.put("house_id", house_id);
        
        post(ApplicationUtil.getApplicationContext(), UPDATE_RECOMMEND_STATUS, params, responseHandler);
    }
    
    public static void recommendClientById(String client_id, String house_id, String salesman_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        params.put("salesman_id", salesman_id);
        
        post(ApplicationUtil.getApplicationContext(), RECOMMEND_BY_CLIENT_ID_URL, params, responseHandler);
    }
    
    public static void getSelectClientList(String house_id, AsyncHttpResponseHandler responseHandler) {
    	setClientHeader();
        RequestParams params = new RequestParams();
        params.put("house_id", house_id);

        post(ApplicationUtil.getApplicationContext(), SELECT_CLIENT_LIST_URL, params, responseHandler);
	}
    
    public static void recommendClientVisit(String client_id, String house_id, AsyncHttpResponseHandler responseHandler) {
    	setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);

        post(ApplicationUtil.getApplicationContext(), RECOMMEND_VISIT_URL, params, responseHandler);
	}
}
