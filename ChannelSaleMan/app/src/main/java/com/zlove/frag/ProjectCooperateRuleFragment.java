package com.zlove.frag;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.zlove.act.ActCommonWebView;
import com.zlove.adapter.ProjectCooperateRuleAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.bean.project.ProjectItemRuleList;
import com.zlove.bean.project.ProjectRuleBean;
import com.zlove.bean.project.ProjectRuleData;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ProjectHttpRequest;


public class ProjectCooperateRuleFragment extends BaseFragment implements OnItemClickListener {
    public static final String RULE_DETAIL_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url) + "ruleWeb/index?id=";
    
    private TextView tvNoRule;
    
    private ListView listView;
    private List<ProjectItemRuleList> infos = new ArrayList<ProjectItemRuleList>();
    private ProjectCooperateRuleAdapter adapter;

    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_project_cooperate_rule;
    }

    @Override
    protected void setUpView(View view) {
        
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("合作规则");
        
        listView = (ListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        listView.setVisibility(View.GONE);
        tvNoRule = (TextView) view.findViewById(R.id.tv_no_data);
        tvNoRule.setVisibility(View.GONE);
        
        adapter = new ProjectCooperateRuleAdapter(infos, getActivity());
        listView.setAdapter(adapter);
        
        ProjectHttpRequest.requestProjectRuleList(ChannelCookie.getInstance().getCurrentHouseId(), "0", "10", new GetProjectRuleListHandler(this));
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (ListUtils.isEmpty(infos)) {
            return;
        }
        ProjectItemRuleList item = infos.get(arg2);
        if (item == null) {
            return;
        }
        
        String url = RULE_DETAIL_URL + item.getHouse_rule_id();
        Intent intent = new Intent(getActivity(), ActCommonWebView.class);
        intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_URL, url);
        intent.putExtra(IntentKey.INTENT_KEY_COMMON_WEBVIEW_TITLE, "规则详情");
        startActivity(intent);
    }
    
    class GetProjectRuleListHandler extends HttpResponseHandlerFragment<ProjectCooperateRuleFragment> {

        public GetProjectRuleListHandler(ProjectCooperateRuleFragment context) {
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
                ProjectRuleBean bean = JsonUtil.toObject(new String(content), ProjectRuleBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ProjectRuleData data = bean.getData();
                        if (data != null) {
                            tvNoRule.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            List<ProjectItemRuleList> tmpList = data.getHouse_rule_list();
                            if (ListUtils.isEmpty(tmpList)) {
                                tvNoRule.setVisibility(View.VISIBLE);
                                tvNoRule.setText("该楼盘暂未发布合作规则");
                                listView.setVisibility(View.GONE);
                            }
                            if (!ListUtils.isEmpty(tmpList)) {
                                listView.setVisibility(View.VISIBLE);
                            }
                            if (tmpList.size() >= 2) {
                                listView.setVisibility(View.VISIBLE);
                               for (int i = 1; i < tmpList.size(); i++) {
                                   infos.add(tmpList.get(i));
                                   adapter.notifyDataSetChanged();
                               }
                            }
                        } else {
                            listView.setVisibility(View.GONE);
                            tvNoRule.setVisibility(View.VISIBLE);
                            tvNoRule.setText("该楼盘暂未发布合作规则");
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
        }
        
    }

}