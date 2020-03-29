
package com.zlove.adapter.independent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.base.util.GImageLoader;
import com.zlove.base.util.ListUtils;
import com.zlove.bean.project.ProjectItemHouseList;
import com.zlove.channel.R;

import java.util.List;

public class ProjectAdapter extends BaseAdapter {

    private Context context;
    private List<ProjectItemHouseList> projectInfos;
//    private ProjectAddCustomerDelegate delegate;
//    private String projectName = "";
//	private String projectId = "";

    public ProjectAdapter(Context context, List<ProjectItemHouseList> projectInfos, ProjectAddCustomerDelegate delegate) {
        this.context = context;
        this.projectInfos = projectInfos;
//        this.delegate = delegate;
    }

    @Override
    public int getCount() {
        if (ListUtils.isEmpty(projectInfos)) {
            return 0;
        }
        return projectInfos.size();
    }

    @Override
    public Object getItem(int position) {
        if (ListUtils.isEmpty(projectInfos)) {
            return null;
        }
        return projectInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_project_new, null);
            holder.tvProjectName = (TextView) convertView.findViewById(R.id.id_project_name);
            holder.tvProjectCommission = (TextView) convertView.findViewById(R.id.commission);
            holder.tvProjectPrice = (TextView) convertView.findViewById(R.id.id_project_price);
            holder.projectImg = (ImageView) convertView.findViewById(R.id.id_project_img);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ProjectItemHouseList info = projectInfos.get(position);
        if (info != null) {
            holder.tvProjectName.setText("[" + info.getArea_desc() + "] " + info.getName());
            holder.tvProjectPrice.setText("价格:" + info.getPrice_desc());
            GImageLoader.getInstance().getImageLoader().displayImage(info.getThumb(), holder.projectImg, GImageLoader.getInstance().getNormalOptions());
            holder.tvProjectCommission.setText(info.getHouse_rule_desc());
        }

        return convertView;
    }

    class ViewHolder {

        TextView tvProjectName;
        TextView tvProjectPrice;
        TextView tvProjectCommission;
        ImageView projectImg;
    }
    
    public interface ProjectAddCustomerDelegate {
        void addCustomerAction(String projectName, String projectId);
    }


}
