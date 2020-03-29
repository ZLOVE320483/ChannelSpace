package com.zlove.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlove.base.util.ListUtils;
import com.zlove.base.util.UIUtil;
import com.zlove.bean.client.ClientRecommendHouseListItem;
import com.zlove.bean.client.ClientTraceListItem;
import com.zlove.channelsaleman.R;

import java.util.List;

/**
 * Created by ZLOVE on 2016/12/28.
 */
public class CustomerDetailAdapter extends SingleDataListAdapter<ClientRecommendHouseListItem> {

    private CustomerFromIndependentOperateListener independentOperateListener;
    private CustomerFromSelfOperateListener selfOperateListener;

    public CustomerDetailAdapter(List<ClientRecommendHouseListItem> data, Context context) {
        super(data, context);
    }

    public void setIndependentOperateListener(CustomerFromIndependentOperateListener independentOperateListener) {
        this.independentOperateListener = independentOperateListener;
    }

    public void setSelfOperateListener(CustomerFromSelfOperateListener selfOperateListener) {
        this.selfOperateListener = selfOperateListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getType().equals("1")) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = getItemViewType(position);
        IndependentViewHolder independentViewHolder;
        SelfViewHolder selfViewHolder;
        if (convertView == null) {
            if (itemType == 1) {
                independentViewHolder = new IndependentViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_detail_from_indenpendent, null);
                independentViewHolder.findViews(convertView);
                independentViewHolder.bindView(position);
                convertView.setTag(independentViewHolder);
            } else {
                selfViewHolder = new SelfViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_detail_from_self, null);
                selfViewHolder.findViews(convertView);
                selfViewHolder.bindView(position);
                convertView.setTag(selfViewHolder);
            }
        } else {
            if (itemType == 1) {
                independentViewHolder = (IndependentViewHolder) convertView.getTag();
                independentViewHolder.bindView(position);
            } else {
                selfViewHolder = (SelfViewHolder) convertView.getTag();
                selfViewHolder.bindView(position);
            }
        }

        return convertView;
    }

    class IndependentViewHolder {
        TextView tvProjectName;
        TextView tvIndependentName;
        TextView tvVisitAgain;
        ImageView ivIndependentPhone;
        TextView tvStatusReport;
        TextView tvStatusVisit;
        TextView tvDecideVisit;
        TextView tvDate1;
        TextView tvTime1;
        TextView tvDate2;
        TextView tvTime2;
        TextView tvDate3;
        TextView tvTime3;
        TextView tvDate4;
        TextView tvTime4;
        TextView tvDate5;
        TextView tvTime5;
        TextView tvLeft1;
        TextView tvLeft2;

        //-----客户进度----
        ImageView ivStatus1;
        ImageView ivStatus2;
        ImageView ivStatus3;
        ImageView ivStatus4;
        ImageView ivStatus5;

        View line_1_1;
        View line_2_1;
        View line_2_2;
        View line_3_1;
        View line_3_2;
        View line_4_1;
        View line_4_2;
        View line_5_1;
        //----客户进度----

        TextView tvCustomerEffectJudge;
        View selectDateTimeView;
        TextView tvDateTime;
        View setOverdueView;
        TextView tvDisable;
        TextView tvAddTraceRecord;

        View noRecordView;
        View moreRecordView;
        LinearLayout recordContainer;

        private void findViews(View view) {
            tvProjectName = (TextView) view.findViewById(R.id.id_project_name);
            tvIndependentName = (TextView) view.findViewById(R.id.id_independent_name);
            tvVisitAgain = (TextView) view.findViewById(R.id.visit_again);
            ivIndependentPhone = (ImageView) view.findViewById(R.id.id_independent_phone);
            tvStatusReport = (TextView) view.findViewById(R.id.status_report);
            tvStatusVisit = (TextView) view.findViewById(R.id.status_visit);
            tvDecideVisit = (TextView) view.findViewById(R.id.visit_decide);
            tvDate1 = (TextView) view.findViewById(R.id.date_1);
            tvTime1 = (TextView) view.findViewById(R.id.time_1);
            tvDate2 = (TextView) view.findViewById(R.id.date_2);
            tvTime2 = (TextView) view.findViewById(R.id.time_2);
            tvDate3 = (TextView) view.findViewById(R.id.date_3);
            tvTime3 = (TextView) view.findViewById(R.id.time_3);
            tvDate4 = (TextView) view.findViewById(R.id.date_4);
            tvTime4 = (TextView) view.findViewById(R.id.time_4);
            tvDate5 = (TextView) view.findViewById(R.id.date_5);
            tvTime5 = (TextView) view.findViewById(R.id.time_5);
            tvLeft1 = (TextView) view.findViewById(R.id.left1);
            tvLeft2 = (TextView) view.findViewById(R.id.left2);

            //-----客户进度----
            ivStatus1 = (ImageView) view.findViewById(R.id.id_state_report);
            ivStatus2 = (ImageView) view.findViewById(R.id.id_state_visit);
            ivStatus3 = (ImageView) view.findViewById(R.id.id_state_from);
            ivStatus4 = (ImageView) view.findViewById(R.id.id_state_appointment);
            ivStatus5 = (ImageView) view.findViewById(R.id.id_state_deal);
            line_1_1 = view.findViewById(R.id.id_state_1_1);
            line_2_1 = view.findViewById(R.id.id_state_2_1);
            line_2_2 = view.findViewById(R.id.id_state_2_2);
            line_3_1 = view.findViewById(R.id.id_state_3_1);
            line_3_2 = view.findViewById(R.id.id_state_3_2);
            line_4_1 = view.findViewById(R.id.id_state_4_1);
            line_4_2 = view.findViewById(R.id.id_state_4_2);
            line_5_1 = view.findViewById(R.id.id_state_5_1);
            //-----客户进度----

            tvAddTraceRecord= (TextView) view.findViewById(R.id.add_customer_trace_record);

            tvCustomerEffectJudge = (TextView) view.findViewById(R.id.id_customer_effect_judge);
            selectDateTimeView = view.findViewById(R.id.select_date_time);
            setOverdueView = view.findViewById(R.id.set_overdue);
            tvDisable = (TextView) view.findViewById(R.id.is_disabled);
            tvDateTime = (TextView) view.findViewById(R.id.date_time);
            noRecordView = view.findViewById(R.id.no_record_cotainer);
            moreRecordView = view.findViewById(R.id.customer_more_record_view);
            recordContainer = (LinearLayout) view.findViewById(R.id.record_container);
        }

        private void bindView(int position) {
            final ClientRecommendHouseListItem data = getItem(position);
            if (data == null) {
                return;
            }
            final String phone = data.getBroker_phone();
            if (!TextUtils.isEmpty(data.getHouse_name())) {
                tvProjectName.setText(data.getHouse_name());
            }
            tvIndependentName.setText(data.getBroker_name());
            tvDateTime.setText(data.getRe_visite_time());
            if (!TextUtils.isEmpty(data.getRec_continue_desc())) {
                tvLeft1.setText(data.getRec_continue_desc());
            }
            if (!TextUtils.isEmpty(data.getVisit_continue_desc())) {
                tvLeft2.setText(data.getVisit_continue_desc());
            }
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
            String status = data.getStatus();
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
                    String[] noEffectTime = data.getNot_effect_time().split(" ");
                    tvDate1.setText(noEffectTime[0]);
                    tvTime1.setText(noEffectTime[1]);
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

            List<ClientTraceListItem> list = data.getTrace_list();
            recordContainer.removeAllViews();
            if (ListUtils.isEmpty(list)) {
                noRecordView.setVisibility(View.VISIBLE);
                recordContainer.setVisibility(View.GONE);
                moreRecordView.setVisibility(View.GONE);
            } else if (list.size() == 1) {
                noRecordView.setVisibility(View.GONE);
                recordContainer.setVisibility(View.VISIBLE);
                moreRecordView.setVisibility(View.GONE);
                ClientTraceListItem item = list.get(0);
                View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_trace_record, null);
                TextView tvName = (TextView) view.findViewById(R.id.id_user_name);
                TextView tvContent = (TextView) view.findViewById(R.id.tv_record);
                TextView tvTime = (TextView) view.findViewById(R.id.id_time);
                ImageView divider = (ImageView) view.findViewById(R.id.id_divider_1);
                divider.setVisibility(View.VISIBLE);
                tvName.setText(item.getUser());
                tvContent.setText(item.getContent());
                tvTime.setText(item.getTrace_time());
                recordContainer.addView(view);
            } else {
                noRecordView.setVisibility(View.GONE);
                recordContainer.setVisibility(View.VISIBLE);
                moreRecordView.setVisibility(View.VISIBLE);
                ClientTraceListItem item1 = list.get(0);
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_trace_record, null);
                TextView tvName1 = (TextView) view1.findViewById(R.id.id_user_name);
                TextView tvContent1 = (TextView) view1.findViewById(R.id.tv_record);
                TextView tvTime1 = (TextView) view1.findViewById(R.id.id_time);
                ImageView divider1 = (ImageView) view1.findViewById(R.id.id_divider_1);
                divider1.setVisibility(View.VISIBLE);
                tvName1.setText(item1.getUser());
                tvContent1.setText(item1.getContent());
                tvTime1.setText(item1.getTrace_time());
                recordContainer.addView(view1);

                ClientTraceListItem item2 = list.get(1);
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_trace_record, null);
                TextView tvName2 = (TextView) view2.findViewById(R.id.id_user_name);
                TextView tvContent2 = (TextView) view2.findViewById(R.id.tv_record);
                TextView tvTime2 = (TextView) view2.findViewById(R.id.id_time);
                ImageView divider2 = (ImageView) view2.findViewById(R.id.id_divider_1);
                divider2.setVisibility(View.VISIBLE);
                tvName2.setText(item2.getUser());
                tvContent2.setText(item2.getContent());
                tvTime2.setText(item2.getTrace_time());
                recordContainer.addView(view2);
            }


            ivIndependentPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("tel:" + phone);
                    Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                    mContext.startActivity(intent);
                }
            });
            tvCustomerEffectJudge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (independentOperateListener != null) {
                        independentOperateListener.customerEffectJudge(data.getHouse_id());
                    }
                }
            });
            tvDecideVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (independentOperateListener != null) {
                        independentOperateListener.decideVisit(data.getHouse_id());
                    }
                }
            });
            selectDateTimeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (independentOperateListener != null) {
                        independentOperateListener.selectDateTime(data.getHouse_id());
                    }
                }
            });
            setOverdueView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (independentOperateListener != null) {
                        independentOperateListener.setOverDue(data.getHouse_id());
                    }
                }
            });
            tvAddTraceRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (independentOperateListener != null) {
                        independentOperateListener.addRecord(data.getBroker_id(), data.getHouse_id());
                    }
                }
            });
            tvVisitAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (independentOperateListener != null) {
                        independentOperateListener.visitAgain(data.getHouse_id());
                    }
                }
            });
            moreRecordView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (independentOperateListener != null) {
                        independentOperateListener.showMoreRecord(data.getHouse_id());
                    }
                }
            });
        }

    }

    class SelfViewHolder {
        TextView tvProjectName;
        TextView tvDecideVisit;
        TextView tvDate1;
        TextView tvTime1;
        TextView tvDate2;
        TextView tvTime2;
        TextView tvDate3;
        TextView tvTime3;
        TextView tvDate4;
        TextView tvTime4;

        //-----客户进度----
        ImageView ivStatus1;
        ImageView ivStatus2;
        ImageView ivStatus3;
        ImageView ivStatus4;

        View line_1_1;
        View line_2_1;
        View line_2_2;
        View line_3_1;
        View line_3_2;
        View line_4_1;
        //----客户进度----

        TextView tvIsEffect;
        View selectDateTimeView;
        TextView tvDateTime;
        TextView tvAddTraceRecord;

        View noRecordView;
        View moreRecordView;
        LinearLayout recordContainer;
        
        private void findViews(View view) {
            tvProjectName = (TextView) view.findViewById(R.id.id_project_name);
            tvDecideVisit = (TextView) view.findViewById(R.id.visit_decide);
            tvDate1 = (TextView) view.findViewById(R.id.date_1);
            tvTime1 = (TextView) view.findViewById(R.id.time_1);
            tvDate2 = (TextView) view.findViewById(R.id.date_2);
            tvTime2 = (TextView) view.findViewById(R.id.time_2);
            tvDate3 = (TextView) view.findViewById(R.id.date_3);
            tvTime3 = (TextView) view.findViewById(R.id.time_3);
            tvDate4 = (TextView) view.findViewById(R.id.date_4);
            tvTime4 = (TextView) view.findViewById(R.id.time_4);

            //-----客户进度----
            ivStatus1 = (ImageView) view.findViewById(R.id.id_state_1);
            ivStatus2 = (ImageView) view.findViewById(R.id.id_state_2);
            ivStatus3 = (ImageView) view.findViewById(R.id.id_state_3);
            ivStatus4 = (ImageView) view.findViewById(R.id.id_state_4);
            line_1_1 = view.findViewById(R.id.id_state_1_1);
            line_2_1 = view.findViewById(R.id.id_state_2_1);
            line_2_2 = view.findViewById(R.id.id_state_2_2);
            line_3_1 = view.findViewById(R.id.id_state_3_1);
            line_3_2 = view.findViewById(R.id.id_state_3_2);
            line_4_1 = view.findViewById(R.id.id_state_4_1);
            //-----客户进度----
            tvAddTraceRecord= (TextView) view.findViewById(R.id.add_customer_trace_record);
            tvIsEffect = (TextView) view.findViewById(R.id.is_effect);
            selectDateTimeView = view.findViewById(R.id.select_date_time);
            tvDateTime = (TextView) view.findViewById(R.id.date_time);
            noRecordView = view.findViewById(R.id.no_record_cotainer);
            moreRecordView = view.findViewById(R.id.customer_more_record_view);
            recordContainer = (LinearLayout) view.findViewById(R.id.record_container);
        }

        private void bindView(int position) {
            final ClientRecommendHouseListItem data = getItem(position);
            if (data == null) {
                return;
            }

            tvDateTime.setText(data.getRe_visite_time());
            String isValid = data.getIs_valided();
            if (isValid.equals("0")) {
                tvIsEffect.setText("无效");
            } else if (isValid.equals("1")) {
                tvIsEffect.setText("有效");
            }

            String status = data.getStatus();
            if (!TextUtils.isEmpty(data.getHouse_name())) {
                tvProjectName.setText(data.getHouse_name());
            }
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

            List<ClientTraceListItem> list = data.getTrace_list();
            recordContainer.removeAllViews();
            if (ListUtils.isEmpty(list)) {
                noRecordView.setVisibility(View.VISIBLE);
                recordContainer.setVisibility(View.GONE);
                moreRecordView.setVisibility(View.GONE);
            } else if (list.size() == 1) {
                noRecordView.setVisibility(View.GONE);
                recordContainer.setVisibility(View.VISIBLE);
                moreRecordView.setVisibility(View.GONE);
                ClientTraceListItem item = list.get(0);
                View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_trace_record, null);
                TextView tvName = (TextView) view.findViewById(R.id.id_user_name);
                TextView tvContent = (TextView) view.findViewById(R.id.tv_record);
                TextView tvTime = (TextView) view.findViewById(R.id.id_time);
                ImageView divider = (ImageView) view.findViewById(R.id.id_divider_1);
                divider.setVisibility(View.VISIBLE);
                tvName.setText(item.getUser());
                tvContent.setText(item.getContent());
                tvTime.setText(item.getTrace_time());
                recordContainer.addView(view);
            } else {
                noRecordView.setVisibility(View.GONE);
                recordContainer.setVisibility(View.VISIBLE);
                moreRecordView.setVisibility(View.VISIBLE);
                ClientTraceListItem item1 = list.get(0);
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_trace_record, null);
                TextView tvName1 = (TextView) view1.findViewById(R.id.id_user_name);
                TextView tvContent1 = (TextView) view1.findViewById(R.id.tv_record);
                TextView tvTime1 = (TextView) view1.findViewById(R.id.id_time);
                ImageView divider1 = (ImageView) view1.findViewById(R.id.id_divider_1);
                divider1.setVisibility(View.VISIBLE);
                tvName1.setText(item1.getUser());
                tvContent1.setText(item1.getContent());
                tvTime1.setText(item1.getTrace_time());
                recordContainer.addView(view1);

                ClientTraceListItem item2 = list.get(1);
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_trace_record, null);
                TextView tvName2 = (TextView) view2.findViewById(R.id.id_user_name);
                TextView tvContent2 = (TextView) view2.findViewById(R.id.tv_record);
                TextView tvTime2 = (TextView) view2.findViewById(R.id.id_time);
                ImageView divider2 = (ImageView) view2.findViewById(R.id.id_divider_1);
                divider2.setVisibility(View.VISIBLE);
                tvName2.setText(item2.getUser());
                tvContent2.setText(item2.getContent());
                tvTime2.setText(item2.getTrace_time());
                recordContainer.addView(view2);
            }

            tvDecideVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selfOperateListener != null) {
                        selfOperateListener.selfDecideVisit(data.getHouse_id());
                    }
                }
            });

            selectDateTimeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selfOperateListener != null) {
                        selfOperateListener.selfSelectDateTime(data.getHouse_id());
                    }
                }
            });

            tvAddTraceRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selfOperateListener != null) {
                        selfOperateListener.selfAddRecord(data.getBroker_id(), data.getHouse_id());
                    }
                }
            });

            moreRecordView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selfOperateListener != null) {
                        selfOperateListener.selfShowMoreRecord(data.getHouse_id());
                    }
                }
            });
        }
    }

    public interface CustomerFromIndependentOperateListener {
        void customerEffectJudge(String houseId);
        void decideVisit(String houseId);
        void selectDateTime(String houseId);
        void setOverDue(String houseId);
        void addRecord(String brokerId, String houseId);
        void visitAgain(String houseId);
        void showMoreRecord(String houseId);
    }

    public interface CustomerFromSelfOperateListener {
        void selfDecideVisit(String houseId);
        void selfSelectDateTime(String houseId);
        void selfAddRecord(String brokerId, String houseId);
        void selfShowMoreRecord(String houseId);
    }
}
