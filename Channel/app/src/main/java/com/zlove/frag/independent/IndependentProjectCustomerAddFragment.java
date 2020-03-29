package com.zlove.frag.independent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.zlove.act.independent.ActFriendSelect;
import com.zlove.adapter.independent.ProjectCustomerAddAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.bean.client.ClientSelectBean;
import com.zlove.bean.client.ClientSelectData;
import com.zlove.bean.client.ClientSelectListItem;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;


public class IndependentProjectCustomerAddFragment extends BaseFragment implements OnItemClickListener {

    private Dialog reqDialog;
    
    private ListView listView;
    private ProjectCustomerAddAdapter adapter;
    private List<ClientSelectListItem> infos = new ArrayList<ClientSelectListItem>();
    
    private String projectId = "";
    private String clientId = "";
    private String saleManName = "";
    private String saleManId = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_project_customer_add;
    }

    @Override
    protected void setUpView(View view) {
    	Intent intent = getActivity().getIntent();
    	if (intent != null) {
			if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_ID)) {
				projectId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
			}
		}
    	
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("我的客户");
        
        if (reqDialog == null) {
            reqDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        }
        
        listView = (ListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        
        adapter = new ProjectCustomerAddAdapter(infos, getActivity());
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	if (ListUtils.isEmpty(infos)) {
			ClientHttpRequest.getSelectClientList(projectId, new GetClientSelectListHandler(this));
		}
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ClientSelectListItem item = adapter.getItem(arg2);
        if (item.getIs_recommended() == 1) {
            showShortToast("该客户已报备过");
            return;
        }
        clientId = item.getClient_id();
        Intent intent = new Intent(getActivity(), ActFriendSelect.class);
        intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
        startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_FRIEND);
    }

    class GetClientSelectListHandler extends HttpResponseHandlerFragment<IndependentProjectCustomerAddFragment> {

        
        public GetClientSelectListHandler(IndependentProjectCustomerAddFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
            if (reqDialog != null && !reqDialog.isShowing()) {
                reqDialog.show();
            }
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (!isAdded()) {
                return;
            }
            if (content != null) {
                ClientSelectBean bean = JsonUtil.toObject(new String(content), ClientSelectBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ClientSelectData data = bean.getData();
                        if (data != null) {
                            List<ClientSelectListItem> tmpList = data.getClient_list();
                            infos.addAll(tmpList);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
                if (ListUtils.isEmpty(infos)) {
                    showShortToast("暂无客户信息");
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
            if (reqDialog != null && reqDialog.isShowing()) {
                reqDialog.dismiss();
            }
        }
        
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentKey.REQUEST_CODE_SELECT_FRIEND) {
            if (data != null) {
                saleManId = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_ID);
                saleManName = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_NAME);
                Intent intent = new Intent();
                intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
                intent.putExtra(IntentKey.INTENT_KEY_SALEMAN_ID, saleManId);
                intent.putExtra(IntentKey.INTENT_KEY_SALEMAN_NAME, saleManName);
                finishActivity(intent);
            }
        }
        
    }
    
}
