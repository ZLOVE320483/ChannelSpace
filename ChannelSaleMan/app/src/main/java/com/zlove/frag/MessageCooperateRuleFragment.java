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
import com.zlove.adapter.MessageCooperateRuleAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.message.MessageCooperateRuleBean;
import com.zlove.bean.message.MessageCooperateRuleData;
import com.zlove.bean.message.MessageCooperateRuleListItem;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.MessageHttpRequest;

public class MessageCooperateRuleFragment extends BaseFragment implements OnItemClickListener, PullableViewListener {
    
    public static final String RULE_DETAIL_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "ruleWeb/index?id=";
    
    private PullListView listView;
    private List<MessageCooperateRuleListItem> ruleItems = new ArrayList<MessageCooperateRuleListItem>();
    private MessageCooperateRuleAdapter adapter;
    
    private int pageIndex = 0;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.common_act_listview_with_top_bar;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("合作规则");

        listView = (PullListView) view.findViewById(R.id.id_listview);
        
        adapter = new MessageCooperateRuleAdapter(ruleItems, getActivity());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(ruleItems)) {
            MessageHttpRequest.requestMessageList("5", "0", "10", new GetMessageCooperateRuleListHandler(this, LOAD_ACTION.ONREFRESH));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        MessageCooperateRuleListItem item = adapter.getItem(position - 1);
        if (item == null) {
            return;
        }
        if (item.getStatus().equals("0")) {
        	MessageHttpRequest.setMessageRead(item.getUser_message_id(), new CommonHandler(this));
            item.setStatus("1");
            adapter.notifyDataSetChanged();
		}
        String url = RULE_DETAIL_URL + item.getExtra().getRule_id() + "&user_message_id=" + item.getUser_message_id();

        Intent intent = new Intent(getActivity(), ActCommonWebView.class);
        intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, url);
        intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "规则详情");
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        MessageHttpRequest.requestMessageList("5", "0", "10", new GetMessageCooperateRuleListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        MessageHttpRequest.requestMessageList("5", String.valueOf(pageIndex), "10", new GetMessageCooperateRuleListHandler(this, LOAD_ACTION.LOADERMORE));
    }
    
    class GetMessageCooperateRuleListHandler extends HttpResponseHandlerFragment<MessageCooperateRuleFragment> {

        private LOAD_ACTION action;

        public GetMessageCooperateRuleListHandler(MessageCooperateRuleFragment context, LOAD_ACTION action) {
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
                MessageCooperateRuleBean bean = JsonUtil.toObject(new String(content), MessageCooperateRuleBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        MessageCooperateRuleData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo info = data.getPage_info();
                            pageIndex = info.getPage_index();

                            if (action == LOAD_ACTION.ONREFRESH) {
                                ruleItems.clear();
                            }
                            List<MessageCooperateRuleListItem> tmpList = data.getMessage_list();
                            if (tmpList.size() < 10) {
                                listView.setPullLoadEnable(false);
                            } else {
                                listView.setPullLoadEnable(true);
                            }
                            ruleItems.addAll(tmpList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            if (ListUtils.isEmpty(ruleItems)) {
                showShortToast("合作规则暂无更新");
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
