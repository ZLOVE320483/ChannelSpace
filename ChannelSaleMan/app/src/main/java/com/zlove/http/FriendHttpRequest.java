package com.zlove.http;

import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpClient;
import com.zlove.base.http.RequestParams;
import com.zlove.base.util.ApplicationUtil;


public class FriendHttpRequest extends HttpClient {
    
    private static final String GET_FRIEND_LIST_URL = BASE_URL + "friend/getFriendList";
    private static final String GET_FRIEND_INFO_URL = BASE_URL + "friend/getFriendInfo";
    private static final String GET_FRIEND_RECOMMEND_LIST_URL = BASE_URL + "friend/getFriendRecommendList";
    private static final String UPDATE_FRIEND_INFO_URL = BASE_URL + "friend/updateFriendInfo";
    
    public static void getFriendListRequest(String house_id, String keyword, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        
        RequestParams params = new RequestParams();
        params.put("house_id", house_id);
        params.put("keyword", keyword);
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        post(ApplicationUtil.getApplicationContext(), GET_FRIEND_LIST_URL, params, responseHandler);
    }
    
    public static void getFriendInfoRequest(String friend_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        
        RequestParams params = new RequestParams();
        params.put("friend_id", friend_id);
        post(ApplicationUtil.getApplicationContext(), GET_FRIEND_INFO_URL, params, responseHandler);
    }
    
    public static void getFriendRecommendListRequest(String friend_id, String page_index, String page_size, String keyword, 
        String category_id, String house_type, String property_type, String status, String house_id, String is_disabled, AsyncHttpResponseHandler responseHandler) {
        
        setClientHeader();
        
        RequestParams params = new RequestParams();
        params.put("friend_id", friend_id);
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        params.put("keyword", keyword);
        params.put("category_id", category_id);
        params.put("house_type", house_type);
        params.put("property_type", property_type);
        params.put("status", status);
        params.put("house_id", house_id);
        params.put("is_disabled", is_disabled);
        post(ApplicationUtil.getApplicationContext(), GET_FRIEND_RECOMMEND_LIST_URL, params, responseHandler);
    }
    
    public static void updateFriendInfoRequest(String friend_id, String friend_name, String friend_gender, String category_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        
        RequestParams params = new RequestParams();
        params.put("friend_id", friend_id);
        params.put("friend_name", friend_name);
        params.put("friend_gender", friend_gender);
        params.put("category_id", category_id);
        post(ApplicationUtil.getApplicationContext(), UPDATE_FRIEND_INFO_URL, params, responseHandler);
    }
}
