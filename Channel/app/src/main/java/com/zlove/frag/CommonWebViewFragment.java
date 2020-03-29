package com.zlove.frag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;

public class CommonWebViewFragment extends BaseFragment {
	
	private WebView webView;
	private ProgressBar progressBar;
	
	private String url;
	private String title;

	@Override
	protected int getInflateLayout() {
		return R.layout.common_frag_webview;
	}

	@Override
	protected void setUpView(View view) {
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			if (intent.hasExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE)) {
				title = intent.getStringExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE);
			}
			if (intent.hasExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL)) {
				url = intent.getStringExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL);
			}
		}
		setBackButton(view.findViewById(R.id.id_back));
		if (TextUtils.isEmpty(title)) {
			((TextView) view.findViewById(R.id.id_title)).setText("详情");
		} else {
			((TextView) view.findViewById(R.id.id_title)).setText(title);
		}
		
		webView = (WebView) view.findViewById(R.id.webview);
		progressBar = (ProgressBar) view.findViewById(R.id.progress);
		
		initWebView();
	}
	
	@SuppressLint("SetJavaScriptEnabled") 
	@SuppressWarnings("deprecation")
	private void initWebView() {
		if (url.contains("about")) {
			url = url + "?appVersion=" + ApplicationUtil.getVerName(getActivity());
		}
		webView.loadUrl(url);
		webView.setWebChromeClient(new MyChromeClient());
		webView.setWebViewClient(new MyWebViewClient());

		WebSettings webSettings = webView.getSettings();
		webSettings.setPluginState(PluginState.ON);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setDatabaseEnabled(true);
		// 全屏显示
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setUseWideViewPort(true);
	}
	
	class MyChromeClient extends WebChromeClient {
		
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
            if (progress == 100) {
                if (progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        }
    }

	class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

}
