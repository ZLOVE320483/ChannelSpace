package com.zlove.frag.independent;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.act.ActCommonWebView;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.LogUtil;
import com.zlove.base.widget.CommonDialog;
import com.zlove.base.widget.CommonDialog.ConfirmAction;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.user.UserVerifyCodeBean;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.UserHttpRequest;

import org.apache.http.Header;


public class IndependentRegisterFragment extends BaseFragment implements OnClickListener, ConfirmAction {

    public static final String USER_PROTOCOL_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "commonWeb/policy";

    private EditText etUserName = null;
    private EditText etAccount = null;
    private ImageView ivAccountDel = null;
    private EditText etVerifyCode = null;
    private Button btnGetVerifyCode = null;
    private EditText etPassword = null;
    private ImageView ivPwdDel = null;
    private EditText etConfirmPwd = null;
    private ImageView ivConfirmPwdDel = null;
    private Button btnRegister = null;
    private TextView tvRegisterRequest = null;

    private GetVerifyCodeTimer timer;

    private String userName = "";
    private String account = "";
    private String verifyCode = "";
    private String password = "";
    private String confirmPwd = "";

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_register;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("注册");
        etUserName = (EditText) view.findViewById(R.id.id_user_name);
        etAccount = (EditText) view.findViewById(R.id.id_account);
        ivAccountDel = (ImageView) view.findViewById(R.id.id_account_delete);
        etVerifyCode = (EditText) view.findViewById(R.id.id_verify_code);
        etPassword = (EditText) view.findViewById(R.id.id_password);
        ivPwdDel = (ImageView) view.findViewById(R.id.id_password_delete);
        etConfirmPwd = (EditText) view.findViewById(R.id.id_et_confirm_pwd);
        ivConfirmPwdDel = (ImageView) view.findViewById(R.id.id_et_confirm_pwd_delete);
        btnGetVerifyCode = (Button) view.findViewById(R.id.id_get_verify_code);
        btnRegister = (Button) view.findViewById(R.id.id_register);
        tvRegisterRequest = (TextView) view.findViewById(R.id.id_user_protocol);

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

        etConfirmPwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    ivConfirmPwdDel.setVisibility(View.GONE);
                } else {
                    ivConfirmPwdDel.setVisibility(View.VISIBLE);
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

        etConfirmPwd.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String str = etConfirmPwd.getText().toString();
                if (str.length() > 0) {
                    Editable etext = etConfirmPwd.getText();
                    int position = etext.length();
                    Selection.setSelection(etext, position);
                }
                if (hasFocus && str.length() > 0) {
                    ivConfirmPwdDel.setVisibility(View.VISIBLE);
                } else {
                    ivConfirmPwdDel.setVisibility(View.GONE);
                }
            }
        });

        ivAccountDel.setOnClickListener(this);
        ivPwdDel.setOnClickListener(this);
        ivConfirmPwdDel.setOnClickListener(this);
        btnGetVerifyCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvRegisterRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnGetVerifyCode) {
            getVerifyCodeClick();
        } else if (v == ivAccountDel) {
            etAccount.setText("");
            etPassword.setText("");
            etConfirmPwd.setText("");
        } else if (v == ivPwdDel) {
            etPassword.setText("");
            etConfirmPwd.setText("");
        } else if (v == ivConfirmPwdDel) {
            etConfirmPwd.setText("");
        } else if (v == btnRegister) {
            onRegisterClick();
        } else if (v == tvRegisterRequest) {
            Intent intent = new Intent(getActivity(), ActCommonWebView.class);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, USER_PROTOCOL_URL);
            intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "用户协议");
            startActivity(intent);
        }
    }

    private void getVerifyCodeClick() {
        account = etAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            showShortToast("请输入您的手机号");
            return;
        }
        CommonDialog dialog = new CommonDialog(getActivity(), this, "我们将发送验证码至:", account);
        dialog.showdialog();
    }

    private void onRegisterClick() {
        userName = etUserName.getText().toString().trim();
        account = etAccount.getText().toString().trim();
        verifyCode = etVerifyCode.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        confirmPwd = etConfirmPwd.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            showShortToast("请输入您的真实姓名");
            return;
        }
        if (TextUtils.isEmpty(account)) {
            showShortToast("请输入您的手机号");
            return;
        }
        if (TextUtils.isEmpty(verifyCode)) {
            showShortToast("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showShortToast("请设置您的密码");
            return;
        }
        if (password.length() < 6 || password.length() > 16) {
            showShortToast("请输入6~16位密码");
            return;
        }
        if (TextUtils.isEmpty(confirmPwd)) {
            showShortToast("请确认您的密码");
            return;
        }
        if (!password.equals(confirmPwd)) {
            showShortToast("两次密码不一致,请重新输入");
            return;
        }
        UserHttpRequest.userRegisterRequest(account, password, verifyCode, userName, new UserRegisterHandler(this));
    }

    class GetVerifyCodeTimer extends CountDownTimer {

        public GetVerifyCodeTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btnGetVerifyCode.setText("重新获取");
            btnGetVerifyCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btnGetVerifyCode.setClickable(false);
            btnGetVerifyCode.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    @Override
    public void confirm() {
        UserHttpRequest.userVerifyCodeRequest(account, new GetVerifyCodeHandler(this));
        if (timer == null) {
            timer = new GetVerifyCodeTimer(60 * 1000, 1000);
        }
        timer.start();
    }

    class GetVerifyCodeHandler extends HttpResponseHandlerFragment<IndependentRegisterFragment> {

        public GetVerifyCodeHandler(IndependentRegisterFragment context) {
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
                UserVerifyCodeBean bean = JsonUtil.toObject(new String(content), UserVerifyCodeBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        LogUtil.d("ZLOVE", "message---" + bean.getMessage() + ", verifyCode---" + bean.getData().getCode());
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

    class UserRegisterHandler extends HttpResponseHandlerFragment<IndependentRegisterFragment> {

        public UserRegisterHandler(IndependentRegisterFragment context) {
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
                        ChannelCookie.getInstance().saveUserName(userName);
                        showShortToast("注册成功,请登录");
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

}
