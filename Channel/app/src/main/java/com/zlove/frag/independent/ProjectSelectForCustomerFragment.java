package com.zlove.frag.independent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zlove.act.independent.ActFriendSelect;
import com.zlove.adapter.independent.ProjectSelectAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.bean.project.ProjectSelectBean;
import com.zlove.bean.project.ProjectSelectData;
import com.zlove.bean.project.ProjectSelectListItem;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ProjectHttpRequest;


public class ProjectSelectForCustomerFragment extends BaseFragment implements OnItemClickListener {
	
	public static String FROM_ADD_FRIEND = "is_from_add_friend";
	private boolean isFromAddFriend = false;

    private Dialog reqDialog;
    
    private Button btnCancel;
    
    private ListView listView;
    private ProjectSelectAdapter adapter;
    private List<ProjectSelectListItem> infos = new ArrayList<ProjectSelectListItem>();

    private String projectId = "";
    private String saleManName = "";
    private String saleManId = "";
    
    private String clientId = "";

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_project_select;
    }

    @Override
    protected void setUpView(View view) {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_CLIENT_ID)) {
                clientId = intent.getStringExtra(IntentKey.INTENT_KEY_CLIENT_ID);
            }
            if (intent.hasExtra(FROM_ADD_FRIEND)) {
            	isFromAddFriend = intent.getBooleanExtra(FROM_ADD_FRIEND, false);
            }
        }
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("选择楼盘");
        
        if (reqDialog == null) {
            reqDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        }

        listView = (ListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        btnCancel = (Button) view.findViewById(R.id.id_confirm);
        btnCancel.setVisibility(View.GONE);

        if (adapter == null) {
            adapter = new ProjectSelectAdapter(infos, getActivity());
        }
        adapter.setFromAddFriend(isFromAddFriend);

        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(infos)) {
            ProjectHttpRequest.requestSelectProjectList(ChannelCookie.getInstance().getCurrentCityId(), "", "", "", "", "", clientId, new GetProjectSelectListHandler(this));
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ProjectSelectListItem item = adapter.getItem(arg2);
        if (item.getIs_recommend().equals("1") && !isFromAddFriend) {
            showShortToast("该客户已报备过");
            return;
        }
        if (item != null) {
            projectId = item.getHouse_id();
        }
        if (isFromAddFriend) {
			Intent data = new Intent();
			data.putExtra(IntentKey.INTENT_KEY_PROJECT_NAME, item.getName());
			data.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, item.getHouse_id());
			finishActivity(data);
		} else {
			Intent intent = new Intent(getActivity(), ActFriendSelect.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_FRIEND);
		}
    }
    
    class GetProjectSelectListHandler extends HttpResponseHandlerFragment<ProjectSelectForCustomerFragment> {

        public GetProjectSelectListHandler(ProjectSelectForCustomerFragment context) {
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
                ProjectSelectBean bean = JsonUtil.toObject(new String(content), ProjectSelectBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ProjectSelectData data = bean.getData();
                        if (data != null) {
                            List<ProjectSelectListItem> tmpList = data.getHouse_list();
                            if (!ListUtils.isEmpty(tmpList)) {
                                infos.addAll(tmpList);
                                adapter.notifyDataSetChanged();
                            } else {
                                showShortToast("暂无楼盘供选择");
                            }
                        } else {
                            showShortToast("暂无楼盘供选择");
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
                else {
                    showShortToast("暂无楼盘供选择");
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
                intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
                intent.putExtra(IntentKey.INTENT_KEY_SALEMAN_ID, saleManId);
                intent.putExtra(IntentKey.INTENT_KEY_SALEMAN_NAME, saleManName);
                finishActivity(intent);
                
            }
        }
    }

}
