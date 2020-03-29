package com.zlove.frag.independent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.zlove.act.independent.ActIndependentCustomerDetail;
import com.zlove.adapter.independent.FriendDetailAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.friend.FriendDetailBean;
import com.zlove.bean.friend.FriendDetailData;
import com.zlove.bean.friend.FriendRecommendListBean;
import com.zlove.bean.friend.FriendRecommendListData;
import com.zlove.bean.friend.FriendRecommendListItem;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.FriendHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class IndependentFriendDetailFragment extends BaseFragment implements PullableViewListener, OnItemClickListener {
    
    private PullListView listView;
    private FriendDetailAdapter adapter;
    private List<FriendRecommendListItem> infos = new ArrayList<FriendRecommendListItem>();
    
    private TextView tvFriendName;
    private TextView tvCustomerCount;
    
    private Dialog loadingDialog;
    private String saleManId = "";
    private int pageIndex = 0;
    
    boolean isEdit = false;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_friend_detail;
    }

    @Override
    protected void setUpView(View view) {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_SALEMAN_ID)) {
                saleManId = intent.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_ID);
            }
        }
        
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("好友详情");
        
        loadingDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        
        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        
        initHeadView();
        
        adapter = new FriendDetailAdapter(infos, getActivity());
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        FriendHttpRequest.getFriendDetailRequest(saleManId, new GetFriendDetailHandler(this));
        if (ListUtils.isEmpty(infos)) {
            FriendHttpRequest.getFriendRecommendListRequest(saleManId, "0", "10", new GetFriendRecommendListHandler(this, LOAD_ACTION.ONREFRESH));
        }
    }
    
    @SuppressLint("InflateParams") 
    private void initHeadView() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.list_friend_detail_head_view, null);
        tvFriendName = (TextView) headView.findViewById(R.id.id_friend_name);
        tvCustomerCount = (TextView) headView.findViewById(R.id.customer_count);
        
        listView.addHeaderView(headView);
    }

    @Override
    public void onRefresh() {
        FriendHttpRequest.getFriendRecommendListRequest(saleManId, "0", "10", new GetFriendRecommendListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        FriendHttpRequest.getFriendRecommendListRequest(saleManId, String.valueOf(pageIndex), "10", new GetFriendRecommendListHandler(this, LOAD_ACTION.LOADERMORE));
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    	if (arg2 < 2) {
			return;
		}
        FriendRecommendListItem item = adapter.getItem(arg2 - 2);
        if (item == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), ActIndependentCustomerDetail.class);
        intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, item.getClient_id());
        startActivityForResult(intent, IntentKey.REQUEST_CODE_CUSTOMER_DETAIL);
    }
    
    class GetFriendDetailHandler extends HttpResponseHandlerFragment<IndependentFriendDetailFragment> {

        public GetFriendDetailHandler(IndependentFriendDetailFragment context) {
            super(context);
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
                FriendDetailBean bean = JsonUtil.toObject(new String(content), FriendDetailBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        FriendDetailData data = bean.getData();
                        if (data != null) {
                            tvFriendName.setText(data.getName());
                            tvCustomerCount.setText(data.getClient_num());
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
            if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
        }
        
    }
    
    class GetFriendRecommendListHandler extends HttpResponseHandlerFragment<IndependentFriendDetailFragment> {
        
        private LOAD_ACTION action;
        
        public GetFriendRecommendListHandler(IndependentFriendDetailFragment context, LOAD_ACTION action) {
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
                    showShortToast("暂无报备客户");
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
            if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentKey.REQUEST_CODE_CUSTOMER_DETAIL) {
            if (data != null) {
                isEdit = data.getBooleanExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, false);
            }
            if (isEdit) {
                onRefresh();
            }
        }
    }

}
