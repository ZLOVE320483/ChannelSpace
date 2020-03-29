package com.zlove.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.bean.project.HouseListItem;
import com.zlove.channelsaleman.R;


public class HouseSelectAdapter extends SingleDataListAdapter<HouseListItem> {
    
    private String houseId;

    public HouseSelectAdapter(List<HouseListItem> data, Context context) {
        super(data, context);
    }
    
    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_project_change, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.name);
            holder.rbCheck = (TextView) convertView.findViewById(R.id.check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        HouseListItem item = getItem(position);
        if (item != null) {
            holder.tvName.setText(item.getName());
            if (item.getHouse_id().equals(this.houseId)) {
                holder.rbCheck.setSelected(true);
            } else {
                holder.rbCheck.setSelected(false);
            }
        }
        
        return convertView;
    }
    
    class ViewHolder {
        TextView tvName;
        TextView rbCheck;
    }

}
