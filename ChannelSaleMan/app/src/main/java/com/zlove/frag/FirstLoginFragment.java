package com.zlove.frag;

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

import com.zlove.act.ActPwdSet;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.widget.CommonDialog;
import com.zlove.base.widget.CommonDialog.ConfirmAction;
import com.zlove.bean.common.CommonBean;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.UserHttpRequest;


public class FirstLoginFragment extends BaseFragment implements OnClickListener, ConfirmAction {

    private EditText etAccount;
    private ImageView ivAccountDel;
    private EditText etVerifyCode;
    
    private Button btnNext = null;
    private Button btnGetVerifyCode = null;
    private GetVerifyCodeTimer timer;
    
    private String account = "";
    private String verifyCode = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_first_login;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("首次登录");
        
        etAccount = (EditText) view.findViewById(R.id.id_account);
        ivAccountDel = (ImageView) view.findViewById(R.id.id_account_delete);
        etVerifyCode = (EditText) view.findViewById(R.id.id_verify_code);
        
        btnGetVerifyCode = (Button) view.findViewById(R.id.id_get_verify_code);
        btnNext = (Button) view.findViewById(R.id.id_confirm);
        
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

        ivAccountDel.setOnClickListener(this);

        btnNext.setOnClickListener(this);
        btnGetVerifyCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnNext) {
            onNextClick();
        } else if (v == btnGetVerifyCode) {
            getVerifyCodeClick();
		} else if (v == ivAccountDel) {
            etAccount.setText("");
        }
    }
    
    private void onNextClick() {
        account = etAccount.getText().toString().trim();
        verifyCode = etVerifyCode.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            showShortToast("请输入您的手机号");
            return;
        }
        if (TextUtils.isEmpty(verifyCode)) {
            showShortToast("请输入验证码");
            return;
        }
        
        Intent intent = new Intent(getActivity(), ActPwdSet.class);
        intent.putExtra(IntentKey.INTENT_KEY_USER_PHONE, account);
        intent.putExtra(IntentKey.INTENT_KEY_VERIFY_CODE, verifyCode);
        startActivity(intent);
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
        UserHttpRequest.firstLoginBoundPhone(account, new GetVerifyCodeHandler(this));
        if (timer == null) {
            timer = new GetVerifyCodeTimer(60 * 1000, 1000);
        }
        timer.start();
    }

    class GetVerifyCodeHandler extends HttpResponseHandlerFragment<FirstLoginFragment> {

        public GetVerifyCodeHandler(FirstLoginFragment context) {
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
