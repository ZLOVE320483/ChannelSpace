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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zlove.act.ActContact;
import com.zlove.act.ActCustomerChannel;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DateTimePickDialogUtil;
import com.zlove.base.util.DateTimePickDialogUtil.SetRevisitTimeListener;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.DoubleDirectSeekBar;
import com.zlove.base.widget.DoubleDirectSeekBar.OnSeekBarChangeListener;
import com.zlove.bean.common.CommonBean;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;


public class CustomerAddFragment extends BaseFragment implements OnClickListener, SetRevisitTimeListener {
	
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

    private View selectChannelView;
    private TextView tvChannel;
    
    private View setRevisitView;
    private TextView tvDateTime;
    
    private Button btnSave;

    private String customerName = "";
    private String customerPhone = "";
    private String categoryId = "1";
    private String fromType = "0";
    private String minPrice = "0";
    private String maxPrice = "1000";
    private String fromWay = "";
    private String visitTime = "";
    
    private List<String> layoutIds = new ArrayList<String>();
    private List<String> typeIds = new ArrayList<String>();
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_customer_add;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("新增客户");
        
        ivContact = (ImageView) view.findViewById(R.id.iv_contact);
        ivContact.setOnClickListener(this);
        
        etCustomerName = (EditText) view.findViewById(R.id.id_customer_name);
		etCustomerPhone = (EditText) view.findViewById(R.id.id_customer_phone);
		
		customerIntentionAView = view.findViewById(R.id.id_customer_intention_a);
		customerIntentionBView = view.findViewById(R.id.id_customer_intention_b);
		customerIntentionCView = view.findViewById(R.id.id_customer_intention_c);
		customerIntentionDView = view.findViewById(R.id.id_customer_intention_d);
		
		rbIntentionA = (RadioButton)view.findViewById(R.id.rb_intention_a);
		rbIntentionB = (RadioButton)view.findViewById(R.id.rb_intention_b);
		rbIntentionC = (RadioButton)view.findViewById(R.id.rb_intention_c);
		rbIntentionD = (RadioButton)view.findViewById(R.id.rb_intention_d);
		rbIntentionA.setChecked(true);
		
		rbFromPhone = (RadioButton) view.findViewById(R.id.from_phone);
        rbFromVisit = (RadioButton) view.findViewById(R.id.from_visit);
        rbFromPhone.setChecked(true);
        
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
        rbTypeApart = (CheckBox) view.findViewById(R.id.id_house_type_apart);
        rbTypeOffice = (CheckBox) view.findViewById(R.id.id_project_type_office);
        rbTypeShop = (CheckBox) view.findViewById(R.id.id_project_type_shop);
		
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
		
		rbIntentionA.setClickable(false);
        rbIntentionB.setClickable(false);
        rbIntentionC.setClickable(false);
        rbIntentionD.setClickable(false);
        
        customerIntentionAView.setOnClickListener(this);
		customerIntentionBView.setOnClickListener(this);
		customerIntentionCView.setOnClickListener(this);
		customerIntentionDView.setOnClickListener(this);
		
        selectChannelView = view.findViewById(R.id.select_customer_channel);
        selectChannelView.setOnClickListener(this);
        tvChannel = (TextView) view.findViewById(R.id.customer_channel);
		
		setRevisitView = view.findViewById(R.id.set_revisit_time);
		setRevisitView.setOnClickListener(this);
		tvDateTime = (TextView) view.findViewById(R.id.date_time);

		btnSave = (Button) view.findViewById(R.id.id_confirm);
		btnSave.setOnClickListener(this);
        
        projectPriceBar = (DoubleDirectSeekBar) view.findViewById(R.id.project_price_bar);
        projectPriceBar.setProgressHigh(1000);
        projectPriceBar.setProgressLow(0);
        
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
    }

    @Override
    public void onClick(View v) {
        if (v == ivContact) {
            Intent intent = new Intent(getActivity(), ActContact.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_CONTACT);
        } else if (v == customerIntentionAView) {
        	categoryId = "1";
            rbIntentionA.setChecked(true);
            rbIntentionB.setChecked(false);
            rbIntentionC.setChecked(false);
            rbIntentionD.setChecked(false);
        } else if (v == customerIntentionBView) {
        	categoryId = "2";
            rbIntentionA.setChecked(false);
            rbIntentionB.setChecked(true);
            rbIntentionC.setChecked(false);
            rbIntentionD.setChecked(false);
        } else if (v == customerIntentionCView) {
        	categoryId = "3";
            rbIntentionA.setChecked(false);
            rbIntentionB.setChecked(false);
            rbIntentionC.setChecked(true);
            rbIntentionD.setChecked(false);
        } else if (v == customerIntentionDView) {
        	categoryId = "4";
            rbIntentionA.setChecked(false);
            rbIntentionB.setChecked(false);
            rbIntentionC.setChecked(false);
            rbIntentionD.setChecked(true);
        } else if (v == rbFromPhone) {
        	fromType = "0";
            rbFromPhone.setChecked(true);
            rbFromVisit.setChecked(false);
        } else if (v == rbFromVisit) {
        	fromType = "1";
            rbFromPhone.setChecked(false);
            rbFromVisit.setChecked(true);
        } else if (v == rbLayoutOne) {
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
        } else if (v == rbLayoutTwo) {
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
        } else if (v == rbLayoutThree) {
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
        } else if (v == rbLayoutFour) {
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
        } else if (v == rbLayoutFive) {
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
        } else if (v == rbLayoutFiveMore) {
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
        } else if (v == rbTypeHigh) {
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
        } else if (v == rbTypeMulty) {
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
        } else if (v == rbTypeStack) {
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
        } else if (v == rbTypeHouse) {
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
        } else if (v == rbTypeApart) {
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
        } else if (v == rbTypeOffice) {
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
        } else if (v == rbTypeShop) {
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
        } else if (v == setRevisitView) {
            DateTimePickDialogUtil util = new DateTimePickDialogUtil(getActivity(), this);
            util.showDateSelectDialog("选择回访日期");
        } else if (v == selectChannelView) {
            Intent intent = new Intent(getActivity(), ActCustomerChannel.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_CHANNEL);
        } else if (v == btnSave) {
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
		customerPhone = etCustomerPhone.getText().toString().trim();
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
        if (TextUtils.isEmpty(houseTypes)) {
            showShortToast("请选择户型");
            return;
        }
        if (TextUtils.isEmpty(propertyTypes)) {
            showShortToast("请选择类型");
            return;
        }
        
        ClientHttpRequest.recommendClientRequest(customerName, customerPhone, categoryId, minPrice, maxPrice, houseTypes, 
        		ChannelCookie.getInstance().getCurrentHouseId(), propertyTypes, "100", fromType, fromWay, visitTime, new RecommendClientHandler(this));
    }

    @Override
    public void setRevisitTime(String time) {
        tvDateTime.setText(time);
        this.visitTime = time;
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
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
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
	        } else if (requestCode == IntentKey.REQUEST_CODE_SELECT_CHANNEL) {
				fromWay = data.getStringExtra(IntentKey.INTENT_KEY_SELECT_CHANNEL);
				tvChannel.setText(fromWay);
			}
    	}
    }
    
    class RecommendClientHandler extends HttpResponseHandlerFragment<CustomerAddFragment> {

		public RecommendClientHandler(CustomerAddFragment context) {
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
						showShortToast("添加成功");
						Intent intent = new Intent();
						finishActivity(intent);
					} else {
						showShortToast(bean.getMessage());
					}
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
		}
		
    }
}
