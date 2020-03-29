package com.zlove.frag;

import org.apache.http.Header;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.UIUtil;
import com.zlove.bean.project.ProjectRuleDetailBean;
import com.zlove.bean.project.ProjectRuleDetailData;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ProjectHttpRequest;


public class ProjectCooperateRuleDetailFragment extends BaseFragment {

	private TextView tvRuleTitle;
	private TextView tvFirstCommission;
	private TextView tvTailCommission;
	private TextView tvCommissionPoint;
	private TextView tvPayFinisher;
	private TextView tvRuleTime;
	private TextView tvRule;
	private TextView tvNoRule;
	
	private String houseRuleId;
	
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_project_cooperate_rule_detail;
    }

    @Override
    protected void setUpView(View view) {
    	if (getActivity().getIntent() != null) {
			Intent intent = getActivity().getIntent();
			if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_RULE_ID)) {
				houseRuleId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_RULE_ID);
			}
		}
    	
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("规则详情");
        
        tvRuleTitle = (TextView) view.findViewById(R.id.tv_rule_title);
        tvFirstCommission = (TextView) view.findViewById(R.id.tv_first_commission);
        tvTailCommission = (TextView) view.findViewById(R.id.tv_tail_commission);
        tvCommissionPoint = (TextView) view.findViewById(R.id.tv_commission_point);
        tvPayFinisher = (TextView) view.findViewById(R.id.tv_pay_type);
        tvRuleTime = (TextView) view.findViewById(R.id.tv_cooperate_time);
        tvRule = (TextView) view.findViewById(R.id.tv_rule);
        tvNoRule = (TextView) view.findViewById(R.id.tv_no_data);
        tvNoRule.setVisibility(View.GONE);
        
        ProjectHttpRequest.requestProjectRuleDetail(houseRuleId, new GetProjectRuleDetailHandler(this));
        
    }
    
    class GetProjectRuleDetailHandler extends HttpResponseHandlerFragment<ProjectCooperateRuleDetailFragment> {

		public GetProjectRuleDetailHandler(ProjectCooperateRuleDetailFragment context) {
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
				ProjectRuleDetailBean bean = JsonUtil.toObject(new String(content), ProjectRuleDetailBean.class);
				if (bean != null) {
					if (bean.getStatus() == 200) {
						ProjectRuleDetailData data = bean.getData();
						if (data != null) {
					        tvNoRule.setVisibility(View.GONE);
							tvRuleTitle.setText(UIUtil.replaceNullOrEmpty(data.getTitle()));
							tvFirstCommission.setText(UIUtil.replaceNullOrEmpty(data.getFirst_money()));
							tvTailCommission.setText(UIUtil.replaceNullOrEmpty(data.getLast_money()));
							tvCommissionPoint.setText(UIUtil.replaceNullOrEmpty(data.getMoney_note()));
							tvPayFinisher.setText(UIUtil.replaceNullOrEmpty(data.getFinish_user()));
							tvRuleTime.setText(UIUtil.replaceNullOrEmpty(data.getCooperate_time()));
							tvRule.setText("合作规则：" + Html.fromHtml(data.getCooperate_rule()));
						} else {
					        tvNoRule.setVisibility(View.VISIBLE);
					        tvNoRule.setText("该楼盘暂未发布合作规则");
                        }
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
