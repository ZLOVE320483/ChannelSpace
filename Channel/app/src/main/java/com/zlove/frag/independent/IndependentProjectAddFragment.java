package com.zlove.frag.independent;

import org.apache.http.Header;

import android.content.Intent;
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
import com.zlove.bean.project.ProjectItemHouseList;
import com.zlove.bean.project.ProjectSerchByCodeBean;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ProjectHttpRequest;


public class IndependentProjectAddFragment extends BaseFragment implements OnClickListener {

	private EditText etCode;
	private Button btnConfirm;
	
	private String code = "";
	
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_project_add;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("新增楼盘"); 
        
        etCode = (EditText) view.findViewById(R.id.id_user_name);
        btnConfirm = (Button) view.findViewById(R.id.id_confirm);
        
        btnConfirm.setOnClickListener(this);
    }

	@Override
	public void onClick(View view) {
		if (view == btnConfirm) {
			UIUtil.hideKeyboard(getActivity());
			code = etCode.getText().toString().trim();
			if (TextUtils.isEmpty(code)) {
				showShortToast("请输入楼盘代码");
				return;
			}
			ProjectHttpRequest.addProjectByCode(code, new SearchProjectByCodeHandler(this));
		}
	}
	
	class SearchProjectByCodeHandler extends HttpResponseHandlerFragment<IndependentProjectAddFragment> {

		public SearchProjectByCodeHandler(IndependentProjectAddFragment context) {
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
				ProjectSerchByCodeBean bean = JsonUtil.toObject(new String(content), ProjectSerchByCodeBean.class);
				if (bean != null) {
					if (bean.getStatus() == 200) {
						ProjectItemHouseList item = bean.getData();
						if (item != null) {
							Intent intent = new Intent();
							intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ITEM_SEARCH_BY_CODE, item);
							finishActivity(intent);
						}
					} else {
                        showShortToast(bean.getMessage());
                    }
				} else {
                    showShortToast("楼盘信息不存在");
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
