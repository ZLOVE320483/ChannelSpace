package com.zlove.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zlove.base.util.UIUtil;
import com.zlove.bean.client.ClientListItem;
import com.zlove.channelsaleman.R;

import java.util.List;

public class CustomerFromSelfAdapter extends SingleDataListAdapter<ClientListItem> {

	private int type = 1;
	
    public CustomerFromSelfAdapter(List<ClientListItem> data, Context context) {
        super(data, context);
    }

    public void setType(int type) {
		this.type = type;
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
            holder.tvCustomerSaleman = (TextView) convertView.findViewById(R.id.id_customer_saleman);
            holder.tvCustomerTime = (TextView) convertView.findViewById(R.id.id_customer_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        ClientListItem item = getItem(position);
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
            
            holder.tvCustomerName.setText(item.getName());
            holder.tvCustomerPhone.setText(item.getPhone());
            holder.tvCustomerTime.setText(item.getCreate_time());
            
            if (type == 1) {
                holder.tvProjectArea.setText(item.getIntent_location_ids());
			} else if (type == 2) {
				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins(0,0,0,0);
				if (item.getFrom_type().equals("0")) {
					holder.tvProjectArea.setText("来电");
				} else {
					holder.tvProjectArea.setText("来访");
				}
				holder.tvCustomerTime.setLayoutParams(layoutParams);
			}
            
            holder.tvProjectLayout.setText(item.getHouse_types());
            holder.tvProjectType.setText(item.getProperty_types());
            holder.tvCustomerSaleman.setText(item.getBroker_name());
            
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
            
            int status = item.getStatus();
            if (status == 1) {
                holder.tvCustomerState.setVisibility(View.GONE);
            } else if (status == 2) {
                holder.tvCustomerState.setVisibility(View.GONE);
            } else if (status == 3) {
                holder.tvCustomerState.setVisibility(View.GONE);
            } else if (status == 4) {
                holder.tvCustomerState.setVisibility(View.GONE);
            } else if (status == 5) {
                holder.tvCustomerState.setText("到访");
                holder.tvCustomerState.setVisibility(View.VISIBLE);
            } else if (status == 6) {
                holder.tvCustomerState.setText("认筹");
                holder.tvCustomerState.setVisibility(View.VISIBLE);
            } else if (status == 7) {
                holder.tvCustomerState.setText("成交");
                holder.tvCustomerState.setVisibility(View.VISIBLE);
            } else if (status == 8) {
                holder.tvCustomerState.setText("结佣");
                holder.tvCustomerState.setVisibility(View.VISIBLE);
            } else {
                holder.tvCustomerState.setText("报备");
                holder.tvCustomerState.setVisibility(View.VISIBLE);
            }
            
            if (item.getIs_valided().equals("0")) {
                holder.tvCustomerState.setText("无效");
                holder.tvCustomerState.setVisibility(View.VISIBLE);
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
        TextView tvCustomerSaleman;
        TextView tvCustomerTime;
    }

}
