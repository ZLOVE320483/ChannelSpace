package com.zlove.frag.independent;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zlove.act.independent.ActBankSelect;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.UIUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.user.BankListItem;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.UserHttpRequest;

import org.apache.http.Header;

public class IndependentBankCardAddFragment extends BaseFragment implements OnClickListener {

	private EditText etBankCardNum = null;
	private EditText etBankCardUserName = null;
	private TextView tvBankCardName = null;
	private Button btnSubmit = null;
	private View selectBankContainer = null;
	
	private String bankCardNum = "";
	private String bankCardUserName = "";
	private String bankName = "";
	private BankListItem item;

	@Override
	protected int getInflateLayout() {
		return R.layout.frag_indenpendent_add_bank_card;
	}

	@Override
	protected void setUpView(View view) {
	    
	    Intent intent = getActivity().getIntent();
	    if (intent != null && intent.hasExtra(IntentKey.INTENT_KEY_BANK_ITEM)) {
	        item = (BankListItem) intent.getExtras().get(IntentKey.INTENT_KEY_BANK_ITEM);
        }
	    
		setBackButton(view.findViewById(R.id.id_back));
		((TextView) view.findViewById(R.id.id_title)).setText("添加银行卡");
		
		etBankCardNum = (EditText) view.findViewById(R.id.id_bank_card_num);
		etBankCardUserName = (EditText) view.findViewById(R.id.id_bank_card_user_name);
		tvBankCardName = (TextView) view.findViewById(R.id.id_bank_card_name);
		btnSubmit = (Button) view.findViewById(R.id.id_confirm);
		
		selectBankContainer = view.findViewById(R.id.select_bank_container);
		selectBankContainer.setOnClickListener(this);
		
		if (item != null) {
            ((TextView) view.findViewById(R.id.id_title)).setText("编辑银行卡");
            etBankCardNum.setText(item.getBank_num());
            etBankCardUserName.setText(item.getBank_user());
            tvBankCardName.setText(item.getBank_name());
            bankName = item.getBank_name();
        }

		btnSubmit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btnSubmit) {
			onSubmitClick();
		} else if (v == selectBankContainer) {
			Intent intent = new Intent(getActivity(), ActBankSelect.class);
			startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_BANK);
		}
	}
	
	private void onSubmitClick() {
	    UIUtil.hideKeyboard(getActivity());
	    
		bankCardNum = etBankCardNum.getText().toString().trim();
		bankCardUserName = etBankCardUserName.getText().toString().trim();
		
		if (TextUtils.isEmpty(bankCardNum)) {
			showShortToast("请选择银行");
			return;
		}
		if (TextUtils.isEmpty(bankCardUserName)) {
			showShortToast("请输入开户人姓名");
			return;
		}
		UserHttpRequest.userAddBankRequest(bankCardNum, bankName, bankCardUserName, new SubmitBankCardHandler(this));
	}
	
	class SubmitBankCardHandler extends HttpResponseHandlerFragment<IndependentBankCardAddFragment> {

        public SubmitBankCardHandler(IndependentBankCardAddFragment context) {
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
                        showShortToast("提交成功");
                        getActivity().setResult(Activity.RESULT_OK);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IntentKey.REQUEST_CODE_SELECT_BANK) {
			if (data != null) {
				if (data.hasExtra(IntentKey.INTENT_KEY_BANK_NAME)) {
					bankName = data.getStringExtra(IntentKey.INTENT_KEY_BANK_NAME);
					tvBankCardName.setText(bankName);
				}
			}
		}
	}

}
