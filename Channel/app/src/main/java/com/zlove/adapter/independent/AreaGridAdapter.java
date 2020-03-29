package com.zlove.adapter.independent;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.zlove.base.util.ListUtils;
import com.zlove.bean.city.AreaGridBean;
import com.zlove.channel.R;


public class AreaGridAdapter extends BaseAdapter {
    
    private Context context;
    private List<AreaGridBean> areas;
    
    public AreaGridAdapter(Context context, List<AreaGridBean> areas) {
        this.context = context;
        this.areas = areas;
    }

    @Override
    public int getCount() {
        if (ListUtils.isEmpty(areas)) {
            return 0;
        }
        return areas.size();
    }

    @Override
    public AreaGridBean getItem(int position) {
        if (ListUtils.isEmpty(areas)) {
            return null;
        }
        return areas.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_area, null);
            holder.rbArea = (RadioButton) convertView.findViewById(R.id.rb_area);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.rbArea.setClickable(false);
        holder.rbArea.setFocusable(false);
        AreaGridBean bean = getItem(position);
        if (bean != null) {
			if (bean.isSelect()) {
				holder.rbArea.setChecked(true);
			} else {
				holder.rbArea.setChecked(false);
			}
			holder.rbArea.setText(bean.getItem().getName());
		}
        
        return convertView;
    }
    
    class ViewHolder {
        RadioButton rbArea;
    }

}
