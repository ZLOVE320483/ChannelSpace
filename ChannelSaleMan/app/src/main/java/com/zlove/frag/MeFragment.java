package com.zlove.frag;

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
import com.zlove.act.ActLogin;
import com.zlove.act.ActMyProject;
import com.zlove.act.ActStationMessage;
import com.zlove.act.ActUserFeedBack;
import com.zlove.act.ActUserInfoChange;
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
import com.zlove.channelsaleman.ImageLoaderDisplayAsCircleListener;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.AppHttpRequest;
import com.zlove.http.UserHttpRequest;


public class MeFragment extends BaseFragment implements OnClickListener, ConfirmAction {
    
    public interface ChangeProjectListener {
        void change();
    }
    
    public static final String ABOUT_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "commonWeb/aboutus";
    public static final String USER_PROTOCOL_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "commonWeb/policy";
    public static final String APP_INTRODUCE_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "commonWeb/introduce";

    private ImageView ivAvatar = null;
    private TextView tvUserName = null;
    private View userInfoView = null;
    private View myProjectView = null;
    private View messageView = null;
    private View functionView = null;
    private View userProtocolView = null;
    private View userFeedBacklView = null;
    private View aboutView = null;
    private View checkAppVersionlView = null;
    private Button btnExit = null;

    private String appVersionUrl = "";
    
    private ChangeProjectListener listener;
    
    public void setListener(ChangeProjectListener listener) {
        this.listener = listener;
    }
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_main_me;
    }

    @Override
    protected void setUpView(View view) {
        ivAvatar = (ImageView) view.findViewById(R.id.id_avatar);
        tvUserName = (TextView) view.findViewById(R.id.id_user_name);
        userInfoView = view.findViewById(R.id.id_user_info);
        myProjectView = view.findViewById(R.id.id_my_project);
        messageView = view.findViewById(R.id.id_message);
        functionView = view.findViewById(R.id.id_function_introdution);
        userProtocolView = view.findViewById(R.id.id_user_protocol);
        userFeedBacklView = view.findViewById(R.id.id_user_feedback);
        aboutView = view.findViewById(R.id.id_about);
        checkAppVersionlView = view.findViewById(R.id.id_check_app_version);
        btnExit = (Button) view.findViewById(R.id.id_exit);
        
        GImageLoader.getInstance().getImageLoader().displayImage(ChannelCookie.getInstance().getUserAvatar(), ivAvatar, GImageLoader.getInstance().getAvatarOptions(), new ImageLoaderDisplayAsCircleListener());
        tvUserName.setText(ChannelCookie.getInstance().getUserName());
        
        userInfoView.setOnClickListener(this);
        myProjectView.setOnClickListener(this);
        messageView.setOnClickListener(this);
        functionView.setOnClickListener(this);
        userProtocolView.setOnClickListener(this);
        userFeedBacklView.setOnClickListener(this);
        aboutView.setOnClickListener(this);
        checkAppVersionlView.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == userInfoView) {
            Intent intent = new Intent(getActivity(), ActUserInfoChange.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_CHANGE_USER_NAME);
        } else if (v == myProjectView) {
            Intent intent = new Intent(getActivity(), ActMyProject.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_CHANGE_PROJECT);
        } else if (v == userFeedBacklView) {
            Intent intent = new Intent(getActivity(), ActUserFeedBack.class);
            startActivity(intent);
        } else if (v == functionView) {
            Intent intent = new Intent(getActivity(), ActCommonWebView.class);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, APP_INTRODUCE_URL);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "功能介绍");
            startActivity(intent);
		} else if (v == userProtocolView) {
            Intent intent = new Intent(getActivity(), ActCommonWebView.class);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, USER_PROTOCOL_URL);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "用户协议");
            startActivity(intent);
        } else if (v == aboutView) {
            Intent intent = new Intent(getActivity(), ActCommonWebView.class);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, ABOUT_URL);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "关于我们");
            startActivity(intent);
        } else if (v == messageView) {
            Intent intent = new Intent(getActivity(), ActStationMessage.class);
            startActivity(intent);
        } else if (v == checkAppVersionlView) {
            AppHttpRequest.updateAppVersionRequest(new UpdateAppHandler(this, true));
        } else if (v == btnExit) {
            UserHttpRequest.userLogout(new LogoutHandler(this));
        }
    }
    
    class UpdateAppHandler extends HttpResponseHandlerFragment<MeFragment> {

        boolean needShowToast = false;
        
        public UpdateAppHandler(MeFragment context, boolean needShowToast) {
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
                            CommonDialog dialog = new CommonDialog(getActivity(), MeFragment.this, "发现新版本", data.getRelease_note(), "立即下载");
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
    
    class LogoutHandler extends HttpResponseHandlerFragment<MeFragment> {

        public LogoutHandler(MeFragment context) {
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
                        XGPushManager.unregisterPush(getActivity());
                        ChannelCookie.getInstance().clearUserInfo();
                        Intent intent = new Intent(getActivity(), ActLogin.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finishActivity();
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
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == IntentKey.REQUEST_CODE_CHANGE_USER_NAME) {
            tvUserName.setText(ChannelCookie.getInstance().getUserName());
            GImageLoader.getInstance().getImageLoader().displayImage(ChannelCookie.getInstance().getUserAvatar(), ivAvatar, GImageLoader.getInstance().getAvatarOptions(), new ImageLoaderDisplayAsCircleListener());
        } else if (requestCode == IntentKey.REQUEST_CODE_CHANGE_PROJECT && data != null) {
            boolean isChange = data.getBooleanExtra(IntentKey.INTENT_KEY_CHANGE_PROJECT, false);
            if (isChange) {
                if (listener != null) {
                    listener.change();
                }
            }
        }
    }

}
