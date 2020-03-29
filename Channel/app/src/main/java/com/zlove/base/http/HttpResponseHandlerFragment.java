
package com.zlove.base.http;

import java.lang.ref.WeakReference;

import org.apache.http.Header;

import android.content.Intent;
import android.util.Log;

import com.zlove.act.independent.ActIndependentLogin;
import com.zlove.base.BaseFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;

public class HttpResponseHandlerFragment<T extends BaseFragment> extends AsyncHttpResponseHandler {

    private WeakReference<T> contextReference; // 用弱引用指向activity或者fragment,以防止内存泄漏

    public HttpResponseHandlerFragment(T context) {
        super();
        contextReference = new WeakReference<T>(context);
    }

    public enum LOAD_ACTION {
        ONREFRESH, LOADERMORE
    }

    @Override
    public void onStart() {
        super.onStart();
        if (contextReference.get() == null) {
            Log.i("HttpClient", "ON_START: Context is null IN " + this.getClass().getSimpleName());
            return;
        }
        if (!contextReference.get().isAdded()) {
            Log.i("HttpClient", "ON_START: fragment hasn't be added IN " + this.getClass().getSimpleName());
            return;
        }
        if (contextReference.get().getActivity() == null) {
            Log.i("HttpClient", "ON_START: the owner activity is null IN " + this.getClass().getSimpleName());
            return;
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] content) {
        if (contextReference.get() == null) {
            Log.i("HttpClient", "ON_SUCCESS: Context is null INr " + this.getClass().getSimpleName());
            return;
        }
        if (!contextReference.get().isAdded()) {
            Log.i("HttpClient", "ON_SUCCESS: fragment hasn't be added IN " + this.getClass().getSimpleName());
            return;
        }
        if (contextReference.get().getActivity() == null) {
            Log.i("HttpClient", "ON_SUCCESS: the owner activity is null IN " + this.getClass().getSimpleName());
            return;
        }
        if (content != null) {
            Log.i("HttpClient", new String(content));
            CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
            if (bean != null) {
                if (bean.getStatus() == 490) {
                    contextReference.get().showShortToast("您的账号在其他设备登录过,请重新登录");
                    ChannelCookie.getInstance().setLoginPass(false);
                    ChannelCookie.getInstance().setPassword("");
                    Intent intent = new Intent(contextReference.get().getActivity(), ActIndependentLogin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(IntentKey.INTENT_KEY_MODIFY_PWD, true);
                    contextReference.get().startActivity(intent);
                    contextReference.get().finishActivity();
                }
            }
        }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
        if (contextReference.get() == null) {
            Log.i("HttpClient", "ON_FAILURE: Context is null IN " + this.getClass().getSimpleName());
            return;
        }
        if (!contextReference.get().isAdded()) {
            Log.i("HttpClient", "ON_FAILURE: fragment hasn't be added IN " + this.getClass().getSimpleName());
            return;
        }
        if (contextReference.get().getActivity() == null) {
            Log.i("HttpClient", "ON_FAILURE: the owner activity is null IN " + this.getClass().getSimpleName());
            return;
        }
        if (content != null) {
            Log.i("HttpClient", new String(content));
        }

    }

    @Override
    public void onFinish() {
        if (contextReference.get() == null) {
            Log.i("HttpClient", "ON_FINISH: Context is null IN " + this.getClass().getSimpleName());
            return;
        }
        if (!contextReference.get().isAdded()) {
            Log.i("HttpClient", "ON_FINISH: fragment hasn't be added IN " + this.getClass().getSimpleName());
            return;
        }
        if (contextReference.get().getActivity() == null) {
            Log.i("HttpClient", "ON_FINISH: the owner activity is null IN " + this.getClass().getSimpleName());
            return;
        }
        super.onFinish();
    }

    public T getFragment() {
        if (contextReference == null) {
            return null;
        }
        return contextReference.get();
    }

}
