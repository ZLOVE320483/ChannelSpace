package com.zlove.frag;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.zlove.act.ActCommonWebView;
import com.zlove.adapter.ProjectDynamicAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.project.ProjectNewsBean;
import com.zlove.bean.project.ProjectNewsData;
import com.zlove.bean.project.ProjectNewsListItem;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ProjectHttpRequest;


public class ProjectDynamicFragment extends BaseFragment implements OnItemClickListener, PullableViewListener {

    public static final String PROJETC_DETAIL_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "newsWeb/index?id=";
    
    private PullListView listView;
    private ProjectDynamicAdapter adapter;
    private List<ProjectNewsListItem> infos = new ArrayList<ProjectNewsListItem>();

    private TextView tvNoRule;
    
    private String projectId = "";
    private int pageIndex = 0;

    @Override
    protected int getInflateLayout() {
        return R.layout.common_act_listview_with_top_bar;
    }

    @Override
    protected void setUpView(View view) {
        
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("楼盘动态");
        
        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        
        tvNoRule = (TextView) view.findViewById(R.id.tv_no_data);
        tvNoRule.setVisibility(View.GONE);
        
        if (adapter == null) {
            adapter = new ProjectDynamicAdapter(infos, getActivity());
        }
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        projectId = ChannelCookie.getInstance().getCurrentHouseId();
        if (ListUtils.isEmpty(infos)) {
            ProjectHttpRequest.requestProjectNewsList(projectId, "0", "10", new GetProjectNewsListHandler(this, LOAD_ACTION.ONREFRESH));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProjectNewsListItem item = adapter.getItem(position - 1);
        if (item == null) {
            return;
        }
        String url = PROJETC_DETAIL_URL + item.getHouse_news_id();

        Intent intent = new Intent(getActivity(), ActCommonWebView.class);
        intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, url);
        intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "楼盘动态");
        startActivity(intent);
    }
    
    class GetProjectNewsListHandler extends HttpResponseHandlerFragment<ProjectDynamicFragment> {

        private LOAD_ACTION action;
        
        public GetProjectNewsListHandler(ProjectDynamicFragment context, LOAD_ACTION action) {
            super(context);
            this.action = action;
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
                ProjectNewsBean bean = JsonUtil.toObject(new String(content), ProjectNewsBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ProjectNewsData data = bean.getData();
                        if (data != null) {
                            CommonPageInfo info = data.getPage_info();
                            if (info != null) {
                                pageIndex = info.getPage_index();
                            }
                            if (action == LOAD_ACTION.ONREFRESH) {
                                infos.clear();
                            }
                            List<ProjectNewsListItem> tmpList = data.getHouseNewsList();
                            if (!ListUtils.isEmpty(tmpList)) {
                                if (tmpList.size() < 10) {
                                    listView.setPullLoadEnable(false);
                                } else {
                                    listView.setPullLoadEnable(true);
                                }
                                infos.addAll(tmpList);
                                adapter.notifyDataSetChanged();
                                tvNoRule.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);
                            } else {
                                tvNoRule.setVisibility(View.VISIBLE);
                                tvNoRule.setText("该楼盘暂未发布过动态");
                            }
                        } else {
                            tvNoRule.setVisibility(View.VISIBLE);
                            tvNoRule.setText("该楼盘暂未发布过动态");
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
            listView.stopRefresh();
            listView.stopLoadMore();
        }
    }

    @Override
    public void onRefresh() {
        ProjectHttpRequest.requestProjectNewsList(projectId, "0", "10", new GetProjectNewsListHandler(this, LOAD_ACTION.ONREFRESH));
    }

    @Override
    public void onLoadMore() {
        ProjectHttpRequest.requestProjectNewsList(projectId, String.valueOf(pageIndex), "10", new GetProjectNewsListHandler(this, LOAD_ACTION.LOADERMORE));
    }

}
