package com.zlove.frag.independent;

import org.apache.http.Header;

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

import com.zlove.act.independent.ActIndependentLogin;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
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


public class IndependentUserAccountChangeFragment extends BaseFragment implements OnClickListener, ConfirmAction {
    
    private TextView tvCurrentAccount;
    
    private EditText etAccount = null;
    private ImageView ivAccountDel = null;
    private EditText etConfirmAccount = null;
    private ImageView ivConfirmAccountDel = null;
    
    private EditText etVerifyCode = null;
    private Button btnGetVerifyCode = null;
    private Button btnSubmit = null;
    
    private GetVerifyCodeTimer timer;
    
    private String account = "";
    private String confirmAccount = "";
    private String verifyCode = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_user_accout_change;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("修改绑定账号");
        
        tvCurrentAccount = (TextView) view.findViewById(R.id.current_accout);
        etAccount = (EditText) view.findViewById(R.id.id_account);
        ivAccountDel = (ImageView) view.findViewById(R.id.id_account_delete);
        etConfirmAccount = (EditText) view.findViewById(R.id.id_new_account);
        ivConfirmAccountDel = (ImageView) view.findViewById(R.id.id_new_account_delete);
        etVerifyCode = (EditText) view.findViewById(R.id.id_verify_code);
        btnGetVerifyCode = (Button) view.findViewById(R.id.id_get_verify_code);
        btnSubmit = (Button) view.findViewById(R.id.id_confirm);
        
        tvCurrentAccount.setText("当前手机号码:" + ChannelCookie.getInstance().getAccount());
        
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
        
        etConfirmAccount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    ivConfirmAccountDel.setVisibility(View.GONE);
                } else {
                    ivConfirmAccountDel.setVisibility(View.VISIBLE);
                }
            }
        });
        
        etConfirmAccount.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String str = etConfirmAccount.getText().toString();
                if (str.length() > 0) {
                    Editable etext = etConfirmAccount.getText();
                    int position = etext.length();
                    Selection.setSelection(etext, position);
                }
                if (hasFocus && str.length() > 0) {
                    ivConfirmAccountDel.setVisibility(View.VISIBLE);
                } else {
                    ivConfirmAccountDel.setVisibility(View.GONE);
                }
            }
        });
        
        ivAccountDel.setOnClickListener(this);
        ivConfirmAccountDel.setOnClickListener(this);
        btnGetVerifyCode.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ivAccountDel) {
        	etAccount.setText("");
        } else if (v == btnGetVerifyCode) {
            getVerifyCodeClick();
        } else if (v == btnSubmit) {
            onSubmitClick();
        } else if (v == ivConfirmAccountDel) {
            etConfirmAccount.setText("");
        }
    }
    
    private void onSubmitClick() {
		account = etAccount.getText().toString().trim();
		confirmAccount = etConfirmAccount.getText().toString().trim();
		verifyCode = etVerifyCode.getText().toString().trim();
		
		if (TextUtils.isEmpty(verifyCode)) {
            showShortToast("请输入您的验证码");
            return;
        }
		
		if (TextUtils.isEmpty(account)) {
			showShortToast("请输入您的手机号");
			return;
		}
		
		if (account.equals(ChannelCookie.getInstance().getAccount())) {
            showShortToast("与当前手机号一致,请重新输入");
            return;
        }
		
		if (TextUtils.isEmpty(confirmAccount)) {
            showShortToast("请确认您的手机号");
            return;
        }
		
		if (!account.equals(confirmAccount)) {
            showShortToast("手机号码不一致,请重新输入");
            return;
        }
		
		UserHttpRequest.userUpdatePhoneRequest(account, verifyCode, new UpdateUserAccountHandler(this));
	}
    
    private void getVerifyCodeClick() {
        CommonDialog dialog = new CommonDialog(getActivity(), this, "我们将发送验证码至:", ChannelCookie.getInstance().getAccount());
        dialog.showdialog();
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
        UserHttpRequest.getCodeForUpdatePhone(ChannelCookie.getInstance().getAccount(), new GetVerifyCodeHandler(this));
        
        if (timer == null) {
            timer = new GetVerifyCodeTimer(60 * 1000, 1000);
        }
        timer.start();
    }
    
    class GetVerifyCodeHandler extends HttpResponseHandlerFragment<IndependentUserAccountChangeFragment> {

        public GetVerifyCodeHandler(IndependentUserAccountChangeFragment context) {
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
    
    class UpdateUserAccountHandler extends HttpResponseHandlerFragment<IndependentUserAccountChangeFragment> {

        public UpdateUserAccountHandler(IndependentUserAccountChangeFragment context) {
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
                if (bean.getStatus() == 200) {
                    showShortToast("修改成功,重新登录");
                    ChannelCookie.getInstance().setLoginPass(false);
                    ChannelCookie.getInstance().setPassword("");
                    Intent intent = new Intent(getActivity(), ActIndependentLogin.class);
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
