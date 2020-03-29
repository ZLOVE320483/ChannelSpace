package com.zlove.adapter.independent;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.adapter.common.SingleDataListAdapter;
import com.zlove.bean.friend.FriendListItem;
import com.zlove.channel.R;


public class FriendAdapter extends SingleDataListAdapter<FriendListItem> {

    
    public FriendAdapter(List<FriendListItem> data, Context context) {
        super(data, context);
    }

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_friend, null);
            holder.tvFriendName = (TextView) convertView.findViewById(R.id.id_friend_name);
            holder.tvFriendPhone = (TextView) convertView.findViewById(R.id.id_friend_phone);
            holder.ivFriendMessage = (ImageView) convertView.findViewById(R.id.id_friend_message);
            holder.ivFriendCall = (ImageView) convertView.findViewById(R.id.id_friend_call);
            holder.tvFriendAddress = (TextView) convertView.findViewById(R.id.id_friend_address);
            holder.tvFriendReport = (TextView) convertView.findViewById(R.id.id_friend_report);
            holder.tvFriendVisit = (TextView) convertView.findViewById(R.id.id_friend_visit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        final FriendListItem item = getItem(position);
        if (item != null) {
            if (item.getFriend_name().length() < 6) {
                holder.tvFriendName.setText(item.getFriend_name());
            } else {
                holder.tvFriendName.setText(item.getFriend_name().substring(0, 6) + "...");
            }
            holder.tvFriendPhone.setText(item.getFriend_phone());
            holder.tvFriendReport.setText(String.valueOf(item.getRec_num()));
            holder.tvFriendVisit.setText(String.valueOf(item.getVisit_num()));
            holder.tvFriendAddress.setText(item.getHouse_names());
        }
        
        holder.ivFriendMessage.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:" + item.getFriend_phone());
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(sendIntent);
            }
        });
        
        holder.ivFriendCall.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + item.getFriend_phone());   
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);     
                mContext.startActivity(intent);  
            }
        });
        
        return convertView;
    }

    class ViewHolder {
        TextView tvFriendName;
        TextView tvFriendPhone;
        ImageView ivFriendMessage;
        ImageView ivFriendCall;
        TextView tvFriendAddress;
        TextView tvFriendReport;
        TextView tvFriendVisit;
    }
    
}
