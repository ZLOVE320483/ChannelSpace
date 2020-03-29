package com.zlove.adapter.independent;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.adapter.common.SingleDataListAdapter;
import com.zlove.base.util.UIUtil;
import com.zlove.bean.project.ProjectItemRuleList;
import com.zlove.channel.R;


public class ProjectCooperateRuleAdapter extends SingleDataListAdapter<ProjectItemRuleList> {

    public ProjectCooperateRuleAdapter(List<ProjectItemRuleList> data, Context context) {
        super(data, context);
    }

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_project_cooperate_rule, null);
            holder.tvTime = (TextView) convertView.findViewById(R.id.id_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        ProjectItemRuleList item = getItem(position);
        if (item != null) {
            holder.tvTime.setText(UIUtil.replaceNullOrEmpty(item.getCooperate_time()));
        }
        
        return convertView;
    }
    
    class ViewHolder {
        TextView tvTime;
    }

}
