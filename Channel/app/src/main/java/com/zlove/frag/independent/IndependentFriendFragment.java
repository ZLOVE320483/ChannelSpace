package com.zlove.frag.independent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.act.independent.ActFriendSearch;
import com.zlove.act.independent.ActIndependentFriendAdd;
import com.zlove.act.independent.ActIndependentFriendDetail;
import com.zlove.adapter.independent.FriendAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.friend.FriendListBean;
import com.zlove.bean.friend.FriendListData;
import com.zlove.bean.friend.FriendListItem;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.FriendHttpRequest;

public class IndependentFriendFragment extends BaseFragment implements OnClickListener, OnItemClickListener, PullableViewListener {

    private TextView etSearch = null;
    private PullListView listView = null;
    private FriendAdapter adapter;
    private List<FriendListItem> friendInfos = new ArrayList<FriendListItem>();
    private ImageView btnAddFriend = null;

    private Dialog loadingDialog;
    private int pageIndex = 0;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_friend;
    }

    @Override
    protected void setUpView(View view) {
        ((TextView) view.findViewById(R.id.id_title)).setText("好友(业务员)");

        loadingDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");

        etSearch = (TextView) view.findViewById(R.id.id_search);
        etSearch.setText("搜好友");
        etSearch.setOnClickListener(this);
        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        if (friendInfos.size() < 10) {
            listView.setPullLoadEnable(false);
        } else {
            listView.setPullLoadEnable(true);
        }
        listView.setPullRefreshEnable(true);
        listView.setHeaderDividersEnabled(false);
        listView.setFooterDividersEnabled(false);
        
        btnAddFriend = (ImageView) view.findViewById(R.id.id_confirm);
        btnAddFriend.setOnClickListener(this);
        
        adapter = new FriendAdapter(friendInfos, getActivity());
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(friendInfos)) {
            FriendHttpRequest.getFriendListRequest("", "0", "10", new GetFriendListHandler(this, LOAD_ACTION.ONREFRESH));
        }
        if (friendInfos.size() < 10) {
			listView.setPullLoadEnable(false);
		} else {
			listView.setPullLoadEnable(true);
		}
    }
    
    @Override
    public void onClick(View v) {
        if (v == btnAddFriend) {
            Intent intent = new Intent(getActivity(), ActIndependentFriendAdd.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_ADD_FRIEND);
        } else if (v == etSearch) {
			Intent intent = new Intent(getActivity(), ActFriendSearch.class);
			startActivity(intent);
		}
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        
        if (ListUtils.isEmpty(friendInfos)) {
            return;
        }
        
        FriendListItem item = adapter.getItem(position - 1);
        if (item == null) {
            return;
        }
        
        Intent intent = new Intent(getActivity(), ActIndependentFriendDetail.class);
        intent.putExtra(IntentKey.INTENT_KEY_SALEMAN_ID, item.getFriend_id());
        startActivity(intent);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IntentKey.REQUEST_CODE_ADD_FRIEND) {
                onRefresh();
            }
        }
    }
    
    class GetFriendListHandler extends HttpResponseHandlerFragment<IndependentFriendFragment> {

        private LOAD_ACTION action;
        
        public GetFriendListHandler(IndependentFriendFragment context, LOAD_ACTION action) {
            super(context);
            this.action = action;
        }
        
        @Override
        public void onStart() {
            super.onStart();
            if (loadingDialog != null && !loadingDialog.isShowing()) {
				loadingDialog.show();
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
                showShortToast("你还没有添加任何好友");
            }
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
            if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
            listView.stopRefresh();
            listView.stopLoadMore();
        }
        
    }

	@Override
	public void onRefresh() {
        FriendHttpRequest.getFriendListRequest("", "0", "10", new GetFriendListHandler(this, LOAD_ACTION.ONREFRESH));
	}

	@Override
	public void onLoadMore() {
        FriendHttpRequest.getFriendListRequest("", String.valueOf(pageIndex), "10", new GetFriendListHandler(this, LOAD_ACTION.LOADERMORE));
	}

}
