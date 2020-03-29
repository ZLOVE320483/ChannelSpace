package com.zlove.frag.independent;

import org.apache.http.Header;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.UIUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.channel.R;
import com.zlove.http.UserHttpRequest;


public class IndependentUserFeedBackFragment extends BaseFragment implements OnClickListener {
	
	private EditText etUserFeedBack = null;
	private Button btnSubmit = null;
	
	private String feedback = "";
	
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_user_feedback;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("用户反馈");
        
        etUserFeedBack = (EditText) view.findViewById(R.id.id_user_feedback);
        btnSubmit = (Button) view.findViewById(R.id.id_confirm);
        
        btnSubmit.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
    	if (view == btnSubmit) {
    		onSubmitClick();
		}
    }
    
    private void onSubmitClick() {
        UIUtil.hideKeyboard(getActivity());
		feedback = etUserFeedBack.getText().toString().trim();
		if (TextUtils.isEmpty(feedback)) {
			showShortToast("请输入反馈意见");
			return;
		}
		UserHttpRequest.userFeedBack(feedback ,new SubmitFeedBackHandler(this));
	}
    
    class SubmitFeedBackHandler extends HttpResponseHandlerFragment<IndependentUserFeedBackFragment> {

        public SubmitFeedBackHandler(IndependentUserFeedBackFragment context) {
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
                        showShortToast("提交成功");
                        finishActivity();
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
