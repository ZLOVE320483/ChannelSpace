package com.zlove.frag.independent;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zlove.act.independent.ActIndependentFindPwd;
import com.zlove.act.independent.ActIndependentMain;
import com.zlove.act.independent.ActIndependentRegister;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.LogUtil;
import com.zlove.base.util.NetworkUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.user.UserLoginBean;
import com.zlove.bean.user.UserLoginData;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.UserHttpRequest;

import org.apache.http.Header;


public class IndependentLoginFragment extends BaseFragment implements OnClickListener {
    
    private ImageView loginBgView;
    private AutoCompleteTextView etAccount;
    private ImageView ivAccountDel;
    private EditText etPassword;
    private ImageView ivPwdDel;
    private Button btnLogin;
    private Button btnFindPwd;
    private Button btnRegister;
    
    private String account = "";
    private String password = "";
    
    boolean isFromChangePwd = false;
    Dialog loginDialog = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginDialog = DialogManager.getLoadingDialog(getActivity(), "正在登录...");
    }
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_login;
    }

    @Override
    protected void setUpView(View view) {
        if (!NetworkUtil.isNetworkConnected(ApplicationUtil.getApplicationContext())) {
            showShortToast("网络异常,请检查您的网络连接");
        }
        
        if (ChannelCookie.getInstance().isLoginPass()) {
        	Intent intent = new Intent(getActivity(), ActIndependentMain.class);
            startActivity(intent);
            finishActivity();
        }
        
        if (getActivity().getIntent() != null) {
            Intent intent = getActivity().getIntent();
            if (intent.hasExtra(IntentKey.INTENT_KEY_MODIFY_PWD)) {
                isFromChangePwd = intent.getBooleanExtra(IntentKey.INTENT_KEY_MODIFY_PWD, false);
            }
            if (isFromChangePwd) {
                showShortToast("请输入密码重新登录");
            }
        }
        loginBgView = (ImageView) view.findViewById(R.id.view);
        etAccount = (AutoCompleteTextView) view.findViewById(R.id.id_account);
        ivAccountDel = (ImageView) view.findViewById(R.id.id_account_delete);
        etPassword = (EditText) view.findViewById(R.id.id_password);
        ivPwdDel = (ImageView) view.findViewById(R.id.id_password_delete);
        
        btnLogin = (Button) view.findViewById(R.id.id_login);
        btnFindPwd = (Button) view.findViewById(R.id.id_find_pwd);
        btnRegister = (Button) view.findViewById(R.id.id_register);
        
		loginBgView.setBackgroundResource(R.drawable.bg_login_top_1);
        
        etAccount.setText(ChannelCookie.getInstance().getAccount());
        etPassword.setText(ChannelCookie.getInstance().getPassword());

        etAccount.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    etPassword.setText("");
                    ivAccountDel.setVisibility(View.GONE);
                } else {
                    ivAccountDel.setVisibility(View.VISIBLE);
                }
            }
        });
        
        etPassword.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    ivPwdDel.setVisibility(View.GONE);
                } else {
                    ivPwdDel.setVisibility(View.VISIBLE);
                }
            }
        });
        
        etAccount.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String str = etAccount.getText().toString();
                if (str.length() > 0) {
                    Editable etext = etAccount.getText();
                    int position = etext.length();
                    Selection.setSelection(etext, position);
                }
                if (hasFocus && str.length() > 0) {
                    ivAccountDel.setVisibility(View.VISIBLE);
                } else {
                    ivAccountDel.setVisibility(View.GONE);
                }
            }
        });

        etPassword.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String str = etPassword.getText().toString();
                if (str.length() > 0) {
                    Editable etext = etPassword.getText();
                    int position = etext.length();
                    Selection.setSelection(etext, position);
                }
                if (hasFocus && str.length() > 0) {
                    ivPwdDel.setVisibility(View.VISIBLE);
                } else {
                    ivPwdDel.setVisibility(View.GONE);
                }
            }
        });
        
        
        ivAccountDel.setOnClickListener(this);
        ivPwdDel.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnFindPwd.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        if (v == ivAccountDel) {
            etAccount.setText("");
            etPassword.setText("");
        } else if (v == ivPwdDel) {
            etPassword.setText("");
        } else if (v == btnLogin) {
            onLoginClick();
        } else if (v == btnFindPwd) {
            Intent intent = new Intent(getActivity(), ActIndependentFindPwd.class);
            startActivity(intent);
        } else if (v == btnRegister) {
            Intent intent = new Intent(getActivity(), ActIndependentRegister.class);
            startActivity(intent);
        }
    }
    
    private void onLoginClick() {
		account = etAccount.getText().toString().trim();
		password = etPassword.getText().toString().trim();
		
		if (TextUtils.isEmpty(account)) {
			showShortToast("请输入您的手机号");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			showShortToast("请输入您的密码");
			return;
		}
		if (password.length() < 6 || password.length() > 16) {
			showShortToast("请输入6~16位密码");
			return;
		}
		if (!NetworkUtil.isNetworkConnected(ApplicationUtil.getApplicationContext())) {
            showShortToast("网络异常,请检查您的网络连接");
            return;
        }
		UserHttpRequest.userLoginRequest(account, password, new UserLoginHandler(this));
    }
    
    class UserLoginHandler extends HttpResponseHandlerFragment<IndependentLoginFragment> {

        public UserLoginHandler(IndependentLoginFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
            if (loginDialog != null && !loginDialog.isShowing()) {
                loginDialog.show();
            }
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (!isAdded()) {
                return;
            }
            if (content != null) {
                CommonBean commonBean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (commonBean != null) {
                    if (commonBean.getStatus() == 200) {
                        UserLoginBean bean = JsonUtil.toObject(new String(content), UserLoginBean.class);
                        ChannelCookie.getInstance().setLoginPass(true);
                        UserLoginData data = bean.getData();
                        if (data != null) {
                            String sessionId = data.getSession_id();
                            ChannelCookie.getInstance().saveUserInfo(account, password, sessionId);
                            String gender = data.getGender();
                            if (gender.equals("1")) {
                                ChannelCookie.getInstance().saveUserGender("男");
                            } else if (gender.equals("2")) {
                                ChannelCookie.getInstance().saveUserGender("女");
                            } else {
                                ChannelCookie.getInstance().saveUserGender("");
                            }
                            String userAvatar = data.getAvatar();
                            ChannelCookie.getInstance().saveUserAvatar(userAvatar);
                            String realName = data.getRealname();
                            ChannelCookie.getInstance().saveUserName(realName);
                            if (!TextUtils.isEmpty(ChannelCookie.getInstance().getToken()) && !TextUtils.isEmpty(ChannelCookie.getInstance().getSessionId()))
                                UserHttpRequest.setXinGeRequest(ChannelCookie.getInstance().getDeviceId(), ChannelCookie.getInstance().getToken(), new SetXinGeHandler());
                        }
                        Intent intent = new Intent(getActivity(), ActIndependentMain.class);
                        startActivity(intent);
                        finishActivity();
                    } else {
                        showShortToast(commonBean.getMessage());
                    }
                } else {
                    showShortToast("账号或密码错误");
                }
            }
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
            if (loginDialog != null && loginDialog.isShowing()) {
                loginDialog.dismiss();
            }
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
            if (loginDialog != null && loginDialog.isShowing()) {
                loginDialog.dismiss();
            }
        }
        
    }

    class SetXinGeHandler extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            if (responseBody != null) {
                CommonBean bean = JsonUtil.toObject(new String(responseBody), CommonBean.class);
                if (bean != null) {
                    LogUtil.d("ZLOVE", "SetXinGeHandler---status---" + bean.getStatus());
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }

    }

}