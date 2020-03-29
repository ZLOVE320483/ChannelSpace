package com.zlove.http;

import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpClient;
import com.zlove.base.http.RequestParams;
import com.zlove.base.util.ApplicationUtil;


public class MessageHttpRequest extends HttpClient {

	public static final String GET_MESSAGE_HOME_COUNT_URL = BASE_URL + "message/getHomeList";
	public static final String GET_MESSAGE_LIST_URL = BASE_URL + "message/getMessageList";
    public static final String SET_MESSAGE_READ_URL = BASE_URL + "message/setRead";
	
	public static void requestMessageHomeCount(AsyncHttpResponseHandler responseHandler) {
		setClientHeader();
		RequestParams params = new RequestParams();
		post(ApplicationUtil.getApplicationContext(), GET_MESSAGE_HOME_COUNT_URL, params, responseHandler);
	}
	
	public static void requestMessageList(String type, String page_index, String page_size, AsyncHttpResponseHandler responseHandler) {
		setClientHeader();
		RequestParams params = new RequestParams();
		params.put("type", type);
		params.put("page_index", page_index);
		params.put("page_size", page_size);
		post(ApplicationUtil.getApplicationContext(), GET_MESSAGE_LIST_URL, params, responseHandler);
	}
	
    public static void setMessageRead(String user_message_id, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("user_message_id", user_message_id);
        post(ApplicationUtil.getApplicationContext(), SET_MESSAGE_READ_URL, params, responseHandler);
    }
}
