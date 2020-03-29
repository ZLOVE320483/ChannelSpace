package com.zlove.frag.independent;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zlove.adapter.independent.CustomerTraceRecordAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.client.ClientTraceListBean;
import com.zlove.bean.client.ClientTraceListData;
import com.zlove.bean.client.ClientTraceListItem;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class IndependentCustomerTraceRecordFragment extends BaseFragment implements PullableViewListener {
    
    private PullListView listView;
    private CustomerTraceRecordAdapter adapter;
    private List<ClientTraceListItem> infos = new ArrayList<ClientTraceListItem>();

    private int pageIndex = 0;
    private String houseId = "";
    private String clientId = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_customer_trace_record;
    }

    @Override
    protected void setUpView(View view) {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_CLIENT_ID)) {
                clientId = intent.getStringExtra(IntentKey.INTENT_KEY_CLIENT_ID);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_ID)) {
                houseId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
            }
        }
        
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("客户记录追踪");
        
        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        listView.setFooterDividersEnabled(false);
        listView.setHeaderDividersEnabled(false);
        
        if (adapter == null) {
            adapter = new CustomerTraceRecordAdapter(infos, getActivity());
        }
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(infos)) {
            ClientHttpRequest.getClientTraceListRequest(clientId, houseId, "0", "10", new GetClientTraceListHandler(this, LOAD_ACTION.ONREFRESH));
        }
    }

    @Override
    public void onRefresh() {
        ClientHttpRequest.getClientTraceListRequest(clientId, houseId, "0", "10", new GetClientTraceListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        ClientHttpRequest.getClientTraceListRequest(clientId, houseId, String.valueOf(pageIndex), "10", new GetClientTraceListHandler(this, LOAD_ACTION.LOADERMORE));
    }
    
    class GetClientTraceListHandler extends HttpResponseHandlerFragment<IndependentCustomerTraceRecordFragment> {

        private LOAD_ACTION action;
        
        public GetClientTraceListHandler(IndependentCustomerTraceRecordFragment context, LOAD_ACTION action) {
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
                ClientTraceListBean bean = JsonUtil.toObject(new String(content), ClientTraceListBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ClientTraceListData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo pageInfo = data.getPage_info();
                            pageIndex = pageInfo.getPage_index();
                            if (action == LOAD_ACTION.ONREFRESH) {
                                infos.clear();
                            }
                            List<ClientTraceListItem> tmpList = data.getTrace_list();
                            if (tmpList.size() < 10) {
                                listView.setPullLoadEnable(false);
                            } else {
                                listView.setPullLoadEnable(true);
                            }
                            infos.addAll(tmpList);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
                if (ListUtils.isEmpty(infos)) {
                    showShortToast("暂无客户记录");
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
            listView.stopLoadMore();
            listView.stopRefresh();
        }
        
    }

}
