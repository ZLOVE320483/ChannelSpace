package com.zlove.frag;

import org.apache.http.Header;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.act.ActLogin;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.bean.user.UserChangePwdBean;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.UserHttpRequest;


public class UserPwdChangeFragment extends BaseFragment implements OnClickListener {
    
    private EditText etCurrentPwd = null;
    private ImageView ivCurrentPwdDel = null;
    
    private EditText etNewPwd = null;
    private ImageView ivNewPwdDel = null;
    
    private EditText etConfirmNewPwd = null;
    private ImageView ivConfirmNewPwdDel = null;
    
    private Button btnSubmit = null;
    
    private String currentPwd = "";
    private String newPwd = "";
    private String confirmNewPwd = "";

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_pwd_change;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("修改密码");
        
        etCurrentPwd = (EditText) view.findViewById(R.id.id_current_password);
        ivCurrentPwdDel = (ImageView) view.findViewById(R.id.id_current_password_delete);
        
        etNewPwd = (EditText) view.findViewById(R.id.id_password);
        ivNewPwdDel = (ImageView) view.findViewById(R.id.id_password_delete);
        
        etConfirmNewPwd = (EditText) view.findViewById(R.id.id_et_confirm_pwd);
        ivConfirmNewPwdDel = (ImageView) view.findViewById(R.id.id_et_confirm_pwd_delete);
        
        btnSubmit = (Button) view.findViewById(R.id.id_confirm);
        
        etCurrentPwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    ivCurrentPwdDel.setVisibility(View.GONE);
                } else {
                    ivCurrentPwdDel.setVisibility(View.VISIBLE);
                }
            }
        });
        
        etNewPwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    ivNewPwdDel.setVisibility(View.GONE);
                } else {
                    ivNewPwdDel.setVisibility(View.VISIBLE);
                }
            }
        });
        
        etConfirmNewPwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    ivConfirmNewPwdDel.setVisibility(View.GONE);
                } else {
                    ivConfirmNewPwdDel.setVisibility(View.VISIBLE);
                }
            }
        });
        
        ivCurrentPwdDel.setOnClickListener(this);
        ivNewPwdDel.setOnClickListener(this);
        ivConfirmNewPwdDel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ivCurrentPwdDel) {
            etCurrentPwd.setText("");
        } else if (v == ivNewPwdDel) {
            etNewPwd.setText("");
        } else if (v == ivConfirmNewPwdDel) {
            etConfirmNewPwd.setText("");
        } else if (v == btnSubmit) {
            onSubmitClick();
        }
    }
    
    private void onSubmitClick() {
        currentPwd = etCurrentPwd.getText().toString().trim();
        newPwd = etNewPwd.getText().toString().trim();
        confirmNewPwd = etConfirmNewPwd.getText().toString().trim();
        
        if (TextUtils.isEmpty(currentPwd)) {
            showShortToast("请输入当前密码");
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            showShortToast("请输入新密码");
            return;
        }
        if (newPwd.length() < 6 || newPwd.length() > 16) {
            showShortToast("请输入6~16位新密码");
            return;
        }
        if (TextUtils.isEmpty(confirmNewPwd)) {
            showShortToast("请确认新密码");
            return;
        }
        if (!ChannelCookie.getInstance().getPassword().equals(currentPwd)) {
            showShortToast("当前密码输入错误,请重新输入");
            return;
        }
        if (!newPwd.equals(confirmNewPwd)) {
            showShortToast("两次新密码输入不一致,请重新输入");
            return;
        }
        UserHttpRequest.userModifyPwdRequest(currentPwd ,newPwd, new ChangePwdHandler(this));
    }
    
    class ChangePwdHandler extends HttpResponseHandlerFragment<UserPwdChangeFragment> {

        public ChangePwdHandler(UserPwdChangeFragment context) {
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
                UserChangePwdBean bean = JsonUtil.toObject(new String(content), UserChangePwdBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        showShortToast("修改成功,请重新登录");
                        ChannelCookie.getInstance().setLoginPass(false);
                        ChannelCookie.getInstance().setPassword("");
                        Intent intent = new Intent(getActivity(), ActLogin.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra(IntentKey.INTENT_KEY_MODIFY_PWD, true);
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

}
