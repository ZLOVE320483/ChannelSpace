package com.zlove.frag.independent;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zlove.act.independent.ActIndependentBankCardAdd;
import com.zlove.adapter.independent.BankAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.bean.user.BankBean;
import com.zlove.bean.user.BankListItem;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.UserHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class IndependentBankCardFragment extends BaseFragment implements OnClickListener, OnItemClickListener {

    private Button btnEdit = null;
    private ListView listView;
    private List<BankListItem> list = new ArrayList<BankListItem>();
    private BankListItem data;
    private BankAdapter adapter;
    
    private View noBankContainer;
    private Button btnAddBankCard;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_bank_card;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("我的银行卡");
        listView = (ListView) view.findViewById(R.id.id_listview);
        noBankContainer = view.findViewById(R.id.no_bank_container);
        
        listView.setVisibility(View.VISIBLE);
        noBankContainer.setVisibility(View.GONE);
        
        btnAddBankCard = (Button) view.findViewById(R.id.btn_add_bank);
        btnAddBankCard.setOnClickListener(this);
        
        btnEdit = (Button) view.findViewById(R.id.id_confirm);
        btnEdit.setOnClickListener(this);
        btnEdit.setText("编辑");
        btnEdit.setVisibility(View.GONE);
        
        if (adapter == null) {
			adapter = new BankAdapter(list, getActivity());
		}
        listView.setAdapter(adapter);
		UserHttpRequest.userGetBankRequest(new GetUserBankCardHandler(this));
    }
    
    @Override
    public void onClick(View v) {
        if (v == btnEdit) {
            Intent intent = new Intent(getActivity(), ActIndependentBankCardAdd.class);
            intent.putExtra(IntentKey.INTENT_KEY_BANK_ITEM, data);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_ADD_BANK);
        } else if (v == btnAddBankCard) {
            Intent intent = new Intent(getActivity(), ActIndependentBankCardAdd.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_ADD_BANK);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == IntentKey.REQUEST_CODE_ADD_BANK) {
			UserHttpRequest.userGetBankRequest(new GetUserBankCardHandler(this));
		}
    }
    
    class GetUserBankCardHandler extends HttpResponseHandlerFragment<IndependentBankCardFragment> {

		public GetUserBankCardHandler(IndependentBankCardFragment context) {
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
				BankBean bean = JsonUtil.toObject(new String(content), BankBean.class);
				if (bean != null) {
				    data = bean.getData();
					if (data != null) {
					    if (TextUtils.isEmpty(data.getBank_name()) || TextUtils.isEmpty(data.getBank_num()) || TextUtils.isEmpty(data.getBank_user())) {
		                    listView.setVisibility(View.GONE);
		                    noBankContainer.setVisibility(View.VISIBLE);
		                    return;
                        }
						list.clear();
						list.add(data);
						btnEdit.setVisibility(View.VISIBLE);
					}
				}
				if (!ListUtils.isEmpty(list)) {
					listView.setVisibility(View.VISIBLE);
					noBankContainer.setVisibility(View.GONE);
					adapter.notifyDataSetChanged();
				} else {
					listView.setVisibility(View.GONE);
					noBankContainer.setVisibility(View.VISIBLE);
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
		}
    	
    }
}
