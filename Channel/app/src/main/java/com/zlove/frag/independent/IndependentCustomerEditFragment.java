package com.zlove.frag.independent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

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
import android.widget.RadioButton;
import android.widget.TextView;

import com.zlove.adapter.independent.CustomerAreaSelectAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.util.StringUtil;
import com.zlove.base.util.UIUtil;
import com.zlove.base.widget.CommonGridView;
import com.zlove.base.widget.DoubleDirectSeekBar;
import com.zlove.base.widget.DoubleDirectSeekBar.OnSeekBarChangeListener;
import com.zlove.bean.city.AreaGridBean;
import com.zlove.bean.city.AreaListBean;
import com.zlove.bean.city.AreaListData;
import com.zlove.bean.city.AreaListItem;
import com.zlove.bean.client.ClientDetailData;
import com.zlove.bean.common.CommonBean;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.CityHttpRequest;
import com.zlove.http.ClientHttpRequest;


public class IndependentCustomerEditFragment extends BaseFragment implements OnClickListener, OnItemClickListener {
    
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

    private Dialog loadDialog;

	private CommonGridView gridView;
	private List<AreaGridBean> areas = new ArrayList<AreaGridBean>();
    List<AreaListItem> areaList = new ArrayList<AreaListItem>();
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
    
    private Button btnSave = null;
    
    private String customerName = "";
    private String categoryId = "1";
    private List<String> areaIds = new ArrayList<String>();
    private List<String> layoutIds = new ArrayList<String>();
    private List<String> typeIds = new ArrayList<String>();
    private String initMin = "0";
    private String initMax = "1000";
    private String intentArea = "130";
    private String gender = "1";
    
    private String locationIds = "";
    private String houseTypes = "";
    private String propertyTypes = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_customer_edit;
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

        loadDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        
        etCustomerName = (EditText) view.findViewById(R.id.id_customer_name);
        tvCustomerPhone = (TextView) view.findViewById(R.id.id_customer_phone);   
        customerIntentionAView = view.findViewById(R.id.id_customer_intention_a);
        customerIntentionBView = view.findViewById(R.id.id_customer_intention_b);
        customerIntentionCView = view.findViewById(R.id.id_customer_intention_c);
        customerIntentionDView = view.findViewById(R.id.id_customer_intention_d);
        
        gridView = (CommonGridView) view.findViewById(R.id.gridview);
        adapter = new CustomerAreaSelectAdapter(getActivity(), areas);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        
        rbIntentionA = (RadioButton)view.findViewById(R.id.rb_intention_a);
        rbIntentionB = (RadioButton)view.findViewById(R.id.rb_intention_b);
        rbIntentionC = (RadioButton)view.findViewById(R.id.rb_intention_c);
        rbIntentionD = (RadioButton)view.findViewById(R.id.rb_intention_d);

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
        
        rbIntentionA.setClickable(false);
        rbIntentionB.setClickable(false);
        rbIntentionC.setClickable(false);
        rbIntentionD.setClickable(false);

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
            locationIds = data.getIntent_location_ids();
            categoryId = data.getClient_category_id();
            propertyTypes = data.getProperty_types();
            houseTypes = data.getHouse_types();
            initMin = data.getIntent_price_min();
            initMax = data.getIntent_price_max();
            
            setAreaIds();
            setLayoutIds();
            setTypeIds();
            
        	etCustomerName.setText(data.getClient_name());
        	tvCustomerPhone.setText(data.getClient_phone());
        	setClientIntentionState(data.getClient_category_id());
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
	
	@Override
    public void onResume() {
        super.onResume();
        if (ListUtils.isEmpty(areas)) {
            CityHttpRequest.getAreaList(ChannelCookie.getInstance().getCurrentCityId(), new GetAreaListHandler(this));
        }
    }
	
	private void setAreaIds() {
        String[] locIds = locationIds.split(",");
        for (int i = 0; i < locIds.length; i++) {
            areaIds.add(locIds[i]);
        }
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
		if (!ListUtils.isEmpty(areaIds)) {
		    locationIds = ListUtils.sortJoin(areaIds);
        }
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
        
        ClientHttpRequest.updateClientRequest(customerName, categoryId, locationIds, 
        		initMin, initMax, intentArea, 
        		houseTypes, propertyTypes, gender, clientId, new UpdateClientHandler(this));
	}
    
    class GetAreaListHandler extends HttpResponseHandlerFragment<IndependentCustomerEditFragment> {

        public GetAreaListHandler(IndependentCustomerEditFragment context) {
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
                        if (data != null && StringUtil.isContains(data.getIntent_location_ids(), areaListItem.getId())) {
                            gridBean.setSelect(true);
						} else {
							gridBean.setSelect(false);
						}
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
    
    class UpdateClientHandler extends HttpResponseHandlerFragment<IndependentCustomerEditFragment> {

		public UpdateClientHandler(IndependentCustomerEditFragment context) {
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
						getActivity().setResult(Activity.RESULT_OK, data);
						finishActivity();
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
