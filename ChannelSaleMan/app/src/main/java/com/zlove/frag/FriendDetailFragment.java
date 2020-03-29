package com.zlove.frag;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zlove.act.ActCustomerFilter;
import com.zlove.act.ActCustomerFromIndependentDetail;
import com.zlove.act.ActFriendCustomerSearch;
import com.zlove.act.ActFriendEdit;
import com.zlove.adapter.FriendDetailAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.client.ClientFilterBean;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.friend.FriendInfoBean;
import com.zlove.bean.friend.FriendInfoData;
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


public class FriendDetailFragment extends BaseFragment implements OnClickListener, OnItemClickListener, PullableViewListener {
    
    private PullListView listView;
    private FriendDetailAdapter adapter;
    List<FriendRecommendListItem> infos = new ArrayList<FriendRecommendListItem>();
    
    private TextView tvSearch;
    private TextView tvTitle;
    private Button btnEdit;
    private Button btnClassify;
    
    private TextView tvCount;
    private String friendId = "";
    private int pageIndex = 0;
    
    private String categoryId = "";
    private String houseType = "";
    private String propertyType = "";
    private String status = "";
    private String isDisabled = "";

    private boolean isEdit = false;
    private boolean friendIsEdit = false;
    
    private FriendInfoData data = null;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_friend_detail;
    }

    @Override
    protected void setUpView(View view) {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_FRIEND_ID)) {
                friendId = intent.getStringExtra(IntentKey.INTENT_KEY_FRIEND_ID);
            }
        }
        
        view.findViewById(R.id.id_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra(IntentKey.INTENT_KEY_FRIEND_IS_EDIT, friendIsEdit);
				finishActivity(intent);
			}
		});
        tvTitle = (TextView) view.findViewById(R.id.id_title);
        tvTitle.setText("好友详情");
        tvSearch = (TextView) view.findViewById(R.id.id_search);
        tvSearch.setOnClickListener(this);
        
        tvCount = (TextView) view.findViewById(R.id.tv_report_count);
        
        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        
        btnEdit = (Button) view.findViewById(R.id.id_edit);
        btnEdit.setVisibility(View.VISIBLE);
        btnEdit.setText("编辑");
        btnEdit.setOnClickListener(this);
        
        btnClassify = (Button) view.findViewById(R.id.id_classify);
        btnClassify.setOnClickListener(this);
        
        if (adapter == null) {
            adapter = new FriendDetailAdapter(infos, getActivity());
        }
        
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        FriendHttpRequest.getFriendInfoRequest(friendId, new GetFriendInfoHandler(this));
        if (ListUtils.isEmpty(infos)) {
            FriendHttpRequest.getFriendRecommendListRequest(friendId, "0", "10", "", categoryId, houseType, propertyType, status, 
                ChannelCookie.getInstance().getCurrentHouseId(), isDisabled, new GetFriendRecommendListHandler(this, LOAD_ACTION.ONREFRESH));
        }
    }
    
    @Override
    public void onClick(View v) {
        if (v == btnEdit) {
            Intent intent = new Intent(getActivity(), ActFriendEdit.class);
            intent.putExtra(IntentKey.INTENT_KEY_FRIEND_INFO_DATA, data);
            intent.putExtra(IntentKey.INTENT_KEY_FRIEND_ID, friendId);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_FRIEND_EDIT);
        } else if (v == btnClassify) {
            Intent intent = new Intent(getActivity(), ActCustomerFilter.class);
            ClientFilterBean bean = new ClientFilterBean();
            bean.setCategoryId(categoryId);
            bean.setHouseType(houseType);
            bean.setPropertyType(propertyType);
            bean.setStatus(status);
            bean.setIsDisable(isDisabled);
            intent.putExtra(IntentKey.INTENT_KEY_CLIENT_FILTER_ITEM, bean);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_CLIENT_FILTER_INDEPENDENT);
        } else if (v == tvSearch) {
            Intent intent = new Intent(getActivity(), ActFriendCustomerSearch.class);
            intent.putExtra(IntentKey.INTENT_KEY_FRIEND_ID, friendId);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_FRIEND_EDIT);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
    public void onRefresh() {
        categoryId = "";
        houseType = "";
        propertyType = "";
        status = "";
        isDisabled = "";
        FriendHttpRequest.getFriendRecommendListRequest(friendId, "0", "10", "", categoryId, houseType, propertyType, status, 
            ChannelCookie.getInstance().getCurrentHouseId(), isDisabled, new GetFriendRecommendListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        FriendHttpRequest.getFriendRecommendListRequest(friendId, String.valueOf(pageIndex), "10", "", categoryId, houseType, propertyType, status, 
            ChannelCookie.getInstance().getCurrentHouseId(), isDisabled, new GetFriendRecommendListHandler(this, LOAD_ACTION.LOADERMORE));
    }
    
    class GetFriendInfoHandler extends HttpResponseHandlerFragment<FriendDetailFragment> {

        public GetFriendInfoHandler(FriendDetailFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                FriendInfoBean bean = JsonUtil.toObject(new String(content), FriendInfoBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        data = bean.getData();
                        tvTitle.setText(data.getFriend_name());
                        tvCount.setText("他报备了:" + data.getTotal_num() + "组");
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
        }
    }
    
    class GetFriendRecommendListHandler extends HttpResponseHandlerFragment<FriendDetailFragment> {

        private LOAD_ACTION action;
        
        public GetFriendRecommendListHandler(FriendDetailFragment context, LOAD_ACTION action) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode == Activity.RESULT_OK) {
			if (requestCode == IntentKey.REQUEST_CODE_FRIEND_EDIT) {
				friendIsEdit = true;
		        FriendHttpRequest.getFriendInfoRequest(friendId, new GetFriendInfoHandler(this));
			} else if (requestCode == IntentKey.REQUEST_CODE_CLIENT_FILTER_INDEPENDENT) {
                if (data != null) {
                    ClientFilterBean bean = (ClientFilterBean) data.getExtras().get(IntentKey.INTENT_KEY_CLIENT_FILTER_ITEM);
                    categoryId = bean.getCategoryId();
                    houseType = bean.getHouseType();
                    propertyType = bean.getPropertyType();
                    status = bean.getStatus();
                    isDisabled = bean.getIsDisable();
                    FriendHttpRequest.getFriendRecommendListRequest(friendId, "0", "10", "", categoryId, houseType, propertyType, status, 
                        ChannelCookie.getInstance().getCurrentHouseId(), isDisabled, new GetFriendRecommendListHandler(this, LOAD_ACTION.ONREFRESH));
                }
            } else if (requestCode == IntentKey.REQUEST_CODE_CLIENT_EDIT && data != null) {
                isEdit = data.getBooleanExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, false);
                if (isEdit) {
                    onRefresh();
                }
            }
		}
    }
}
