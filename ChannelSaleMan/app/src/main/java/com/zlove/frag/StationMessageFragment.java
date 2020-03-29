package com.zlove.frag;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.zlove.act.ActCommonWebView;
import com.zlove.adapter.StationMessageAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.app.SelfMesaageListItem;
import com.zlove.bean.app.SelfMessageListBean;
import com.zlove.bean.app.SelfMessageListData;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.AppHttpRequest;


public class StationMessageFragment extends BaseFragment implements OnItemClickListener, PullableViewListener {
    
    public static String MESSAGE_DETAIL_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "sysNewsWeb/index?id=";
    
    private PullListView listView;
    private StationMessageAdapter adapter;
    private List<SelfMesaageListItem> infos = new ArrayList<SelfMesaageListItem>();
    
    private int pageIndex = 0;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_station_message;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("站内消息");
        
        listView = (PullListView) view.findViewById(R.id.id_listview);

        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        
        if (adapter == null) {
            adapter = new StationMessageAdapter(infos, getActivity());
            listView.setAdapter(adapter);
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(infos)) {
            AppHttpRequest.getStateMessageListRequest("0", "10", new GetStationMessageListHandler(this, LOAD_ACTION.ONREFRESH));
        }
    }

    @Override
    public void onRefresh() {
        AppHttpRequest.getStateMessageListRequest("0", "10", new GetStationMessageListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        AppHttpRequest.getStateMessageListRequest(String.valueOf(pageIndex), "10", new GetStationMessageListHandler(this, LOAD_ACTION.LOADERMORE));
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        SelfMesaageListItem item = adapter.getItem(position - 1);
        if (item == null) {
            return;
        }
        String url = MESSAGE_DETAIL_URL + item.getExtra().getNews_id();
        Intent intent = new Intent(getActivity(), ActCommonWebView.class);
        intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, url);
        intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "消息详情");
        startActivity(intent);
    }
    
    class GetStationMessageListHandler extends HttpResponseHandlerFragment<StationMessageFragment> {
        private LOAD_ACTION action;
        
        public GetStationMessageListHandler(StationMessageFragment context, LOAD_ACTION action) {
            super(context);
            this.action = action;
        }
        
        @Override
        public void onStart() {
            super.onStart();
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (!isAdded()) {
                return;
            }
            if (content != null) {
                SelfMessageListBean bean = JsonUtil.toObject(new String(content), SelfMessageListBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        SelfMessageListData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo info = data.getPage_info();
                            pageIndex = info.getPage_index();
                            
                            if (action == LOAD_ACTION.ONREFRESH) {
                                infos.clear();
                            }
                            List<SelfMesaageListItem> tmpList = data.getMessage_list();
                            if (!ListUtils.isEmpty(tmpList)) {
                                if (tmpList.size() < 10) {
                                    listView.setPullLoadEnable(false);
                                } else {
                                    listView.setPullLoadEnable(true);
                                }
                                infos.addAll(tmpList);
                                adapter.notifyDataSetChanged();
                            } else {
                                showShortToast("还未发布任何站内消息");
                            }
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                } else {
                    showShortToast("还未发布任何站内消息");
                }
            }
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content,
                Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
            listView.stopRefresh();
            listView.stopLoadMore();
        }
        
    }

}
