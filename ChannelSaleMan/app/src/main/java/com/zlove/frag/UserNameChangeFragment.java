package com.zlove.frag;

import org.apache.http.Header;

import android.app.Activity;
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
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.http.UserHttpRequest;


public class UserNameChangeFragment extends BaseFragment implements OnClickListener {
    
    private Button btnConfirm = null;
    private EditText etUserName = null;
    private String userName;

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_user_name_change;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("修改姓名");
        etUserName = (EditText) view.findViewById(R.id.id_user_name);
        etUserName.setText(ChannelCookie.getInstance().getUserName());
        btnConfirm = (Button) view.findViewById(R.id.id_confirm);
        btnConfirm.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        if (view == btnConfirm) {
            UIUtil.hideKeyboard(getActivity());
            userName = etUserName.getText().toString().trim();
            if (TextUtils.isEmpty(userName)) {
                showShortToast("请输入您的真实姓名");
                return;
            }
            if (userName.equals(ChannelCookie.getInstance().getUserName())) {
                showShortToast("与当前姓名相同");
                return;
            }
            UserHttpRequest.userModifyNameRequest(userName, new ChangeUserNameHandler(this));
        }
    }
    
    class ChangeUserNameHandler extends HttpResponseHandlerFragment<UserNameChangeFragment> {

        public ChangeUserNameHandler(UserNameChangeFragment context) {
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
                        ChannelCookie.getInstance().saveUserName(userName);
                        showShortToast("修改成功");
                        getActivity().setResult(Activity.RESULT_OK);
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
