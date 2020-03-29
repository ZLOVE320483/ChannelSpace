package com.zlove.adapter.message;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.adapter.common.SingleDataListAdapter;
import com.zlove.base.util.UIUtil;
import com.zlove.bean.message.MessageProjectDynamicListItem;
import com.zlove.channel.R;

public class MessageProjectDynamicAdapter extends SingleDataListAdapter<MessageProjectDynamicListItem> {

	public MessageProjectDynamicAdapter(
			List<MessageProjectDynamicListItem> data, Context context) {
		super(data, context);
	}

	@SuppressLint("InflateParams") 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_message_project_dynamic, null);
			holder.projectName = (TextView) convertView.findViewById(R.id.id_project_name);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.id_project_dynamic_theme);
			holder.tvTime = (TextView) convertView.findViewById(R.id.id_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		MessageProjectDynamicListItem item = getItem(position);
		if (item != null) {
		    holder.tvTitle.setText(item.getExtra().getTitle());
			holder.projectName.setText(item.getExtra().getHouse_name());
			holder.tvTime.setText(item.getExtra().getSend_time());
			String readStatus = item.getStatus();
			if (readStatus.equals("0")) {
                holder.tvTitle.setTextColor(UIUtil.getResColor(R.color.common_text_black_2));
                holder.projectName.setTextColor(UIUtil.getResColor(R.color.common_text_green));
                holder.tvTime.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            } else if (readStatus.equals("1")) {
                holder.tvTitle.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
                holder.projectName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
                holder.tvTime.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            }

		}
		
		return convertView;
	}

	class ViewHolder {
		TextView projectName;
		TextView tvTitle;
		TextView tvTime;
	}
}
