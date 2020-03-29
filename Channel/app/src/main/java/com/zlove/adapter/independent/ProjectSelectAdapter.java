package com.zlove.adapter.independent;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlove.adapter.common.SingleDataListAdapter;
import com.zlove.bean.project.ProjectSelectListItem;
import com.zlove.channel.R;


public class ProjectSelectAdapter extends SingleDataListAdapter<ProjectSelectListItem> {

	private boolean isFromAddFriend = false;	
	
    public ProjectSelectAdapter(List<ProjectSelectListItem> data, Context context) {
        super(data, context);
    }

    public void setFromAddFriend(boolean isFromAddFriend) {
		this.isFromAddFriend = isFromAddFriend;
	}
    
    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_select_project, null);
            holder.tvArea = (TextView) convertView.findViewById(R.id.id_project_area);
            holder.tvName = (TextView) convertView.findViewById(R.id.id_project_name);
            holder.tvState = (TextView) convertView.findViewById(R.id.id_customer_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        ProjectSelectListItem item = getItem(position);
        if (item != null) {
            holder.tvArea.setText("[" + item.getArea_desc() + "]");
            holder.tvName.setText(item.getName());
            if (isFromAddFriend) {
				holder.tvState.setVisibility(View.GONE);
			} else {
				if (item.getIs_recommend().equals("1")) {
					holder.tvState.setVisibility(View.VISIBLE);
				} else {
					holder.tvState.setVisibility(View.GONE);
				}
			}
        }
        
        return convertView;
    }

    class ViewHolder {
        TextView tvArea;
        TextView tvName;
        TextView tvState;
    }
}
