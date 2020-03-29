package com.zlove.http;

import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpClient;
import com.zlove.base.http.RequestParams;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.channel.R;


public class FriendHttpRequest extends HttpClient {
    
    public static final String BASE_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url);
    public static final String GET_FRIEND_LIST_URL = BASE_URL + "friend/getFriendList";
    public static final String GET_FRIEND_DETAIL_URL = BASE_URL + "friend/getFriendInfo";
    public static final String GET_FRIEND_RECOMMEND_LIST_URL = BASE_URL + "friend/getFriendRecommendList";
    public static final String ADD_FRIEND_URL = BASE_URL + "friend/addFriend";
    
    
    public static void getFriendListRequest(String keyword, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        
        RequestParams params = new RequestParams();
        params.put("keyword", keyword);
        params.put("page_index", page_index);
        params.put("page_size", page_size);

        post(ApplicationUtil.getApplicationContext(), GET_FRIEND_LIST_URL, params, responseHandler);
    }
    
    
    public static void getFriendDetailRequest(String friend_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        
        RequestParams params = new RequestParams();
        params.put("friend_id", friend_id);
        post(ApplicationUtil.getApplicationContext(), GET_FRIEND_DETAIL_URL, params, responseHandler);
    }
    
    public static void getFriendRecommendListRequest(String friend_id, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        
        RequestParams params = new RequestParams();
        params.put("friend_id", friend_id);
        params.put("page_index", page_index);
        params.put("page_size", page_size);
        post(ApplicationUtil.getApplicationContext(), GET_FRIEND_RECOMMEND_LIST_URL, params, responseHandler);
    }
    
    public static void addFriendRequest(String salesman_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        
        RequestParams params = new RequestParams();
        params.put("salesman_id", salesman_id);
        post(ApplicationUtil.getApplicationContext(), ADD_FRIEND_URL, params, responseHandler);
    }
    
}
