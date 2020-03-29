package com.zlove.adapter.common;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.base.util.ListUtils;
import com.zlove.bean.common.CallLogInfo;
import com.zlove.channel.R;


public class CallLogAdapter extends BaseAdapter {
    
    private Context context;
    private List<CallLogInfo> callLogInfos;
    
    public CallLogAdapter(Context context, List<CallLogInfo> callLogInfos) {
        this.context = context;
        this.callLogInfos = callLogInfos;
    }

    @Override
    public int getCount() {
        if (ListUtils.isEmpty(callLogInfos)) {
            return 0;
        }
        return callLogInfos.size();
    }

    @Override
    public Object getItem(int position) {
        if (ListUtils.isEmpty(callLogInfos)) {
            return null;
        }
        return callLogInfos.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_call_log, null);
            holder.ivType = (ImageView) convertView.findViewById(R.id.iv_type);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        CallLogInfo info = callLogInfos.get(position);
        if (info != null) {
            switch (info.getType()) {
                case 1:
                    holder.ivType.setBackgroundResource(R.drawable.ico_callout);
                    break;
                case 2:
                    holder.ivType.setBackgroundResource(R.drawable.ico_callin);
                    break;
                case 3:
                    holder.ivType.setBackgroundResource(R.drawable.ico_misscall);
                    break;
                }
            
            holder.tvName.setText(info.getName());
            holder.tvNum.setText(info.getNumber());
            holder.tvDate.setText(info.getDate());
        }
        return convertView;
    }
    
    class ViewHolder {
        ImageView ivType;
        TextView tvName;
        TextView tvNum;
        TextView tvDate;
    }

}
