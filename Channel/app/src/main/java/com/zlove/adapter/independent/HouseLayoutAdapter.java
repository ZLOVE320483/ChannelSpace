package com.zlove.adapter.independent;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.base.util.GImageLoader;
import com.zlove.base.util.ListUtils;
import com.zlove.bean.project.ProjectDetailImgListItem;
import com.zlove.channel.R;

public class HouseLayoutAdapter extends BaseAdapter {
	
	private List<ProjectDetailImgListItem> mData;
	private Context mContext;
	
	public HouseLayoutAdapter(Context context, List<ProjectDetailImgListItem> data) {
		this.mContext = context;
		this.mData = data;
	}

	@Override
	public int getCount() {
		if (ListUtils.isEmpty(mData)) {
			return 0;
		}
		return mData.size();
	}

	@Override
	public ProjectDetailImgListItem getItem(int position) {
		if (ListUtils.isEmpty(mData)) {
			return null;
		}
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_house_layout, null);
			holder.ivHouseLayout = (ImageView) convertView.findViewById(R.id.layout_view);
			holder.tvArea = (TextView) convertView.findViewById(R.id.area);
			holder.tvLayoutType = (TextView) convertView.findViewById(R.id.layout_type);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ProjectDetailImgListItem item = getItem(position);
		if (item != null) {
			holder.tvArea.setText(item.getArea() + "平米");
			holder.tvLayoutType.setText(item.getDesc());
			GImageLoader.getInstance().getImageLoader().displayImage(item.getImage(), holder.ivHouseLayout, GImageLoader.getInstance().getNormalOptions());
		}
		
		return convertView;
	}
	
	class ViewHolder {
		ImageView ivHouseLayout;
		TextView tvArea;
		TextView tvLayoutType;
	}

}
