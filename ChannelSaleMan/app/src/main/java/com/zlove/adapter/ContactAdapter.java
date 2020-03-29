
package com.zlove.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zlove.base.widget.PinnedSectionListView.PinnedSectionListAdapter;
import com.zlove.bean.common.ContactInfo;
import com.zlove.channelsaleman.R;

public class ContactAdapter extends ArrayAdapter<ContactInfo> implements PinnedSectionListAdapter {

    public ContactAdapter(Context context) {
        super(context, -1);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            return ((ContactInfo) getItem(position)).getSectionType();
        } catch (Exception e) {
            e.printStackTrace();
            return ContactInfo.ITEM;
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactInfo item = getItem(position);
        if (isItemViewTypePinned(item.getSectionType())) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_section, null);
            }
            ((TextView) convertView.findViewById(R.id.tv_contact_section)).setText(item.getSectionText());
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_contact, null);
            }
            TextView tvFullName = (TextView) convertView.findViewById(R.id.tv_name);
            TextView tvNum = (TextView) convertView.findViewById(R.id.tv_num);

            tvFullName.setText(item.getFullName());
            tvNum.setText(item.getNumber());
        }
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == ContactInfo.SECTION;
    }

}
