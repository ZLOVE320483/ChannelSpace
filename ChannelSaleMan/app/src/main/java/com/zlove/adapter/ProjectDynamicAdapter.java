package com.zlove.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.bean.project.ProjectNewsListItem;
import com.zlove.channelsaleman.R;

public class ProjectDynamicAdapter extends SingleDataListAdapter<ProjectNewsListItem> {

	public ProjectDynamicAdapter(List<ProjectNewsListItem> data, Context context) {
		super(data, context);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_project_dynamic, null);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.id_project_dynamic_theme);
			holder.tvTime = (TextView) convertView.findViewById(R.id.id_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ProjectNewsListItem item = getItem(position);
		if (item != null) {
			holder.tvTitle.setText(item.getTitle());
			holder.tvTime.setText(item.getCreate_time());
		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView tvTitle;
		TextView tvTime;
	}
}
