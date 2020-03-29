
package com.zlove.frag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zlove.act.independent.ActIndependentBeforeCooperateRule;
import com.zlove.base.BaseFragment;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;

public class CooperateRuleFragment extends BaseFragment implements OnClickListener {

    public static final String RULE_DETAIL_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "ruleWeb/index?id=";
    
    private WebView webView;
    private ProgressBar progressBar;
    private Button btnBeforeRule;

    private String url;
    private String ruleId = "";
    private String projectId = "";

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_rule_webview;
    }

    @Override
    protected void setUpView(View view) {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_RULE_ID)) {
                ruleId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_RULE_ID);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_ID)) {
                projectId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
            }
        }
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("本期规则");

        btnBeforeRule = (Button) view.findViewById(R.id.id_confirm);
        btnBeforeRule.setText("以往规则");
        btnBeforeRule.setOnClickListener(this);

        webView = (WebView) view.findViewById(R.id.webview);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        
        initWebView();
    }

    @Override
    public void onClick(View v) {
        if (v == btnBeforeRule) {
            Intent intent = new Intent(getActivity(), ActIndependentBeforeCooperateRule.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
            startActivity(intent);
        }
    }

    @SuppressLint("SetJavaScriptEnabled") 
    @SuppressWarnings("deprecation")
    private void initWebView() {
        url = RULE_DETAIL_URL + ruleId;
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
