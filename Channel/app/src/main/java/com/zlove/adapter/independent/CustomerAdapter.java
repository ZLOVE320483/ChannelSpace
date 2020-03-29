package com.zlove.adapter.independent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.adapter.common.SingleDataListAdapter;
import com.zlove.base.util.UIUtil;
import com.zlove.bean.client.ClientListItem;
import com.zlove.channel.R;

import java.util.List;


public class CustomerAdapter extends SingleDataListAdapter<ClientListItem> {

    public CustomerAdapter(List<ClientListItem> data, Context context) {
        super(data, context);
    }


    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer, null);
            holder.tvCustomerName = (TextView) convertView.findViewById(R.id.id_customer_name);
            holder.ivCustomerType = (ImageView) convertView.findViewById(R.id.id_customer_type);
            holder.tvCustomerPhone = (TextView) convertView.findViewById(R.id.id_customer_phone);
            holder.tvCustomerState = (TextView) convertView.findViewById(R.id.id_customer_state);
            holder.tvProjectArea = (TextView) convertView.findViewById(R.id.id_project_area);
            holder.tvProjectLayout = (TextView) convertView.findViewById(R.id.project_layout);
            holder.tvProjectPrice = (TextView) convertView.findViewById(R.id.id_project_price);
            holder.tvProjectType = (TextView) convertView.findViewById(R.id.project_type);
            holder.tvProjectName = (TextView) convertView.findViewById(R.id.id_projetc_name);
            holder.tvCustomerSaleman = (TextView) convertView.findViewById(R.id.id_customer_saleman);
            holder.tvCustomerTime = (TextView) convertView.findViewById(R.id.id_customer_time);
            holder.ivHasOverdue = (ImageView) convertView.findViewById(R.id.has_over_due);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        ClientListItem item = getItem(position);
        String minPrice = "0";
        String maxPrice = "不限";
        if (item != null) {
            if (item.getIs_disabled() == 1) {
                holder.ivHasOverdue.setVisibility(View.VISIBLE);
            } else {
                holder.ivHasOverdue.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(item.getIntent_price_min())) {
                minPrice = item.getIntent_price_min();
            }
            if (TextUtils.isEmpty(item.getIntent_price_max()) || item.getIntent_price_max().equals("1000")) {
                maxPrice = "不限";
            } else {
                maxPrice = item.getIntent_price_max() + "万";
            }
            holder.tvCustomerName.setText(item.getName());
            holder.tvCustomerPhone.setText(item.getPhone());
            holder.tvCustomerTime.setText(item.getCreate_time());
            holder.tvProjectArea.setText(item.getIntent_location_ids());
            holder.tvProjectLayout.setText(item.getHouse_types());
            holder.tvProjectType.setText(item.getProperty_types());
            holder.tvProjectPrice.setText(minPrice + "-" + maxPrice);
            if (TextUtils.isEmpty(item.getHouse_name())) {
                holder.tvProjectName.setText("未报备楼盘");
                holder.tvCustomerState.setVisibility(View.GONE);
            } else {
                holder.tvProjectName.setText(item.getHouse_name());
                holder.tvCustomerState.setVisibility(View.VISIBLE);
            }
            holder.tvCustomerSaleman.setText(item.getSalesman());
            
            String categoryId = item.getCategory_id();
            if (categoryId.equals("1")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_a);
                holder.tvCustomerState.setTextColor(UIUtil.getResColor(R.color.common_bg_top_bar));
                holder.tvCustomerState.setBackgroundResource(R.drawable.common_round_rect_bg);
            } else if (categoryId.equals("2")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_b);
                holder.tvCustomerState.setTextColor(UIUtil.getResColor(R.color.common_bg_top_bar));
                holder.tvCustomerState.setBackgroundResource(R.drawable.common_round_rect_bg);
            } else if (categoryId.equals("3")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_c);
                holder.tvCustomerState.setTextColor(UIUtil.getResColor(R.color.common_bg_top_bar));
                holder.tvCustomerState.setBackgroundResource(R.drawable.common_round_rect_bg);
            } else if (categoryId.equals("4")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_d);
                holder.tvCustomerState.setTextColor(UIUtil.getResColor(R.color.common_text_black_6));
                holder.tvCustomerState.setBackgroundResource(R.drawable.common_round_rect_grey_bg);
            }
            
            String status = item.getStatus();
            if (status.equals("1")) {
                holder.tvCustomerState.setText("待验证");
                holder.tvCustomerState.setVisibility(View.GONE);
            } else if (status.equals("2")) {
                holder.tvCustomerState.setText("无效");
            } else if (status.equals("3")) {
                holder.tvCustomerState.setText("有效");
            } else if (status.equals("4")) {
                holder.tvCustomerState.setText("待验证");
                holder.tvCustomerState.setVisibility(View.GONE);
			} else if (status.equals("5")) {
                holder.tvCustomerState.setText("到访");
            } else if (status.equals("6")) {
                holder.tvCustomerState.setText("认筹");
            } else if (status.equals("7")) {
                holder.tvCustomerState.setText("成交");
            } else if (status.equals("8")) {
                holder.tvCustomerState.setText("结佣");
            } else {
                holder.tvCustomerState.setVisibility(View.GONE);
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
        TextView tvProjectName;
        TextView tvCustomerSaleman;
        TextView tvCustomerTime;
        ImageView ivHasOverdue;
    }

}
