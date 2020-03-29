package com.zlove.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.channelsaleman.R;


public class CustomerChannelAdapter extends SingleDataListAdapter<String> {

    public CustomerChannelAdapter(List<String> data, Context context) {
        super(data, context);
    }

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_channel, null);
            holder.tvBankName = (TextView) convertView.findViewById(R.id.id_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String item = getItem(position);
        if (!TextUtils.isEmpty(item)) {
            holder.tvBankName.setText(item);
        }
        return convertView;
    }
    
    class ViewHolder {
        TextView tvBankName;
    }

}
