package com.zlove.frag.independent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.zlove.act.ActCommonWebView;
import com.zlove.adapter.message.MessageProjectDynamicAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.message.MessageProjectDynamicBean;
import com.zlove.bean.message.MessageProjectDynamicData;
import com.zlove.bean.message.MessageProjectDynamicListItem;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.MessageHttpRequest;

public class MessageProjectDynamicFragment extends BaseFragment implements OnItemClickListener, PullableViewListener {

    public static final String PROJETC_DETAIL_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "newsWeb/index?id=";
    
    private PullListView listView;
    private List<MessageProjectDynamicListItem> dynamicItems = new ArrayList<MessageProjectDynamicListItem>();
    private MessageProjectDynamicAdapter adapter;
    
    private int pageIndex = 0;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.common_act_listview_with_top_bar;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("楼盘动态");
        

        listView = (PullListView) view.findViewById(R.id.id_listview);
        
        adapter = new MessageProjectDynamicAdapter(dynamicItems, getActivity());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(dynamicItems)) {
            MessageHttpRequest.requestMessageList("4", "0", "10", new GetMessageProjectDynamicListHandler(this, LOAD_ACTION.ONREFRESH));
        }
    }

    @Override
    public void onRefresh() {
        MessageHttpRequest.requestMessageList("4", "0", "10", new GetMessageProjectDynamicListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        MessageHttpRequest.requestMessageList("4", String.valueOf(pageIndex), "10", new GetMessageProjectDynamicListHandler(this, LOAD_ACTION.LOADERMORE));
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        MessageProjectDynamicListItem item = adapter.getItem(position - 1);
        if (item == null) {
            return;
        }
        if (item.getStatus().equals("0")) {
        	MessageHttpRequest.setMessageRead(item.getUser_message_id(), new CommonHandler(this));
        	item.setStatus("1");
        	adapter.notifyDataSetChanged();
		}
        String url = PROJETC_DETAIL_URL + item.getExtra().getNews_id();

        Intent intent = new Intent(getActivity(), ActCommonWebView.class);
        intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, url);
        intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "动态详情");
        startActivity(intent);
    }
    
    class GetMessageProjectDynamicListHandler extends HttpResponseHandlerFragment<MessageProjectDynamicFragment>  {
        
        private LOAD_ACTION action;

        public GetMessageProjectDynamicListHandler(MessageProjectDynamicFragment context, LOAD_ACTION action) {
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
                MessageProjectDynamicBean bean = JsonUtil.toObject(new String(content), MessageProjectDynamicBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        MessageProjectDynamicData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo info = data.getPage_info();
                            pageIndex = info.getPage_index();

                            if (action == LOAD_ACTION.ONREFRESH) {
                                dynamicItems.clear();
                            }
                            List<MessageProjectDynamicListItem> tmpList = data.getMessage_list();
                            if (tmpList.size() < 10) {
                                listView.setPullLoadEnable(false);
                            } else {
                                listView.setPullLoadEnable(true);
                            }
                            dynamicItems.addAll(tmpList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            if (ListUtils.isEmpty(dynamicItems)) {
                showShortToast("楼盘动态暂无更新");
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
