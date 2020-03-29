
package com.zlove.adapter.independent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zlove.base.widget.PinnedSectionListView.PinnedSectionListAdapter;
import com.zlove.bean.common.ContactInfo;
import com.zlove.bean.independent.FriendSelectInfo;
import com.zlove.bean.project.ProjectSelectSaleManListItem;
import com.zlove.channel.R;

public class FriendSelectAdapter extends ArrayAdapter<ProjectSelectSaleManListItem> implements PinnedSectionListAdapter {

    public FriendSelectAdapter(Context context) {
        super(context, -1);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            return ((ProjectSelectSaleManListItem) getItem(position)).getSectionType();
        } catch (Exception e) {
            e.printStackTrace();
            return ContactInfo.ITEM;
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectSelectSaleManListItem item = getItem(position);
        if (isItemViewTypePinned(item.getSectionType())) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_section, null);
            }
            ((TextView) convertView.findViewById(R.id.tv_contact_section)).setText(item.getSectionText());
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_select_friend, null);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.id_friend_name);
            TextView tvNum = (TextView) convertView.findViewById(R.id.id_friend_phone);

            tvName.setText(item.getRealname());
            if (item.getRealname().length() < 6) {
                tvName.setText(item.getRealname());
            } else {
                tvName.setText(item.getRealname().substring(0, 6) + "...");
            }
            tvNum.setText(item.getPhone());
        }
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == FriendSelectInfo.SECTION;
    }
}
