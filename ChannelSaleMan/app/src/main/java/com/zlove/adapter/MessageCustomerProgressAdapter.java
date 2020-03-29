package com.zlove.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.base.util.UIUtil;
import com.zlove.bean.message.MessageCustomerProgressListItem;
import com.zlove.bean.message.MessageCustomerProgressListItemExtra;
import com.zlove.channelsaleman.R;

import java.util.List;

public class MessageCustomerProgressAdapter extends SingleDataListAdapter<MessageCustomerProgressListItem> {

    public MessageCustomerProgressAdapter(List<MessageCustomerProgressListItem> data, Context context) {
        super(data, context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_progress, null);
            holder.tvState = (TextView) convertView.findViewById(R.id.id_customer_state);
            holder.tvHouseName = (TextView) convertView.findViewById(R.id.house_name);
            holder.tvSaleMan = (TextView) convertView.findViewById(R.id.tv_sale_man);
            holder.tvName = (TextView) convertView.findViewById(R.id.id_customer_name);
            holder.tvStatusIcon = (TextView) convertView.findViewById(R.id.id_state);
            holder.tvTip = (TextView) convertView.findViewById(R.id.id_visit_tip);
            holder.tvTime = (TextView) convertView.findViewById(R.id.id_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        MessageCustomerProgressListItem item = getItem(position);
        if (item != null) {
            MessageCustomerProgressListItemExtra extra = item.getExtra();
            if (extra != null) {
                holder.tvState.setText(extra.getStatus_desc() + "——");
                holder.tvName.setText(extra.getClient_name());
                if (!TextUtils.isEmpty(extra.getHouse_name())) {
                    holder.tvHouseName.setText("[" + extra.getHouse_name() + "]");
                }
                holder.tvSaleMan.setText(extra.getBrokername() + ":");
                holder.tvTip.setText(extra.getContent());
                holder.tvTime.setText(extra.getSend_time());
            }
            
            String readStatus = item.getStatus();
            if (readStatus.equals("0")) {
            	holder.tvState.setTextColor(UIUtil.getResColor(R.color.common_text_black_2));
            	holder.tvSaleMan.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            	holder.tvName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            	holder.tvTip.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
                holder.tvHouseName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            	holder.tvTime.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            } else if (readStatus.equals("1")) {
            	holder.tvState.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            	holder.tvSaleMan.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            	holder.tvName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            	holder.tvTip.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
                holder.tvHouseName.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            	holder.tvTime.setTextColor(UIUtil.getResColor(R.color.common_text_black_4));
            }

        }
        
        return convertView;
    }
    
    class ViewHolder {
        TextView tvState;
        TextView tvName;
        TextView tvHouseName;
        TextView tvSaleMan;
        TextView tvStatusIcon;
        TextView tvTip;
        TextView tvTime;
    }

}
