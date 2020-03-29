package com.zlove.frag;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zlove.act.ActCustomerFromSelfDetail;
import com.zlove.act.ActCustomerFromSelfFilter;
import com.zlove.act.ActCustomerSearch;
import com.zlove.adapter.CustomerFromSelfAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.client.ClientFilterBean;
import com.zlove.bean.client.ClientListBean;
import com.zlove.bean.client.ClientListData;
import com.zlove.bean.client.ClientListItem;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class CustomerFromSelfFragment extends BaseFragment implements OnClickListener, OnItemClickListener, PullableViewListener {
    
    private TextView etSearch = null;
    private Button btnClassify = null;
    private PullListView listView = null;
    private CustomerFromSelfAdapter adapter;
    private List<ClientListItem> customerInfos = new ArrayList<ClientListItem>();
    
    private int pageIndex = 0;
    
    private String keyWord = "";
    private String categoryId = "";
    private String houseType = "";
    private String propertyType = "";
    private String status = "";
    private String isDisabled = "";

    private boolean isEdit = false;

    private Dialog dialog;

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_customer_from_independent;
    }

    @Override
    protected void setUpView(View view) {
        dialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        
        etSearch = (TextView) view.findViewById(R.id.id_search);
        etSearch.setText("搜客户");
        etSearch.setOnClickListener(this);
        
        btnClassify = (Button) view.findViewById(R.id.id_classify);
        listView = (PullListView) view.findViewById(R.id.id_listview);
        
        adapter = new CustomerFromSelfAdapter(customerInfos, getActivity());
        adapter.setType(2);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        
        btnClassify.setOnClickListener(this);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(customerInfos)) {
            getClientList();
        }
        if (customerInfos.size() < 10) {
			listView.setPullLoadEnable(false);
		} else {
			listView.setPullLoadEnable(true);
		}
    }
    
    @Override
    public void onClick(View v) {
        if (v == btnClassify) {
            Intent intent = new Intent(getActivity(), ActCustomerFromSelfFilter.class);
            ClientFilterBean bean = new ClientFilterBean();
            bean.setCategoryId(categoryId);
            bean.setHouseType(houseType);
            bean.setPropertyType(propertyType);
            bean.setStatus(status);
            bean.setIsDisable(isDisabled);
            intent.putExtra(IntentKey.INTENT_KEY_CLIENT_FILTER_ITEM, bean);
            getActivity().startActivityForResult(intent, IntentKey.REQUEST_CODE_CLIENT_FILTER_SELF);
        } else if (v == etSearch) {
            Intent intent = new Intent(getActivity(), ActCustomerSearch.class);
            intent.putExtra(IntentKey.INTENT_KEY_CUSTOMER_TYPE, "2");
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IntentKey.REQUEST_CODE_ADD_CUSTOMER) {
                onRefresh();
            } else if (requestCode == IntentKey.REQUEST_CODE_CLIENT_FILTER_SELF) {
                if (data != null) {
                    ClientFilterBean bean = (ClientFilterBean) data.getExtras().get(IntentKey.INTENT_KEY_CLIENT_FILTER_ITEM);
                    categoryId = bean.getCategoryId();
                    houseType = bean.getHouseType();
                    propertyType = bean.getPropertyType();
                    status = bean.getStatus();
                    isDisabled = bean.getIsDisable();
                    getClientList();
                }
            } else if (requestCode == IntentKey.REQUEST_CODE_CLIENT_EDIT && data != null) {
                isEdit = data.getBooleanExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, false);
                if (isEdit) {
                    onRefresh();
                }
            }
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        ClientListItem item = adapter.getItem(position - 1);
        if (item == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), ActCustomerFromSelfDetail.class);
        intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, item.getClient_id());
        intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, ChannelCookie.getInstance().getCurrentHouseId());
        getActivity().startActivityForResult(intent, IntentKey.REQUEST_CODE_CLIENT_EDIT);
    }
    
    class GetClientListHandler extends HttpResponseHandlerFragment<CustomerFromSelfFragment> {

        private LOAD_ACTION action;
        
        public GetClientListHandler(CustomerFromSelfFragment context, LOAD_ACTION action) {
            super(context);
            this.action = action;
        }
        
        @Override
        public void onStart() {
            super.onStart();
            if (dialog != null && !dialog.isShowing()) {
				dialog.show();
            }
			
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                ClientListBean bean = JsonUtil.toObject(new String(content), ClientListBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ClientListData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo pageInfo = data.getPage_info();
                            pageIndex = pageInfo.getPage_index();
                            if (action == LOAD_ACTION.ONREFRESH) {
                                customerInfos.clear();
                            }
                            List<ClientListItem> tmpList = data.getClient_list();
                            if (tmpList.size() < 10) {
                                listView.setPullLoadEnable(false);
                            } else {
                                listView.setPullLoadEnable(true);
                            }
                            customerInfos.addAll(tmpList);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
                if (ListUtils.isEmpty(customerInfos)) {
                    listView.showNoDataIndicator("暂无客户");
                } else {
                    listView.dismissNoDataIndicator();
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
            if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
        }
        
    }

    @Override
    public void onRefresh() {
        getClientList();
    }

    @Override
    public void onLoadMore() {
        ClientHttpRequest.getClientListRequest(keyWord, categoryId, houseType, propertyType, String.valueOf(pageIndex), "10", status,
            ChannelCookie.getInstance().getCurrentHouseId(), "2", isDisabled, new GetClientListHandler(this, LOAD_ACTION.LOADERMORE));
    }
    
    private void getClientList() {
        ClientHttpRequest.getClientListRequest(keyWord, categoryId, houseType, propertyType, "0", "10", status, 
            ChannelCookie.getInstance().getCurrentHouseId(), "2", isDisabled, new GetClientListHandler(this, LOAD_ACTION.ONREFRESH));
    }

}
