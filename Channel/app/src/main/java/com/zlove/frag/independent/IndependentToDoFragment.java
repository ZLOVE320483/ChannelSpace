
package com.zlove.frag.independent;

import org.apache.http.Header;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zlove.act.independent.ActMessageContactCustomer;
import com.zlove.act.independent.ActMessageCooperateRule;
import com.zlove.act.independent.ActMessageCustomerProgress;
import com.zlove.act.independent.ActMessageCustomerTrace;
import com.zlove.act.independent.ActMessageProjectDynamic;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.bean.message.MessageHomeBean;
import com.zlove.bean.message.MessageHomeData;
import com.zlove.channel.R;
import com.zlove.http.MessageHttpRequest;

public class IndependentToDoFragment extends BaseFragment implements OnClickListener {


    private View contactCustomerView = null;
    private View customerProgressView = null;
    private View customerTraceView = null;
    private View projectDynamicView = null;
    private View cooperateRuleView = null;
    
    private TextView tvContactCustomerCount;
    private TextView tvCustomerProgressCount;
    private TextView tvCustomerTraceCount;
    private TextView tvProjectDynamicCount;
    private TextView tvCooperateRuleCount;
    
    private Dialog loadingDialog;
    
    public boolean isRequest = false;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_todo;
    }

    @Override
    protected void setUpView(View view) {
        ((TextView) view.findViewById(R.id.id_title)).setText("消息");

        loadingDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        
        contactCustomerView = view.findViewById(R.id.id_message_contact_customer);
        customerProgressView = view.findViewById(R.id.id_message_customer_progress);
        customerTraceView = view.findViewById(R.id.id_message_customer_trace);
        projectDynamicView = view.findViewById(R.id.id_message_project_dynamic);
        cooperateRuleView = view.findViewById(R.id.id_message_cooperate_rule);
        
        tvContactCustomerCount = (TextView) view.findViewById(R.id.id_message_contact_customer_count);
        tvCustomerProgressCount = (TextView) view.findViewById(R.id.id_message_customer_progress_count);
        tvCustomerTraceCount = (TextView) view.findViewById(R.id.id_message_customer_trace_count);
        tvProjectDynamicCount = (TextView) view.findViewById(R.id.id_message_project_dynamic_count);
        tvCooperateRuleCount = (TextView) view.findViewById(R.id.id_message_cooperate_rule_count);
        
        contactCustomerView.setOnClickListener(this);
        customerProgressView.setOnClickListener(this);
        customerTraceView.setOnClickListener(this);
        projectDynamicView.setOnClickListener(this);
        cooperateRuleView.setOnClickListener(this);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	MessageHttpRequest.requestMessageHomeCount(new MessageHomeCountHandler(this));
    }
    
    @Override
    public void onClick(View v) {
        if (v == contactCustomerView) {
            Intent intent = new Intent(getActivity(), ActMessageContactCustomer.class);
            startActivity(intent);
        } else if (v == customerProgressView) {
            Intent intent = new Intent(getActivity(), ActMessageCustomerProgress.class);
            startActivity(intent);
        } else if (v == customerTraceView) {
            Intent intent = new Intent(getActivity(), ActMessageCustomerTrace.class);
            startActivity(intent);
        } else if (v == projectDynamicView) {
            Intent intent = new Intent(getActivity(), ActMessageProjectDynamic.class);
            startActivity(intent);
        } else if (v == cooperateRuleView) {
            Intent intent = new Intent(getActivity(), ActMessageCooperateRule.class);
            startActivity(intent);
        }
    }
    
    class MessageHomeCountHandler extends HttpResponseHandlerFragment<IndependentToDoFragment> {

		public MessageHomeCountHandler(IndependentToDoFragment context) {
			super(context);
			isRequest = true;
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
				MessageHomeBean bean = JsonUtil.toObject(new String(content), MessageHomeBean.class);
				if (bean != null) {
					if (bean.getStatus() == 200) {
						MessageHomeData data = bean.getData();
						if (data != null) {
							int contactCustomerCount = data.getContact_client_num();
							int customerProgressCount = data.getProcess_client_num();
							int customerTraceCount = data.getTrack_client_num();
							int projectDynamicCount = data.getHouse_news_num();
							int cooperateRuleCount = data.getCooperate_num();
							
							if (contactCustomerCount > 0) {
								tvContactCustomerCount.setVisibility(View.VISIBLE);
								tvContactCustomerCount.setText(String.valueOf(contactCustomerCount));
							} else {
								tvContactCustomerCount.setVisibility(View.GONE);
							}
							
							if (customerProgressCount > 0) {
								tvCustomerProgressCount.setVisibility(View.VISIBLE);
								tvCustomerProgressCount.setText(String.valueOf(customerProgressCount));
							} else {
								tvCustomerProgressCount.setVisibility(View.GONE);
							}
							
							if (customerTraceCount > 0) {
								tvCustomerTraceCount.setVisibility(View.VISIBLE);
								tvCustomerTraceCount.setText(String.valueOf(customerTraceCount));
							} else {
								tvCustomerTraceCount.setVisibility(View.GONE);
							}
							
							if (projectDynamicCount > 0) {
								tvProjectDynamicCount.setVisibility(View.VISIBLE);
								tvProjectDynamicCount.setText(String.valueOf(projectDynamicCount));
							} else {
								tvProjectDynamicCount.setVisibility(View.GONE);
							}
							
							if (cooperateRuleCount > 0) {
								tvCooperateRuleCount.setVisibility(View.VISIBLE);
								tvCooperateRuleCount.setText(String.valueOf(cooperateRuleCount));
							} else {
								tvCooperateRuleCount.setVisibility(View.GONE);
							}
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
			isRequest = false;
			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
		}
    	
    }

}
