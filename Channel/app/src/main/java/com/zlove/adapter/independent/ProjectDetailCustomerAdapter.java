
package com.zlove.adapter.independent;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.adapter.common.SingleDataListAdapter;
import com.zlove.bean.project.ProjectDetailReportedCustomerItem;
import com.zlove.channel.R;

public class ProjectDetailCustomerAdapter extends SingleDataListAdapter<ProjectDetailReportedCustomerItem> {

    public ProjectDetailCustomerAdapter(List<ProjectDetailReportedCustomerItem> data, Context context) {
        super(data, context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_project_detail, null);
            holder.tvCustomerName = (TextView) convertView.findViewById(R.id.id_customer_name);
            holder.ivCustomerType = (ImageView) convertView.findViewById(R.id.id_customer_type);
            holder.tvCustomerPhone = (TextView) convertView.findViewById(R.id.id_customer_phone);
            holder.tvSaleManName = (TextView) convertView.findViewById(R.id.id_saleman_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        ProjectDetailReportedCustomerItem item = getItem(position);
        if (item != null) {
            if (item.getClient_name().length() < 6) {
                holder.tvCustomerName.setText(item.getClient_name());
            } else {
                holder.tvCustomerName.setText(item.getClient_name().substring(0, 6) + "...");
            }
            holder.tvCustomerPhone.setText(item.getClient_phone());
            holder.tvSaleManName.setText(item.getSalesman_name());
            if (item.getClient_category_id().equals("1")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_a);
            } else if (item.getClient_category_id().equals("2")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_b);
            } else if (item.getClient_category_id().equals("3")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_c);
            } else if (item.getClient_category_id().equals("4")) {
                holder.ivCustomerType.setBackgroundResource(R.drawable.customer_type_d);
            }
        }
        
        return convertView;
    }

    class ViewHolder {

        TextView tvCustomerName;
        ImageView ivCustomerType;
        TextView tvCustomerPhone;
        TextView tvSaleManName;
    }

}
