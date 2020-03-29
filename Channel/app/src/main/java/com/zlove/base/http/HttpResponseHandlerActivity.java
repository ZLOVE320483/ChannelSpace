package com.zlove.base.http;

import java.lang.ref.WeakReference;

import org.apache.http.Header;

import android.app.Activity;
import android.util.Log;


public class HttpResponseHandlerActivity <T extends Activity> extends AsyncHttpResponseHandler{
    
    private WeakReference<T> contextReference; // 用弱引用指向activity或者fragment,以防止内存泄漏
    
    public HttpResponseHandlerActivity(T context) {
        super();
        contextReference = new WeakReference<T>(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (contextReference.get() == null ) {
            Log.i("HttpClient", "ON_START: Context is null for " + this.getClass().getSimpleName());
            return;
        }
    }
    
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] content) {
        if (contextReference.get() == null ) {
            Log.i("HttpClient", "ON_SUCCESS: Context is null for " + this.getClass().getSimpleName());
            return;
        }
        if(content != null){
            Log.i("HttpClient", new String(content));
        }
        
    }
    
    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
        if (contextReference.get() == null ) {
            Log.i("HttpClient", "ON_FAILURE: Context is null for " + this.getClass().getSimpleName());
            return;
        }
        if(content != null){
            Log.i("HttpClient", new String(content));
        }
        
    }

    @Override
    public void onFinish() {
        if (contextReference.get() == null ) {
            Log.i("HttpClient", "ON_FINISH: Context is null for " + this.getClass().getSimpleName());
            return;
        }
        super.onFinish();
    }
    
    public T getActivity() {
        if (contextReference == null) {
            return null;
        }
        return contextReference.get();
    }
}

