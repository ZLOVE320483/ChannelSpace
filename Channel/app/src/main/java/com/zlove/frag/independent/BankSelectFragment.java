package com.zlove.frag.independent;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.zlove.adapter.independent.BankSelectAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;

import java.util.ArrayList;
import java.util.List;

public class BankSelectFragment extends BaseFragment implements OnItemClickListener {

	private ListView listView;
	private BankSelectAdapter adapter;
	private List<String> bankNames;
	
	@Override
	protected int getInflateLayout() {
		return R.layout.common_frag_listview_with_top;
	}

	@Override
	protected void setUpView(View view) {
		setBackButton(view.findViewById(R.id.id_back));
		((TextView) view.findViewById(R.id.id_title)).setText("选择银行卡");
		listView = (ListView) view.findViewById(R.id.id_listview);
		listView.setOnItemClickListener(this);
		
		bankNames = new ArrayList<String>();
		bankNames.add("中国银行");
		bankNames.add("中国建设银行");
		bankNames.add("中国工商银行");
		bankNames.add("中国农业银行");
		bankNames.add("中国交通银行");
		
		adapter = new BankSelectAdapter(bankNames, getActivity());
		listView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent data = new Intent();
		data.putExtra(IntentKey.INTENT_KEY_BANK_NAME, bankNames.get(arg2));
		finishActivity(data);
	}

}
