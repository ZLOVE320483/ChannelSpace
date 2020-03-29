package com.zlove.frag.independent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.act.independent.ActCustomerFilter;
import com.zlove.act.independent.ActCustomerSearch;
import com.zlove.act.independent.ActIndependentCustomerAdd;
import com.zlove.act.independent.ActIndependentCustomerDetail;
import com.zlove.adapter.independent.CustomerAdapter;
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
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class IndependentCustomerFragment extends BaseFragment implements OnClickListener, OnItemClickListener, PullableViewListener {
    
    private TextView etSearch = null;
    private ImageView btnAddCustomer = null;
    private Button btnClassify = null;
    private PullListView listView = null;
    private CustomerAdapter adapter;
    private List<ClientListItem> customerInfos = new ArrayList<ClientListItem>();
    
    private int pageIndex = 0;
    
    private String keyWord = "";
    private String categoryId = "";
    private String houseType = "";
    private String propertyType = "";
    private String status = "";
    private String isDisabled = "";

    private Dialog loadingDialog;
    boolean isEdit = false;

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_customer;
    }

    @Override
    protected void setUpView(View view) {
        ((TextView) view.findViewById(R.id.id_title)).setText("客户");

        loadingDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        
        etSearch = (TextView) view.findViewById(R.id.id_search);
        etSearch.setText("搜客户");
        etSearch.setOnClickListener(this);
        
        btnAddCustomer = (ImageView) view.findViewById(R.id.id_confirm);
        btnClassify = (Button) view.findViewById(R.id.id_classify);
        listView = (PullListView) view.findViewById(R.id.id_listview);
        
        adapter = new CustomerAdapter(customerInfos, getActivity());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        if (customerInfos.size() < 10) {
            listView.setPullLoadEnable(false);
        } else {
            listView.setPullLoadEnable(true);
        }
        listView.setPullRefreshEnable(true);
        listView.setHeaderDividersEnabled(false);
        listView.setFooterDividersEnabled(false);
        
        btnAddCustomer.setOnClickListener(this);
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
        if (v == btnAddCustomer) {
            Intent intent = new Intent(getActivity(), ActIndependentCustomerAdd.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_ADD_CUSTOMER);
        } else if (v == btnClassify) {
            Intent intent = new Intent(getActivity(), ActCustomerFilter.class);
            ClientFilterBean bean = new ClientFilterBean();
            bean.setCategoryId(categoryId);
            bean.setHouseType(houseType);
            bean.setPropertyType(propertyType);
            bean.setStatus(status);
            bean.setIsDisable(isDisabled);
            intent.putExtra(IntentKey.INTENT_KEY_CLIENT_FILTER_ITEM, bean);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_CLIENT_FILTER);
        } else if (v == etSearch) {
            Intent intent = new Intent(getActivity(), ActCustomerSearch.class);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IntentKey.REQUEST_CODE_ADD_CUSTOMER) {
                onRefresh();
            } else if (requestCode == IntentKey.REQUEST_CODE_CLIENT_FILTER) {
                if (data != null) {
                    ClientFilterBean bean = (ClientFilterBean) data.getExtras().get(IntentKey.INTENT_KEY_CLIENT_FILTER_ITEM);
                    categoryId = bean.getCategoryId();
                    houseType = bean.getHouseType();
                    propertyType = bean.getPropertyType();
                    status = bean.getStatus();
                    isDisabled = bean.getIsDisable();
                    getClientList();
                }
            } else if (requestCode == IntentKey.REQUEST_CODE_CUSTOMER_DETAIL) {
                if (data != null) {
                    isEdit = data.getBooleanExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, false);
                }
                if (isEdit) {
                    onRefresh();
                }
            }
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        if (ListUtils.isEmpty(customerInfos)) {
            return;
        }
        
        ClientListItem item = adapter.getItem(position - 1);
        if (item == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), ActIndependentCustomerDetail.class);
        intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, item.getClient_id());
        startActivityForResult(intent, IntentKey.REQUEST_CODE_CUSTOMER_DETAIL);
    }
    
    class GetClientListHandler extends HttpResponseHandlerFragment<IndependentCustomerFragment> {

        private LOAD_ACTION action;
        
        public GetClientListHandler(IndependentCustomerFragment context, LOAD_ACTION action) {
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
                    showShortToast("暂无客户信息");
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
            if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
        }
        
    }

    @Override
    public void onRefresh() {
        getClientList();
    }

    @Override
    public void onLoadMore() {
        ClientHttpRequest.getClientListRequest(keyWord, categoryId, houseType, propertyType, String.valueOf(pageIndex), "10", status, isDisabled, new GetClientListHandler(this,
            LOAD_ACTION.LOADERMORE));
    }
    
    private void getClientList() {
        ClientHttpRequest.getClientListRequest(keyWord, categoryId, houseType, propertyType, "0", "10", status, isDisabled, new GetClientListHandler(this,
            LOAD_ACTION.ONREFRESH));
    }

}
