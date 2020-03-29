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
import com.zlove.base.util.DialogManager.DecideVisitListener;
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


public class CustomerFromIndependentDetailFragment extends BaseFragment implements OnClickListener, CustomerEffectSelectListener, SetRevisitTimeListener, PullableViewListener, DecideVisitListener, DialogManager.SetOverdueListener {
	
	private PullListView listView;
	private List<ClientTraceListItem> infos = new ArrayList<ClientTraceListItem>();
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
	private TextView tvIndependentName;
    private TextView tvVisitAgain;
	private ImageView ivIndependentPhone;
	private TextView tvStatusReport;
    private TextView tvStatusVisit;
    private TextView tvDecideVisit;
    private TextView tvDate1;
    private TextView tvTime1;
    private TextView tvDate2;
    private TextView tvTime2;
    private TextView tvDate3;
    private TextView tvTime3;
    private TextView tvDate4;
    private TextView tvTime4;
    private TextView tvDate5;
    private TextView tvTime5;
    private TextView tvLeft1;
    private TextView tvLeft2;
    
    //-----客户进度----
    private ImageView ivStatus1;
    private ImageView ivStatus2;
    private ImageView ivStatus3;
    private ImageView ivStatus4;
    private ImageView ivStatus5;
    
    private View line_1_1;
    private View line_2_1;
    private View line_2_2;
    private View line_3_1;
    private View line_3_2;
    private View line_4_1;
    private View line_4_2;
    private View line_5_1;
    //----客户进度----
	
	private TextView tvCustomerEffectJudge;
	private View selectDateTimeView;
	private TextView tvDateTime;
    private View setOverdueView;
    private TextView tvDisable;
    private TextView tvAddTraceRecord;

    private String clientId = "";
    private String houseId = "";
    private ClientDetailData data = null;
    private String independentPhone = "";
    private String customerPhone = "";
    private String revisitTime = "";
    
    private Dialog loadingDialog;
    
    private int pageIndex = 0;
    
    private boolean isEdit = false;
    private String status;

    
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
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.list_head_view_customer_detail_from_independent, null);
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
    	tvIndependentName = (TextView) headView.findViewById(R.id.id_independent_name);
        tvVisitAgain = (TextView) headView.findViewById(R.id.visit_again);
    	ivIndependentPhone = (ImageView) headView.findViewById(R.id.id_independent_phone);
    	tvStatusReport = (TextView) headView.findViewById(R.id.status_report);
    	tvStatusVisit = (TextView) headView.findViewById(R.id.status_visit);
    	tvDecideVisit = (TextView) headView.findViewById(R.id.visit_decide);
    	tvDate1 = (TextView) headView.findViewById(R.id.date_1);
    	tvTime1 = (TextView) headView.findViewById(R.id.time_1);
    	tvDate2 = (TextView) headView.findViewById(R.id.date_2);
    	tvTime2 = (TextView) headView.findViewById(R.id.time_2);
    	tvDate3 = (TextView) headView.findViewById(R.id.date_3);
    	tvTime3 = (TextView) headView.findViewById(R.id.time_3);
    	tvDate4 = (TextView) headView.findViewById(R.id.date_4);
    	tvTime4 = (TextView) headView.findViewById(R.id.time_4);
    	tvDate5 = (TextView) headView.findViewById(R.id.date_5);
    	tvTime5 = (TextView) headView.findViewById(R.id.time_5);
    	tvLeft1 = (TextView) headView.findViewById(R.id.left1);
    	tvLeft2 = (TextView) headView.findViewById(R.id.left2);
    	
    	//-----客户进度----
    	ivStatus1 = (ImageView) headView.findViewById(R.id.id_state_report);
        ivStatus2 = (ImageView) headView.findViewById(R.id.id_state_visit);
        ivStatus3 = (ImageView) headView.findViewById(R.id.id_state_from);
        ivStatus4 = (ImageView) headView.findViewById(R.id.id_state_appointment);
        ivStatus5 = (ImageView) headView.findViewById(R.id.id_state_deal);
        line_1_1 = headView.findViewById(R.id.id_state_1_1);
        line_2_1 = headView.findViewById(R.id.id_state_2_1);
        line_2_2 = headView.findViewById(R.id.id_state_2_2);
        line_3_1 = headView.findViewById(R.id.id_state_3_1);
        line_3_2 = headView.findViewById(R.id.id_state_3_2);
        line_4_1 = headView.findViewById(R.id.id_state_4_1);
        line_4_2 = headView.findViewById(R.id.id_state_4_2);
        line_5_1 = headView.findViewById(R.id.id_state_5_1);
      //-----客户进度----
    	
        tvAddTraceRecord= (TextView) headView.findViewById(R.id.add_customer_trace_record);
    	
    	tvCustomerEffectJudge = (TextView) headView.findViewById(R.id.id_customer_effect_judge);
    	selectDateTimeView = headView.findViewById(R.id.select_date_time);
        setOverdueView = headView.findViewById(R.id.set_overdue);
        tvDisable = (TextView) headView.findViewById(R.id.is_disabled);
        tvDateTime = (TextView) headView.findViewById(R.id.date_time);
    	listView.addHeaderView(headView);
    	
    	ivCustomerMessage.setOnClickListener(this);
    	ivCustomerCall.setOnClickListener(this);
    	ivIndependentPhone.setOnClickListener(this);
    	tvCustomerEffectJudge.setOnClickListener(this);
    	tvDecideVisit.setOnClickListener(this);
    	selectDateTimeView.setOnClickListener(this);
        setOverdueView.setOnClickListener(this);
        tvAddTraceRecord.setOnClickListener(this);
        tvVisitAgain.setOnClickListener(this);
    }

    @Override
    public void selectCustomerEffect(String effectOrInvalid) {
        ClientHttpRequest.decideEffectRequest(clientId, houseId, effectOrInvalid, new DecideEffectHandler(this));
    }

    @Override
    public void onClick(View v) {
        if (v == tvCustomerEffectJudge) {
            DialogManager.showCustomerEffectDialog(getActivity(), this);
        } else if (v == btnEdit) {
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
        } else if (v == ivIndependentPhone) {
            call(independentPhone);
        } else if (v == ivCustomerMessage) {
            message(customerPhone);
        } else if (v == ivCustomerCall) {
            call(customerPhone);
        } else if (v == tvDecideVisit) {
        	DialogManager.showDecideVisitDialog(getActivity(), this);
        } else if (v == setOverdueView) {
            DialogManager.showSetOverDueDialog(getActivity(), CustomerFromIndependentDetailFragment.this);
        } else if (v == tvVisitAgain) {
            ClientHttpRequest.setRevisitRequest(clientId, ChannelCookie.getInstance().getCurrentHouseId(), new SetReVisitHandler(this));
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
        tvCustomerName.setText(data.getClient_name());
        tvCustomerPhone.setText(data.getClient_phone());
        tvCustomerIntentionDesc.setText(data.getClient_category_desc());
        String isDisabled = data.getIs_disabled();
        if (isDisabled.equals("1")) {
            tvDisable.setText("已过期");
        } else {
            tvDisable.setText("未过期");
        }

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
        tvProjectArea.setText(data.getIntent_location_desc());
            
        tvIndependentName.setText("经纪人:" + data.getBroker_name());
        independentPhone = data.getBroker_phone();
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
        
        if (!TextUtils.isEmpty(data.getRec_continue_desc())) {
			tvLeft1.setText(data.getRec_continue_desc());
		}
        if (!TextUtils.isEmpty(data.getVisit_continue_desc())) {
			tvLeft2.setText(data.getVisit_continue_desc());
		}

        status = data.getStatus();
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
        if (!TextUtils.isEmpty(data.getFinish_time())) {
			String[] finishTime = data.getFinish_time().split(" ");
			tvDate5.setText(finishTime[0]);
			tvTime5.setText(finishTime[1]);
		}
        
        
        if (status.equals("8")) {
            tvVisitAgain.setVisibility(View.GONE);
            setOverdueView.setVisibility(View.GONE);
			ivStatus1.setImageResource(R.drawable.ic_checked);
			ivStatus2.setImageResource(R.drawable.ic_checked);
			ivStatus3.setImageResource(R.drawable.ic_checked);
			ivStatus4.setImageResource(R.drawable.ic_checked);
			ivStatus5.setImageResource(R.drawable.ic_checked);
		    
		    line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_4_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_5_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    
		    tvCustomerEffectJudge.setVisibility(View.GONE);
		    tvDecideVisit.setVisibility(View.GONE);
            tvStatusReport.setText("有效");
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.VISIBLE);
            tvTime2.setVisibility(View.VISIBLE);
            tvDate3.setVisibility(View.VISIBLE);
            tvTime3.setVisibility(View.VISIBLE);
            tvDate4.setVisibility(View.VISIBLE);
            tvTime4.setVisibility(View.VISIBLE);
            tvDate5.setVisibility(View.VISIBLE);
            tvTime5.setVisibility(View.VISIBLE);
            // TODO
		    
		} else if (status.equals("7")) {
            tvVisitAgain.setVisibility(View.GONE);
            setOverdueView.setVisibility(View.GONE);
			ivStatus1.setImageResource(R.drawable.ic_checked);
			ivStatus2.setImageResource(R.drawable.ic_checked);
			ivStatus3.setImageResource(R.drawable.ic_checked);
			ivStatus4.setImageResource(R.drawable.ic_checked);
			ivStatus5.setImageResource(R.drawable.ic_unchecked);
		    
		    line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_4_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_5_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    
		    tvCustomerEffectJudge.setVisibility(View.GONE);
		    tvDecideVisit.setVisibility(View.GONE);
            tvStatusReport.setText("有效");
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.VISIBLE);
            tvTime2.setVisibility(View.VISIBLE);
            tvDate3.setVisibility(View.VISIBLE);
            tvTime3.setVisibility(View.VISIBLE);
            tvDate4.setVisibility(View.VISIBLE);
            tvTime4.setVisibility(View.VISIBLE);
            tvDate5.setVisibility(View.GONE);
            tvTime5.setVisibility(View.GONE);

            // TODO

		    
		}  else if (status.equals("6")) {
            tvVisitAgain.setVisibility(View.VISIBLE);
            setOverdueView.setVisibility(View.VISIBLE);
			ivStatus1.setImageResource(R.drawable.ic_checked);
			ivStatus2.setImageResource(R.drawable.ic_checked);
			ivStatus3.setImageResource(R.drawable.ic_checked);
			ivStatus4.setImageResource(R.drawable.ic_unchecked);
			ivStatus5.setImageResource(R.drawable.ic_unchecked);
		    
		    line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_5_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    
		    tvCustomerEffectJudge.setVisibility(View.GONE);
		    tvDecideVisit.setVisibility(View.GONE);
            tvStatusReport.setText("有效");
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.VISIBLE);
            tvTime2.setVisibility(View.VISIBLE);
            tvDate3.setVisibility(View.VISIBLE);
            tvTime3.setVisibility(View.VISIBLE);
            tvDate4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            tvDate5.setVisibility(View.GONE);
            tvTime5.setVisibility(View.GONE);
            
            // TODO
		    
		} else if (status.equals("5")) {
            tvVisitAgain.setVisibility(View.VISIBLE);
            setOverdueView.setVisibility(View.VISIBLE);
			ivStatus1.setImageResource(R.drawable.ic_checked);
			ivStatus2.setImageResource(R.drawable.ic_checked);
			ivStatus3.setImageResource(R.drawable.ic_unchecked);
			ivStatus4.setImageResource(R.drawable.ic_unchecked);
			ivStatus5.setImageResource(R.drawable.ic_unchecked);
		    
		    line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_5_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    
		    tvCustomerEffectJudge.setVisibility(View.GONE);
		    tvDecideVisit.setVisibility(View.GONE);
            tvStatusReport.setText("有效");
            tvStatusVisit.setText("到访");
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.VISIBLE);
            tvTime2.setVisibility(View.VISIBLE);
            tvDate3.setVisibility(View.GONE);
            tvTime3.setVisibility(View.GONE);
            tvDate4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            tvDate5.setVisibility(View.GONE);
            tvTime5.setVisibility(View.GONE);
		    
		} else if (status.equals("4")) {
            tvVisitAgain.setVisibility(View.GONE);
            setOverdueView.setVisibility(View.VISIBLE);
			ivStatus1.setImageResource(R.drawable.ic_checked);
			ivStatus2.setImageResource(R.drawable.ic_unchecked);
			ivStatus3.setImageResource(R.drawable.ic_unchecked);
			ivStatus4.setImageResource(R.drawable.ic_unchecked);
			ivStatus5.setImageResource(R.drawable.ic_unchecked);
		    
		    line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
		    line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_5_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    
		    tvCustomerEffectJudge.setVisibility(View.GONE);
		    tvDecideVisit.setVisibility(View.VISIBLE);
            tvStatusReport.setText("有效");
            tvStatusVisit.setText("待验证");
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.GONE);
            tvTime2.setVisibility(View.GONE);
            tvDate3.setVisibility(View.GONE);
            tvTime3.setVisibility(View.GONE);
            tvDate4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            tvDate5.setVisibility(View.GONE);
            tvTime5.setVisibility(View.GONE);

            // TODO

		} else if (status.equals("3")) {
            tvVisitAgain.setVisibility(View.GONE);
            setOverdueView.setVisibility(View.VISIBLE);
			ivStatus1.setImageResource(R.drawable.ic_checked);
			ivStatus2.setImageResource(R.drawable.ic_unchecked);
			ivStatus3.setImageResource(R.drawable.ic_unchecked);
			ivStatus4.setImageResource(R.drawable.ic_unchecked);
			ivStatus5.setImageResource(R.drawable.ic_unchecked);
		    
		    line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_5_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

		    tvCustomerEffectJudge.setVisibility(View.GONE);
		    tvDecideVisit.setVisibility(View.VISIBLE);
            tvStatusReport.setText("有效");
            tvStatusVisit.setText("到访");
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.GONE);
            tvTime2.setVisibility(View.GONE);
            tvDate3.setVisibility(View.GONE);
            tvTime3.setVisibility(View.GONE);
            tvDate4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            tvDate5.setVisibility(View.GONE);
            tvTime5.setVisibility(View.GONE);

            // TODO
			
		} else if (status.equals("2")) {
            tvVisitAgain.setVisibility(View.GONE);
            setOverdueView.setVisibility(View.GONE);
			ivStatus1.setImageResource(R.drawable.ic_checked);
			ivStatus2.setImageResource(R.drawable.ic_unchecked);
			ivStatus3.setImageResource(R.drawable.ic_unchecked);
			ivStatus4.setImageResource(R.drawable.ic_unchecked);
			ivStatus5.setImageResource(R.drawable.ic_unchecked);
		    
		    line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_5_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    
		    tvCustomerEffectJudge.setVisibility(View.GONE);
		    tvDecideVisit.setVisibility(View.GONE);
            tvStatusReport.setText("无效");
            
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(data.getNot_effect_time())) {
    			String[] noeffectTime = data.getNot_effect_time().split(" ");
    			tvDate1.setText(noeffectTime[0]);
    			tvTime1.setText(noeffectTime[1]);
    		}
            tvDate2.setVisibility(View.GONE);
            tvTime2.setVisibility(View.GONE);
            tvDate3.setVisibility(View.GONE);
            tvTime3.setVisibility(View.GONE);
            tvDate4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            tvDate5.setVisibility(View.GONE);
            tvTime5.setVisibility(View.GONE);
            tvLeft2.setVisibility(View.GONE);
		} else if (status.equals("1")) {
            tvVisitAgain.setVisibility(View.GONE);
            setOverdueView.setVisibility(View.GONE);
			ivStatus1.setImageResource(R.drawable.ic_checked);
			ivStatus2.setImageResource(R.drawable.ic_unchecked);
			ivStatus3.setImageResource(R.drawable.ic_unchecked);
			ivStatus4.setImageResource(R.drawable.ic_unchecked);
			ivStatus5.setImageResource(R.drawable.ic_unchecked);
		    
		    line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_4_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    line_5_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
		    
		    tvCustomerEffectJudge.setVisibility(View.VISIBLE);
		    tvDecideVisit.setVisibility(View.GONE);
            tvStatusReport.setText("有效/无效");
            tvDate1.setVisibility(View.GONE);
            tvTime1.setVisibility(View.GONE);
            tvDate2.setVisibility(View.GONE);
            tvTime2.setVisibility(View.GONE);
            tvDate3.setVisibility(View.GONE);
            tvTime3.setVisibility(View.GONE);
            tvDate4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            tvDate5.setVisibility(View.GONE);
            tvTime5.setVisibility(View.GONE);
            tvLeft2.setVisibility(View.GONE);
		}
        
        if (data.getVisit_refused() == 1) {
            ivStatus1.setImageResource(R.drawable.ic_checked);
            ivStatus2.setImageResource(R.drawable.ic_unchecked);
            ivStatus3.setImageResource(R.drawable.ic_unchecked);
            ivStatus4.setImageResource(R.drawable.ic_unchecked);
            ivStatus5.setImageResource(R.drawable.ic_unchecked);
            
            line_1_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_2_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_2_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_3_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_3_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_4_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_4_2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            line_5_1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            
            tvCustomerEffectJudge.setVisibility(View.GONE);
            tvDecideVisit.setVisibility(View.VISIBLE);
            tvStatusReport.setText("有效");
            tvStatusVisit.setText("未到访");
            tvDate1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.VISIBLE);
            tvDate2.setVisibility(View.GONE);
            tvTime2.setVisibility(View.GONE);
            tvDate3.setVisibility(View.GONE);
            tvTime3.setVisibility(View.GONE);
            tvDate4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            tvDate5.setVisibility(View.GONE);
            tvTime5.setVisibility(View.GONE);
        }
    }
    
    class GetClientInfoHandler extends HttpResponseHandlerFragment<CustomerFromIndependentDetailFragment> {

        public GetClientInfoHandler(CustomerFromIndependentDetailFragment context) {
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
    
    class GetTraceListHandler extends HttpResponseHandlerFragment<CustomerFromIndependentDetailFragment> {

        private LOAD_ACTION action;
        
        public GetTraceListHandler(CustomerFromIndependentDetailFragment context, LOAD_ACTION action) {
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
    
    class SetRevisitTimeHandler extends HttpResponseHandlerFragment<CustomerFromIndependentDetailFragment> {

        public SetRevisitTimeHandler(CustomerFromIndependentDetailFragment context) {
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
    
    class DecideEffectHandler extends HttpResponseHandlerFragment<CustomerFromIndependentDetailFragment> {

        public DecideEffectHandler(CustomerFromIndependentDetailFragment context) {
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

                        ClientHttpRequest.getClientInfoRequest(clientId, houseId, new GetClientInfoHandler(getFragment()));
                    
//                        tvCustomerEffectJudge.setVisibility(View.GONE);
//                        tvDate1.setVisibility(View.VISIBLE);
//                        tvDate1.setText(DateFormatUtil.getFormateDate(System.currentTimeMillis(), "MM-dd"));
//                        tvTime1.setVisibility(View.GONE);
//                        
//                        if (effectOrInvalid.equals("0")) {
//                            tvStatusReport.setText("无效");
//                        } else if (effectOrInvalid.equals("1")) {
//                            tvStatusReport.setText("有效");
//                            tvDecideVisit.setVisibility(View.VISIBLE);
//                        }
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
    
    class DecideVisitHandler extends HttpResponseHandlerFragment<CustomerFromIndependentDetailFragment> {

    	
        public DecideVisitHandler(CustomerFromIndependentDetailFragment context, boolean isVisited) {
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
	public void decide(int type) {
		if (type == 1) {
            ClientHttpRequest.decideVisitRequest(clientId, houseId, "1", new DecideVisitHandler(this, true));
		} else if (type == 0) {
            ClientHttpRequest.decideVisitRequest(clientId, houseId, "0", new DecideVisitHandler(this, false));
		}
	}

    @Override
    public void setOverdue(String ovderdue) {
        ClientHttpRequest.setOverdueRequest(clientId, ovderdue, ChannelCookie.getInstance().getCurrentHouseId(), new SetOverDueHandler(this));
    }

    class SetOverDueHandler extends HttpResponseHandlerFragment<CustomerFromIndependentDetailFragment> {


        public SetOverDueHandler(CustomerFromIndependentDetailFragment context) {
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

    class SetReVisitHandler extends HttpResponseHandlerFragment<CustomerFromIndependentDetailFragment> {


        public SetReVisitHandler(CustomerFromIndependentDetailFragment context) {
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
}

