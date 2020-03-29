package com.zlove.frag;

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
import com.zlove.base.util.LogUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

public class AddCustomerTraceRecordFragment extends BaseFragment implements OnClickListener {

	private EditText etContent;
	private String content;
	private Button btnSubmit;
	
	private String independentId = "";
	private String clientId = "";
	private String houseId = "";
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
			if (intent.hasExtra(IntentKey.INTENT_KEY_INDEPENDENT_ID)) {
				independentId = intent.getStringExtra(IntentKey.INTENT_KEY_INDEPENDENT_ID);
			}
			if (intent.hasExtra(IntentKey.INTENT_KEY_CLIENT_ID)) {
                clientId = intent.getStringExtra(IntentKey.INTENT_KEY_CLIENT_ID);
            }
			if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_ID)) {
				houseId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
			}
		}
		LogUtil.d("HttpClient", "independentId---" + independentId + "---clientId---" + clientId + "---houseId---" + houseId);
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("新增客户记录");
        etContent = (EditText) view.findViewById(R.id.id_customer_trace);
        
        btnSubmit = (Button) view.findViewById(R.id.id_confirm);
        btnSubmit.setOnClickListener(this);
    }

	@Override
	public void onClick(View view) {
		if (view == btnSubmit) {
			onSubmitClick();
		}
	}
	
	private void onSubmitClick() {
		content = etContent.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			showShortToast("请输入记录内容");
			return;
		}
		ClientHttpRequest.addClientTraceRequest(clientId, houseId, content, independentId, new AddTraceRecordHandler(this));
	}
	
	class AddTraceRecordHandler extends HttpResponseHandlerFragment<AddCustomerTraceRecordFragment> {

		public AddTraceRecordHandler(AddCustomerTraceRecordFragment context) {
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
			if (content != null) {
				CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
				if (bean.getStatus() == 200) {
					showShortToast("添加成功");
					Intent intent = new Intent();
					finishActivity(intent);
				} else {
					showShortToast(bean.getMessage());
				}
			}
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] content,
				Throwable error) {
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
