package com.zlove.adapter.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zlove.base.widget.PinnedSectionListView.PinnedSectionListAdapter;
import com.zlove.bean.common.CityInfo;
import com.zlove.bean.common.ContactInfo;
import com.zlove.channel.R;


public class CityAdapter extends ArrayAdapter<CityInfo> implements PinnedSectionListAdapter {

    public CityAdapter(Context context) {
        super(context, -1);
    }
    
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            return ((CityInfo) getItem(position)).getSectionType();
        } catch (Exception e) {
            e.printStackTrace();
            return ContactInfo.ITEM;
        }
    }
    
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityInfo item = getItem(position);
        if (isItemViewTypePinned(item.getSectionType())) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_section, null);
            }
            ((TextView) convertView.findViewById(R.id.tv_contact_section)).setText(item.getSectionText());
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_city, null);
            }
            TextView tvCityName = (TextView) convertView.findViewById(R.id.tv_city_name);
            tvCityName.setText(item.getName());
        }
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == ContactInfo.SECTION;
    }

}
