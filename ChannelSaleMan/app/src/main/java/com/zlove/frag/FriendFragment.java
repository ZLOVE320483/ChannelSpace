package com.zlove.frag;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.zlove.act.ActFriendDetail;
import com.zlove.act.ActFriendSearch;
import com.zlove.adapter.FriendAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.friend.FriendListBean;
import com.zlove.bean.friend.FriendListData;
import com.zlove.bean.friend.FriendListItem;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.FriendHttpRequest;


public class FriendFragment extends BaseFragment implements OnClickListener, OnItemClickListener, PullableViewListener {

    private PullListView listView;
    private FriendAdapter adapter;
    private List<FriendListItem> infos = new ArrayList<FriendListItem>();
    private TextView tvSearch;
    
    private int pageIndex = 0;

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_main_friend;
    }

    @Override
    protected void setUpView(View view) {
        ((TextView) view.findViewById(R.id.id_title)).setText("好友");
        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        
        tvSearch = (TextView) view.findViewById(R.id.id_search);
        tvSearch.setText("搜好友");
        tvSearch.setOnClickListener(this);

        if (adapter == null) {
            adapter = new FriendAdapter(infos, getActivity());
        }
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(infos)) {
            FriendHttpRequest.getFriendListRequest(ChannelCookie.getInstance().getCurrentHouseId(), "", "0", "10", new GetFriendListHandler(this, LOAD_ACTION.ONREFRESH));
        }
        if (infos.size() < 10) {
			listView.setPullLoadEnable(false);
		} else {
			listView.setPullLoadEnable(true);
		}
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (ListUtils.isEmpty(infos)) {
            return;
        }
        FriendListItem item = infos.get(position - 1);
        if (item == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), ActFriendDetail.class);
        intent.putExtra(IntentKey.INTENT_KEY_FRIEND_ID, item.getFriend_id());
        startActivityForResult(intent, IntentKey.REQUEST_CODE_FRIEND_EDIT);
    }

    @Override
    public void onRefresh() {
        FriendHttpRequest.getFriendListRequest(ChannelCookie.getInstance().getCurrentHouseId(), "", "0", "10", new GetFriendListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        FriendHttpRequest.getFriendListRequest(ChannelCookie.getInstance().getCurrentHouseId(), "", String.valueOf(pageIndex), "10", new GetFriendListHandler(this, LOAD_ACTION.LOADERMORE));
    }

    @Override
    public void onClick(View v) {
        if (v == tvSearch) {
            Intent intent = new Intent(getActivity(), ActFriendSearch.class);
            startActivity(intent);
        }
    }
    
    class GetFriendListHandler extends HttpResponseHandlerFragment<FriendFragment> {

        private LOAD_ACTION action;
        
        public GetFriendListHandler(FriendFragment context, LOAD_ACTION action) {
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
                FriendListBean bean = JsonUtil.toObject(new String(content), FriendListBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        FriendListData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo pageInfo = data.getPage_info();
                            pageIndex = pageInfo.getPage_index();
                            if (action == LOAD_ACTION.ONREFRESH) {
                                infos.clear();
                            }
                            List<FriendListItem> tmpList = data.getFriend_list();
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
                    showShortToast("暂无好友");
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
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == IntentKey.REQUEST_CODE_FRIEND_EDIT) {
			if (data != null) {
				if (data.getBooleanExtra(IntentKey.INTENT_KEY_FRIEND_IS_EDIT, false)) {
					onRefresh();
				}
			}
		}
    }

}
