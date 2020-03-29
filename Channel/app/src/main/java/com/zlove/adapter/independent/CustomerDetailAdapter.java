package com.zlove.adapter.independent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zlove.act.independent.ActIndependentCustomerTraceRecord;
import com.zlove.adapter.common.SingleDataListAdapter;
import com.zlove.base.http.HttpResponseHandler;
import com.zlove.base.util.DateTimePickDialogUtil;
import com.zlove.base.util.DateTimePickDialogUtil.SetRevisitTimeListener;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.DialogManager.ExtendReportTimeListener;
import com.zlove.base.util.DialogManager.ExtendVisitTimeListener;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.util.UIUtil;
import com.zlove.base.widget.VisitRecommendDialog;
import com.zlove.base.widget.VisitRecommendDialog.VisitRecommendAction;
import com.zlove.bean.client.ClientRecommendHouseListItem;
import com.zlove.bean.client.ClientRecommendHouseTraceListItem;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.common.ExtendVisitBean;
import com.zlove.bean.common.ExtendVisitData;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

import java.util.List;

public class CustomerDetailAdapter extends SingleDataListAdapter<ClientRecommendHouseListItem> implements ExtendReportTimeListener, SetRevisitTimeListener, ExtendVisitTimeListener, VisitRecommendAction {
    
	private CustomerActionListener listener;
	private String clientId = "";
	
    public CustomerDetailAdapter(List<ClientRecommendHouseListItem> data, Context context, CustomerActionListener listener) {
        super(data, context);
        this.listener = listener;
    }
    
    public void setClientId(String clientId) {
		this.clientId = clientId;
	}

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_detail, null);
            // ----Head----
            holder.tvProjectName = (TextView) convertView.findViewById(R.id.id_project_name);
            holder.tvSaleManName = (TextView) convertView.findViewById(R.id.id_saleman_name);
            holder.ivSaleManMessage = (ImageView) convertView.findViewById(R.id.id_saleman_message);
            holder.ivSaleManCall = (ImageView) convertView.findViewById(R.id.id_saleman_phone);
            holder.ivOverDue = (ImageView) convertView.findViewById(R.id.has_over_due);
            //----Report----
            holder.tvReportDate = (TextView) convertView.findViewById(R.id.report_date);
            holder.tvReportTime = (TextView) convertView.findViewById(R.id.report_time);
            holder.ivReportState = (ImageView) convertView.findViewById(R.id.id_state_report);
            holder.tvReportStateTip = (TextView) convertView.findViewById(R.id.report_state_tip);
            holder.tvReportLeftTime = (TextView) convertView.findViewById(R.id.report_left_time);
            holder.reportLine1 = (TextView) convertView.findViewById(R.id.report_line_1);
            holder.reportLine2 = (TextView) convertView.findViewById(R.id.report_line_2);
            holder.tvContinueDay1 = (TextView) convertView.findViewById(R.id.continue_day_1);
            //----Visit----
            holder.tvVisitDate = (TextView) convertView.findViewById(R.id.visit_date);
            holder.tvVisitTime = (TextView) convertView.findViewById(R.id.visit_time);
            holder.visitTipIcon = (ImageView) convertView.findViewById(R.id.visit_tip_icon);
            holder.ivVisitState = (ImageView) convertView.findViewById(R.id.id_state_visit);
            holder.tvVisitStateTip = (TextView) convertView.findViewById(R.id.visit_state_tip);
            holder.tvVisitVerify = (TextView) convertView.findViewById(R.id.visit_verify);
            holder.visitLine1 = (TextView) convertView.findViewById(R.id.visit_line_1);
            holder.visitLine2 = (TextView) convertView.findViewById(R.id.visit_line_2);
            holder.tvContinueDay2 = (TextView) convertView.findViewById(R.id.continue_day_2);
            //----Form----
            holder.tvFormDate = (TextView) convertView.findViewById(R.id.form_date);
            holder.tvFormTime = (TextView) convertView.findViewById(R.id.form_time);
            holder.ivFormState = (ImageView) convertView.findViewById(R.id.id_state_from);
            holder.tvFormStateTip = (TextView) convertView.findViewById(R.id.form_state_tip);
            holder.formLine1 = (TextView) convertView.findViewById(R.id.form_line_1);
            holder.formLine2 = (TextView) convertView.findViewById(R.id.form_line_2);
            //----Deal----
            holder.tvDealDate = (TextView) convertView.findViewById(R.id.deal_date);
            holder.tvDealTime = (TextView) convertView.findViewById(R.id.deal_time);
            holder.ivDealState = (ImageView) convertView.findViewById(R.id.id_state_deal);
            holder.tvDealStateTip = (TextView) convertView.findViewById(R.id.form_state_tip);
            holder.dealLine1 = (TextView) convertView.findViewById(R.id.deal_line_1);
            holder.dealLine2 = (TextView) convertView.findViewById(R.id.deal_line_2);
            //----Commission----
            holder.tvCommissionDate = (TextView) convertView.findViewById(R.id.commission_date);
            holder.tvCommissionTime = (TextView) convertView.findViewById(R.id.commission_time);
            holder.ivCommissionState = (ImageView) convertView.findViewById(R.id.id_state_commission);
            holder.tvCommissionStateTip = (TextView) convertView.findViewById(R.id.commission_state_tip);
            
            holder.extendReportTimeView = convertView.findViewById(R.id.extend_report_time);
            holder.tvExtendTime = (TextView) convertView.findViewById(R.id.tv_extend_tip);
            holder.extendDivider = (ImageView) convertView.findViewById(R.id.extend_divider);
            holder.selectTimeView = convertView.findViewById(R.id.select_date_time);
            holder.tvDateTime = (TextView) convertView.findViewById(R.id.date_time);
            
            holder.tvSaleManFeedBack = (TextView) convertView.findViewById(R.id.sale_man_feed_back);
            
            holder.noRecordContainer = convertView.findViewById(R.id.no_record_cotainer);
            holder.recordContainer1 = convertView.findViewById(R.id.record_container_1);
            holder.recordDivider1 = (ImageView) convertView.findViewById(R.id.record_divider_1);
            holder.recordDivider2 = (ImageView) convertView.findViewById(R.id.record_divider_2);
            holder.recordContainer2 = convertView.findViewById(R.id.record_container_2);
            holder.moreRecordView = convertView.findViewById(R.id.customer_more_record_view);
            
            holder.name1 = (TextView) convertView.findViewById(R.id.id_user_name_1);
            holder.time1 = (TextView) convertView.findViewById(R.id.tv_time_1);
            holder.record1 = (TextView) convertView.findViewById(R.id.tv_record_1);
            holder.name2 = (TextView) convertView.findViewById(R.id.id_user_name_2);
            holder.time2 = (TextView) convertView.findViewById(R.id.tv_time_2);
            holder.record2 = (TextView) convertView.findViewById(R.id.tv_record_2);
            holder.tvAddTraceRecord = (TextView) convertView.findViewById(R.id.add_customer_trace_record);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        final ClientRecommendHouseListItem item = getItem(position);
        if (item != null) {
            holder.tvProjectName.setText(item.getHouse_name());
            holder.tvSaleManName.setText(item.getSalesman_name());
            holder.tvDateTime.setText(item.getRe_visite_time());
        }

        if (!TextUtils.isEmpty(item.getRec_continue_desc())) {
            holder.tvContinueDay1.setText(item.getRec_continue_desc());
        }
        if (!TextUtils.isEmpty(item.getVisit_continue_desc())) {
            holder.tvContinueDay2.setText(item.getVisit_continue_desc());
        }

        if (item.getIs_disabled().equals("1")) {
            holder.ivOverDue.setVisibility(View.VISIBLE);
        } else {
            holder.ivOverDue.setVisibility(View.GONE);
        }
        
        String clientCategoryId = item.getClient_category_id();
        if (clientCategoryId.equals("1")) {
        	holder.tvSaleManFeedBack.setText("A");
		} else if (clientCategoryId.equals("2")) {
        	holder.tvSaleManFeedBack.setText("B");
		} else if (clientCategoryId.equals("3")) {
        	holder.tvSaleManFeedBack.setText("C");
		} else if (clientCategoryId.equals("4")) {
        	holder.tvSaleManFeedBack.setText("D");
		}
        final String is_expired = item.getIs_expired();
        final String status = item.getStatus();
        final String houseId = item.getHouse_id();
        
        if (status.equals("1")) {
        	holder.extendReportTimeView.setVisibility(View.GONE);
        	holder.extendDivider.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(item.getRecommend_time())) {
				String[] recommendTime = item.getRecommend_time().split(" ");
	            holder.tvReportDate.setText(recommendTime[0]);
	            holder.tvReportTime.setText(recommendTime[1]);
	            holder.tvReportDate.setVisibility(View.VISIBLE);
	            holder.tvReportTime.setVisibility(View.VISIBLE);
			} else {
	            holder.tvReportDate.setVisibility(View.GONE);
	            holder.tvReportTime.setVisibility(View.GONE);
			}
            holder.ivReportState.setImageResource(R.drawable.ic_checked);
            holder.tvReportStateTip.setText("待验证");
            holder.tvReportLeftTime.setVisibility(View.VISIBLE);
            holder.tvReportLeftTime.setText(item.getRec_remain_desc());
            holder.reportLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.reportLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getVisit_time())) {
    			String[] visitTime = item.getVisit_time().split(" ");
                holder.tvVisitDate.setText(visitTime[0]);
                holder.tvVisitTime.setText(visitTime[1]);
            } else {
	            holder.tvVisitDate.setVisibility(View.GONE);
	            holder.tvVisitTime.setVisibility(View.GONE);
			}
            holder.visitTipIcon.setVisibility(View.GONE);
            holder.ivVisitState.setImageResource(R.drawable.ic_unchecked);
            holder.tvVisitStateTip.setText("到访");
            holder.tvVisitVerify.setVisibility(View.GONE);
            holder.visitLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.visitLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getIntent_time())) {
                String[] formTime = item.getIntent_time().split(" ");
                holder.tvFormDate.setText(formTime[0]);
                holder.tvFormTime.setText(formTime[1]);
            } else {
	            holder.tvFormDate.setVisibility(View.GONE);
	            holder.tvFormTime.setVisibility(View.GONE);
			}
            holder.ivFormState.setImageResource(R.drawable.ic_unchecked);
            holder.formLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.formLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            
            if (!TextUtils.isEmpty(item.getOrder_time())) {
                String[] dealTime = item.getOrder_time().split(" ");
                holder.tvDealDate.setText(dealTime[0]);
                holder.tvDealTime.setText(dealTime[1]);
	            holder.tvDealDate.setVisibility(View.VISIBLE);
	            holder.tvDealTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvDealDate.setVisibility(View.GONE);
	            holder.tvDealTime.setVisibility(View.GONE);
			}
            holder.ivDealState.setImageResource(R.drawable.ic_unchecked);
            holder.dealLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.dealLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getFinish_time())) {
                String[] commissionTime = item.getFinish_time().split(" ");
                holder.tvCommissionDate.setText(commissionTime[0]);
                holder.tvCommissionTime.setText(commissionTime[1]);
            } else {
	            holder.tvCommissionDate.setVisibility(View.GONE);
	            holder.tvCommissionTime.setVisibility(View.GONE);
			}
            holder.ivCommissionState.setImageResource(R.drawable.ic_unchecked);
            if (is_expired.equals("1")) {
            	holder.tvReportLeftTime.setText("客户过期");
            	holder.tvReportLeftTime.setVisibility(View.VISIBLE);
			}
		} else if (status.equals("2")) {
        	holder.extendReportTimeView.setVisibility(View.GONE);
            holder.extendDivider.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(item.getNot_effect_time())) {
				String[] noEffectTime = item.getNot_effect_time().split(" ");
	            holder.tvReportDate.setText(noEffectTime[0]);
	            holder.tvReportTime.setText(noEffectTime[1]);
	            holder.tvReportDate.setVisibility(View.VISIBLE);
	            holder.tvReportTime.setVisibility(View.VISIBLE);
			} else {
	            holder.tvReportDate.setVisibility(View.GONE);
	            holder.tvReportTime.setVisibility(View.GONE);
			}
            holder.ivReportState.setImageResource(R.drawable.ic_checked);
            holder.tvReportStateTip.setText("无效");
            holder.tvReportLeftTime.setVisibility(View.GONE);
            holder.reportLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.reportLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getVisit_time())) {
    			String[] visitTime = item.getVisit_time().split(" ");
                holder.tvVisitDate.setText(visitTime[0]);
                holder.tvVisitTime.setText(visitTime[1]);
            } else {
	            holder.tvVisitDate.setVisibility(View.GONE);
	            holder.tvVisitTime.setVisibility(View.GONE);
			}
            holder.visitTipIcon.setVisibility(View.GONE);
            holder.ivVisitState.setImageResource(R.drawable.ic_unchecked);
            holder.tvVisitStateTip.setText("到访");
            holder.tvVisitVerify.setVisibility(View.GONE);
            holder.visitLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.visitLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getIntent_time())) {
                String[] formTime = item.getIntent_time().split(" ");
                holder.tvFormDate.setText(formTime[0]);
                holder.tvFormTime.setText(formTime[1]);
            } else {
	            holder.tvFormDate.setVisibility(View.GONE);
	            holder.tvFormTime.setVisibility(View.GONE);
			}
            holder.ivFormState.setImageResource(R.drawable.ic_unchecked);
            holder.formLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.formLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            
            if (!TextUtils.isEmpty(item.getOrder_time())) {
                String[] dealTime = item.getOrder_time().split(" ");
                holder.tvDealDate.setText(dealTime[0]);
                holder.tvDealTime.setText(dealTime[1]);
            } else {
	            holder.tvDealDate.setVisibility(View.GONE);
	            holder.tvDealTime.setVisibility(View.GONE);
			}
            holder.ivDealState.setImageResource(R.drawable.ic_unchecked);
            holder.dealLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.dealLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getFinish_time())) {
                String[] commissionTime = item.getFinish_time().split(" ");
                holder.tvCommissionDate.setText(commissionTime[0]);
                holder.tvCommissionTime.setText(commissionTime[1]);
            } else {
	            holder.tvCommissionDate.setVisibility(View.GONE);
	            holder.tvCommissionTime.setVisibility(View.GONE);
			}
            holder.ivCommissionState.setImageResource(R.drawable.ic_unchecked);
            if (is_expired.equals("1")) {
            	holder.tvReportLeftTime.setText("客户过期");
            	holder.tvReportLeftTime.setVisibility(View.VISIBLE);
			}
		} else if (status.equals("3")) {
        	holder.extendReportTimeView.setVisibility(View.GONE);
            holder.extendDivider.setVisibility(View.VISIBLE);
        	holder.tvExtendTime.setText("延长报备有效期");
            holder.tvExtendTime.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(item.getRecommend_time())) {
				String[] recTime = item.getRecommend_time().split(" ");
	            holder.tvReportDate.setText(recTime[0]);
	            holder.tvReportTime.setText(recTime[1]);
	            holder.tvReportDate.setVisibility(View.VISIBLE);
	            holder.tvReportTime.setVisibility(View.VISIBLE);
			} else {
	            holder.tvReportDate.setVisibility(View.GONE);
	            holder.tvReportTime.setVisibility(View.GONE);
			}
            holder.ivReportState.setImageResource(R.drawable.ic_checked);
            holder.tvReportStateTip.setText("有效");
            holder.tvReportLeftTime.setVisibility(View.VISIBLE);
            holder.tvReportLeftTime.setText(item.getRec_remain_desc());
            holder.reportLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.reportLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getVisit_time())) {
    			String[] visitTime = item.getVisit_time().split(" ");
                holder.tvVisitDate.setText(visitTime[0]);
                holder.tvVisitTime.setText(visitTime[1]);
            } else {
	            holder.tvVisitDate.setVisibility(View.GONE);
	            holder.tvVisitTime.setVisibility(View.GONE);
			}
            holder.visitTipIcon.setVisibility(View.VISIBLE);
            holder.ivVisitState.setImageResource(R.drawable.ic_unchecked);
            holder.tvVisitStateTip.setText("到访");
            holder.tvVisitVerify.setVisibility(View.GONE);
            holder.visitLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.visitLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getIntent_time())) {
                String[] formTime = item.getIntent_time().split(" ");
                holder.tvFormDate.setText(formTime[0]);
                holder.tvFormTime.setText(formTime[1]);
            } else {
	            holder.tvFormDate.setVisibility(View.GONE);
	            holder.tvFormTime.setVisibility(View.GONE);
			}
            holder.ivFormState.setImageResource(R.drawable.ic_unchecked);
            holder.formLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.formLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            
            if (!TextUtils.isEmpty(item.getOrder_time())) {
                String[] dealTime = item.getOrder_time().split(" ");
                holder.tvDealDate.setText(dealTime[0]);
                holder.tvDealTime.setText(dealTime[1]);
	            holder.tvDealDate.setVisibility(View.VISIBLE);
	            holder.tvDealTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvDealDate.setVisibility(View.GONE);
	            holder.tvDealTime.setVisibility(View.GONE);
			}
            holder.ivDealState.setImageResource(R.drawable.ic_unchecked);
            holder.dealLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.dealLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getFinish_time())) {
                String[] commissionTime = item.getFinish_time().split(" ");
                holder.tvCommissionDate.setText(commissionTime[0]);
                holder.tvCommissionTime.setText(commissionTime[1]);
            } else {
	            holder.tvCommissionDate.setVisibility(View.GONE);
	            holder.tvCommissionTime.setVisibility(View.GONE);
			}
            holder.ivCommissionState.setImageResource(R.drawable.ic_unchecked);
            if (is_expired.equals("1")) {
            	holder.tvReportLeftTime.setText("客户过期");
            	holder.tvReportLeftTime.setVisibility(View.VISIBLE);
			}
		} else if (status.equals("4")) {
        	holder.extendReportTimeView.setVisibility(View.GONE);
            holder.extendDivider.setVisibility(View.VISIBLE);
        	holder.tvExtendTime.setText("延长报备有效期");
            holder.tvExtendTime.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(item.getRecommend_time())) {
				String[] reportTime = item.getRecommend_time().split(" ");
	            holder.tvReportDate.setText(reportTime[0]);
	            holder.tvReportTime.setText(reportTime[1]);
			} else {
	            holder.tvReportDate.setVisibility(View.GONE);
	            holder.tvReportTime.setVisibility(View.GONE);
			}
            holder.ivReportState.setImageResource(R.drawable.ic_checked);
            holder.tvReportStateTip.setText("有效");
            holder.tvReportLeftTime.setVisibility(View.VISIBLE);
            holder.tvReportLeftTime.setText(item.getRec_remain_desc());

            if (item.getVisit_refused() == 0) {
                holder.visitTipIcon.setVisibility(View.GONE);
                holder.reportLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
                holder.reportLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
                holder.tvVisitStateTip.setText("待验证");
                if (!TextUtils.isEmpty(item.getVisit_time())) {
        			String[] visitTime = item.getVisit_time().split(" ");
                    holder.tvVisitDate.setText(visitTime[0]);
                    holder.tvVisitTime.setText(visitTime[1]);
    	            holder.tvVisitDate.setVisibility(View.VISIBLE);
    	            holder.tvVisitTime.setVisibility(View.VISIBLE);
                } else {
    	            holder.tvVisitDate.setVisibility(View.GONE);
    	            holder.tvVisitTime.setVisibility(View.GONE);
    			}
			} else if (item.getVisit_refused() == 1) {
				holder.visitTipIcon.setVisibility(View.VISIBLE);
                holder.reportLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
                holder.reportLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
                holder.tvVisitStateTip.setText("未到访");
                holder.tvVisitDate.setVisibility(View.GONE);
                holder.tvVisitTime.setVisibility(View.GONE);
			}
            
            holder.ivVisitState.setImageResource(R.drawable.ic_unchecked);
            holder.tvVisitVerify.setVisibility(View.GONE);
            holder.visitLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.visitLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getIntent_time())) {
                String[] formTime = item.getIntent_time().split(" ");
                holder.tvFormDate.setText(formTime[0]);
                holder.tvFormTime.setText(formTime[1]);
            } else {
	            holder.tvFormDate.setVisibility(View.GONE);
	            holder.tvFormTime.setVisibility(View.GONE);
			}
            holder.ivFormState.setImageResource(R.drawable.ic_unchecked);
            holder.formLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.formLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            
            if (!TextUtils.isEmpty(item.getOrder_time())) {
                String[] dealTime = item.getOrder_time().split(" ");
                holder.tvDealDate.setText(dealTime[0]);
                holder.tvDealTime.setText(dealTime[1]);
            } else {
	            holder.tvDealDate.setVisibility(View.GONE);
	            holder.tvDealTime.setVisibility(View.GONE);
			}
            holder.ivDealState.setImageResource(R.drawable.ic_unchecked);
            holder.dealLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.dealLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getFinish_time())) {
                String[] commissionTime = item.getFinish_time().split(" ");
                holder.tvCommissionDate.setText(commissionTime[0]);
                holder.tvCommissionTime.setText(commissionTime[1]);
            } else {
	            holder.tvCommissionDate.setVisibility(View.GONE);
	            holder.tvCommissionTime.setVisibility(View.GONE);
			}
            holder.ivCommissionState.setImageResource(R.drawable.ic_unchecked);
            if (is_expired.equals("1")) {
            	holder.tvReportLeftTime.setText("客户过期");
            	holder.tvReportLeftTime.setVisibility(View.VISIBLE);
			}
		} else if (status.equals("5")) {
            holder.extendReportTimeView.setVisibility(View.GONE);
            holder.extendDivider.setVisibility(View.VISIBLE);
        	holder.tvExtendTime.setText("延长到访有效期");
			if (!TextUtils.isEmpty(item.getRecommend_time())) {
				String[] reportTime = item.getRecommend_time().split(" ");
	            holder.tvReportDate.setText(reportTime[0]);
	            holder.tvReportTime.setText(reportTime[1]);
			} else {
	            holder.tvReportDate.setVisibility(View.GONE);
	            holder.tvReportTime.setVisibility(View.GONE);
			}
            holder.ivReportState.setImageResource(R.drawable.ic_checked);
            holder.tvReportStateTip.setText("有效");
            holder.tvReportLeftTime.setVisibility(View.GONE);
            holder.reportLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            holder.reportLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));

            if (!TextUtils.isEmpty(item.getVisit_time())) {
    			String[] visitTime = item.getVisit_time().split(" ");
                holder.tvVisitDate.setText(visitTime[0]);
                holder.tvVisitTime.setText(visitTime[1]);
	            holder.tvVisitDate.setVisibility(View.VISIBLE);
	            holder.tvVisitTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvVisitDate.setVisibility(View.GONE);
	            holder.tvVisitTime.setVisibility(View.GONE);
			}
            holder.visitTipIcon.setVisibility(View.GONE);
            holder.ivVisitState.setImageResource(R.drawable.ic_checked);
            holder.tvVisitStateTip.setText("到访");
            holder.tvVisitVerify.setVisibility(View.VISIBLE);
            holder.tvVisitVerify.setText(item.getVisit_remain_desc());
            holder.visitLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.visitLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getIntent_time())) {
                String[] formTime = item.getIntent_time().split(" ");
                holder.tvFormDate.setText(formTime[0]);
                holder.tvFormTime.setText(formTime[1]);
            } else {
	            holder.tvFormDate.setVisibility(View.GONE);
	            holder.tvFormTime.setVisibility(View.GONE);
			}
            holder.ivFormState.setImageResource(R.drawable.ic_unchecked);
            holder.formLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.formLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            
            if (!TextUtils.isEmpty(item.getOrder_time())) {
                String[] dealTime = item.getOrder_time().split(" ");
                holder.tvDealDate.setText(dealTime[0]);
                holder.tvDealTime.setText(dealTime[1]);
            } else {
	            holder.tvDealDate.setVisibility(View.GONE);
	            holder.tvDealTime.setVisibility(View.GONE);
			}
            holder.ivDealState.setImageResource(R.drawable.ic_unchecked);
            holder.dealLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.dealLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getFinish_time())) {
                String[] commissionTime = item.getFinish_time().split(" ");
                holder.tvCommissionDate.setText(commissionTime[0]);
                holder.tvCommissionTime.setText(commissionTime[1]);
            } else {
	            holder.tvCommissionDate.setVisibility(View.GONE);
	            holder.tvCommissionTime.setVisibility(View.GONE);
			}
            holder.ivCommissionState.setImageResource(R.drawable.ic_unchecked);
            if (is_expired.equals("1")) {
                holder.tvReportLeftTime.setText("客户过期");
                holder.tvReportLeftTime.setVisibility(View.VISIBLE);
            }
		} else if (status.equals("6")) {
        	holder.extendReportTimeView.setVisibility(View.GONE);
            holder.extendDivider.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(item.getRecommend_time())) {
				String[] reportTime = item.getRecommend_time().split(" ");
	            holder.tvReportDate.setText(reportTime[0]);
	            holder.tvReportTime.setText(reportTime[1]);
                holder.tvReportDate.setVisibility(View.VISIBLE);
                holder.tvReportTime.setVisibility(View.VISIBLE);
			} else {
	            holder.tvReportDate.setVisibility(View.GONE);
	            holder.tvReportTime.setVisibility(View.GONE);
			}
            holder.ivReportState.setImageResource(R.drawable.ic_checked);
            holder.tvReportStateTip.setText("有效");
            holder.tvReportLeftTime.setVisibility(View.GONE);
            holder.reportLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            holder.reportLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));

            if (!TextUtils.isEmpty(item.getVisit_time())) {
    			String[] visitTime = item.getVisit_time().split(" ");
                holder.tvVisitDate.setText(visitTime[0]);
                holder.tvVisitTime.setText(visitTime[1]);
                holder.tvVisitDate.setVisibility(View.VISIBLE);
                holder.tvVisitTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvVisitDate.setVisibility(View.GONE);
	            holder.tvVisitTime.setVisibility(View.GONE);
			}
            holder.visitTipIcon.setVisibility(View.GONE);
            holder.ivVisitState.setImageResource(R.drawable.ic_checked);
            holder.tvVisitStateTip.setText("到访");
            holder.tvVisitVerify.setVisibility(View.GONE);
            holder.visitLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            holder.visitLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));

            if (!TextUtils.isEmpty(item.getIntent_time())) {
                String[] formTime = item.getIntent_time().split(" ");
                holder.tvFormDate.setText(formTime[0]);
                holder.tvFormTime.setText(formTime[1]);
                holder.tvFormDate.setVisibility(View.VISIBLE);
                holder.tvFormTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvFormDate.setVisibility(View.GONE);
	            holder.tvFormTime.setVisibility(View.GONE);
			}
            holder.ivFormState.setImageResource(R.drawable.ic_checked);
            holder.formLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.formLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            
            if (!TextUtils.isEmpty(item.getOrder_time())) {
                String[] dealTime = item.getOrder_time().split(" ");
                holder.tvDealDate.setText(dealTime[0]);
                holder.tvDealTime.setText(dealTime[1]);
	            holder.tvDealDate.setVisibility(View.VISIBLE);
	            holder.tvDealTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvDealDate.setVisibility(View.GONE);
	            holder.tvDealTime.setVisibility(View.GONE);
			}
            holder.ivDealState.setImageResource(R.drawable.ic_unchecked);
            holder.dealLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.dealLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getFinish_time())) {
                String[] commissionTime = item.getFinish_time().split(" ");
                holder.tvCommissionDate.setText(commissionTime[0]);
                holder.tvCommissionTime.setText(commissionTime[1]);
                holder.tvCommissionDate.setVisibility(View.VISIBLE);
                holder.tvCommissionTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvCommissionDate.setVisibility(View.GONE);
	            holder.tvCommissionTime.setVisibility(View.GONE);
			}
            holder.ivCommissionState.setImageResource(R.drawable.ic_unchecked);
		} else if (status.equals("7")) {
        	holder.extendReportTimeView.setVisibility(View.GONE);
            holder.extendDivider.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(item.getRecommend_time())) {
				String[] reportTime = item.getRecommend_time().split(" ");
	            holder.tvReportDate.setText(reportTime[0]);
	            holder.tvReportTime.setText(reportTime[1]);
                holder.tvReportDate.setVisibility(View.VISIBLE);
                holder.tvReportTime.setVisibility(View.VISIBLE);
			} else {
	            holder.tvReportDate.setVisibility(View.GONE);
	            holder.tvReportTime.setVisibility(View.GONE);
			}
            holder.ivReportState.setImageResource(R.drawable.ic_checked);
            holder.tvReportStateTip.setText("有效");
            holder.tvReportLeftTime.setVisibility(View.GONE);
            holder.reportLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            holder.reportLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));

            if (!TextUtils.isEmpty(item.getVisit_time())) {
    			String[] visitTime = item.getVisit_time().split(" ");
                holder.tvVisitDate.setText(visitTime[0]);
                holder.tvVisitTime.setText(visitTime[1]);
                holder.tvVisitDate.setVisibility(View.VISIBLE);
                holder.tvVisitTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvVisitDate.setVisibility(View.GONE);
	            holder.tvVisitTime.setVisibility(View.GONE);
			}
            holder.visitTipIcon.setVisibility(View.GONE);
            holder.ivVisitState.setImageResource(R.drawable.ic_checked);
            holder.tvVisitStateTip.setText("到访");
            holder.tvVisitVerify.setVisibility(View.GONE);
            holder.visitLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            holder.visitLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));

            if (!TextUtils.isEmpty(item.getIntent_time())) {
                String[] formTime = item.getIntent_time().split(" ");
                holder.tvFormDate.setText(formTime[0]);
                holder.tvFormTime.setText(formTime[1]);
                holder.tvFormDate.setVisibility(View.VISIBLE);
                holder.tvFormTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvFormDate.setVisibility(View.GONE);
	            holder.tvFormTime.setVisibility(View.GONE);
			}
            holder.ivFormState.setImageResource(R.drawable.ic_checked);
            holder.formLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            holder.formLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            
            if (!TextUtils.isEmpty(item.getOrder_time())) {
                String[] dealTime = item.getOrder_time().split(" ");
                holder.tvDealDate.setText(dealTime[0]);
                holder.tvDealTime.setText(dealTime[1]);
	            holder.tvDealDate.setVisibility(View.VISIBLE);
	            holder.tvDealTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvDealDate.setVisibility(View.GONE);
	            holder.tvDealTime.setVisibility(View.GONE);
			}
            holder.ivDealState.setImageResource(R.drawable.ic_checked);
            holder.dealLine1.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));
            holder.dealLine2.setBackgroundColor(UIUtil.getResColor(R.color.customer_state_normal_divider_color));

            if (!TextUtils.isEmpty(item.getFinish_time())) {
                String[] commissionTime = item.getFinish_time().split(" ");
                holder.tvCommissionDate.setText(commissionTime[0]);
                holder.tvCommissionTime.setText(commissionTime[1]);
            } else {
	            holder.tvCommissionDate.setVisibility(View.GONE);
	            holder.tvCommissionTime.setVisibility(View.GONE);
			}
            holder.ivCommissionState.setImageResource(R.drawable.ic_unchecked);
		} else if (status.equals("8")) {
        	holder.extendReportTimeView.setVisibility(View.GONE);
            holder.extendDivider.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(item.getRecommend_time())) {
				String[] reportTime = item.getRecommend_time().split(" ");
	            holder.tvReportDate.setText(reportTime[0]);
	            holder.tvReportTime.setText(reportTime[1]);
                holder.tvReportDate.setVisibility(View.VISIBLE);
                holder.tvReportTime.setVisibility(View.VISIBLE);
			} else {
	            holder.tvReportDate.setVisibility(View.GONE);
	            holder.tvReportTime.setVisibility(View.GONE);
			}
            holder.ivReportState.setImageResource(R.drawable.ic_checked);
            holder.tvReportStateTip.setText("有效");
            holder.tvReportLeftTime.setVisibility(View.GONE);
            holder.reportLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            holder.reportLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));

            if (!TextUtils.isEmpty(item.getVisit_time())) {
    			String[] visitTime = item.getVisit_time().split(" ");
                holder.tvVisitDate.setText(visitTime[0]);
                holder.tvVisitTime.setText(visitTime[1]);
                holder.tvVisitDate.setVisibility(View.VISIBLE);
                holder.tvVisitTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvVisitDate.setVisibility(View.GONE);
	            holder.tvVisitTime.setVisibility(View.GONE);
			}
            holder.visitTipIcon.setVisibility(View.GONE);
            holder.ivVisitState.setImageResource(R.drawable.ic_checked);
            holder.tvVisitStateTip.setText("到访");
            holder.tvVisitVerify.setVisibility(View.GONE);
            holder.visitLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            holder.visitLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));

            if (!TextUtils.isEmpty(item.getIntent_time())) {
                String[] intentTime = item.getIntent_time().split(" ");
                holder.tvFormDate.setText(intentTime[0]);
                holder.tvFormTime.setText(intentTime[1]);
                holder.tvFormDate.setVisibility(View.VISIBLE);
                holder.tvFormTime.setVisibility(View.VISIBLE);
            } else {
                holder.tvFormDate.setVisibility(View.GONE);
                holder.tvFormTime.setText(View.GONE);
			}
            holder.ivFormState.setImageResource(R.drawable.ic_checked);
            holder.formLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            holder.formLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            
            if (!TextUtils.isEmpty(item.getOrder_time())) {
                String[] dealTime = item.getOrder_time().split(" ");
                holder.tvDealDate.setText(dealTime[0]);
                holder.tvDealTime.setText(dealTime[1]);
	            holder.tvDealDate.setVisibility(View.VISIBLE);
	            holder.tvDealTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvDealDate.setVisibility(View.GONE);
	            holder.tvDealTime.setVisibility(View.GONE);
			}
            holder.ivDealState.setImageResource(R.drawable.ic_checked);
            holder.dealLine1.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));
            holder.dealLine2.setBackgroundColor(UIUtil.getResColor(R.color.common_text_green));

            if (!TextUtils.isEmpty(item.getFinish_time())) {
                String[] commissionTime = item.getFinish_time().split(" ");
                holder.tvCommissionDate.setText(commissionTime[0]);
                holder.tvCommissionTime.setText(commissionTime[1]);
                holder.tvCommissionDate.setVisibility(View.VISIBLE);
                holder.tvCommissionTime.setVisibility(View.VISIBLE);
            } else {
	            holder.tvCommissionDate.setVisibility(View.GONE);
	            holder.tvCommissionTime.setVisibility(View.GONE);
			}
            holder.ivCommissionState.setImageResource(R.drawable.ic_checked);
		}
        
        holder.visitTipIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (is_expired.equals("1")) {
					showShortToast("客户过期,无法操作");
					return;
				}
				VisitRecommendDialog dialog = new VisitRecommendDialog(mContext, CustomerDetailAdapter.this, "温馨提示", "提示业务员客户已到访?", holder.visitTipIcon, holder.tvVisitDate, holder.tvVisitTime);
				dialog.showdialog(houseId);
			}
		});
        
        
        List<ClientRecommendHouseTraceListItem> traceList = item.getTrace_list();
        if (ListUtils.isEmpty(traceList)) {
			holder.noRecordContainer.setVisibility(View.VISIBLE);
			holder.recordContainer1.setVisibility(View.GONE);
			holder.recordContainer2.setVisibility(View.GONE);
			holder.recordDivider1.setVisibility(View.GONE);
			holder.recordDivider2.setVisibility(View.GONE);
			holder.moreRecordView.setVisibility(View.GONE);
		} else if (traceList.size() == 1) {
			holder.noRecordContainer.setVisibility(View.GONE);
			holder.recordContainer1.setVisibility(View.VISIBLE);
			holder.recordContainer2.setVisibility(View.GONE);
			holder.recordDivider1.setVisibility(View.GONE);
			holder.recordDivider2.setVisibility(View.VISIBLE);
			holder.moreRecordView.setVisibility(View.VISIBLE);
			ClientRecommendHouseTraceListItem record1 = traceList.get(0);
			holder.name1.setText(record1.getUser());
			holder.record1.setText(record1.getContent());
			holder.time1.setText(record1.getTrace_time());
		} else if (traceList.size() == 2) {
			holder.noRecordContainer.setVisibility(View.GONE);
			holder.recordContainer1.setVisibility(View.VISIBLE);
			holder.recordContainer2.setVisibility(View.VISIBLE);
			holder.recordDivider1.setVisibility(View.VISIBLE);
			holder.recordDivider2.setVisibility(View.VISIBLE);
			holder.moreRecordView.setVisibility(View.VISIBLE);
			ClientRecommendHouseTraceListItem record1 = traceList.get(0);
			holder.name1.setText(record1.getUser());
			holder.record1.setText(record1.getContent());
			holder.time1.setText(record1.getTrace_time());

			ClientRecommendHouseTraceListItem record2 = traceList.get(1);
			holder.name2.setText(record2.getUser());
			holder.record2.setText(record2.getContent());
			holder.time2.setText(record2.getTrace_time());
		} else {
			holder.noRecordContainer.setVisibility(View.GONE);
			holder.recordContainer1.setVisibility(View.VISIBLE);
			holder.recordContainer2.setVisibility(View.VISIBLE);
			holder.recordDivider1.setVisibility(View.VISIBLE);
			holder.recordDivider2.setVisibility(View.VISIBLE);
			holder.moreRecordView.setVisibility(View.VISIBLE);
			
			ClientRecommendHouseTraceListItem record1 = traceList.get(0);
			holder.name1.setText(record1.getUser());
			holder.record1.setText(record1.getContent());
			holder.time1.setText(record1.getTrace_time());

			ClientRecommendHouseTraceListItem record2 = traceList.get(1);
			holder.name2.setText(record2.getUser());
			holder.record2.setText(record2.getContent());
			holder.time2.setText(record2.getTrace_time());
		}
        
        holder.ivSaleManMessage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:" + item.getSalesman_phone());
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(sendIntent);
            }
        });

        holder.ivSaleManCall.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + item.getSalesman_phone());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                mContext.startActivity(intent);
            }
        });
        
        
        
        holder.tvAddTraceRecord.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
				if (is_expired.equals("1")) {
					showShortToast("客户过期,无法操作");
					return;
				}
            	if (status.equals("2")) {
            		showShortToast("客户无效,无法操作");
					return;
				}
            	if (listener != null) {
					listener.addTraceRecord(item.getHouse_id());
				}
            }
        });
        
        holder.moreRecordView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActIndependentCustomerTraceRecord.class);
                intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
                intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, houseId);
                mContext.startActivity(intent);
            }
        });
        
        holder.extendReportTimeView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
//				if (is_expired.equals("1")) {
//					showShortToast("客户过期,无法操作");
//					return;
//				}
				if (status.equals("2")) {
					showShortToast("客户无效,无法操作");
					return;
				}
            	if (status.equals("3") || status.equals("4")) {
            		DialogManager.showExtendReportTimeDialog(mContext, CustomerDetailAdapter.this, clientId, houseId, holder.tvReportLeftTime);
				} else if (status.equals("5")) {
            		DialogManager.showExtendVisitTimeDialog(mContext, CustomerDetailAdapter.this, clientId, houseId, holder.tvVisitVerify);
				}
            }
        });
        
        holder.selectTimeView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
//            	if (is_expired.equals("1")) {
//					showShortToast("客户过期,无法操作");
//					return;
//				}
            	if (status.equals("2")) {
            		showShortToast("客户无效,无法操作");
					return;
				}
                DateTimePickDialogUtil util = new DateTimePickDialogUtil(mContext, holder.tvDateTime, CustomerDetailAdapter.this, clientId, houseId);
                util.showDateSelectDialog("设置回访日期");
            }
        });
        
        return convertView;
    }
    
    class ViewHolder {
        //-------Head--------
        TextView tvProjectName;
        TextView tvSaleManName;
        ImageView ivSaleManMessage;
        ImageView ivSaleManCall;
        ImageView ivOverDue;
        
        //------Report------
        TextView tvReportDate;
        TextView tvReportTime;
        ImageView ivReportState;
        TextView tvReportStateTip;
        TextView tvReportLeftTime;
        TextView reportLine1;
        TextView reportLine2;
        TextView tvContinueDay1;
        
        //-----Visit------
        TextView tvVisitDate;
        TextView tvVisitTime;
        ImageView visitTipIcon;
        ImageView ivVisitState;
        TextView tvVisitStateTip;
        TextView tvVisitVerify;
        TextView visitLine1;
        TextView visitLine2;
        TextView tvContinueDay2;
        
        //----Form----
        TextView tvFormDate;
        TextView tvFormTime;
        ImageView ivFormState;
        TextView tvFormStateTip;
        TextView formLine1;
        TextView formLine2;
        
        //----Deal----
        TextView tvDealDate;
        TextView tvDealTime;
        ImageView ivDealState;
        TextView tvDealStateTip;
        TextView dealLine1;
        TextView dealLine2;
        
        //----Commission----
        TextView tvCommissionDate;
        TextView tvCommissionTime;
        ImageView ivCommissionState;
        TextView tvCommissionStateTip;
        
        //----Bottom----
        View extendReportTimeView;
        TextView tvExtendTime;
        ImageView extendDivider;
        
        View selectTimeView;
        TextView tvDateTime;
        
        TextView tvSaleManFeedBack;
        
        View noRecordContainer;
        View recordContainer1;
        ImageView recordDivider1;
        ImageView recordDivider2;
        View recordContainer2;
        View moreRecordView;
        
        TextView tvAddTraceRecord;
        TextView name1;
        TextView time1;
        TextView record1;
        TextView name2;
        TextView time2;
        TextView record2;
    }
    
    @Override
    public void extendReprotTime(String clientId, String houseId, TextView tvLeftTime) {
        ClientHttpRequest.extendRecommendTimeRequest(clientId, houseId, new ExtendRecommendTimeHandler(tvLeftTime, 1));
    }
    
    @Override
	public void extendVisitTime(String clientId, String houseId, TextView tvLeftTime) {
        ClientHttpRequest.extendVisitTimeRequest(clientId, houseId, new ExtendRecommendTimeHandler(tvLeftTime, 2));
	}
    
    class ExtendRecommendTimeHandler extends HttpResponseHandler {
        
        private TextView tvLeftTime;
        private int type;
        
        public ExtendRecommendTimeHandler(TextView tvLeftTime, int type) {
            this.tvLeftTime = tvLeftTime;
            this.type = type;
        }
        
        @Override
        public void onStart() {
            super.onStart();
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                ExtendVisitBean bean = JsonUtil.toObject(new String(content), ExtendVisitBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        showShortToast("设置成功");
                        ExtendVisitData data = bean.getData();
                        if (data != null) {
                            if (type == 1) {
                                tvLeftTime.setText(data.getRec_remain_desc());
                            } else {
                                tvLeftTime.setText(data.getVisit_remain_desc());
                            }
						}
                    } else {
                        showShortToast(bean.getMessage());
                    }
                } else {
                    showShortToast("设置失败,请重试");
                }
            } else {
                showShortToast("延长失败,请重试");
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
    public void setRevisitTime(String clientId, String houseId, String time, TextView tvDateTime) {
        ClientHttpRequest.setRevisitTimeRequest(clientId, houseId, time, new SetRevisitTimeHandler(time, tvDateTime));
    }

    class SetRevisitTimeHandler extends HttpResponseHandler {

        private String time;
        private TextView tvDateTime;
        
        public SetRevisitTimeHandler(String time, TextView tvDateTime) {
            this.time = time;
            this.tvDateTime = tvDateTime;
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
                        tvDateTime.setText(time);
                    } else {
                        showShortToast(bean.getMessage());
                    }
                } else {
                    showShortToast("设置失败,请重试");
                }
            } else {
                showShortToast("设置失败,请重试");
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
    
    private void showShortToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
    
    public interface CustomerActionListener {
    	void addTraceRecord(String houseId);
    	
    	void refresh();
    }


	@Override
	public void confirm(ImageView icon, TextView tvDate, TextView tvTime, String houseId) {
		ClientHttpRequest.recommendClientVisit(clientId, houseId, new VisitRecommendHandler(icon, tvDate, tvTime));
	}
	
	class VisitRecommendHandler extends HttpResponseHandler {
		
		ImageView icon;
	    TextView tvDate;
	    TextView tvTime;
		
		public VisitRecommendHandler(ImageView icon, TextView tvDate, TextView tvTime) {
	        this.icon = icon;
	        this.tvDate = tvDate;
	        this.tvTime = tvTime;
		}
		
		@Override
		public void onStart() {
			super.onStart();
		}
		
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] content) {
			super.onSuccess(statusCode, headers, content);if (content != null) {
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        showShortToast("提醒成功");
                        if (listener != null) {
                			listener.refresh();
                		}
                    } else {
                        showShortToast(bean.getMessage());
                    }
                } else {
                    showShortToast("提醒失败,请重试");
                }
            } else {
                showShortToast("提醒失败,请重试");
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
