package com.zlove.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.base.util.UIUtil;
import com.zlove.bean.message.MessageContactCustomerListItem;
import com.zlove.channelsaleman.R;

import java.util.List;

public class MessageContactCustomerAdapter extends SingleDataListAdapter<MessageContactCustomerListItem> {

	public MessageContactCustomerAdapter(List<MessageContactCustomerListItem> data, Context context) {
		super(data, context);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_contact_customer, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.id_customer_name);
			holder.ivCustomerIntention = (ImageView) convertView.findViewById(R.id.id_customer_intention);
			holder.ivFriendMessage = (ImageView) convertView.findViewById(R.id.id_friend_message);
			holder.ivFriendCall = (ImageView) convertView.findViewById(R.id.id_friend_call);
			holder.tvBackTime = (TextView) convertView.findViewById(R.id.id_time);
			holder.tvIndependentMan = (TextView) convertView.findViewById(R.id.id_independent_name);
			holder.tvHouseName = (TextView) convertView.findViewById(R.id.house_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MessageContactCustomerListItem item = getItem(position);
		if (item != null) {
			holder.tvName.setText(item.getExtra().getClient_name());
			holder.tvBackTime.setText("回访时间:" + item.getExtra().getRevisit_time());
			if (!TextUtils.isEmpty(item.getExtra().getHouse_name())) {
				holder.tvHouseName.setText("[" + item.getExtra().getHouse_name() + "]");
			}
			if (!TextUtils.isEmpty(item.getExtra().getBroker_name())) {
				holder.tvIndependentMan.setVisibility(View.VISIBLE);
				holder.tvIndependentMan.setText("经纪人:" + item.getExtra().getBroker_name());
			} else {
				holder.tvIndependentMan.setVisibility(View.GONE);
			}
			String categoryId = item.getExtra().getCategory_id();
			if (categoryId.equals("1")) {
                holder.ivCustomerIntention.setBackgroundResource(R.drawable.customer_type_a);
            } else if (categoryId.equals("2")) {
                holder.ivCustomerIntention.setBackgroundResource(R.drawable.customer_type_b);
            } else if (categoryId.equals("3")) {
                holder.ivCustomerIntention.setBackgroundResource(R.drawable.customer_type_c);
            } else if (categoryId.equals("4")) {
                holder.ivCustomerIntention.setBackgroundResource(R.drawable.customer_type_d);
            }
			String readStatus = item.getStatus();
			if (readStatus.equals("0")) {
				holder.tvName.setTextColor(UIUtil.getResColor(R.color.common_text_black_2));
				holder.tvBackTime.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
				holder.tvIndependentMan.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
			} else if (readStatus.equals("1")) {
				holder.tvName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
				holder.tvBackTime.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
				holder.tvIndependentMan.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
			}

		}
		
		final String phone = item.getExtra().getClient_phone();
		
		holder.ivFriendMessage.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:" + phone);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(sendIntent);
            }
        });
		
		holder.ivFriendCall.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + phone);   
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);     
                mContext.startActivity(intent);  
            
            
            }
        });
		
		return convertView;
	}
	
	class ViewHolder {
		TextView tvName;
		ImageView ivCustomerIntention;
		ImageView ivFriendMessage;
		ImageView ivFriendCall;
		TextView tvBackTime;
		TextView tvHouseName;
		TextView tvIndependentMan;
	}

}
