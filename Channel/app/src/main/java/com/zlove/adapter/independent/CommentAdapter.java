package com.zlove.adapter.independent;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zlove.base.util.ListUtils;
import com.zlove.bean.independent.CommentInfo;
import com.zlove.channel.R;


public class CommentAdapter extends BaseAdapter {
    
    private Context context;
    private List<CommentInfo> infos;
    
    
    public CommentAdapter(Context context, List<CommentInfo> infos) {
        this.context = context;
        this.infos = infos;
    }

    @Override
    public int getCount() {
        if (ListUtils.isEmpty(infos)) {
            return 0;
        }
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        if (ListUtils.isEmpty(infos)) {
            return null;
        }
        return infos.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_comment, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.id_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    
    class ViewHolder {
        TextView tvName;
    }

}
