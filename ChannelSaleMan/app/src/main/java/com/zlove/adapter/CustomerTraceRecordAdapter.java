package com.zlove.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.bean.client.ClientTraceListItem;
import com.zlove.channelsaleman.R;

public class CustomerTraceRecordAdapter extends SingleDataListAdapter<ClientTraceListItem> {

	public CustomerTraceRecordAdapter(List<ClientTraceListItem> data, Context context) {
		super(data, context);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_trace_record, null);
			
			holder.tvName = (TextView) convertView.findViewById(R.id.id_user_name);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_record);
			holder.tvTime = (TextView) convertView.findViewById(R.id.id_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		 ClientTraceListItem item = getItem(position);
	        if (item != null) {
	            holder.tvName.setText(item.getUser());
	            holder.tvContent.setText(item.getContent());
	            holder.tvTime.setText(item.getTrace_time());
	        }
		
		return convertView;
	}
	
	class ViewHolder {
		TextView tvName;
		TextView tvContent;
		TextView tvTime;
	}

}
