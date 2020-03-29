package com.zlove.frag;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zlove.act.ActAddCustomerTraceRecord;
import com.zlove.act.ActCustomerChatRecord;
import com.zlove.adapter.CustomerDetailAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DateTimePickDialogUtil;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.bean.client.ClientDetailBean;
import com.zlove.bean.client.ClientDetailData;
import com.zlove.bean.client.ClientRecommendHouseListBean;
import com.zlove.bean.client.ClientRecommendHouseListItem;
import com.zlove.bean.common.CommonBean;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZLOVE on 2016/12/27.
 */
public class MessageCustomerDetailFragment extends BaseFragment implements View.OnClickListener, CustomerDetailAdapter.CustomerFromIndependentOperateListener,
        DialogManager.CustomerEffectSelectListener, DialogManager.DecideVisitListener, DateTimePickDialogUtil.SetRevisitTimeListener,
        DialogManager.SetOverdueListener, CustomerDetailAdapter.CustomerFromSelfOperateListener, DialogManager.DecideVisitFromSelfListener {

    private ListView listView;
    private CustomerDetailAdapter adapter;
    private List<ClientRecommendHouseListItem> list = new ArrayList<>();
    private View headView;
    private TextView tvCustomerName;
    private TextView tvCustomerPhone;
    private ImageView ivCustomerIntention;
    private TextView tvCustomerIntentionDesc;
    private ImageView ivCustomerCall;
    private TextView tvProjectAreaTag;
    private TextView tvProjectArea;
    private TextView tvProjectPrice;
    private TextView tvProjectLayout;
    private TextView tvProjectType;

    private Dialog loadingDialog;

    private String clientId;
    private String houseId;
    private ClientDetailData data = null;

    @Override
    protected int getInflateLayout() {
        return R.layout.common_frag_listview_with_top;
    }

    @Override
    protected void setUpView(View view) {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_CLIENT_ID)) {
                clientId = intent.getStringExtra(IntentKey.INTENT_KEY_CLIENT_ID);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_ID)) {
                houseId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
            }
        }
        loadingDialog = DialogManager.getLoadingDialog(getActivity(), "加载中...");
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("客户详情");

        listView = (ListView) view.findViewById(R.id.id_listview);
        adapter = new CustomerDetailAdapter(list, getActivity());
        adapter.setIndependentOperateListener(this);
        adapter.setSelfOperateListener(this);
        initHeadView();
        listView.setAdapter(adapter);

        ClientHttpRequest.getClientInfoRequest(clientId, houseId, new GetClientInfoHandler(this));
        ClientHttpRequest.getRecommendHouseList(clientId, new GetRecommendHouseListHandler(this));
    }

    private void initHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.list_head_message_customer_detail, null);
        tvCustomerName = (TextView) headView.findViewById(R.id.id_customer_name);
        tvCustomerPhone = (TextView) headView.findViewById(R.id.id_customer_phone);
        ivCustomerIntention = (ImageView) headView.findViewById(R.id.customer_intention_icon);
        tvCustomerIntentionDesc = (TextView) headView.findViewById(R.id.id_customer_intention);
        ivCustomerCall = (ImageView) headView.findViewById(R.id.id_customer_call);
        tvProjectAreaTag = (TextView) headView.findViewById(R.id.tv_area);
        tvProjectArea = (TextView) headView.findViewById(R.id.id_project_area);
        tvProjectPrice = (TextView) headView.findViewById(R.id.id_project_price);
        tvProjectLayout = (TextView) headView.findViewById(R.id.id_project_house_layout);
        tvProjectType = (TextView) headView.findViewById(R.id.id_project_product);
        ivCustomerCall.setOnClickListener(this);
        listView.addHeaderView(headView);
    }

    private void setClientInfo() {
        tvCustomerName.setText(data.getClient_name());
        tvCustomerPhone.setText(data.getClient_phone());
        tvCustomerIntentionDesc.setText(data.getClient_category_desc());

        String minPrice = "0";
        String maxPrice;
        if (!TextUtils.isEmpty(data.getIntent_price_min())) {
            minPrice = data.getIntent_price_min();
        }
        if (TextUtils.isEmpty(data.getIntent_price_max()) || data.getIntent_price_max().equals("1000")) {
            maxPrice = "不限";
        } else {
            maxPrice = data.getIntent_price_max() + "万";
        }
        if (data.getClient_category_id().equals("1")) {
            ivCustomerIntention.setImageResource(R.drawable.customer_type_a);
        } else if (data.getClient_category_id().equals("2")) {
            ivCustomerIntention.setImageResource(R.drawable.customer_type_b);
        } else if (data.getClient_category_id().equals("3")) {
            ivCustomerIntention.setImageResource(R.drawable.customer_type_c);
        } else if (data.getClient_category_id().equals("4")) {
            ivCustomerIntention.setImageResource(R.drawable.customer_type_d);
        }

        tvProjectPrice.setText(minPrice + "-" + maxPrice);
        tvProjectLayout.setText(data.getHouse_types_desc());
        tvProjectType.setText(data.getProperty_types_desc());
        if (!TextUtils.isEmpty(data.getIntent_location_desc())) {
            tvProjectArea.setText(data.getIntent_location_desc());
        } else {
            tvProjectAreaTag.setText("来源:");
            if (data.getFrom_type().equals("0")) {
                tvProjectArea.setText("来电");
            } else {
                tvProjectArea.setText("来访");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ivCustomerCall) {
            takeCall(data.getClient_phone());
        }
    }

    private void takeCall(String phoneNum) {
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);
    }

    class GetRecommendHouseListHandler extends HttpResponseHandlerFragment<MessageCustomerDetailFragment> {

        public GetRecommendHouseListHandler(MessageCustomerDetailFragment context) {
            super(context);
        }

        @Override
        public void onStart() {
            super.onStart();
            if (loadingDialog != null && !loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                ClientRecommendHouseListBean bean = JsonUtil.toObject(new String(content), ClientRecommendHouseListBean.class);
                if (bean == null) {
                    return;
                }
                if (bean.getStatus() == 200) {
                    if (ListUtils.isEmpty(bean.getData().getRecommend_house_list())) {
                        return;
                    }
                    list.clear();
                    list.addAll(bean.getData().getRecommend_house_list());
                    adapter.notifyDataSetChanged();
                } else {
                    showShortToast(bean.getMessage());
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
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }

    class GetClientInfoHandler extends HttpResponseHandlerFragment<MessageCustomerDetailFragment> {

        public GetClientInfoHandler(MessageCustomerDetailFragment context) {
            super(context);
        }

        @Override
        public void onStart() {
            super.onStart();
            if (loadingDialog != null && !loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (!isAdded()) {
                return;
            }
            if (content != null) {
                ClientDetailBean bean = JsonUtil.toObject(new String(content), ClientDetailBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        data = bean.getData();
                        if (data != null) {
                            setClientInfo();
                        } else {
                            showShortToast("获取客户详情失败");
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                } else {
                    showShortToast("获取客户详情失败");
                }
            } else {
                showShortToast("获取客户详情失败");
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }

    class DecideEffectHandler extends HttpResponseHandlerFragment<MessageCustomerDetailFragment> {

        public DecideEffectHandler(MessageCustomerDetailFragment context) {
            super(context);
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        showShortToast("设置成功");
                        ClientHttpRequest.getRecommendHouseList(clientId, new GetRecommendHouseListHandler(MessageCustomerDetailFragment.this));
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

    class DecideVisitHandler extends HttpResponseHandlerFragment<MessageCustomerDetailFragment> {


        public DecideVisitHandler(MessageCustomerDetailFragment context) {
            super(context);
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ClientHttpRequest.getRecommendHouseList(clientId, new GetRecommendHouseListHandler(MessageCustomerDetailFragment.this));
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

    class SetRevisitTimeHandler extends HttpResponseHandlerFragment<MessageCustomerDetailFragment> {

        public SetRevisitTimeHandler(MessageCustomerDetailFragment context) {
            super(context);
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        showShortToast("设置成功");
                        ClientHttpRequest.getRecommendHouseList(clientId, new GetRecommendHouseListHandler(MessageCustomerDetailFragment.this));
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

    class SetOverDueHandler extends HttpResponseHandlerFragment<MessageCustomerDetailFragment> {


        public SetOverDueHandler(MessageCustomerDetailFragment context) {
            super(context);
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ClientHttpRequest.getRecommendHouseList(clientId, new GetRecommendHouseListHandler(MessageCustomerDetailFragment.this));
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

    class SetReVisitHandler extends HttpResponseHandlerFragment<MessageCustomerDetailFragment> {


        public SetReVisitHandler(MessageCustomerDetailFragment context) {
            super(context);
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        showShortToast("设置成功");
                        ClientHttpRequest.getRecommendHouseList(clientId, new GetRecommendHouseListHandler(MessageCustomerDetailFragment.this));
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
    public void customerEffectJudge(String houseId) {
        this.houseId = houseId;
        DialogManager.showCustomerEffectDialog(getActivity(), this);
    }

    @Override
    public void selectCustomerEffect(String effectOrInvalid) {
        ClientHttpRequest.decideEffectRequest(clientId, houseId, effectOrInvalid, new DecideEffectHandler(this));
    }

    @Override
    public void decideVisit(String houseId) {
        this.houseId = houseId;
        DialogManager.showDecideVisitDialog(getActivity(), this);
    }

    @Override
    public void decide(int type) {
        if (type == 1) {
            ClientHttpRequest.decideVisitRequest(clientId, houseId, "1", new DecideVisitHandler(this));
        } else if (type == 0) {
            ClientHttpRequest.decideVisitRequest(clientId, houseId, "0", new DecideVisitHandler(this));
        }
    }

    @Override
    public void selectDateTime(String houseId) {
        this.houseId = houseId;
        DateTimePickDialogUtil util = new DateTimePickDialogUtil(getActivity(), this);
        util.showDateSelectDialog("设置回访日期");
    }

    @Override
    public void setRevisitTime(String time) {
        ClientHttpRequest.setRevisitTimeRequest(clientId, houseId, time, new SetRevisitTimeHandler(this));
    }

    @Override
    public void setOverDue(String houseId) {
        this.houseId = houseId;
        DialogManager.showSetOverDueDialog(getActivity(), this);
    }

    @Override
    public void setOverdue(String ovderdue) {
        ClientHttpRequest.setOverdueRequest(clientId, ovderdue, houseId, new SetOverDueHandler(this));
    }

    @Override
    public void addRecord(String brokerId, String houseId) {
        this.houseId = houseId;
        Intent intent = new Intent(getActivity(), ActAddCustomerTraceRecord.class);
        intent.putExtra(IntentKey.INTENT_KEY_INDEPENDENT_ID, brokerId);
        intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
        intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, houseId);
        startActivityForResult(intent, IntentKey.REQUEST_CODE_ADD_CUSTOMER_RECORD);
    }

    @Override
    public void visitAgain(String houseId) {
        this.houseId = houseId;
        ClientHttpRequest.setRevisitRequest(clientId, houseId, new SetReVisitHandler(this));
    }

    @Override
    public void selfDecideVisit(String houseId) {
        this.houseId = houseId;
        DialogManager.showDecideVisitFromeSelfDialog(getActivity(), this);
    }

    @Override
    public void decide() {
        ClientHttpRequest.setVisitedRequest(clientId, houseId, new SetReVisitHandler(this));
    }

    @Override
    public void selfSelectDateTime(String houseId) {
        this.houseId = houseId;
        DateTimePickDialogUtil util = new DateTimePickDialogUtil(getActivity(), this);
        util.showDateSelectDialog("设置回访日期");
    }

    @Override
    public void selfAddRecord(String brokerId, String houseId) {
        this.houseId = houseId;
        Intent intent = new Intent(getActivity(), ActAddCustomerTraceRecord.class);
        intent.putExtra(IntentKey.INTENT_KEY_INDEPENDENT_ID, brokerId);
        intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
        intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, houseId);
        startActivityForResult(intent, IntentKey.REQUEST_CODE_ADD_CUSTOMER_RECORD);
    }

    @Override
    public void showMoreRecord(String houseId) {
        this.houseId = houseId;
        Intent intent = new Intent(getActivity(), ActCustomerChatRecord.class);
        intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
        intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, houseId);
        startActivity(intent);
    }

    @Override
    public void selfShowMoreRecord(String houseId) {
        this.houseId = houseId;
        Intent intent = new Intent(getActivity(), ActCustomerChatRecord.class);
        intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, clientId);
        intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, houseId);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IntentKey.REQUEST_CODE_ADD_CUSTOMER_RECORD) {
                ClientHttpRequest.getRecommendHouseList(clientId, new GetRecommendHouseListHandler(MessageCustomerDetailFragment.this));
            }
        }
    }
}
