package com.zlove.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.base.util.UIUtil;
import com.zlove.bean.message.MessageNewCustomerListItem;
import com.zlove.bean.message.MessageNewCustomerListItemExtra;
import com.zlove.channelsaleman.R;

import java.util.List;

public class MessageNewCustomerAdapter extends SingleDataListAdapter<MessageNewCustomerListItem> {

	public MessageNewCustomerAdapter(List<MessageNewCustomerListItem> data, Context context) {
		super(data, context);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_new_customer, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.id_customer_name);
			holder.ivCustomerIntention = (ImageView) convertView.findViewById(R.id.id_customer_intention);
			holder.tvPhone = (TextView) convertView.findViewById(R.id.id_customer_phone);
			holder.tvProjectArea = (TextView) convertView.findViewById(R.id.id_project_area);
			holder.tvProjectPrice = (TextView) convertView.findViewById(R.id.id_project_price);
			holder.tvProjectLayout = (TextView) convertView.findViewById(R.id.id_project_house_layout);
			holder.tvProjectType = (TextView) convertView.findViewById(R.id.id_project_product);
			holder.tvIndependentName = (TextView) convertView.findViewById(R.id.id_independent_name);
			holder.tvHouseName = (TextView) convertView.findViewById(R.id.house_name);
			holder.tvTime = (TextView) convertView.findViewById(R.id.id_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		MessageNewCustomerListItem item = getItem(position);
		if (item != null) {
			MessageNewCustomerListItemExtra extra = item.getExtra();
			holder.tvName.setText(extra.getClient_name());
			holder.tvPhone.setText(extra.getClient_phone());
			holder.tvProjectArea.setText(extra.getIntent_locations_desc());
			holder.tvProjectLayout.setText(extra.getHouse_type_desc());
			holder.tvIndependentName.setText(extra.getBroker_name());
			holder.tvHouseName.setText("[" + extra.getHouse_name() + "]");

			String minPrice = "0";
			String maxPrice = "不限";
			if (!TextUtils.isEmpty(extra.getIntent_price_min())) {
				minPrice = extra.getIntent_price_min();
			}
			if (TextUtils.isEmpty(extra.getIntent_price_max())
					|| extra.getIntent_price_max().equals("1000")) {
				maxPrice = "不限";
			} else {
				maxPrice = extra.getIntent_price_max() + "万";
			}

			holder.tvProjectPrice.setText(minPrice + "-" + maxPrice);
			
			holder.tvProjectType.setText(extra.getIntent_property_desc());
				
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
			holder.tvTime.setText(extra.getSend_time());
			
			String readStatus = item.getStatus();
			if (readStatus.equals("0")) {
    			holder.tvName.setTextColor(UIUtil.getResColor(R.color.common_text_black_2));
    			holder.tvPhone.setTextColor(UIUtil.getResColor(R.color.common_text_black_3));
    			holder.tvProjectArea.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
    			holder.tvProjectLayout.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
    			holder.tvIndependentName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            } else if (readStatus.equals("1")) {
    			holder.tvName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
    			holder.tvPhone.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
    			holder.tvProjectArea.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
    			holder.tvProjectLayout.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
    			holder.tvIndependentName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            }
		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView tvName;
		ImageView ivCustomerIntention;
		TextView tvPhone;
		TextView tvProjectArea;
		TextView tvProjectPrice;
		TextView tvProjectLayout;
		TextView tvProjectType;
		TextView tvIndependentName;
		TextView tvHouseName;
		TextView tvTime;
	}

}
