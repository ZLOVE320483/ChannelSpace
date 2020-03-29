package com.zlove.frag.independent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zlove.act.independent.ActFriendSelect;
import com.zlove.act.independent.ActIndependentContact;
import com.zlove.act.independent.ActProjectSelect;
import com.zlove.adapter.independent.CustomerAreaSelectAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.util.UIUtil;
import com.zlove.base.widget.CommonConfirmDialog;
import com.zlove.base.widget.CommonConfirmDialog.ConfirmListener;
import com.zlove.base.widget.CommonGridView;
import com.zlove.base.widget.DoubleDirectSeekBar;
import com.zlove.base.widget.DoubleDirectSeekBar.OnSeekBarChangeListener;
import com.zlove.bean.city.AreaGridBean;
import com.zlove.bean.city.AreaListBean;
import com.zlove.bean.city.AreaListData;
import com.zlove.bean.city.AreaListItem;
import com.zlove.bean.common.CommonBean;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.CityHttpRequest;
import com.zlove.http.ClientHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class IndependentCustomerAddFragment extends BaseFragment implements OnClickListener, OnItemClickListener {
	
	private EditText etCustomerName;
	private ImageView ivContact;
	private EditText etCustomerPhone;
	
	private View customerIntentionAView;
	private View customerIntentionBView;
	private View customerIntentionCView;
	private View customerIntentionDView;
	private RadioButton rbIntentionA;
	private RadioButton rbIntentionB;
	private RadioButton rbIntentionC;
	private RadioButton rbIntentionD;
	
	private CommonGridView gridView;
	private List<AreaGridBean> areas = new ArrayList<>();
    List<AreaListItem> areaList = new ArrayList<>();
	private CustomerAreaSelectAdapter adapter;
	
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
    
    private View reportProjectView;
    private TextView tvProjectName;
    private View projectFriendView;
    private TextView tvFriendName;

    private EditText etClientDesc;
    private Button btnSave = null;
    
    private String customerName = "";
    private String customerPhone = "";
    private String categoryId = "1";
    private List<String> areaIds = new ArrayList<>();
    private List<String> layoutIds = new ArrayList<>();
    private List<String> typeIds = new ArrayList<>();
    private String minPrice = "0";
    private String maxPrice = "1000";
    private String intentArea = "130";
    private String gender = "1";
    
    private String projectName = "";
    private String projectId = "";
    private String friendName = "";
    private String saleManId = "";
    private String clientDesc;

    private Dialog loadDialog;
    
	@Override
	protected int getInflateLayout() {
		return R.layout.frag_independent_customer_add;
	}

	@Override
	protected void setUpView(View view) {
		view.findViewById(R.id.id_back).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                UIUtil.hideKeyboard(getActivity());
                finishActivity();
            }
        });
		
		((TextView) view.findViewById(R.id.id_title)).setText("新增客户");

        loadDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
		
		Intent intent = getActivity().getIntent();
		if (intent != null) {
		    if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_ID)) {
                projectId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
            }
		    if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_NAME)) {
		        projectName = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_NAME);
            }
		    if (intent.hasExtra(IntentKey.INTENT_KEY_CUSTOMER_NAME)) {
                customerName = intent.getStringExtra(IntentKey.INTENT_KEY_CUSTOMER_NAME);
            }
		    if (intent.hasExtra(IntentKey.INTENT_KEY_CUSTOMER_NUMBER)) {
                customerPhone = intent.getStringExtra(IntentKey.INTENT_KEY_CUSTOMER_NUMBER);
            }
        }
		
		etCustomerName = (EditText) view.findViewById(R.id.id_customer_name);
		etCustomerPhone = (EditText) view.findViewById(R.id.id_customer_phone);
		
		if (customerName.equals(customerPhone)) {
            etCustomerName.setText("");
        } else {
            etCustomerName.setText(customerName);
        }
		etCustomerPhone.setText(customerPhone);
		
		ivContact = (ImageView) view.findViewById(R.id.iv_contact);
		ivContact.setOnClickListener(this);
		
		customerIntentionAView = view.findViewById(R.id.id_customer_intention_a);
		customerIntentionBView = view.findViewById(R.id.id_customer_intention_b);
		customerIntentionCView = view.findViewById(R.id.id_customer_intention_c);
		customerIntentionDView = view.findViewById(R.id.id_customer_intention_d);
		
		rbIntentionA = (RadioButton)view.findViewById(R.id.rb_intention_a);
		rbIntentionB = (RadioButton)view.findViewById(R.id.rb_intention_b);
		rbIntentionC = (RadioButton)view.findViewById(R.id.rb_intention_c);
		rbIntentionD = (RadioButton)view.findViewById(R.id.rb_intention_d);
		rbIntentionA.setChecked(true);
		
		rbIntentionA.setClickable(false);
        rbIntentionB.setClickable(false);
        rbIntentionC.setClickable(false);
        rbIntentionD.setClickable(false);
        
        gridView = (CommonGridView) view.findViewById(R.id.gridview);
        adapter = new CustomerAreaSelectAdapter(getActivity(), areas);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        rbLayoutOne = (CheckBox) view.findViewById(R.id.rb_project_layout_one);
        rbLayoutTwo = (CheckBox) view.findViewById(R.id.rb_project_layout_two);
        rbLayoutThree = (CheckBox) view.findViewById(R.id.rb_project_layout_three);
        rbLayoutFour = (CheckBox) view.findViewById(R.id.rb_project_layout_four);
        rbLayoutFive = (CheckBox) view.findViewById(R.id.rb_project_layout_five);
        rbLayoutFiveMore = (CheckBox) view.findViewById(R.id.rb_project_layout_five_more);
        
        rbTypeHigh = (CheckBox) view.findViewById(R.id.rb_house_type_high);
        rbTypeMulty = (CheckBox) view.findViewById(R.id.rb_house_type_multy);
        rbTypeStack = (CheckBox) view.findViewById(R.id.rb_house_type_stack);
        rbTypeHouse = (CheckBox) view.findViewById(R.id.rb_house_type_house);
        rbTypeApart = (CheckBox) view.findViewById(R.id.rb_house_type_apart);
        rbTypeOffice = (CheckBox) view.findViewById(R.id.rb_house_type_office);
        rbTypeShop = (CheckBox) view.findViewById(R.id.rb_house_type_shop);


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
		
		projectPriceBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            
            @Override
            public void onProgressChanged(DoubleDirectSeekBar seekBar, int progressLow, int progressHigh) {
                minPrice = String.valueOf(progressLow);
                maxPrice = String.valueOf(progressHigh);
                projectPriceBar.setInitHigh(progressHigh);
                projectPriceBar.setInitLow(progressLow);
            }
            
            @Override
            public void onProgressBefore() {
            }
            
            @Override
            public void onProgressAfter() {
            }
        });
		
		reportProjectView = view.findViewById(R.id.id_report_project);
		tvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
		tvProjectName.setText(projectName);
		projectFriendView = view.findViewById(R.id.id_project_friend);
        tvFriendName = (TextView) view.findViewById(R.id.tv_friend_name);

        etClientDesc = (EditText) view.findViewById(R.id.id_user_feedback);

		reportProjectView.setOnClickListener(this);
		projectFriendView.setOnClickListener(this);
	}
	
	@Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(areas)) {
            CityHttpRequest.getAreaList(ChannelCookie.getInstance().getCurrentCityId(), new GetAreaListHandler(this));
        }
    }

	@Override
	public void onClick(View view) {
	    if (view == ivContact) {
            Intent intent = new Intent(getActivity(), ActIndependentContact.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_CONTACT);
        } else if (view == customerIntentionAView) {
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
		} else if (view == rbLayoutOne) {
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
        } else if (view == rbLayoutTwo && !layoutIds.contains("2")) {
            if (layoutIds.size() >= 3) {
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
        } else if (view == btnSave) {
			onSaveClick();
		} else if (view == reportProjectView) {
            Intent intent = new Intent(getActivity(), ActProjectSelect.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_PROJECT);
        } else if (view == projectFriendView) {
            if (TextUtils.isEmpty(projectId)) {
                showShortToast("请先选择楼盘");
                return;
            }
            Intent intent = new Intent(getActivity(), ActFriendSelect.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_FRIEND);
        }
	}
	
	private void onSaveClick() {
		customerName = etCustomerName.getText().toString().trim();
		customerPhone = etCustomerPhone.getText().toString().trim();
        clientDesc = etClientDesc.getText().toString().trim();
		String locationIds = ListUtils.sortJoin(areaIds);
		String houseTypes = ListUtils.sortJoin(layoutIds);
		String propertyTypes = ListUtils.sortJoin(typeIds);
		
		if (TextUtils.isEmpty(customerName)) {
			showShortToast("请输入客户姓名");
			return;
		}
		if (TextUtils.isEmpty(customerPhone)) {
			showShortToast("请输入客户电话号码");
			return;
		}
		if (TextUtils.isEmpty(locationIds)) {
            showShortToast("请选择区域");
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
        if (TextUtils.isEmpty(projectId)) {
            ClientHttpRequest.addNewClientRequest(customerName, customerPhone, categoryId, locationIds, minPrice, maxPrice, intentArea, houseTypes,
                propertyTypes, gender, clientDesc, new AddCustomerHandler(this));
        } else {
            if (TextUtils.isEmpty(saleManId)) {
                showShortToast("请选择业务员");
                return;
            }
            ClientHttpRequest.recommeClientRequest(customerName, customerPhone, categoryId, locationIds, minPrice, maxPrice, intentArea, houseTypes, 
                propertyTypes, projectId, saleManId, clientDesc, new RecommendCustomerHandler(this));
        }
	}

	
	private void changeCheckBoxState(final CheckBox btnChange) {
        if (!btnChange.isChecked()) {
            btnChange.setChecked(false);
        } else {
            btnChange.setChecked(true);
        }
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (data != null) {
	        if (requestCode == IntentKey.REQUEST_CODE_SELECT_CONTACT) {
	            customerName = data.getStringExtra(IntentKey.INTENT_KEY_CONTACT_NAME);
	            customerPhone = data.getStringExtra(IntentKey.INTENT_KEY_CONTACT_NUMBER);
	            if (customerPhone.contains(" ")) {
					customerPhone = customerPhone.replaceAll(" ", "");
				}
	            if (customerName.equals(customerPhone)) {
	                etCustomerName.setText("");
	            } else {
	                etCustomerName.setText(customerName);
	            }
	            etCustomerPhone.setText(customerPhone);
	        } else if (requestCode == IntentKey.REQUEST_CODE_SELECT_PROJECT) {
                projectName = data.getStringExtra(IntentKey.INTENT_KEY_PROJECT_NAME);
                tvProjectName.setText(projectName);
                projectId = data.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
                if (TextUtils.isEmpty(projectId)) {
                	friendName = "";
                	saleManId = "";
                	tvFriendName.setText("");
				} else {
				    Intent intent = new Intent(getActivity(), ActFriendSelect.class);
				    intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
				    startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_FRIEND);
                }
            } else if (requestCode == IntentKey.REQUEST_CODE_SELECT_FRIEND) {
                friendName = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_NAME);
                saleManId = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_ID);
                tvFriendName.setText(friendName);
            }
        }
	}
	
	class GetAreaListHandler extends HttpResponseHandlerFragment<IndependentCustomerAddFragment> {

        public GetAreaListHandler(IndependentCustomerAddFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
            if (loadDialog != null && !loadDialog.isShowing()) {
                loadDialog.show();
            }
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (!isAdded()) {
                return;
            }
            if (content != null) {
                AreaListBean bean = JsonUtil.toObject(new String(content), AreaListBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        AreaListData data = bean.getData();
                        if (data != null) {
                            List<AreaListItem> tmpList = data.getArea_list();
                            if (!ListUtils.isEmpty(tmpList)) {
                                areaList.addAll(tmpList);
                            }
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
                if (!ListUtils.isEmpty(areaList)) {
                    for(AreaListItem areaListItem : areaList) {
                        AreaGridBean gridBean = new AreaGridBean();
                        gridBean.setItem(areaListItem);
                        gridBean.setSelect(false);
                        areas.add(gridBean);
                    }
                }
                adapter.notifyDataSetChanged();
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
            if (loadDialog != null && loadDialog.isShowing()) {
                loadDialog.dismiss();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        AreaGridBean bean = areas.get(position);
        if (bean == null) {
            return;
        }
        if (bean.isSelect()) {
            bean.setSelect(false);
            areaIds.remove(bean.getItem().getId());
        } else {
            if (areaIds.size() >= 3) {
                showShortToast("最多可选择3个区域");
                return;
            }
            bean.setSelect(true);
            areaIds.add(bean.getItem().getId());
        }
        adapter.notifyDataSetChanged();
    }
    
    class AddCustomerHandler extends HttpResponseHandlerFragment<IndependentCustomerAddFragment> {

        public AddCustomerHandler(IndependentCustomerAddFragment context) {
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
                if (bean.getStatus() == 200) {
                    showShortToast("添加成功");
                    getActivity().setResult(Activity.RESULT_OK);
                    finishActivity();
                } else {
                    showShortToast(bean.getMessage());
                } 
            } else {
                showShortToast("添加失败");
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
    
    class RecommendCustomerHandler extends HttpResponseHandlerFragment<IndependentCustomerAddFragment> {

        public RecommendCustomerHandler(IndependentCustomerAddFragment context) {
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
                if (bean.getStatus() == 200) {
                    CommonConfirmDialog dialog = new CommonConfirmDialog(getActivity(), "业务员:" + friendName + " 向您反馈客户是否有效", new ConfirmListener() {
                        
                        @Override
                            public void confirm() {
                                getActivity().setResult(Activity.RESULT_OK);
                                finishActivity();
                            }
                        });
                    dialog.showdialog();
                } else {
                    showShortToast(bean.getMessage());
                } 
            } else {
                showShortToast("报备失败");
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("min", minPrice);
        outState.putString("max", maxPrice);
    }
    
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            minPrice = savedInstanceState.getString("min");
            maxPrice = savedInstanceState.getString("max");
        }

        projectPriceBar.setInitHigh(Integer.valueOf(maxPrice));
        projectPriceBar.setInitLow(Integer.valueOf(minPrice));
    }
}
