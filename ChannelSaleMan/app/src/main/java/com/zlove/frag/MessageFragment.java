package com.zlove.frag;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zlove.act.ActMessageContactCustomer;
import com.zlove.act.ActMessageCooperateRule;
import com.zlove.act.ActMessageCustomerProgress;
import com.zlove.act.ActMessageCustomerTrace;
import com.zlove.act.ActMessageNewCustomer;
import com.zlove.act.ActMessageProjectDynamic;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.bean.message.MessageHomeBean;
import com.zlove.bean.message.MessageHomeData;
import com.zlove.channelsaleman.R;
import com.zlove.http.MessageHttpRequest;


public class MessageFragment extends BaseFragment implements OnClickListener {
    
    private View newCustomerView = null;
    private View contactCustomerView = null;
    private View customerProgressView = null;
    private View customerTraceView = null;
    private View projectDynamicView = null;
    private View cooperateRuleView = null;
    
    private TextView tvNewCustomerCount;
    private TextView tvContactCustomerCount;
    private TextView tvCustomerProgressCount;
    private TextView tvCustomerTraceCount;
    private TextView tvProjectDynamicCount;
    private TextView tvCooperateRuleCount;
    
    public boolean isRequest = false;

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_main_message;
    }

    @Override
    protected void setUpView(View view) {
        ((TextView) view.findViewById(R.id.id_title)).setText("消息");
        
        newCustomerView = view.findViewById(R.id.id_message_new_customer);
        contactCustomerView = view.findViewById(R.id.id_message_contact_customer);
        customerProgressView = view.findViewById(R.id.id_message_customer_progress);
        customerTraceView = view.findViewById(R.id.id_message_customer_trace);
        projectDynamicView = view.findViewById(R.id.id_message_project_dynamic);
        cooperateRuleView = view.findViewById(R.id.id_message_cooperate_rule);
        
        tvNewCustomerCount = (TextView) view.findViewById(R.id.id_message_new_customer_count);
        tvContactCustomerCount = (TextView) view.findViewById(R.id.id_message_contact_customer_count);
        tvCustomerProgressCount = (TextView) view.findViewById(R.id.id_message_customer_progress_count);
        tvCustomerTraceCount = (TextView) view.findViewById(R.id.id_message_customer_trace_count);
        tvProjectDynamicCount = (TextView) view.findViewById(R.id.id_message_project_dynamic_count);
        tvCooperateRuleCount = (TextView) view.findViewById(R.id.id_message_cooperate_rule_count);
        
        tvNewCustomerCount.setVisibility(View.GONE);
        tvContactCustomerCount.setVisibility(View.GONE);
        tvCustomerProgressCount.setVisibility(View.GONE);
        tvCustomerTraceCount.setVisibility(View.GONE);
        tvProjectDynamicCount.setVisibility(View.GONE);
        tvCooperateRuleCount.setVisibility(View.GONE);
        
        newCustomerView.setOnClickListener(this);
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
        if (v == newCustomerView) {
            Intent intent = new Intent(getActivity(), ActMessageNewCustomer.class);
            startActivity(intent);
        } else if (v == contactCustomerView) {
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
    
    class MessageHomeCountHandler extends HttpResponseHandlerFragment<MessageFragment> {

		public MessageHomeCountHandler(MessageFragment context) {
			super(context);
		}
		
		@Override
		public void onStart() {
			super.onStart();
            isRequest = true;
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
							int newCustomerCount = data.getNew_client_num();
							int contactCustomerCount = data.getContact_client_num();
							int customerProgressCount = data.getProcess_client_num();
							int customerTraceCount = data.getTrack_client_num();
							int projectDynamicCount = data.getHouse_news_num();
							int cooperateRuleCount = data.getCooperate_num();
							
							if (newCustomerCount > 0) {
								tvNewCustomerCount.setVisibility(View.VISIBLE);
								tvNewCustomerCount.setText(String.valueOf(newCustomerCount));
							
							} else {
								tvNewCustomerCount.setVisibility(View.GONE);
							}
							
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
            isRequest = false;
			super.onFinish();
		}
    	
    }

}
