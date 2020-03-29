package com.zlove.frag.independent;

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

import com.zlove.act.independent.ActIndependentFriendDetail;
import com.zlove.adapter.independent.FriendAdapter;
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
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.FriendHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class FriendSearchFragment extends BaseFragment implements OnItemClickListener, PullableViewListener {

    private Dialog reqDialog;

    private EditText etSearch = null;
    private PullListView listView = null;
    private FriendAdapter adapter;
    private List<FriendListItem> friendInfos = new ArrayList<FriendListItem>();

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
        
        adapter = new FriendAdapter(friendInfos, getActivity());
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
        FriendHttpRequest.getFriendListRequest(keyword, "0", "10", new GetFriendListHandler(this, LOAD_ACTION.ONREFRESH));
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
            if (reqDialog != null && !reqDialog.isShowing()) {
                reqDialog.show();
            }
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
                            CommonPageInfo info = data.getPage_info();
                            if (info != null) {
                                pageIndex = info.getPage_index();
                            }
                            
                            if (action == LOAD_ACTION.ONREFRESH) {
                                friendInfos.clear();
                            }
                            List<FriendListItem> tmpList = data.getFriend_list();
                            if (!ListUtils.isEmpty(tmpList)) {
                                if (tmpList.size() < 10) {
                                    listView.setPullLoadEnable(false);
                                } else {
                                    listView.setPullLoadEnable(true);
                                }
                                friendInfos.addAll(tmpList);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
            }
            if (ListUtils.isEmpty(friendInfos)) {
                showShortToast("暂无好友信息");
            }
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
            if (reqDialog != null && reqDialog.isShowing()) {
                reqDialog.dismiss();
            }
            listView.stopRefresh();
            listView.stopLoadMore();
        }
        
    }

    @Override
    public void onRefresh() {
        FriendHttpRequest.getFriendListRequest(keyword, "0", "10", new GetFriendListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        FriendHttpRequest.getFriendListRequest(keyword, String.valueOf(pageIndex), "10", new GetFriendListHandler(this, LOAD_ACTION.LOADERMORE));
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        FriendListItem item = adapter.getItem(position - 1);
        if (item == null) {
            return;
        }
        
        Intent intent = new Intent(getActivity(), ActIndependentFriendDetail.class);
        intent.putExtra(IntentKey.INTENT_KEY_SALEMAN_ID, item.getFriend_id());
        startActivity(intent);
    }

}
