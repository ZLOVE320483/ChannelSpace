package com.zlove.frag;

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

import com.zlove.act.ActAddCustomerTraceRecord;
import com.zlove.act.ActCustomerEdit;
import com.zlove.adapter.CustomerTraceRecordAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.DateTimePickDialogUtil;
import com.zlove.base.util.DateTimePickDialogUtil.SetRevisitTimeListener;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.DialogManager.CustomerEffectSelectListener;
import com.zlove.base.util.DialogManager.DecideVisitFromSelfListener;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.util.UIUtil;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.client.ClientDetailBean;
import com.zlove.bean.client.ClientDetailData;
import com.zlove.bean.client.ClientTraceBean;
import com.zlove.bean.client.ClientTraceData;
import com.zlove.bean.client.ClientTraceListItem;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class CustomerFromSelfDetailFragment extends BaseFragment implements OnClickListener, CustomerEffectSelectListener, SetRevisitTimeListener, PullableViewListener, DecideVisitFromSelfListener {
    
    private PullListView listView;
    private List<ClientTraceListItem> infos = new ArrayList<>();
    private CustomerTraceRecordAdapter adapter;
    private View noRecordView;

    private Button btnEdit;
    
    private View headView;
    private TextView tvCustomerName;
    private TextView tvCustomerPhone;
    private ImageView ivCustomerIntention;
    private TextView tvCustomerIntentionDesc;
    private ImageView ivCustomerMessage;
    private ImageView ivCustomerCall;
    private TextView tvProjectArea;
    private TextView tvProjectPrice;
    private TextView tvProjectLayout;
    private TextView tvProjectType;
    private TextView tvDecideVisit;
    private TextView tvDate1;
    private TextView tvTime1;
    private TextView tvDate2;
    private TextView tvTime2;
    private TextView tvDate3;
    private TextView tvTime3;
    private TextView tvDate4;
    private TextView tvTime4;
    
    //-----客户进度----
    private ImageView ivStatus1;
    private ImageView ivStatus2;
    private ImageView ivStatus3;
    private ImageView ivStatus4;
    
    private View line_1_1;
    private View line_2_1;
    private View line_2_2;
    private View line_3_1;
    private View line_3_2;
    private View line_4_1;
    //----客户进度----
    
    private TextView tvIsEffect;
    private View selectDateTimeView;
    private TextView tvDateTime;
    private TextView tvAddTraceRecord;

    private String clientId = "";
    private String houseId = "";
    private ClientDetailData data = null;
    private String customerPhone = "";
    private String revisitTime = "";
    
    private Dialog loadingDialog;
    
    private int pageIndex = 0;
    
    private boolean isEdit = false;
    private String isValid = "1";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_customer_detail;
    }

    @Override
    protected void setUpView(View view) {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_CLIENT_ID)) {
                clientId = intent.getStringExtra(IntentKey.INTENT_KEY_CLIENT_ID);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_ID)) {
                houseId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
            }
        }

        houseId = TextUtils.isEmpty(houseId) ? ChannelCookie.getInstance().getCurrentHouseId() : houseId;
        
        loadingDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        
        view.findViewById(R.id.id_back).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, isEdit);
                finishActivity(result);
            }
        });
        
        ((TextView) view.findViewById(R.id.id_title)).setText("客户详情");
        
        btnEdit = (Button) view.findViewById(R.id.id_edit);
        btnEdit.setVisibility(View.VISIBLE);
        btnEdit.setText("编辑");
        btnEdit.setOnClickListener(this);
        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        
        initHeadView();
        
        if (adapter == null) {
            adapter = new CustomerTraceRecordAdapter(infos, getActivity());
        }
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (data == null) {
            ClientHttpRequest.getClientInfoRequest(clientId, houseId, new GetClientInfoHandler(this));
        }
        if (ListUtils.isEmpty(infos)) {
            ClientHttpRequest.getClientTraceListRequest(clientId, "0", "10", houseId, new GetTraceListHandler(this, LOAD_ACTION.ONREFRESH));
        }
    }

    @SuppressLint("InflateParams")
    private void initHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.list_head_view_customer_detail_from_self, null);
        noRecordView = headView.findViewById(R.id.no_record_cotainer);
        tvCustomerName = (TextView) headView.findViewById(R.id.id_customer_name);
        tvCustomerPhone = (TextView) headView.findViewById(R.id.id_customer_phone);
        ivCustomerIntention = (ImageView) headView.findViewById(R.id.customer_intention_icon);
        tvCustomerIntentionDesc = (TextView) headView.findViewById(R.id.id_customer_intention);
        ivCustomerMessage = (ImageView) headView.findViewById(R.id.id_customer_message);
        ivCustomerCall = (ImageView) headView.findViewById(R.id.id_customer_call);
        tvProjectArea = (TextView) headView.findViewById(R.id.id_project_area);
        tvProjectPrice = (TextView) headView.findViewById(R.id.id_project_price);
        tvProjectLayout = (TextView) headView.findViewById(R.id.id_project_house_layout);
        tvProjectType = (TextView) headView.findViewById(R.id.id_project_product);
        tvDecideVisit = (TextView) headView.findViewById(R.id.visit_decide);
        tvDate1 = (TextView) headView.findViewById(R.id.date_1);
        tvTime1 = (TextView) headView.findViewById(R.id.time_1);
        tvDate2 = (TextView) headView.findViewById(R.id.date_2);
        tvTime2 = (TextView) headView.findViewById(R.id.time_2);
        tvDate3 = (TextView) headView.findViewById(R.id.date_3);
        tvTime3 = (TextView) headView.findViewById(R.id.time_3);
        tvDate4 = (TextView) headView.findViewById(R.id.date_4);
        tvTime4 = (TextView) headView.findViewById(R.id.time_4);
        
        //-----客户进度----
        ivStatus1 = (ImageView) headView.findViewById(R.id.id_state_1);
        ivStatus2 = (ImageView) headView.findViewById(R.id.id_state_2);
        ivStatus3 = (ImageView) headView.findViewById(R.id.id_state_3);
        ivStatus4 = (ImageView) headView.findViewById(R.id.id_state_4);
        line_1_1 = headView.findViewById(R.id.id_state_1_1);
        line_2_1 = headView.findViewById(R.id.id_state_2_1);
        line_2_2 = headView.findViewById(R.id.id_state_2_2);
        line_3_1 = headView.findViewById(R.id.id_state_3_1);
        line_3_2 = headView.findViewById(R.id.id_state_3_2);
        line_4_1 = headView.findViewById(R.id.id_state_4_1);
      //-----客户进度----
        
        tvAddTraceRecord= (TextView) headView.findViewById(R.id.add_customer_trace_record);
        
        tvIsEffect = (TextView) headView.findViewById(R.id.is_effect);
        
        selectDateTimeView = headView.findViewById(R.id.select_date_time);
        tvDateTime = (TextView) headView.findViewById(R.id.date_time);
        listView.addHeaderView(headView);
        
        ivCustomerMessage.setOnClickListener(this);
        ivCustomerCall.setOnClickListener(this);
        tvDecideVisit.setOnClickListener(this);
        selectDateTimeView.setOnClickListener(this);
        tvAddTraceRecord.setOnClickListener(this);
    }

    @Override
    public void selectCustomerEffect(String effectOrInvalid) {
        ClientHttpRequest.setValidedRequest(clientId, houseId, effectOrInvalid, new DecideEffectHandler(this));
    }

    @Override
    public void onClick(View v) {
        if (v == btnEdit) {
            Intent intent = new Intent(getActivity(), ActCustomerEdit.class);
            intent.putExtra(IntentKey.INTENT_KEY_CLIENT_DETAIL_ITEM, data);
            intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_CLIENT_EDIT);
        } else if (v == selectDateTimeView) {
            DateTimePickDialogUtil util = new DateTimePickDialogUtil(getActivity(), this);
            util.showDateSelectDialog("设置回访日期");
        } else if (v == tvAddTraceRecord) {
            Intent intent = new Intent(getActivity(), ActAddCustomerTraceRecord.class);
            intent.putExtra(IntentKey.INTENT_KEY_INDEPENDENT_ID, data.getBroker_id());
            intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_ADD_CUSTOMER_RECORD);
        } else if (v == ivCustomerMessage) {
            message(customerPhone);
        } else if (v == ivCustomerCall) {
            call(customerPhone);
        } else if (v == tvDecideVisit) {
            DialogManager.showDecideVisitFromeSelfDialog(getActivity(), this);
        }
    }   
    
    private void call(String phone) {
        Uri uri = Uri.parse("tel:" + phone);   
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);     
        startActivity(intent);  
    }
    
    private void message(String phone) {
        Uri uri = Uri.parse("smsto:" + phone);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(sendIntent);
    }

    @Override
    public void setRevisitTime(String time) {
        this.revisitTime = time;
        ClientHttpRequest.setRevisitTimeRequest(clientId, houseId, time, new SetRevisitTimeHandler(this));
    }
    
    private void setClientInfo() {
    	isValid = data.getIs_valided();
        tvCustomerName.setText(data.getClient_name());
        tvCustomerPhone.setText(data.getClient_phone());
        tvCustomerIntentionDesc.setText(data.getClient_category_desc());

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
        if (data.getFrom_type().equals("0")) {
            tvProjectArea.setText("来电");
        } else {
            tvProjectArea.setText("来访");
        }
        customerPhone = data.getClient_phone();
        tvDateTime.setText(data.getRe_visite_time());
        
        if (data.getClient_category_id().equals("1")) {
            ivCustomerIntention.setImageResource(R.drawable.customer_type_a);
        } else if (data.getClient_category_id().equals("2")) {
            ivCustomerIntention.setImageResource(R.drawable.customer_type_b);
        } else if (data.getClient_category_id().equals("3")) {
            ivCustomerIntention.setImageResource(R.drawable.customer_type_c);
        } else if (data.getClient_category_id().equals("4")) {
            ivCustomerIntention.setImageResource(R.drawable.customer_type_d);
        }
        
        if (isValid.equals("0")) {
			tvIsEffect.setText("无效");
		} else if (isValid.equals("1")) {
			tvIsEffect.setText("有效");
		}
        
        String status = data.getStatus();
        if (!TextUtils.isEmpty(data.getRecommend_time())) {
            String[] recommendTime = data.getRecommend_time().split(" ");
            tvDate1.setText(recommendTime[0]);
            tvTime1.setText(recommendTime[1]);
        }
        if (!TextUtils.isEmpty(data.getVisit_time())) {
            String[] visitTime = data.getVisit_time().split(" ");
            tvDate2.setText(visitTime[0]);
            tvTime2.setText(visitTime[1]);
        }
        if (!TextUtils.isEmpty(data.getIntent_time())) {
			String[] intentTime = data.getIntent_time().split(" ");
			tvDate3.setText(intentTime[0]);
			tvTime3.setText(intentTime[1]);
		}
        if (!TextUtils.isEmpty(data.getOrder_time())) {
			String[] orderTime = data.getOrder_time().split(" ");
			tvDate4.setText(orderTime[0]);
			tvTime4.setText(orderTime[1]);
		}
        
        if (status.equals("8")) {
            ivStatus1.setImageResource(R.drawable.ic_checked);
            ivStatus2.setImageResource(R.drawable.ic_checked);
            ivStatus3.setImageResource(R.drawable.ic_checked);
            ivStatus4.setImageResource(R.drawable.ic_checked);
            
            line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            
            tvDecideVisit.setVisibility(View.GONE);
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.VISIBLE);
            tvTime2.setVisibility(View.VISIBLE);
            tvDate3.setVisibility(View.VISIBLE);
            tvTime3.setVisibility(View.VISIBLE);
            tvDate4.setVisibility(View.VISIBLE);
            tvTime4.setVisibility(View.VISIBLE);
            
            // TODO
            
        } else if (status.equals("7")) {
            ivStatus1.setImageResource(R.drawable.ic_checked);
            ivStatus2.setImageResource(R.drawable.ic_checked);
            ivStatus3.setImageResource(R.drawable.ic_checked);
            ivStatus4.setImageResource(R.drawable.ic_checked);
            
            line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            
            tvDecideVisit.setVisibility(View.GONE);
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.VISIBLE);
            tvTime2.setVisibility(View.VISIBLE);
            tvDate3.setVisibility(View.VISIBLE);
            tvTime3.setVisibility(View.VISIBLE);
            tvDate4.setVisibility(View.VISIBLE);
            tvTime4.setVisibility(View.VISIBLE);
            
            // TODO

            
        }  else if (status.equals("6")) {
            ivStatus1.setImageResource(R.drawable.ic_checked);
            ivStatus2.setImageResource(R.drawable.ic_checked);
            ivStatus3.setImageResource(R.drawable.ic_checked);
            ivStatus4.setImageResource(R.drawable.ic_unchecked);
            
            line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            
            tvDecideVisit.setVisibility(View.GONE);
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.VISIBLE);
            tvTime2.setVisibility(View.VISIBLE);
            tvDate3.setVisibility(View.VISIBLE);
            tvTime3.setVisibility(View.VISIBLE);
            tvDate4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            
            // TODO
            
        } else if (status.equals("5")) {
            ivStatus1.setImageResource(R.drawable.ic_checked);
            ivStatus2.setImageResource(R.drawable.ic_checked);
            ivStatus3.setImageResource(R.drawable.ic_unchecked);
            ivStatus4.setImageResource(R.drawable.ic_unchecked);
            
            line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            
            tvDecideVisit.setVisibility(View.GONE);
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.VISIBLE);
            tvTime2.setVisibility(View.VISIBLE);
            tvDate3.setVisibility(View.GONE);
            tvTime3.setVisibility(View.GONE);
            tvDate4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            
        } else if (status.equals("1") || status.equals("2") || status.equals("3") || status.equals("4")) {
            ivStatus1.setImageResource(R.drawable.ic_checked);
            ivStatus2.setImageResource(R.drawable.ic_unchecked);
            ivStatus3.setImageResource(R.drawable.ic_unchecked);
            ivStatus4.setImageResource(R.drawable.ic_unchecked);
            
            line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            
            tvDecideVisit.setVisibility(View.VISIBLE);
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.GONE);
            tvTime2.setVisibility(View.GONE);
            tvDate3.setVisibility(View.GONE);
            tvTime3.setVisibility(View.GONE);
            tvDate4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
        }
    }
    
    class GetClientInfoHandler extends HttpResponseHandlerFragment<CustomerFromSelfDetailFragment> {

        public GetClientInfoHandler(CustomerFromSelfDetailFragment context) {
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
    
    class GetTraceListHandler extends HttpResponseHandlerFragment<CustomerFromSelfDetailFragment> {

        private LOAD_ACTION action;
        
        public GetTraceListHandler(CustomerFromSelfDetailFragment context, LOAD_ACTION action) {
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
            if (content != null) {
                ClientTraceBean bean = JsonUtil.toObject(new String(content), ClientTraceBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ClientTraceData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo pageInfo = data.getPage_info();
                            pageIndex = pageInfo.getPage_index();
                            List<ClientTraceListItem> tmpList = data.getTrace_list();
                            if (tmpList.size() < 10) {
                                listView.setPullLoadEnable(false);
                            } else {
                                listView.setPullLoadEnable(true);
                            }
                            if (action == LOAD_ACTION.ONREFRESH) {
                                infos.clear();
                            }
                            infos.addAll(tmpList);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
            }
            if (ListUtils.isEmpty(infos)) {
                noRecordView.setVisibility(View.VISIBLE);
            } else {
                noRecordView.setVisibility(View.GONE);
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
        }
        
    }

    @Override
    public void onRefresh() {
        ClientHttpRequest.getClientTraceListRequest(clientId, "0", "10", houseId, new GetTraceListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        ClientHttpRequest.getClientTraceListRequest(clientId, String.valueOf(pageIndex), "10", houseId, new GetTraceListHandler(this, LOAD_ACTION.LOADERMORE));
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IntentKey.REQUEST_CODE_ADD_CUSTOMER_RECORD) {
                onRefresh();
            } else if (requestCode == IntentKey.REQUEST_CODE_CLIENT_EDIT && data != null) {
                isEdit = data.getBooleanExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, false);
                if (isEdit) {
                    ClientHttpRequest.getClientInfoRequest(clientId, houseId, new GetClientInfoHandler(this));
                }
            }
        }
    }
    
    class SetRevisitTimeHandler extends HttpResponseHandlerFragment<CustomerFromSelfDetailFragment> {

        public SetRevisitTimeHandler(CustomerFromSelfDetailFragment context) {
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
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        showShortToast("设置成功");
                        tvDateTime.setText(revisitTime);
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
    
    class DecideEffectHandler extends HttpResponseHandlerFragment<CustomerFromSelfDetailFragment> {

        public DecideEffectHandler(CustomerFromSelfDetailFragment context) {
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
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                    	isEdit = true;
                        showShortToast("设置成功");
                        ClientHttpRequest.getClientInfoRequest(clientId, houseId, new GetClientInfoHandler(getFragment()));
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

	@Override
	public void decide() {
		ClientHttpRequest.setVisitedRequest(clientId, houseId, new SetVisitHandler(this));
	}

    class SetVisitHandler extends HttpResponseHandlerFragment<CustomerFromSelfDetailFragment> {

		public SetVisitHandler(CustomerFromSelfDetailFragment context) {
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
				CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
				if (bean != null) {
					if (bean.getStatus() == 200) {
						isEdit = true;
						showShortToast("设置成功");
						ClientHttpRequest.getClientInfoRequest(clientId, houseId, new GetClientInfoHandler(getFragment()));
					} else {
						showShortToast(bean.getMessage());
					}
				}
			}
		}
		
		
		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] content,
				Throwable error) {
			super.onFailure(statusCode, headers, content, error);
		}
		
		@Override
		public void onFinish() {
			super.onFinish();
		}
    	
    }
}
