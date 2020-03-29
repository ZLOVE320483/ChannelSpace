package com.zlove.frag.independent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zlove.adapter.independent.FriendSelectAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.PinnedSectionListView;
import com.zlove.bean.common.IAlphabetSection;
import com.zlove.bean.project.ProjectSelectSaleManBean;
import com.zlove.bean.project.ProjectSelectSaleManData;
import com.zlove.bean.project.ProjectSelectSaleManListItem;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ProjectHttpRequest;


public class IndependentFriendSelectFragment extends BaseFragment implements OnItemClickListener {

	public static String FROM_ADD_FRIEND = "is_from_add_friend";
	
	private boolean isFromAddFriend = false;
	
    private Dialog reqDialog;
    
    private PinnedSectionListView listView;
    private FriendSelectAdapter adapter;
    private List<ProjectSelectSaleManListItem> infos = new ArrayList<ProjectSelectSaleManListItem>();
    
    private List<ProjectSelectSaleManListItem> friendList = new ArrayList<ProjectSelectSaleManListItem>();
    private List<ProjectSelectSaleManListItem> unFriendList = new ArrayList<ProjectSelectSaleManListItem>();
    
    private String houseId = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_friend_select;
    }

    @Override
    protected void setUpView(View view) {
        
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_ID)) {
                houseId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
            }
            if (intent.hasExtra(FROM_ADD_FRIEND)) {
            	isFromAddFriend = intent.getBooleanExtra(FROM_ADD_FRIEND, false);
            }
        }
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("选择业务员");
        
        if (reqDialog == null) {
            reqDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        }

        listView = (PinnedSectionListView) view.findViewById(R.id.alphabet_list);
        listView.setOnItemClickListener(this);
        
        if (adapter == null) {
            adapter = new FriendSelectAdapter(getActivity());
        }
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(infos)) {
            ProjectHttpRequest.requestSelectProjectSaleManList(houseId, new GetProjectSaleManListHandler(this));
        }
    }
    
    private void setSaleManList(List<ProjectSelectSaleManListItem> friends, List<ProjectSelectSaleManListItem> unFriends) {
        infos.clear();
        if (!ListUtils.isEmpty(friends)) {
            ProjectSelectSaleManListItem info0 = new ProjectSelectSaleManListItem();
            info0.setSectionType(IAlphabetSection.SECTION);
            info0.setSectionText("好友");
            infos.add(info0);
            
            infos.addAll(friends);
        }
        
        if (!ListUtils.isEmpty(unFriends)) {
            ProjectSelectSaleManListItem info4 = new ProjectSelectSaleManListItem();
            info4.setSectionType(IAlphabetSection.SECTION);
            info4.setSectionText("非好友");
            infos.add(info4);
            
            infos.addAll(unFriends);
        }
        setListToArrayAdapter(infos, adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        ProjectSelectSaleManListItem item = adapter.getItem(position);
        if (item == null) {
            return;
        }
        if (item.getSectionType() == IAlphabetSection.SECTION) {
            return;
        }
        if (isFromAddFriend && friendList.contains(item)) {
			showShortToast("你们已经是好友");
			return;
		}
        Intent data = new Intent();
        data.putExtra(IntentKey.INTENT_KEY_SALEMAN_ID, item.getSalesman_id());
        data.putExtra(IntentKey.INTENT_KEY_SALEMAN_NAME, item.getRealname());
        getActivity().setResult(Activity.RESULT_OK, data);
        finishActivity();
    }
    
    class GetProjectSaleManListHandler extends HttpResponseHandlerFragment<IndependentFriendSelectFragment> {

        public GetProjectSaleManListHandler(IndependentFriendSelectFragment context) {
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
                ProjectSelectSaleManBean bean = JsonUtil.toObject(new String(content), ProjectSelectSaleManBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ProjectSelectSaleManData data = bean.getData();
                        if (data != null) {
                            List<ProjectSelectSaleManListItem> friendTmpList = data.getFriend_list();
                            List<ProjectSelectSaleManListItem> unFriendTmpList = data.getUnfriend_list();
                            if (ListUtils.isEmpty(friendTmpList) && ListUtils.isEmpty(unFriendTmpList)) {
                                showShortToast("暂无业务员供选择");
                                return;
                            }
                            if (!ListUtils.isEmpty(friendTmpList)) {
                                friendList.addAll(friendTmpList);
                            }
                            if (!ListUtils.isEmpty(unFriendTmpList)) {
                                unFriendList.addAll(unFriendTmpList);
                            }
                            setSaleManList(friendList, unFriendList);
                        }
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
            if (reqDialog != null && reqDialog.isShowing()) {
                reqDialog.dismiss();
            }
        }
        
    }
    
    public static <T extends Object> void setListToArrayAdapter(List<T> objList, ArrayAdapter<T> arrayAdapter) {
        if (arrayAdapter == null) {
            return;
        }
        arrayAdapter.clear();
        appendListToArrayAdapter(objList, arrayAdapter);
    }

    public static <T extends Object> void appendListToArrayAdapter(List<T> objList, ArrayAdapter<T> arrayAdapter) {
        if (objList != null) {
            for (T object : objList) {
                if (object != null) {
                    arrayAdapter.add(object);
                }
            }
        }
    }

}
