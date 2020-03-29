package com.zlove.frag;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.zlove.act.ActFriendDetail;
import com.zlove.adapter.FriendAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.util.UIUtil;
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

public class FriendSearchFragment extends BaseFragment implements OnItemClickListener, PullableViewListener {

    private Dialog reqDialog;

    private EditText etSearch = null;
    private PullListView listView = null;
    private FriendAdapter adapter;
    private List<FriendListItem> infos = new ArrayList<FriendListItem>();
    private int pageIndex = 0;
    private String keyword = "";

	@Override
	protected int getInflateLayout() {
		return R.layout.frag_customer_search;
	}

	@Override
	protected void setUpView(View view) {
	    view.findViewById(R.id.id_cancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                UIUtil.hideKeyboard(getActivity());
                finishActivity();
            }
        });
        etSearch = (EditText) view.findViewById(R.id.id_search);
        etSearch.setHint("请输入好友姓名或电话");
        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        
        if (reqDialog == null) {
            reqDialog = DialogManager.getLoadingDialog(getActivity(), "正在搜索...");
        }
        
        adapter = new FriendAdapter(infos, getActivity());
        listView.setAdapter(adapter);
        
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    UIUtil.hideKeyboard(getActivity());
                    search(etSearch.getText().toString().trim());
                    return true;
                } else {
                    return false;
                }

            }
        });

	}
	
	private void search(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            showShortToast("请输入关键字");
            return;
        }
        this.keyword = keyword;
        FriendHttpRequest.getFriendListRequest(ChannelCookie.getInstance().getCurrentHouseId(), keyword, "0", "10", new GetFriendListHandler(this, LOAD_ACTION.ONREFRESH));
	}
	

    @Override
    public void onRefresh() {
        FriendHttpRequest.getFriendListRequest(ChannelCookie.getInstance().getCurrentHouseId(), keyword, "0", "10", new GetFriendListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        FriendHttpRequest.getFriendListRequest(ChannelCookie.getInstance().getCurrentHouseId(), keyword, String.valueOf(pageIndex), "10", new GetFriendListHandler(this, LOAD_ACTION.ONREFRESH));
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        if (ListUtils.isEmpty(infos)) {
            return;
        }
        FriendListItem item = infos.get(position - 1);
        if (item == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), ActFriendDetail.class);
        intent.putExtra(IntentKey.INTENT_KEY_FRIEND_ID, item.getFriend_id());
        startActivity(intent);
    }
	
	class GetFriendListHandler extends HttpResponseHandlerFragment<FriendSearchFragment> {

        private LOAD_ACTION action;
        
        public GetFriendListHandler(FriendSearchFragment context, LOAD_ACTION action) {
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


}
