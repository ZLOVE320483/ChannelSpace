package com.zlove.adapter.independent;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.adapter.common.SingleDataListAdapter;
import com.zlove.bean.user.BankListItem;
import com.zlove.channel.R;

public class BankAdapter extends SingleDataListAdapter<BankListItem> {

	public BankAdapter(List<BankListItem> data, Context context) {
		super(data, context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_bank, null);
			holder.tvBankName = (TextView) convertView.findViewById(R.id.bank_name);
			holder.tvBankNum = (TextView) convertView.findViewById(R.id.bank_num);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		BankListItem item = getItem(position);
		if (item != null) {
			holder.tvBankName.setText(item.getBank_name());
			holder.tvBankNum.setText(item.getBank_num());
		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView tvBankName;
		TextView tvBankNum;
	}

}
