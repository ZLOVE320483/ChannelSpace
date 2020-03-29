package com.zlove.frag;

import org.apache.http.Header;

import android.content.Intent;
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

import com.zlove.act.ActLogin;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.UserHttpRequest;


public class PwdSettingFragment extends BaseFragment implements OnClickListener {

    private EditText etPassword = null;
    private ImageView ivPwdDel = null;
    private EditText etConfirmPwd = null;
    private ImageView ivConfirmPwdDel = null;
    private Button btnFindPwd = null;

    private String password = "";
    private String confirmPwd = "";
    private String account = "";
    private String verifyCode = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_pwd_set;
    }

    @Override
    protected void setUpView(View view) {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_USER_PHONE)) {
                account = intent.getStringExtra(IntentKey.INTENT_KEY_USER_PHONE);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_VERIFY_CODE)) {
                verifyCode = intent.getStringExtra(IntentKey.INTENT_KEY_VERIFY_CODE);
            }
        }
        
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("密码设置");
        
        etPassword = (EditText) view.findViewById(R.id.id_password);
        ivPwdDel = (ImageView) view.findViewById(R.id.id_password_delete);
        etConfirmPwd = (EditText) view.findViewById(R.id.id_et_confirm_pwd);
        ivConfirmPwdDel = (ImageView) view.findViewById(R.id.id_et_confirm_pwd_delete);
        btnFindPwd = (Button) view.findViewById(R.id.id_confirm);

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
        

        ivPwdDel.setOnClickListener(this);
        ivConfirmPwdDel.setOnClickListener(this);
        btnFindPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        password = etPassword.getText().toString().trim();
        confirmPwd = etConfirmPwd.getText().toString().trim();
        
        if (TextUtils.isEmpty(password)) {
            showShortToast("请设置您的密码");
            return;
        }
        if (TextUtils.isEmpty(confirmPwd)) {
            showShortToast("请确认您的密码");
            return;
        }
        if (password.length() < 6 || password.length() > 16) {
            showShortToast("请输入6~16位密码");
            return;
        }
        if (!password.equals(confirmPwd)) {
            showShortToast("两次密码不一致,请重新输入");
            return;
        }
        UserHttpRequest.firstLoginSetPwdRequest(account, password, verifyCode, new SetPwdHandler(this));
    }
    
    class SetPwdHandler extends HttpResponseHandlerFragment<PwdSettingFragment> {

        public SetPwdHandler(PwdSettingFragment context) {
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
                        showShortToast("设置成功,请登录");
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
                } else {
                    showShortToast("设置失败,请重试");
                }
            } else {
                showShortToast("设置失败,请重试");
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
