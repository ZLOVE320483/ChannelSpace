package com.zlove.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.bean.friend.FriendRecommendListItem;
import com.zlove.channelsaleman.R;


public class FriendDetailAdapter extends SingleDataListAdapter<FriendRecommendListItem> {

    public FriendDetailAdapter(List<FriendRecommendListItem> data, Context context) {
        super(data, context);
    }

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_friend_detail, null);
            holder.tvCustomerName = (TextView) convertView.findViewById(R.id.id_customer_name);
            holder.ivCustomerType = (ImageView) convertView.findViewById(R.id.id_customer_type);
            holder.tvCustomerPhone = (TextView) convertView.findViewById(R.id.id_customer_phone);
            holder.tvCustomerState = (TextView) convertView.findViewById(R.id.id_customer_state);
            holder.tvProjectArea = (TextView) convertView.findViewById(R.id.id_project_area);
            holder.tvProjectLayout = (TextView) convertView.findViewById(R.id.project_layout);
            holder.tvProjectPrice = (TextView) convertView.findViewById(R.id.id_project_price);
            holder.tvProjectType = (TextView) convertView.findViewById(R.id.project_type);
            holder.tvCustomerTime = (TextView) convertView.findViewById(R.id.id_customer_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        FriendRecommendListItem item = getItem(position);
        String minPrice = "0";
        String maxPrice = "不限";
        if (item != null) {
            if (!TextUtils.isEmpty(item.getIntent_price_min())) {
                minPrice = item.getIntent_price_min();
            }
            if (TextUtils.isEmpty(item.getIntent_price_max()) || item.getIntent_price_max().equals("1000")) {
                maxPrice = "不限";
            } else {
                maxPrice = item.getIntent_price_max() + "万";
            }

            holder.tvProjectPrice.setText(minPrice + "-" + maxPrice);
            
            holder.tvCustomerName.setText(item.getClient_name());
            holder.tvCustomerPhone.setText(item.getClient_phone());
            holder.tvCustomerTime.setText(item.getCreate_time());
            holder.tvProjectArea.setText(item.getIntent_location_desc());
            holder.tvProjectLayout.setText(item.getIntent_house_types_desc());
            holder.tvProjectType.setText(item.getIntent_property_types_desc());
            
            String categoryId = item.getClient_category_id();
            if (categoryId.equals("1")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_a);
            } else if (categoryId.equals("2")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_b);
            } else if (categoryId.equals("3")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_c);
            } else if (categoryId.equals("4")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_d);
            }
            
            String status = item.getStatus();
            if (status.equals("1")) {
                holder.tvCustomerState.setText("待验证");
            } else if (status.equals("2")) {
                holder.tvCustomerState.setText("无效");
            } else if (status.equals("3")) {
                holder.tvCustomerState.setText("有效");
            } else if (status.equals("4")) {
                holder.tvCustomerState.setText("待验证");
            } else if (status.equals("5")) {
                holder.tvCustomerState.setText("到访");
            } else if (status.equals("6")) {
                holder.tvCustomerState.setText("认筹");
            } else if (status.equals("7")) {
                holder.tvCustomerState.setText("成交");
            } else if (status.equals("8")) {
                holder.tvCustomerState.setText("结佣");
            } else {
                holder.tvCustomerState.setText("待验证");
            }
        }
        
        return convertView;
    }

    
    class ViewHolder {
        TextView tvCustomerName;
        ImageView ivCustomerType;
        TextView tvCustomerPhone;
        TextView tvCustomerState;
        TextView tvProjectArea;
        TextView tvProjectLayout;
        TextView tvProjectPrice;
        TextView tvProjectType;
        TextView tvCustomerTime;
    }
}
