package com.zlove.frag.independent;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zlove.act.independent.ActFriendSelect;
import com.zlove.act.independent.ActProjectSelectForCustomer;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.FriendHttpRequest;

import org.apache.http.Header;


public class IndependentFriendAddFragment extends BaseFragment implements OnClickListener {
    
    private View selectProjectView =null;
    private View selectFriendView = null;
    
    private TextView tvProjectName = null;
    private TextView tvFriendName = null;
    
    private Button btnSubmit = null;
    
    private String projectName = "";
    private String friendName = "";
    private String projectId = "";
    private String saleManId = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_friend_add;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("新增好友");
        
        selectProjectView = view.findViewById(R.id.id_report_project);
        selectFriendView = view.findViewById(R.id.id_project_friend);
        
        tvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
        tvFriendName = (TextView) view.findViewById(R.id.tv_friend_name);
        
        btnSubmit = (Button) view.findViewById(R.id.id_confirm);
        btnSubmit.setOnClickListener(this);
        
        selectProjectView.setOnClickListener(this);
        selectFriendView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == selectProjectView) {
            Intent intent = new Intent(getActivity(), ActProjectSelectForCustomer.class);
            intent.putExtra(ProjectSelectForCustomerFragment.FROM_ADD_FRIEND, true);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_PROJECT);
        } else if (v == selectFriendView) {
        	if (TextUtils.isEmpty(projectId)) {
                showShortToast("请先选择楼盘");
                return;
			}
            Intent intent = new Intent(getActivity(), ActFriendSelect.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
            intent.putExtra(IndependentFriendSelectFragment.FROM_ADD_FRIEND, true);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_FRIEND);
        } else if (v == btnSubmit) {
			if (TextUtils.isEmpty(projectId)) {
				showShortToast("请选择楼盘");
				return;
			}
			if (TextUtils.isEmpty(saleManId)) {
				showShortToast("请选择业务员");
				return;
			}
			FriendHttpRequest.addFriendRequest(saleManId, new AddFriendHandler(this));
		}
    }
    
    class AddFriendHandler extends HttpResponseHandlerFragment<IndependentFriendAddFragment> {

        public AddFriendHandler(IndependentFriendAddFragment context) {
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
                        showShortToast("添加成功");
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
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == IntentKey.REQUEST_CODE_SELECT_PROJECT) {
                projectName = data.getStringExtra(IntentKey.INTENT_KEY_PROJECT_NAME);
                tvProjectName.setText(projectName);
                projectId = data.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
                if (!TextUtils.isEmpty(projectId)) {
                    Intent intent = new Intent(getActivity(), ActFriendSelect.class);
                    intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
                    intent.putExtra(IndependentFriendSelectFragment.FROM_ADD_FRIEND, true);
                    startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_FRIEND);
				}
            } else if (requestCode == IntentKey.REQUEST_CODE_SELECT_FRIEND) {
                friendName = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_NAME);
                saleManId = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_ID);
                tvFriendName.setText(friendName);
            }
        }
    }

}
