package com.zlove.frag.independent;

import org.apache.http.Header;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.android.tpush.XGPushManager;
import com.zlove.act.ActCommonWebView;
import com.zlove.act.independent.ActIndependentBankCard;
import com.zlove.act.independent.ActIndependentLogin;
import com.zlove.act.independent.ActIndependentStationMessage;
import com.zlove.act.independent.ActIndependentUserFeedBack;
import com.zlove.act.independent.ActIndependentUserInfoChange;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.GImageLoader;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.widget.CommonDialog;
import com.zlove.base.widget.CommonDialog.ConfirmAction;
import com.zlove.bean.app.AppVersionBean;
import com.zlove.bean.app.AppVersionData;
import com.zlove.bean.common.CommonBean;
import com.zlove.channel.ImageLoaderDisplayAsCircleListener;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.AppHttpRequest;
import com.zlove.http.UserHttpRequest;


public class IndependentMeFragment extends BaseFragment implements OnClickListener, ConfirmAction {
	
	public static final String ABOUT_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "commonWeb/aboutus";
	public static final String USER_PROTOCOL_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "commonWeb/policy";
    public static final String APP_FUNCTION_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "commonWeb/introduce";
	
    private ImageView ivAvatar = null;
    private TextView tvUserName = null;
    private View userInfoContainer = null;
    private View userCommisionContainer = null;
    private View userBankCardContainer = null;
    private View functionContainer = null;
    private View userProtocolContainer = null;
    private View messageContainer = null;
    private View userFeedbackContainer = null;
    private View aboutContainer = null;
    private View checkVersionContainer = null;
    
    private Button btnExit = null;
    
    private String appVersionUrl = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_me;
    }

    @Override
    protected void setUpView(View view) {
        ivAvatar = (ImageView) view.findViewById(R.id.id_avatar);
        tvUserName = (TextView) view.findViewById(R.id.id_user_name);
        userInfoContainer = view.findViewById(R.id.id_user_info);
        userCommisionContainer = view.findViewById(R.id.id_user_commission);
        userBankCardContainer = view.findViewById(R.id.id_user_bank_card);
        functionContainer = view.findViewById(R.id.id_function_introdution);
        userProtocolContainer = view.findViewById(R.id.id_user_protocol);
        messageContainer = view.findViewById(R.id.id_message);
        userFeedbackContainer = view.findViewById(R.id.id_user_feedback);
        aboutContainer = view.findViewById(R.id.id_about);
        checkVersionContainer = view.findViewById(R.id.id_check_app_version);
        btnExit = (Button) view.findViewById(R.id.id_exit);
        
        GImageLoader.getInstance().getImageLoader().displayImage(ChannelCookie.getInstance().getUserAvatar(), ivAvatar, GImageLoader.getInstance().getAvatarOptions(), new ImageLoaderDisplayAsCircleListener());
        tvUserName.setText(ChannelCookie.getInstance().getUserName());
        
        userInfoContainer.setOnClickListener(this);
        userCommisionContainer.setOnClickListener(this);
        userBankCardContainer.setOnClickListener(this);
        functionContainer.setOnClickListener(this);
        userProtocolContainer.setOnClickListener(this);
        messageContainer.setOnClickListener(this);
        userFeedbackContainer.setOnClickListener(this);
        aboutContainer.setOnClickListener(this);
        checkVersionContainer.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        
        AppHttpRequest.updateAppVersionRequest(new UpdateAppHandler(this, false));
    }
    
    @Override
    public void onClick(View v) {
        if (v == userInfoContainer) {
            Intent intent = new Intent(getActivity(), ActIndependentUserInfoChange.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_CHANGE_USER_NAME);
        } else if (v == userCommisionContainer) {
            String url = ApplicationUtil.getApplicationContext().getString(R.string.channel_base_url) + "commonWeb/commissionList?sessionid=" + ChannelCookie.getInstance().getSessionId();

            Intent intent = new Intent(getActivity(), ActCommonWebView.class);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, url);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "我的佣金");
            startActivity(intent);
        } else if (v == userBankCardContainer) {
            Intent intent = new Intent(getActivity(), ActIndependentBankCard.class);
            startActivity(intent);
        } else if (v == functionContainer) {
            Intent intent = new Intent(getActivity(), ActCommonWebView.class);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, APP_FUNCTION_URL);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "功能介绍");
            startActivity(intent);
        } else if (v == userProtocolContainer) {
            Intent intent = new Intent(getActivity(), ActCommonWebView.class);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, USER_PROTOCOL_URL);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "用户协议");
            startActivity(intent);
        } else if (v == messageContainer) {
            Intent intent = new Intent(getActivity(), ActIndependentStationMessage.class);
            startActivity(intent);
        } else if (v == userFeedbackContainer) {
            Intent intent = new Intent(getActivity(), ActIndependentUserFeedBack.class);
            startActivity(intent);
        } else if (v == aboutContainer) {
            Intent intent = new Intent(getActivity(), ActCommonWebView.class);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, ABOUT_URL);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "关于我们");
            startActivity(intent);
        } else if (v == checkVersionContainer) {
            AppHttpRequest.updateAppVersionRequest(new UpdateAppHandler(this, true));
        } else if (v == btnExit) {
            ChannelCookie.getInstance().clearUserInfo();
            XGPushManager.unregisterPush(getActivity());
            Intent intent = new Intent(getActivity(), ActIndependentLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finishActivity();
            UserHttpRequest.userLogout(new LogoutHandler(this));
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentKey.REQUEST_CODE_CHANGE_USER_NAME) {
            tvUserName.setText(ChannelCookie.getInstance().getUserName());
            GImageLoader.getInstance().getImageLoader().displayImage(ChannelCookie.getInstance().getUserAvatar(), ivAvatar, GImageLoader.getInstance().getAvatarOptions(), new ImageLoaderDisplayAsCircleListener());
        }
    }
    
    class UpdateAppHandler extends HttpResponseHandlerFragment<IndependentMeFragment> {

        boolean needShowToast = false;
        
        public UpdateAppHandler(IndependentMeFragment context, boolean needShowToast) {
            super(context);
            this.needShowToast = needShowToast;
        }
        
        @Override
        public void onStart() {
            super.onStart();
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (!isAdded()) {
                return;
            }
            if (content != null) {
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 201) {
                        if (needShowToast) {
                            showShortToast("没有发现新版本");
                        }
                        return;
                    } else if (bean.getStatus() == 200) {
                        AppVersionBean versionBean = JsonUtil.toObject(new String(content), AppVersionBean.class);
                        AppVersionData data = versionBean.getData();
                        if (data != null) {
                            appVersionUrl = data.getUrl();
                            CommonDialog dialog = new CommonDialog(getActivity(), IndependentMeFragment.this, "发现新版本", data.getRelease_note(), "立即下载");
                            dialog.showdialog();
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
            }
            
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
        }
        
    }
    
    class LogoutHandler extends HttpResponseHandlerFragment<IndependentMeFragment> {

        public LogoutHandler(IndependentMeFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (!isAdded()) {
                return;
            }
            if (content != null) {
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        showShortToast("退出成功");
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
            }
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
        }
    }

    @Override
    public void confirm() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(appVersionUrl);
        intent.setData(content_url);
        startActivity(intent);
    }

}
