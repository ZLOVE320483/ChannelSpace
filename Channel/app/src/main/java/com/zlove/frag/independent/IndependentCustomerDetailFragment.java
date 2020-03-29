package com.zlove.frag.independent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.act.independent.ActIndependentAddCustomerTraceRecord;
import com.zlove.act.independent.ActIndependentCustomerEdit;
import com.zlove.act.independent.ActProjectSelectForCustomer;
import com.zlove.adapter.independent.CustomerDetailAdapter;
import com.zlove.adapter.independent.CustomerDetailAdapter.CustomerActionListener;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.CommonConfirmDialog;
import com.zlove.base.widget.CommonConfirmDialog.ConfirmListener;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.client.ClientDetailBean;
import com.zlove.bean.client.ClientDetailData;
import com.zlove.bean.client.ClientRecommendHouseBean;
import com.zlove.bean.client.ClientRecommendHouseData;
import com.zlove.bean.client.ClientRecommendHouseListItem;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class IndependentCustomerDetailFragment extends BaseFragment implements OnClickListener, PullableViewListener, CustomerActionListener {
    
    private PullListView listView;
    private List<ClientRecommendHouseListItem> infos = new ArrayList<ClientRecommendHouseListItem>();
    private CustomerDetailAdapter adapter;

    private Button btnEdit;
    private View reportProjectView;
    
    private View headView;
    private TextView tvCustomerName;
    private TextView tvCustomerPhone;
    private ImageView ivCustomerIntention;
    private TextView tvCustomerIntention;
    private TextView tvProjectArea;
    private TextView tvProjectPrice;
    private TextView tvProjectLayout;
    private TextView tvProjectType;
    private ImageView ivCustomerCall;
    
    private String clientId = "";
    private ClientDetailData data = null;

    private int pageIndex = 0;
    private String projectId = "";
    private String saleManName = "";
    private String saleManId = "";
    
    boolean isFirst = true;
    boolean isEdit = false;
    
    private Dialog loadingDialog;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_customer_detail;
    }

    @Override
    protected void setUpView(View view) {
        
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_CLIENT_ID)) {
                clientId = intent.getStringExtra(IntentKey.INTENT_KEY_CLIENT_ID);
            }
        }
        
        view.findViewById(R.id.id_back).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, isEdit);
                getActivity().setResult(Activity.RESULT_OK, data);
                finishActivity();
            }
        });

        loadingDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        
        ((TextView) view.findViewById(R.id.id_title)).setText("客户详情");
        
        reportProjectView = view.findViewById(R.id.id_confirm);
        reportProjectView.setOnClickListener(this);

        btnEdit = (Button) view.findViewById(R.id.id_edit);
        btnEdit.setVisibility(View.VISIBLE);
        btnEdit.setText("编辑");
        btnEdit.setOnClickListener(this);
        
        listView = (PullListView) view.findViewById(R.id.id_listview);
        
        listView.setPullableViewListener(this);
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(false);
        
        initHeadView();
        
        adapter = new CustomerDetailAdapter(infos, getActivity(), this);
        adapter.setClientId(clientId);
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (data == null) {
            ClientHttpRequest.getClientInfoRequest(clientId, new GetClientInfoHandler(this));
        }
    }
    
    @SuppressLint("InflateParams") 
    private void initHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.list_customer_detail_head_view, null);
        
        tvCustomerName = (TextView) headView.findViewById(R.id.id_customer_name);
        tvCustomerPhone = (TextView) headView.findViewById(R.id.id_customer_phone);
        ivCustomerIntention = (ImageView) headView.findViewById(R.id.customer_intention_icon);
        tvCustomerIntention = (TextView) headView.findViewById(R.id.id_customer_intention);
        tvProjectArea = (TextView) headView.findViewById(R.id.id_project_area);
        tvProjectPrice = (TextView) headView.findViewById(R.id.id_project_price);
        tvProjectLayout = (TextView) headView.findViewById(R.id.id_project_house_layout);
        tvProjectType = (TextView) headView.findViewById(R.id.id_project_product);
        
        ivCustomerCall = (ImageView) headView.findViewById(R.id.id_customer_call);
        
        ivCustomerCall.setOnClickListener(this);
        
        listView.addHeaderView(headView);
    }

    @Override
    public void onClick(View v) {
        if (v == reportProjectView) {
            Intent intent = new Intent(getActivity(), ActProjectSelectForCustomer.class);
            intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_PROJECT);
        } else if (v == btnEdit) {
            Intent intent = new Intent(getActivity(), ActIndependentCustomerEdit.class);
            intent.putExtra(IntentKey.INTENT_KEY_CLIENT_DETAIL_ITEM, data);
            intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_CLIENT_EDIT);
        } else if (v == ivCustomerCall) {
            if (data == null) {
                return;
            }
            Uri uri = Uri.parse("tel:" + data.getClient_phone());   
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);     
            startActivity(intent);  
        }
    }
    
    @Override
    public void onRefresh() {
        ClientHttpRequest.getClientRecommendHouseListRequest(clientId, "0", "10", new GetClientRecommendHouseListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        ClientHttpRequest.getClientRecommendHouseListRequest(clientId, String.valueOf(pageIndex), "10", new GetClientRecommendHouseListHandler(this, LOAD_ACTION.LOADERMORE));
    }
    
    private void setClientInfo() {
        tvCustomerName.setText(data.getClient_name());
        tvCustomerPhone.setText(data.getClient_phone());
        tvCustomerIntention.setText(data.getClient_category_desc());
        tvProjectArea.setText(data.getIntent_location_desc());
        
        String minPrice = "0";
        String maxPrice = "不限";
        if (!TextUtils.isEmpty(data.getIntent_price_min())) {
            minPrice = data.getIntent_price_min();
        }
        if (TextUtils.isEmpty(data.getIntent_price_max()) || data.getIntent_price_max().equals("1000")) {
            maxPrice = "不限";
        } else {
            maxPrice = data.getIntent_price_max() + "万";
        }
        
        tvProjectPrice.setText(minPrice + "-" + maxPrice);
        tvProjectLayout.setText(data.getHouse_types_desc());
        tvProjectType.setText(data.getProperty_types_desc());
        
        if (data.getClient_category_id().equals("1")) {
            ivCustomerIntention.setBackgroundResource(R.drawable.customer_type_a);
        } else if (data.getClient_category_id().equals("2")) {
            ivCustomerIntention.setBackgroundResource(R.drawable.customer_type_b);
        } else if (data.getClient_category_id().equals("3")) {
            ivCustomerIntention.setBackgroundResource(R.drawable.customer_type_c);
        } else if (data.getClient_category_id().equals("4")) {
            ivCustomerIntention.setBackgroundResource(R.drawable.customer_type_d);
        }
    }
    
    class GetClientInfoHandler extends HttpResponseHandlerFragment<IndependentCustomerDetailFragment> {

        public GetClientInfoHandler(IndependentCustomerDetailFragment context) {
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
                ClientDetailBean bean = JsonUtil.toObject(new String(content), ClientDetailBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        data = bean.getData();
                        if (data != null) {
                            setClientInfo();
                            ClientHttpRequest.getClientRecommendHouseListRequest(clientId, "0", "10", new GetClientRecommendHouseListHandler(getFragment(), LOAD_ACTION.ONREFRESH));
                        } else {
                            showShortToast("获取客户详情失败");
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                } else {
                    showShortToast("获取客户详情失败");
                }
            } else {
                showShortToast("获取客户详情失败");
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
    
    class GetClientRecommendHouseListHandler extends HttpResponseHandlerFragment<IndependentCustomerDetailFragment> {

        private LOAD_ACTION action;
        
        public GetClientRecommendHouseListHandler(IndependentCustomerDetailFragment context, LOAD_ACTION action) {
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
                ClientRecommendHouseBean bean = JsonUtil.toObject(new String(content), ClientRecommendHouseBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ClientRecommendHouseData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo pageInfo = data.getPage_info();
                            pageIndex = pageInfo.getPage_index();
                            if (action == LOAD_ACTION.ONREFRESH) {
                                infos.clear();
                            }
                            List<ClientRecommendHouseListItem> tmpList = data.getRecommend_house_list();
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
                if (ListUtils.isEmpty(infos) && isFirst) {
                    showShortToast("该客户还未报备给任何楼盘");
                    isFirst = false;
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
            listView.stopRefresh();
            listView.stopLoadMore();
        }
    }

    class RecommendClientByIdHandler extends HttpResponseHandlerFragment<IndependentCustomerDetailFragment> {

        public RecommendClientByIdHandler(IndependentCustomerDetailFragment context) {
            super(context);
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
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        CommonConfirmDialog dialog = new CommonConfirmDialog(getActivity(), "业务员:" + saleManName + " 向您反馈客户是否有效",
                            new ConfirmListener() {

                                @Override
                                public void confirm() {
                                    onRefresh();
                                }
                            });
                        dialog.showdialog();
                    } else {
                        showShortToast(bean.getMessage());
                    }
                } else {
                    showShortToast("报备失败,请重试");
                }
            } else {
                showShortToast("报备失败,请重试");
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
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode == Activity.RESULT_OK) {
			if (requestCode == IntentKey.REQUEST_CODE_CLIENT_EDIT) {
			    if (data != null) {
                    isEdit = data.getBooleanExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, false);
                }
			    if (isEdit) {
			        ClientHttpRequest.getClientInfoRequest(clientId, new GetClientInfoHandler(this));
                }
			} else if (requestCode == IntentKey.REQUEST_CODE_ADD_TRACE_RECORD) {
	            ClientHttpRequest.getClientRecommendHouseListRequest(clientId, "0", "10", new GetClientRecommendHouseListHandler(this, LOAD_ACTION.ONREFRESH));
			} else if (requestCode == IntentKey.REQUEST_CODE_SELECT_PROJECT) {
                projectId = data.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
                saleManId = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_ID);
                saleManName = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_NAME);
                isEdit = true;
                ClientHttpRequest.recommendClientById(clientId, projectId, saleManId, new RecommendClientByIdHandler(this));
            }
		}
    }

	@Override
	public void addTraceRecord(String houseId) {
        Intent intent = new Intent(getActivity(), ActIndependentAddCustomerTraceRecord.class);
        intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
        intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, houseId);
        startActivityForResult(intent, IntentKey.REQUEST_CODE_ADD_TRACE_RECORD);
	}

	@Override
	public void refresh() {
        ClientHttpRequest.getClientRecommendHouseListRequest(clientId, "0", "10", new GetClientRecommendHouseListHandler(this, LOAD_ACTION.ONREFRESH));
	}

}
