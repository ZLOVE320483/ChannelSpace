
package com.zlove.frag;

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

import com.zlove.act.ActCustomerFromIndependentDetail;
import com.zlove.act.ActCustomerFromSelfDetail;
import com.zlove.adapter.CustomerAdapter;
import com.zlove.adapter.CustomerFromSelfAdapter;
import com.zlove.adapter.SingleDataListAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.util.UIUtil;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.client.ClientListBean;
import com.zlove.bean.client.ClientListData;
import com.zlove.bean.client.ClientListItem;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchFragment extends BaseFragment implements OnItemClickListener, PullableViewListener {

    private Dialog reqDialog;

    private EditText etSearch = null;
    
    private PullListView listView = null;
    private SingleDataListAdapter<ClientListItem> adapter;
    private List<ClientListItem> customerInfos = new ArrayList<ClientListItem>();

    private int pageIndex = 0;
    private String keyword = "";

    private boolean isEdit = false;
    private String type = "1";

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_customer_search;
    }

    @Override
    protected void setUpView(View view) {
        
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_CUSTOMER_TYPE)) {
                type = intent.getStringExtra(IntentKey.INTENT_KEY_CUSTOMER_TYPE);
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
        etSearch.setHint("请输入客户姓名或电话");
        listView = (PullListView) view.findViewById(R.id.id_listview);

        if (reqDialog == null) {
            reqDialog = DialogManager.getLoadingDialog(getActivity(), "正在搜索...");
        }

        if (type.equals("1")) {
            adapter = new CustomerAdapter(customerInfos, getActivity());
			((CustomerAdapter) adapter).setType(1);
		} else {
            adapter = new CustomerFromSelfAdapter(customerInfos, getActivity());
			((CustomerFromSelfAdapter) adapter).setType(2);
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

    private void search(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            showShortToast("请输入关键字");
            return;
        }
        this.keyword = keyword;
        ClientHttpRequest.getClientListRequest(keyword, "", "", "", "0", "10", "", ChannelCookie.getInstance().getCurrentHouseId(), type, "", new GetClientListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    class GetClientListHandler extends HttpResponseHandlerFragment<CustomerSearchFragment> {

        private LOAD_ACTION action;

        public GetClientListHandler(CustomerSearchFragment context, LOAD_ACTION action) {
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
                ClientListBean bean = JsonUtil.toObject(new String(content), ClientListBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ClientListData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo pageInfo = data.getPage_info();
                            pageIndex = pageInfo.getPage_index();
                            if (action == LOAD_ACTION.ONREFRESH) {
                                customerInfos.clear();
                            }
                            List<ClientListItem> tmpList = data.getClient_list();
                            if (tmpList.size() < 10) {
                                listView.setPullLoadEnable(false);
                            } else {
                                listView.setPullLoadEnable(true);
                            }
                            customerInfos.addAll(tmpList);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
                if (ListUtils.isEmpty(customerInfos)) {
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
            listView.stopLoadMore();
            listView.stopRefresh();
        }

    }

    @Override
    public void onRefresh() {
        ClientHttpRequest.getClientListRequest(keyword, "", "", "", "0", "10", "", ChannelCookie.getInstance().getCurrentHouseId(), 
        		type, "", new GetClientListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        ClientHttpRequest.getClientListRequest(keyword, "", "", "", String.valueOf(pageIndex), "10", "", ChannelCookie.getInstance().getCurrentHouseId(), 
                type, "", new GetClientListHandler(this, LOAD_ACTION.LOADERMORE));
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        if (ListUtils.isEmpty(customerInfos)) {
            return;
        }
         ClientListItem item = adapter.getItem(position - 1);
         if (item == null) {
             return;
         }
         if (type.equals("1")) {
             Intent intent = new Intent(getActivity(), ActCustomerFromIndependentDetail.class);
             intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, item.getClient_id());
             startActivityForResult(intent, IntentKey.REQUEST_CODE_CLIENT_EDIT);
        } else {
            Intent intent = new Intent(getActivity(), ActCustomerFromSelfDetail.class);
            intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, item.getClient_id());
            startActivityForResult(intent, IntentKey.REQUEST_CODE_CLIENT_EDIT);
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentKey.REQUEST_CODE_CLIENT_EDIT && data != null) {
            isEdit = data.getBooleanExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, false);
            if (isEdit) {
                onRefresh();
            }
        }
    }

}
