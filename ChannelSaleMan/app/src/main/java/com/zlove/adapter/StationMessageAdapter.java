package com.zlove.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.bean.app.SelfMesaageListItem;
import com.zlove.channelsaleman.R;


public class StationMessageAdapter extends SingleDataListAdapter<SelfMesaageListItem> {

    public StationMessageAdapter(List<SelfMesaageListItem> data, Context context) {
        super(data, context);
    }

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_station_message, null);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.icon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.id_title);
            holder.tvTime = (TextView) convertView.findViewById(R.id.id_time);
            holder.tvContent = (TextView) convertView.findViewById(R.id.id_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SelfMesaageListItem item = getItem(position);
        if (item != null) {
            holder.tvTitle.setText(item.getExtra().getTitle());
            holder.tvContent.setText(item.getExtra().getSummary());
            holder.tvTime.setText(item.getExtra().getSend_time());
            String type = item.getType();
            if (type.equals("1")) {
                holder.ivIcon.setImageResource(R.drawable.ic_state_msg_system);
            } else if (type.equals("2")) {
                holder.ivIcon.setImageResource(R.drawable.ic_state_msg_project);
            } else if (type.equals("3")) {
                holder.ivIcon.setImageResource(R.drawable.ic_state_msg_dynamic);
            } else if (type.equals("4")) {
                holder.ivIcon.setImageResource(R.drawable.ic_state_msg_service);
            }
        }
        return convertView;
    }
    
    class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvTime;
        TextView tvContent;
    }

}
