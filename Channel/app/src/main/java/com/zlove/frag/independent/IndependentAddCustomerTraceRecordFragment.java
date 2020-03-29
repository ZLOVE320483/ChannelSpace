package com.zlove.frag.independent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;


public class IndependentAddCustomerTraceRecordFragment extends BaseFragment implements OnClickListener {
    
    private EditText etTraceContent;
    private Button btnSubmit;
    
    private String content = "";
    private String houseId = "";
    private String clientId = "";
    private Dialog loadingDialog;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_add_customer_trace_record;
    }

    @Override
    protected void setUpView(View view) {
        loadingDialog = DialogManager.getLoadingDialog(getActivity(), "正在提交...");
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_CLIENT_ID)) {
                clientId = intent.getStringExtra(IntentKey.INTENT_KEY_CLIENT_ID);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_ID)) {
                houseId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
            }
        }
        
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("新增客户记录");
        
        etTraceContent = (EditText) view.findViewById(R.id.id_customer_trace);
        btnSubmit = (Button) view.findViewById(R.id.id_confirm);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            content = etTraceContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                showShortToast("请输入客户记录");
                return;
            }
            ClientHttpRequest.addClientTraceRequest(clientId, houseId, content, new AddClientTraceHandlder(this));
        }
    }
    
    class AddClientTraceHandlder extends HttpResponseHandlerFragment<IndependentAddCustomerTraceRecordFragment> {

        public AddClientTraceHandlder(IndependentAddCustomerTraceRecordFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
            if (loadingDialog != null && !loadingDialog.isShowing()) {
                loadingDialog.show();
            }
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
                        showShortToast("提交成功");
                        getActivity().setResult(Activity.RESULT_OK);
                        finishActivity();
                    } else {
                        showShortToast(bean.getMessage());
                    }
                } else {
                    showShortToast("提交失败,请重试");
                }
            } else {
                showShortToast("提交失败,请重试");
            }
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }
}
