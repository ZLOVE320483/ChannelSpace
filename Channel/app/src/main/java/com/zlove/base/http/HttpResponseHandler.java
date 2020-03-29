package com.zlove.base.http;

import org.apache.http.Header;

import android.util.Log;


public class HttpResponseHandler extends AsyncHttpResponseHandler{
    
    @Override
    public void onStart() {
        super.onStart();
    }
    
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] content) {
        if(content != null){
            Log.i("HttpClient", new String(content));
        }
        
    }
    
    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
        if(content != null){
            Log.i("HttpClient", new String(content));
        }
        
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
    
}

