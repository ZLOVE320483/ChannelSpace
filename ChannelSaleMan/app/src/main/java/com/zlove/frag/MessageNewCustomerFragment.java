package com.zlove.frag;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.zlove.act.ActMessageCustomerDetail;
import com.zlove.adapter.MessageNewCustomerAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.message.MessageNewCustomerListBean;
import com.zlove.bean.message.MessageNewCustomerListData;
import com.zlove.bean.message.MessageNewCustomerListItem;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.MessageHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class MessageNewCustomerFragment extends BaseFragment implements OnItemClickListener, PullableViewListener {

    private PullListView listView;
    private MessageNewCustomerAdapter adapter;
    private List<MessageNewCustomerListItem> infos = new ArrayList<MessageNewCustomerListItem>();

    private int pageIndex = 0;

    @Override
    protected int getInflateLayout() {
        return R.layout.common_act_listview_with_top_bar;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("新增客户");

        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);


        if (adapter == null) {
            adapter = new MessageNewCustomerAdapter(infos, getActivity());
        }

        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(infos)) {
            MessageHttpRequest.requestMessageList("0", "0", "10", new GetNewCustomerListHandler(this, LOAD_ACTION.ONREFRESH));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MessageNewCustomerListItem item = adapter.getItem(position - 1);
        if (item == null) {
            return;
        }
        if (item.getStatus().equals("0")) {
            MessageHttpRequest.setMessageRead(item.getUser_message_id(), new CommonHandler(this));
            item.setStatus("1");
            adapter.notifyDataSetChanged();
        }
        Intent intent = new Intent(getActivity(), ActMessageCustomerDetail.class);
        intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, item.getExtra().getClient_id());
        intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, item.getExtra().getHouse_id());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        MessageHttpRequest.requestMessageList("0", "0", "10", new GetNewCustomerListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        MessageHttpRequest.requestMessageList("0", String.valueOf(pageIndex), "10", new GetNewCustomerListHandler(this, LOAD_ACTION.LOADERMORE));
    }

    class GetNewCustomerListHandler extends HttpResponseHandlerFragment<MessageNewCustomerFragment> {

        private LOAD_ACTION action;

        public GetNewCustomerListHandler(MessageNewCustomerFragment context, LOAD_ACTION action) {
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
                MessageNewCustomerListBean bean = JsonUtil.toObject(new String(content), MessageNewCustomerListBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        MessageNewCustomerListData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo pageInfo = data.getPage_info();
                            pageIndex = pageInfo.getPage_index();
                            if (action == LOAD_ACTION.ONREFRESH) {
                                infos.clear();
                            }
                            List<MessageNewCustomerListItem> tmpList = data.getMessage_list();
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
                    showShortToast("新增客户暂无更新");
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
