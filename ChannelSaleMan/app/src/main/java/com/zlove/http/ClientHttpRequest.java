
package com.zlove.http;

import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpClient;
import com.zlove.base.http.RequestParams;
import com.zlove.base.util.ApplicationUtil;

public class ClientHttpRequest extends HttpClient {
    
    public static final String GET_CLIENT_LIST_URL = BASE_URL + "client/getClientList";
    public static final String GET_CLIENT_INFO_URL = BASE_URL + "client/getClientInfo";
    public static final String GET_CLIENT_TRACE_LIST_URL = BASE_URL + "client/getClientTraceList";
    public static final String ADD_CLIENT_TRACE_URL = BASE_URL + "client/addClientTraceLog";
    public static final String RECOMMEND_CLIENT_URL = BASE_URL + "client/recommendClient";
    public static final String UPDATE_CLIENT_URL = BASE_URL + "client/updateClient";
    public static final String SET_REVISIT_TIME_URL = BASE_URL + "client/setRevisitTime";
    public static final String DECIDE_EFFECT_URL = BASE_URL + "client/decideEffected";
    public static final String DECIDE_VISITED_URL = BASE_URL + "client/decideVisited";
    public static final String SET_VISITED_URL = BASE_URL + "client/setVisited";
    public static final String SET_VALIDED_URL = BASE_URL + "client/setValided";
    public static final String SET_OVERDUE_URL = BASE_URL + "client/setClientDisabled";
    public static final String SET_REVISIT_URL = BASE_URL + "client/setRenewVisited";
    public static final String GET_RECOMMEND_HOUSE_LIST_URL = BASE_URL + "client/getRecommendedHousesList";

    public static void getClientListRequest(String keyword, String category_id, String house_type, String property_type, String page_index,
        String page_size, String status, String house_id, String type, String is_disabled, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("keyword", keyword);
        params.put("category_id", category_id);
        params.put("house_type", house_type);
        params.put("property_type", property_type);
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        params.put("status", status);
        params.put("house_id", house_id);
        params.put("type", type);
        params.put("is_disabled", is_disabled);

        post(ApplicationUtil.getApplicationContext(), GET_CLIENT_LIST_URL, params, responseHandler);
    }
    
    public static void getClientInfoRequest(String client_id, String house_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);

        post(ApplicationUtil.getApplicationContext(), GET_CLIENT_INFO_URL, params, responseHandler);
    }
    
    public static void getClientTraceListRequest(String client_id, String page_index, String page_size, String house_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        params.put("house_id", house_id);
        post(ApplicationUtil.getApplicationContext(), GET_CLIENT_TRACE_LIST_URL, params, responseHandler);
    }
    
    public static void addClientTraceRequest(String client_id, String house_id, String content, String broker_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        params.put("content", content);
        params.put("broker_id", broker_id);

        post(ApplicationUtil.getApplicationContext(), ADD_CLIENT_TRACE_URL, params, responseHandler);
    }
    
    public static void recommendClientRequest(String name, String phone, String category_id, String intent_price_min, String intent_price_max, 
        String house_types, String house_id, String property_types, String intent_area, 
        String from_type, String from_way, String visit_time, AsyncHttpResponseHandler responseHandler) {
        
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("phone", phone);
        params.put("category_id", category_id);
        params.put("intent_price_min", intent_price_min);
        params.put("intent_price_max", intent_price_max);
        params.put("house_types", house_types);
        params.put("house_id", house_id);
        params.put("property_types", property_types);
        params.put("intent_area", intent_area);
        params.put("from_type", from_type);
        params.put("from_way", from_way);
        params.put("visit_time", visit_time);

        post(ApplicationUtil.getApplicationContext(), RECOMMEND_CLIENT_URL, params, responseHandler);
    }
    
    public static void updateClientRequest(String name, String category_id, String intent_location_ids, String intent_price_min, String intent_price_max,
        String intent_area, String house_types, String property_types, String client_id, String house_id, String from_type, AsyncHttpResponseHandler responseHandler) {

        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("category_id", category_id);
        params.put("intent_location_ids", intent_location_ids);
        params.put("intent_price_min", intent_price_min);
        params.put("intent_price_max", intent_price_max);
        params.put("intent_area", intent_area);
        params.put("house_types", house_types);
        params.put("property_types", property_types);
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        params.put("from_type", from_type);

        post(ApplicationUtil.getApplicationContext(), UPDATE_CLIENT_URL, params, responseHandler);
    }
    
    public static void setRevisitTimeRequest(String client_id, String house_id, String revisit_time, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        params.put("revisit_time", revisit_time);

        post(ApplicationUtil.getApplicationContext(), SET_REVISIT_TIME_URL, params, responseHandler);
    }
    
    public static void decideEffectRequest(String client_id, String house_id, String is_effected, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        params.put("is_effected", is_effected);
        post(ApplicationUtil.getApplicationContext(), DECIDE_EFFECT_URL, params, responseHandler);
    }
    
    public static void decideVisitRequest(String client_id, String house_id, String is_visited, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        params.put("is_visited", is_visited);
        post(ApplicationUtil.getApplicationContext(), DECIDE_VISITED_URL, params, responseHandler);
    }
    
    public static void setVisitedRequest(String client_id, String house_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        post(ApplicationUtil.getApplicationContext(), SET_VISITED_URL, params, responseHandler);
    }
    
    public static void setValidedRequest(String client_id, String house_id, String is_valided, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", house_id);
        params.put("is_valided", is_valided);
        post(ApplicationUtil.getApplicationContext(), SET_VALIDED_URL, params, responseHandler);
    }

    public static void setOverdueRequest(String client_id, String is_disabled, String houseId, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("is_disabled", is_disabled);
        params.put("client_id", client_id);
        params.put("house_id", houseId);
        post(ApplicationUtil.getApplicationContext(), SET_OVERDUE_URL, params, responseHandler);
    }

    public static void setRevisitRequest(String client_id, String houseId, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("house_id", houseId);
        params.put("is_visited", "1");
        post(ApplicationUtil.getApplicationContext(), SET_REVISIT_URL, params, responseHandler);
    }

    public static void getRecommendHouseList(String client_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("client_id", client_id);
        params.put("page_index", 0);
        params.put("page_size", 10);
        post(ApplicationUtil.getApplicationContext(), GET_RECOMMEND_HOUSE_LIST_URL, params, responseHandler);
    }
}
