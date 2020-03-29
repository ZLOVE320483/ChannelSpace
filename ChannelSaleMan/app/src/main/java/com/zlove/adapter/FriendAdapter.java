package com.zlove.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.bean.friend.FriendListItem;
import com.zlove.channelsaleman.R;

public class FriendAdapter extends SingleDataListAdapter<FriendListItem> {

	public FriendAdapter(List<FriendListItem> data, Context context) {
		super(data, context);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_friend, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.id_friend_name);
			holder.ivFriendIntention = (ImageView) convertView.findViewById(R.id.id_friend_degree);
            holder.ivFriendCall = (ImageView) convertView.findViewById(R.id.id_friend_call);
			holder.tvReportCount = (TextView) convertView.findViewById(R.id.report_count);
			holder.tvVisitCount = (TextView) convertView.findViewById(R.id.visit_count);
			holder.tvDealCount = (TextView) convertView.findViewById(R.id.deal_count);
			holder.tvCommission = (TextView) convertView.findViewById(R.id.commission_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final FriendListItem item = getItem(position);
		if (item != null) {
			holder.tvName.setText(item.getFriend_name());
			holder.tvReportCount.setText("报备:" + item.getRec_num());
			holder.tvVisitCount.setText("到访:" + item.getVisit_num());
			holder.tvDealCount.setText("成交:" + item.getOrder_num());
			holder.tvCommission.setText("结佣:" + item.getFinish_num());
			String categoryId = item.getFriend_phone();
			if (categoryId.equals("1")) {
			    holder.ivFriendIntention.setBackgroundResource(R.drawable.customer_type_a);
            } else if (categoryId.equals("2")) {
                holder.ivFriendIntention.setBackgroundResource(R.drawable.customer_type_b);
            } else if (categoryId.equals("3")) {
                holder.ivFriendIntention.setBackgroundResource(R.drawable.customer_type_c);
            } else if (categoryId.equals("4")) {
                holder.ivFriendIntention.setBackgroundResource(R.drawable.customer_type_d);
            }
		}
		
		holder.ivFriendCall.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + item.getFriend_phone());   
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);     
                mContext.startActivity(intent);  
            }
        });
		
		return convertView;
	}
	
	class ViewHolder {
		TextView tvName;
		ImageView ivFriendIntention;
		ImageView ivFriendCall;
		TextView tvReportCount;
		TextView tvVisitCount;
		TextView tvDealCount;
		TextView tvCommission;
		
	}

}
