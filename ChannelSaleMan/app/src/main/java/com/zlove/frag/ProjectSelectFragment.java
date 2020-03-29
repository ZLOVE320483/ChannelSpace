package com.zlove.frag;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.zlove.adapter.HouseSelectAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.bean.project.HouseListBean;
import com.zlove.bean.project.HouseListData;
import com.zlove.bean.project.HouseListItem;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ProjectHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class ProjectSelectFragment extends BaseFragment implements OnItemClickListener {

    private ListView listView;
    private HouseSelectAdapter adapter;
    private List<HouseListItem> houseList = new ArrayList<HouseListItem>();

    @Override
    protected int getInflateLayout() {
        return R.layout.common_view_listview_with_topbar;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("楼盘切换");
        
        listView = (ListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        adapter = new HouseSelectAdapter(houseList, getActivity());
        adapter.setHouseId(ChannelCookie.getInstance().getCurrentHouseId());
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(houseList)) {
            ProjectHttpRequest.requestProjectList("", "", "0", "100", new GetHouseListHandler(this));
        }
    }
    

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (ListUtils.isEmpty(houseList)) {
            return;
        }
        HouseListItem item = houseList.get(arg2);
        if (item == null) {
            return;
        }
        if (item.getHouse_id().equals(ChannelCookie.getInstance().getCurrentHouseId())) {
            showShortToast("当前已是该楼盘");
            return;
        }
        adapter.setHouseId(item.getHouse_id());
        adapter.notifyDataSetChanged();
        ChannelCookie.getInstance().saveUserCurrentHouseId(item.getHouse_id());
        ChannelCookie.getInstance().saveUserCurrentHouseName(item.getName());
        
        Intent result = new Intent();
        result.putExtra(IntentKey.INTENT_KEY_CHANGE_PROJECT, true);
        finishActivity(result);
    }
    
    class GetHouseListHandler extends HttpResponseHandlerFragment<ProjectSelectFragment> {

        public GetHouseListHandler(ProjectSelectFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                HouseListBean bean = JsonUtil.toObject(new String(content), HouseListBean.class);
                if (bean != null) {
                    HouseListData data = bean.getData();
                    if (data != null) {
                        List<HouseListItem> items = data.getHouse_list();
                        if (items.size() == 1) {
                            ChannelCookie.getInstance().saveUserCurrentHouseId(items.get(0).getHouse_id());
                            ChannelCookie.getInstance().saveUserCurrentHouseName(items.get(0).getName());
                        } else {
                            houseList.addAll(items);
                            adapter.notifyDataSetChanged();
                        }
                        ChannelCookie.getInstance().saveHouseNum(items.size());
                    }
                }
            }
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
        }
    }

}
