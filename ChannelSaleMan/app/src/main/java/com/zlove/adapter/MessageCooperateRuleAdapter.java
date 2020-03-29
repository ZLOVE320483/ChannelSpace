package com.zlove.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.base.util.UIUtil;
import com.zlove.bean.message.MessageCooperateRuleListItem;
import com.zlove.channelsaleman.R;

public class MessageCooperateRuleAdapter extends SingleDataListAdapter<MessageCooperateRuleListItem> {

	public MessageCooperateRuleAdapter(List<MessageCooperateRuleListItem> data, Context context) {
		super(data, context);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_cooperate_rule, null);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.id_rule_name);
			holder.tvProjectName = (TextView) convertView.findViewById(R.id.id_project_name);
			holder.tvTime = (TextView) convertView.findViewById(R.id.id_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		MessageCooperateRuleListItem item = getItem(position);
		if (item != null) {
			holder.tvTitle.setText(item.getExtra().getTitle());
			holder.tvProjectName.setText(item.getExtra().getHouse_name());
			holder.tvTime.setText(item.getExtra().getSend_time());
			
			String readStatus = item.getStatus();
			if (readStatus.equals("0")) {
            	holder.tvTitle.setTextColor(UIUtil.getResColor(R.color.common_text_black_2));
            	holder.tvProjectName.setTextColor(UIUtil.getResColor(R.color.common_text_green));
            	holder.tvTime.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            } else if (readStatus.equals("1")) {
            	holder.tvTitle.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            	holder.tvProjectName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            	holder.tvTime.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            }

		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView tvTitle;
		TextView tvProjectName;
		TextView tvTime;
	}

}
