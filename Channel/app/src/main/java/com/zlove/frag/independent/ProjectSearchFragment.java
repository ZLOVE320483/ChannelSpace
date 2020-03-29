package com.zlove.frag.independent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.zlove.act.independent.ActIndependentCustomerAdd;
import com.zlove.act.independent.ActIndependentProjectDetail;
import com.zlove.act.independent.ActProjectCustomerAdd;
import com.zlove.adapter.independent.ProjectAdapter;
import com.zlove.adapter.independent.ProjectAdapter.ProjectAddCustomerDelegate;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.DialogManager.ProjectAddCustomerListener;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.UIUtil;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.project.ProjectItemBean;
import com.zlove.bean.project.ProjectItemData;
import com.zlove.bean.project.ProjectItemHouseList;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ProjectHttpRequest;

public class ProjectSearchFragment extends BaseFragment implements PullableViewListener, OnItemClickListener, ProjectAddCustomerDelegate, ProjectAddCustomerListener {

    private Dialog reqDialog;
    
    private EditText etSearch = null;
    private PullListView listView = null;
    private ProjectAdapter adapter;
    private List<ProjectItemHouseList> projectInfos = new ArrayList<ProjectItemHouseList>();

    private String projectName = "";
	private String projectId = "";
    private int pageIndex = 0;
    
    private String cityId = "";
    private String name;

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_project_search;
    }

    @Override
    protected void setUpView(View view) {
        
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_CURRENT_CITY_ID)) {
                cityId = intent.getStringExtra(IntentKey.INTENT_KEY_CURRENT_CITY_ID);
            }
        }
        
        view.findViewById(R.id.id_cancel).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                UIUtil.hideKeyboard(getActivity());
                finishActivity();
            }
        });

        etSearch = (EditText) view.findViewById(R.id.id_search);
        etSearch.setHint("请输入楼盘名称");
        listView = (PullListView) view.findViewById(R.id.id_listview);
        
        if (reqDialog == null) {
            reqDialog = DialogManager.getLoadingDialog(getActivity(), "正在搜索...");
        }
        
        if (adapter == null) {
            adapter = new ProjectAdapter(getActivity(), projectInfos, this);
        }
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    UIUtil.hideKeyboard(getActivity());
                    search(etSearch.getText().toString().trim());
                    return true;
                } else {
                    return false;
                }

            }
        });

    }
    
    private void search(String name) {
        if (TextUtils.isEmpty(name)) {
            showShortToast("请输入楼盘名称");
            return;
        }
        this.name = name;
        ProjectHttpRequest.requestProjectList(name, cityId, "", "", "", "", "0", "10", "", new GetProjectListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onRefresh() {
        ProjectHttpRequest.requestProjectList(name, cityId, "", "", "", "", "0", "10", "", new GetProjectListHandler(this, LOAD_ACTION.ONREFRESH));
    
    }

    @Override
    public void onLoadMore() {
        ProjectHttpRequest.requestProjectList(name, cityId, "", "", "", "", String.valueOf(pageIndex), "10", "", new GetProjectListHandler(this, LOAD_ACTION.LOADERMORE));
    
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Intent intent = new Intent(getActivity(), ActIndependentProjectDetail.class);
        ProjectItemHouseList data = projectInfos.get(arg2 - 1);
        if (data != null) {
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, data.getHouse_id());
        }
        startActivity(intent);
    }
    
    class GetProjectListHandler extends HttpResponseHandlerFragment<ProjectSearchFragment> {

        private LOAD_ACTION action;

        public GetProjectListHandler(ProjectSearchFragment context, LOAD_ACTION action) {
            super(context);
            this.action = action;
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
                ProjectItemBean bean = JsonUtil.toObject(new String(content),
                        ProjectItemBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ProjectItemData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo info = data.getPage_info();
                            pageIndex = info.getPage_index();
                            if (action == LOAD_ACTION.ONREFRESH) {
                                projectInfos.clear();
                            }
                            List<ProjectItemHouseList> tmpList = data
                                    .getHouse_list();
                            if (tmpList.size() < 10) {
                                listView.setPullLoadEnable(false);
                            } else {
                                listView.setPullLoadEnable(true);
                            }
                            projectInfos.addAll(tmpList);
                            adapter.notifyDataSetChanged();
                        }

                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
                if (reqDialog != null && reqDialog.isShowing()) {
                    reqDialog.dismiss();
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content,
                Throwable error) {
            super.onFailure(statusCode, headers, content, error);
            if (reqDialog != null && reqDialog.isShowing()) {
                reqDialog.dismiss();
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (reqDialog != null && reqDialog.isShowing()) {
                reqDialog.dismiss();
            }
            listView.stopLoadMore();
            listView.stopRefresh();
        }

    }


    @Override
    public void selectAddCustomer(int pos) {
        if (pos == 0) {
            Intent intent = new Intent(getActivity(), ActIndependentCustomerAdd.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_NAME, projectName);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
            startActivity(intent);
        } else if (pos == 1) {
            Intent intent = new Intent(getActivity(), ActProjectCustomerAdd.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_CUSTOMER);
        }
    }

	@Override
	public void addCustomerAction(String projectName, String projectId) {
		this.projectName = projectName;
		this.projectId = projectId;
		DialogManager.showProjectAddCustomerDialog(getActivity(), this);
	}

}
