package com.zlove.frag;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zlove.adapter.CustomerTraceRecordAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.client.ClientTraceBean;
import com.zlove.bean.client.ClientTraceData;
import com.zlove.bean.client.ClientTraceListItem;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZLOVE on 2017/1/6.
 */
public class CustomerChatRecordFragment extends BaseFragment implements PullableViewListener {

    private PullListView listView;
    private List<ClientTraceListItem> infos = new ArrayList<ClientTraceListItem>();
    private CustomerTraceRecordAdapter adapter;

    private String clientId = "";
    private String houseId = "";
    private int pageIndex = 0;

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_customer_detail;
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

        houseId = TextUtils.isEmpty(houseId) ? ChannelCookie.getInstance().getCurrentHouseId() : houseId;

        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("记录追踪");

        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);

        if (adapter == null) {
            adapter = new CustomerTraceRecordAdapter(infos, getActivity());
        }
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(infos)) {
            ClientHttpRequest.getClientTraceListRequest(clientId, "0", "10", houseId, new GetTraceListHandler(this, HttpResponseHandlerFragment.LOAD_ACTION.ONREFRESH));
        }
    }

    @Override
    public void onRefresh() {
        ClientHttpRequest.getClientTraceListRequest(clientId, "0", "10", houseId, new GetTraceListHandler(this, HttpResponseHandlerFragment.LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        ClientHttpRequest.getClientTraceListRequest(clientId, String.valueOf(pageIndex), "10", houseId, new GetTraceListHandler(this, HttpResponseHandlerFragment.LOAD_ACTION.LOADERMORE));
    }

    class GetTraceListHandler extends HttpResponseHandlerFragment<CustomerChatRecordFragment> {

        private LOAD_ACTION action;

        public GetTraceListHandler(CustomerChatRecordFragment context, LOAD_ACTION action) {
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
            if (content != null) {
                ClientTraceBean bean = JsonUtil.toObject(new String(content), ClientTraceBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ClientTraceData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo pageInfo = data.getPage_info();
                            pageIndex = pageInfo.getPage_index();
                            List<ClientTraceListItem> tmpList = data.getTrace_list();
                            if (tmpList.size() < 10) {
                                listView.setPullLoadEnable(false);
                            } else {
                                listView.setPullLoadEnable(true);
                            }
                            if (action == LOAD_ACTION.ONREFRESH) {
                                infos.clear();
                            }
                            infos.addAll(tmpList);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        showShortToast(bean.getMessage());
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
            listView.stopRefresh();
            listView.stopLoadMore();
        }

    }
}
