package com.zlove.adapter.independent;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.adapter.common.SingleDataListAdapter;
import com.zlove.bean.client.ClientSelectListItem;
import com.zlove.channel.R;


public class ProjectCustomerAddAdapter extends SingleDataListAdapter<ClientSelectListItem> {

    public ProjectCustomerAddAdapter(List<ClientSelectListItem> data, Context context) {
        super(data, context);
    }

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_project_customer_add, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.id_name);
            holder.tvPhoneNum = (TextView) convertView.findViewById(R.id.id_telephone);
            holder.tvState = (TextView) convertView.findViewById(R.id.id_customer_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        ClientSelectListItem item = mData.get(position);
        if (item != null) {
            if (item.getName().length() < 6) {
                holder.tvName.setText(item.getName());
            } else {
                holder.tvName.setText(item.getName().substring(0, 6) + "...");
            }
            holder.tvPhoneNum.setText(item.getPhone());
            if (item.getIs_recommended() == 1) {
                holder.tvState.setVisibility(View.VISIBLE);
            } else {
                holder.tvState.setVisibility(View.GONE);
            }
        }
        
        return convertView;
    }
    
    class ViewHolder {
        TextView tvName;
        TextView tvPhoneNum;
        TextView tvState;
    }

}
