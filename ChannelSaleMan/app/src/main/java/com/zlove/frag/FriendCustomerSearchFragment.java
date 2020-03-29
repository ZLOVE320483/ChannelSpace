package com.zlove.frag;

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

import com.zlove.act.ActCustomerFromIndependentDetail;
import com.zlove.adapter.FriendDetailAdapter;
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
import com.zlove.bean.friend.FriendRecommendListBean;
import com.zlove.bean.friend.FriendRecommendListData;
import com.zlove.bean.friend.FriendRecommendListItem;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.FriendHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class FriendCustomerSearchFragment extends BaseFragment implements OnItemClickListener, PullableViewListener {

    private Dialog reqDialog;

    private EditText etSearch = null;

    private PullListView listView;
    private FriendDetailAdapter adapter;
    List<FriendRecommendListItem> infos = new ArrayList<FriendRecommendListItem>();

    private String friendId = "";
    
    private int pageIndex = 0;
    private String keyword = "";

    private boolean isEdit = false;

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_customer_search;
    }

    @Override
    protected void setUpView(View view) {
        
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_FRIEND_ID)) {
                friendId = intent.getStringExtra(IntentKey.INTENT_KEY_FRIEND_ID);
            }
        }
        
        view.findViewById(R.id.id_cancel).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                UIUtil.hideKeyboard(getActivity());
                Intent result = new Intent();
                result.putExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, isEdit);
                finishActivity(result);
            }
        });
        etSearch = (EditText) view.findViewById(R.id.id_search);
        etSearch.setHint("请输入客户姓名或电话");
        listView = (PullListView) view.findViewById(R.id.id_listview);

        if (reqDialog == null) {
            reqDialog = DialogManager.getLoadingDialog(getActivity(), "正在搜索...");
        }
        
        if (adapter == null) {
            adapter = new FriendDetailAdapter(infos, getActivity());
        }
        
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);

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

        FriendHttpRequest.getFriendRecommendListRequest(friendId, "0", "10", keyword, "", "", "", "", 
            ChannelCookie.getInstance().getCurrentHouseId(), "", new GetFriendRecommendListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    class GetFriendRecommendListHandler extends HttpResponseHandlerFragment<FriendCustomerSearchFragment> {

        private LOAD_ACTION action;
        
        public GetFriendRecommendListHandler(FriendCustomerSearchFragment context, LOAD_ACTION action) {
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
                FriendRecommendListBean bean = JsonUtil.toObject(new String(content), FriendRecommendListBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        FriendRecommendListData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo pageInfo = data.getPage_info();
                            pageIndex = pageInfo.getPage_index();
                            if (action == LOAD_ACTION.ONREFRESH) {
                                infos.clear();
                            }
                            List<FriendRecommendListItem> tmpList = data.getRecommend_list();
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
                    showShortToast("暂无数据");
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
    public void onRefresh() {
        FriendHttpRequest.getFriendRecommendListRequest(friendId, "0", "10", keyword, "", "", "", "", 
            ChannelCookie.getInstance().getCurrentHouseId(), "", new GetFriendRecommendListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        FriendHttpRequest.getFriendRecommendListRequest(friendId, String.valueOf(pageIndex), "10", keyword, "", "", "", "", 
            ChannelCookie.getInstance().getCurrentHouseId(), "", new GetFriendRecommendListHandler(this, LOAD_ACTION.LOADERMORE));
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        if (ListUtils.isEmpty(infos)) {
            return;
        }
        FriendRecommendListItem item = infos.get(position - 1);
        if (item == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), ActCustomerFromIndependentDetail.class);
        intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, item.getClient_id());
        startActivityForResult(intent, IntentKey.REQUEST_CODE_CLIENT_EDIT);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentKey.REQUEST_CODE_CLIENT_EDIT && data != null) {
            isEdit = data.getBooleanExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, false);
            if (isEdit) {
                onRefresh();
            }
        }
    }

}
