package com.zlove.adapter.message;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.adapter.common.SingleDataListAdapter;
import com.zlove.base.util.UIUtil;
import com.zlove.bean.message.MessageCustomerTraceExtra;
import com.zlove.bean.message.MessageCustomerTraceListItem;
import com.zlove.channel.R;

public class MessageCustomerTraceAdapter extends SingleDataListAdapter<MessageCustomerTraceListItem> {

	public MessageCustomerTraceAdapter(List<MessageCustomerTraceListItem> data, Context context) {
		super(data, context);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_trace, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.id_customer_name);
			holder.ivIntentionIcon = (ImageView) convertView.findViewById(R.id.id_customer_intention);
			holder.tvPhone = (TextView) convertView.findViewById(R.id.id_customer_phone);
			holder.tvState = (TextView) convertView.findViewById(R.id.id_customer_state);
			holder.tvTip = (TextView) convertView.findViewById(R.id.id_customer_trace_tip);
			holder.tvTime = (TextView) convertView.findViewById(R.id.id_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		MessageCustomerTraceListItem item = getItem(position);
		if (item != null) {
		    MessageCustomerTraceExtra extra = item.getExtra();
		    if (extra != null) {
                if (extra.getClient_name().length() < 6) {
                    holder.tvName.setText(extra.getClient_name());
                } else {
                    holder.tvName.setText(extra.getClient_name().substring(0, 6) + "...");
                }
	            holder.tvPhone.setText(extra.getClient_phone());
                holder.tvState.setText(extra.getStatus_desc());
                holder.tvTip.setText(extra.getSalesman() + ":" + extra.getContent());
                holder.tvTime.setText(extra.getSend_time());
            }
		    String categoryId = extra.getCategory_id();
		    if (categoryId.equals("1")) {
                holder.ivIntentionIcon.setBackgroundResource(R.drawable.customer_type_a);
            } else if (categoryId.equals("2")) {
                holder.ivIntentionIcon.setBackgroundResource(R.drawable.customer_type_b);
            } else if (categoryId.equals("3")) {
                holder.ivIntentionIcon.setBackgroundResource(R.drawable.customer_type_c);
            } else if (categoryId.equals("4")) {
                holder.ivIntentionIcon.setBackgroundResource(R.drawable.customer_type_d);
            }
		    
		    String readStatus = item.getStatus();
		    if (readStatus.equals("0")) {
                holder.tvName.setTextColor(UIUtil.getResColor(R.color.common_text_black_2));
                holder.tvPhone.setTextColor(UIUtil.getResColor(R.color.common_text_black_3));
                holder.tvTip.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
                holder.tvTime.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            } else if (readStatus.equals("1")) {
                holder.tvName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
                holder.tvPhone.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
                holder.tvTip.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
                holder.tvTime.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            }

		}
		return convertView;
	}
	
	class ViewHolder {
		TextView tvName;
		ImageView ivIntentionIcon;
		TextView tvPhone;
		TextView tvState;
		TextView tvTip;
		TextView tvTime;
	}

}
