package com.zlove.frag;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.util.StringUtil;
import com.zlove.base.util.UIUtil;
import com.zlove.base.widget.DoubleDirectSeekBar;
import com.zlove.base.widget.DoubleDirectSeekBar.OnSeekBarChangeListener;
import com.zlove.bean.client.ClientDetailData;
import com.zlove.bean.common.CommonBean;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;


public class CustomerEditFragment extends BaseFragment implements OnClickListener {
    
    private ClientDetailData data;
    private String clientId = "";
    
    private EditText etCustomerName;
    private TextView tvCustomerPhone;
    
    private View customerIntentionAView;
    private View customerIntentionBView;
    private View customerIntentionCView;
    private View customerIntentionDView;
    private RadioButton rbIntentionA;
    private RadioButton rbIntentionB;
    private RadioButton rbIntentionC;
    private RadioButton rbIntentionD;
    
    private RadioButton rbFromPhone;
    private RadioButton rbFromVisit;

    private CheckBox rbLayoutOne;
    private CheckBox rbLayoutTwo;
    private CheckBox rbLayoutThree;
    private CheckBox rbLayoutFour;
    private CheckBox rbLayoutFive;
    private CheckBox rbLayoutFiveMore;

    private CheckBox rbTypeHigh;
    private CheckBox rbTypeMulty;
    private CheckBox rbTypeStack;
    private CheckBox rbTypeHouse;
    private CheckBox rbTypeApart;
    private CheckBox rbTypeOffice;
    private CheckBox rbTypeShop;
    
    private DoubleDirectSeekBar projectPriceBar; 
    
    private Button btnSave = null;
    
    private String customerName = "";
    private String categoryId = "1";
    private String fromType = "0";
    
    private List<String> layoutIds = new ArrayList<String>();
    private List<String> typeIds = new ArrayList<String>();
    private String initMin = "0";
    private String initMax = "1000";
    private String intentArea = "130";
    
    private String houseTypes = "";
    private String propertyTypes = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_customer_edit;
    }

    @Override
    protected void setUpView(View view) {
        
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_CLIENT_DETAIL_ITEM)) {
                data = (ClientDetailData) intent.getExtras().get(IntentKey.INTENT_KEY_CLIENT_DETAIL_ITEM);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_CLIENT_ID)) {
                clientId = intent.getStringExtra(IntentKey.INTENT_KEY_CLIENT_ID);
            }
        }
        
        view.findViewById(R.id.id_back).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                UIUtil.hideKeyboard(getActivity());
                finishActivity();
            }
        });
        ((TextView) view.findViewById(R.id.id_title)).setText("编辑客户");

        etCustomerName = (EditText) view.findViewById(R.id.id_customer_name);
        tvCustomerPhone = (TextView) view.findViewById(R.id.id_customer_phone);   
        customerIntentionAView = view.findViewById(R.id.id_customer_intention_a);
        customerIntentionBView = view.findViewById(R.id.id_customer_intention_b);
        customerIntentionCView = view.findViewById(R.id.id_customer_intention_c);
        customerIntentionDView = view.findViewById(R.id.id_customer_intention_d);
        
        rbIntentionA = (RadioButton)view.findViewById(R.id.rb_intention_a);
        rbIntentionB = (RadioButton)view.findViewById(R.id.rb_intention_b);
        rbIntentionC = (RadioButton)view.findViewById(R.id.rb_intention_c);
        rbIntentionD = (RadioButton)view.findViewById(R.id.rb_intention_d);
        
        rbFromPhone = (RadioButton) view.findViewById(R.id.from_phone);
        rbFromVisit = (RadioButton) view.findViewById(R.id.from_visit);

        rbLayoutOne = (CheckBox) view.findViewById(R.id.id_project_layout_one);
        rbLayoutTwo = (CheckBox) view.findViewById(R.id.id_project_layout_two);
        rbLayoutThree = (CheckBox) view.findViewById(R.id.id_project_layout_three);
        rbLayoutFour = (CheckBox) view.findViewById(R.id.id_project_layout_four);
        rbLayoutFive = (CheckBox) view.findViewById(R.id.id_project_layout_five);
        rbLayoutFiveMore = (CheckBox) view.findViewById(R.id.id_project_layout_five_more);
        
        rbTypeHigh = (CheckBox) view.findViewById(R.id.id_project_type_high);
        rbTypeMulty = (CheckBox) view.findViewById(R.id.id_project_type_multy);
        rbTypeStack = (CheckBox) view.findViewById(R.id.id_project_type_stack);
        rbTypeHouse = (CheckBox) view.findViewById(R.id.id_project_type_house);
        rbTypeApart = (CheckBox) view.findViewById(R.id.id_project_type_apart);
        rbTypeOffice = (CheckBox) view.findViewById(R.id.id_project_type_office);
        rbTypeShop = (CheckBox) view.findViewById(R.id.id_project_type_shop);
        
        rbIntentionA.setClickable(false);
        rbIntentionB.setClickable(false);
        rbIntentionC.setClickable(false);
        rbIntentionD.setClickable(false);

        rbFromPhone.setOnClickListener(this);
        rbFromVisit.setOnClickListener(this);
        
        rbLayoutOne.setOnClickListener(this);
        rbLayoutTwo.setOnClickListener(this);
        rbLayoutThree.setOnClickListener(this);
        rbLayoutFour.setOnClickListener(this);
        rbLayoutFive.setOnClickListener(this);
        rbLayoutFiveMore.setOnClickListener(this);
        
        rbTypeHigh.setOnClickListener(this);
        rbTypeMulty.setOnClickListener(this);
        rbTypeStack.setOnClickListener(this);
        rbTypeHouse.setOnClickListener(this);
        rbTypeApart.setOnClickListener(this);
        rbTypeOffice.setOnClickListener(this);
        rbTypeShop.setOnClickListener(this);
        
        btnSave = (Button) view.findViewById(R.id.id_confirm);
        btnSave.setOnClickListener(this);
        
        customerIntentionAView.setOnClickListener(this);
        customerIntentionBView.setOnClickListener(this);
        customerIntentionCView.setOnClickListener(this);
        customerIntentionDView.setOnClickListener(this);
        
        projectPriceBar = (DoubleDirectSeekBar) view.findViewById(R.id.project_price_bar);
        
        if (data != null) {
            customerName = data.getClient_name();
            fromType = data.getFrom_type();
            categoryId = data.getClient_category_id();
            propertyTypes = data.getProperty_types();
            houseTypes = data.getHouse_types();
            initMin = data.getIntent_price_min();
            initMax = data.getIntent_price_max();
            
            setLayoutIds();
            setTypeIds();
            
            etCustomerName.setText(customerName);
            tvCustomerPhone.setText(data.getClient_phone());
            setClientFromType(data.getFrom_type());
            setClientIntentionState(categoryId);
            setClientLayoutState(data.getHouse_types());
            setClientTypeState(data.getProperty_types());
            projectPriceBar.setInitLow(Integer.valueOf(initMin));
            projectPriceBar.setInitHigh(Integer.valueOf(initMax));
        }
        
        projectPriceBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            
            @Override
            public void onProgressChanged(DoubleDirectSeekBar seekBar, int progressLow, int progressHigh) {
                initMin = String.valueOf(progressLow);
                initMax = String.valueOf(progressHigh);
                projectPriceBar.setInitLow(Integer.valueOf(initMin));
                projectPriceBar.setInitHigh(Integer.valueOf(initMax));
            }
            
            @Override
            public void onProgressBefore() {
            }
            
            @Override
            public void onProgressAfter() {
            }
        });
        
    }
    
    private void setLayoutIds() {
        String[] locIds = houseTypes.split(",");
        for (int i = 0; i < locIds.length; i++) {
            layoutIds.add(locIds[i]);
        }
    }
    
    private void setTypeIds() {
        String[] locIds = propertyTypes.split(",");
        for (int i = 0; i < locIds.length; i++) {
            typeIds.add(locIds[i]);
        }
    }
    
    private void setClientFromType(String fromType) {
        rbFromPhone.setChecked(fromType.equals("0"));
        rbFromVisit.setChecked(fromType.equals("1"));
    }
    
    private void setClientIntentionState(String clientIntention) {
        rbIntentionA.setChecked(clientIntention.equals("1"));
        rbIntentionB.setChecked(clientIntention.equals("2"));
        rbIntentionC.setChecked(clientIntention.equals("3"));
        rbIntentionD.setChecked(clientIntention.equals("4"));
    }
    
    private void setClientLayoutState(String sourceState) {
        rbLayoutOne.setChecked(StringUtil.isContains(sourceState, "1"));
        rbLayoutTwo.setChecked(StringUtil.isContains(sourceState, "2"));
        rbLayoutThree.setChecked(StringUtil.isContains(sourceState, "3"));
        rbLayoutFour.setChecked(StringUtil.isContains(sourceState, "4"));
        rbLayoutFive.setChecked(StringUtil.isContains(sourceState, "5"));
        rbLayoutFiveMore.setChecked(StringUtil.isContains(sourceState, "6"));
    }
    
    private void setClientTypeState(String sourceState) {
        rbTypeHigh.setChecked(StringUtil.isContains(sourceState, "1"));
        rbTypeMulty.setChecked(StringUtil.isContains(sourceState, "2"));
        rbTypeStack.setChecked(StringUtil.isContains(sourceState, "3"));
        rbTypeHouse.setChecked(StringUtil.isContains(sourceState, "4"));
        rbTypeApart.setChecked(StringUtil.isContains(sourceState, "5"));
        rbTypeOffice.setChecked(StringUtil.isContains(sourceState, "6"));
        rbTypeShop.setChecked(StringUtil.isContains(sourceState, "7"));
    }

    @Override
    public void onClick(View view) {
        if (view == customerIntentionAView) {
            categoryId = "1";
            rbIntentionA.setChecked(true);
            rbIntentionB.setChecked(false);
            rbIntentionC.setChecked(false);
            rbIntentionD.setChecked(false);
        } else if (view == customerIntentionBView) {
            categoryId = "2";
            rbIntentionA.setChecked(false);
            rbIntentionB.setChecked(true);
            rbIntentionC.setChecked(false);
            rbIntentionD.setChecked(false);
        } else if (view == customerIntentionCView) {
            categoryId = "3";
            rbIntentionA.setChecked(false);
            rbIntentionB.setChecked(false);
            rbIntentionC.setChecked(true);
            rbIntentionD.setChecked(false);
        } else if (view == customerIntentionDView) {
            categoryId = "4";
            rbIntentionA.setChecked(false);
            rbIntentionB.setChecked(false);
            rbIntentionC.setChecked(false);
            rbIntentionD.setChecked(true);
        }else if (view == rbLayoutOne) {
            if (layoutIds.size() >= 3 && !layoutIds.contains("1")) {
                showShortToast("最多可选择3种户型");
                rbLayoutOne.setChecked(false);
                return;
            }
            changeCheckBoxState(rbLayoutOne);
            if (rbLayoutOne.isChecked()) {
                layoutIds.add("1");
            } else {
                layoutIds.remove("1");
            }
        } else if (view == rbLayoutTwo) {
            if (layoutIds.size() >= 3 && !layoutIds.contains("2")) {
                showShortToast("最多可选择3种户型");
                rbLayoutTwo.setChecked(false);
                return;
            }
            changeCheckBoxState(rbLayoutTwo);
            if (rbLayoutTwo.isChecked()) {
                layoutIds.add("2");
            } else {
                layoutIds.remove("2");
            }
            
        } else if (view == rbLayoutThree) {
            if (layoutIds.size() >= 3 && !layoutIds.contains("3")) {
                showShortToast("最多可选择3种户型");
                rbLayoutThree.setChecked(false);
                return;
            }
            changeCheckBoxState(rbLayoutThree);
            if (rbLayoutThree.isChecked()) {
                layoutIds.add("3");
            } else {
                layoutIds.remove("3");
            }
        } else if (view == rbLayoutFour) {
            if (layoutIds.size() >= 3 && !layoutIds.contains("4")) {
                showShortToast("最多可选择3种户型");
                rbLayoutFour.setChecked(false);
                return;
            }
            changeCheckBoxState(rbLayoutFour);
            if (rbLayoutFour.isChecked()) {
                layoutIds.add("4");
            } else {
                layoutIds.remove("4");
            }
        } else if (view == rbLayoutFive) {
            if (layoutIds.size() >= 3 && !layoutIds.contains("5")) {
                showShortToast("最多可选择3种户型");
                rbLayoutFive.setChecked(false);
                return;
            }
            changeCheckBoxState(rbLayoutFive);
            if (rbLayoutFive.isChecked()) {
                layoutIds.add("5");
            } else {
                layoutIds.remove("5");
            }
        } else if (view == rbLayoutFiveMore) {
            if (layoutIds.size() >= 3 && !layoutIds.contains("6")) {
                showShortToast("最多可选择3种户型");
                rbLayoutFiveMore.setChecked(false);
                return;
            }
            changeCheckBoxState(rbLayoutFiveMore);
            if (rbLayoutFiveMore.isChecked()) {
                layoutIds.add("6");
            } else {
                layoutIds.remove("6");
            }
        } else if (view == rbTypeHigh) {
            if (typeIds.size() >= 3 && !typeIds.contains("1")) {
                showShortToast("最多可选择3种类型");
                rbTypeHigh.setChecked(false);
                return;
            }
            changeCheckBoxState(rbTypeHigh);
            if (rbTypeHigh.isChecked()) {
                typeIds.add("1");
            } else {
                typeIds.remove("1");
            }
        } else if (view == rbTypeMulty) {
            if (typeIds.size() >= 3 && !typeIds.contains("2")) {
                showShortToast("最多可选择3种类型");
                rbTypeMulty.setChecked(false);
                return;
            }
            changeCheckBoxState(rbTypeMulty);
            if (rbTypeMulty.isChecked()) {
                typeIds.add("2");
            } else {
                typeIds.remove("2");
            }            
        } else if (view == rbTypeStack) {
            if (typeIds.size() >= 3 && !typeIds.contains("3")) {
                showShortToast("最多可选择3种类型");
                rbTypeStack.setChecked(false);
                return;
            }
            changeCheckBoxState(rbTypeStack);
            if (rbTypeStack.isChecked()) {
                typeIds.add("3");
            } else {
                typeIds.remove("3");
            }
        } else if (view == rbTypeHouse) {
            if (typeIds.size() >= 3 && !typeIds.contains("4")) {
                showShortToast("最多可选择3种类型");
                rbTypeHouse.setChecked(false);
                return;
            }
            changeCheckBoxState(rbTypeHouse);
            if (rbTypeHouse.isChecked()) {
                typeIds.add("4");
            } else {
                typeIds.remove("4");
            }
        } else if (view == rbTypeApart) {
            if (typeIds.size() >= 3 && !typeIds.contains("5")) {
                showShortToast("最多可选择3种类型");
                rbTypeApart.setChecked(false);
                return;
            }
            changeCheckBoxState(rbTypeApart);
            if (rbTypeApart.isChecked()) {
                typeIds.add("5");
            } else {
                typeIds.remove("5");
            }
        } else if (view == rbTypeOffice) {
            if (typeIds.size() >= 3 && !typeIds.contains("6")) {
                showShortToast("最多可选择3种类型");
                rbTypeOffice.setChecked(false);
                return;
            }
            changeCheckBoxState(rbTypeOffice);
            if (rbTypeOffice.isChecked()) {
                typeIds.add("6");
            } else {
                typeIds.remove("6");
            }
        } else if (view == rbTypeShop) {
            if (typeIds.size() >= 3 && !typeIds.contains("7")) {
                showShortToast("最多可选择3种类型");
                rbTypeShop.setChecked(false);
                return;
            }
            changeCheckBoxState(rbTypeShop);
            if (rbTypeShop.isChecked()) {
                typeIds.add("7");
            } else {
                typeIds.remove("7");
            }
        } else if (view == rbFromPhone) {
			fromType = "0";
			rbFromPhone.setChecked(true);
			rbFromVisit.setChecked(false);
		} else if (view == rbFromVisit) {
			fromType = "1";
			rbFromPhone.setChecked(false);
			rbFromVisit.setChecked(true);
		} else if (view == btnSave) {
            onSaveClick();
        }
    }
    
    private void changeCheckBoxState(final CheckBox btnChange) {
        if (!btnChange.isChecked()) {
            btnChange.setChecked(false);
        } else {
            btnChange.setChecked(true);
        }
    }
    
    private void onSaveClick() {
        customerName = etCustomerName.getText().toString().trim();
        if (!ListUtils.isEmpty(layoutIds)) {
            houseTypes = ListUtils.sortJoin(layoutIds);
        }
        if (!ListUtils.isEmpty(typeIds)) {
            propertyTypes = ListUtils.sortJoin(typeIds);
        }
        
        if (TextUtils.isEmpty(customerName)) {
            showShortToast("请输入客户姓名");
            return;
        }
        if (TextUtils.isEmpty(houseTypes)) {
            showShortToast("请选择户型");
            return;
        }
        if (TextUtils.isEmpty(propertyTypes)) {
            showShortToast("请选择类型");
            return;
        }
        ClientHttpRequest.updateClientRequest(customerName, categoryId, "", initMin, initMax, 
            intentArea, houseTypes, propertyTypes, clientId, ChannelCookie.getInstance().getCurrentHouseId(), fromType, new UpdateClientHandler(this));
    }
    
    class UpdateClientHandler extends HttpResponseHandlerFragment<CustomerEditFragment> {

        public UpdateClientHandler(CustomerEditFragment context) {
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
                        showShortToast("保存成功");
                        Intent data = new Intent();
                        data.putExtra(IntentKey.INTENT_KEY_CUSTOMER_IS_EDIT, true);
                        finishActivity(data);
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
            } else {
                showShortToast("修改失败");
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("min", initMin);
        outState.putString("max", initMax);
    }
    
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            initMin = savedInstanceState.getString("min");
            initMax = savedInstanceState.getString("max");
        }

        projectPriceBar.setInitLow(Integer.valueOf(initMin));
        projectPriceBar.setInitHigh(Integer.valueOf(initMax));
    }
}
