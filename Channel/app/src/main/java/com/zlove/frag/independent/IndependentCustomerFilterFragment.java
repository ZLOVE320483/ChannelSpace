
package com.zlove.frag.independent;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.base.util.ListUtils;
import com.zlove.bean.client.ClientFilterBean;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;

import java.util.ArrayList;
import java.util.List;

public class IndependentCustomerFilterFragment extends BaseFragment implements OnClickListener {
    
    private CheckBox rbCustomerIntentionAll;
    private CheckBox rbCustomerIntentionA;
    private CheckBox rbCustomerIntentionB;
    private CheckBox rbCustomerIntentionC;
    private CheckBox rbCustomerIntentionD;

    private CheckBox rbLayoutAll;
    private CheckBox rbLayoutOne;
    private CheckBox rbLayoutTwo;
    private CheckBox rbLayoutThree;
    private CheckBox rbLayoutFour;
    private CheckBox rbLayoutFive;
    private CheckBox rbLayoutFiveMore;

    private CheckBox rbTypeAll;
    private CheckBox rbTypeHigh;
    private CheckBox rbTypeMulty;
    private CheckBox rbTypeStack;
    private CheckBox rbTypeHouse;
    private CheckBox rbTypeApart;
    private CheckBox rbTypeOffice;
    private CheckBox rbTypeShop;
    
    private CheckBox rbCustomerStatusAll;
    private CheckBox rbCustomerStatusReportToBeVerify;
    private CheckBox rbCustomerStatusReportEffect;
    private CheckBox rbCustomerStatusVisitToBeVerify;
    private CheckBox rbCustomerStatusVisit;
    private CheckBox rbCustomerStatusForm;
    private CheckBox rbCustomerStatusDeal;
    private CheckBox rbCustomerStatusDone;

    private CheckBox cbCustomerEffectAll;
    private CheckBox cbCustomerEffectHasOverdue;
    private CheckBox cbCustomerEffectNotOverdue;
    
    private Button btnConfirm;
    
    private String categoryId = "";
    private String houseType = "";
    private String propertyType = "";
    private String status = "";
    private String isDisable = "";

    private List<String> categoryIds = new ArrayList<>();
    private List<String> houseTypes = new ArrayList<>();
    private List<String> propertyTypes = new ArrayList<>();
    private List<String> statuses = new ArrayList<>();
    private List<String> isDisables = new ArrayList<>();
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_customer_filter;
    }

    @Override
    protected void setUpView(View view) {
    	
    	Intent intent = getActivity().getIntent();
    	if (intent != null) {
    		if (intent.hasExtra(IntentKey.INTENT_KEY_CLIENT_FILTER_ITEM)) {
    			ClientFilterBean bean = (ClientFilterBean) intent.getSerializableExtra(IntentKey.INTENT_KEY_CLIENT_FILTER_ITEM);
    			categoryId = bean.getCategoryId();
    			houseType = bean.getHouseType();
    			propertyType = bean.getPropertyType();
    			status = bean.getStatus();
                isDisable = bean.getIsDisable();
			}
		}
    	
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("客户筛选");
        
        rbCustomerIntentionAll = (CheckBox) view.findViewById(R.id.id_customer_intention_all);
        rbCustomerIntentionA = (CheckBox) view.findViewById(R.id.id_customer_intention_a);
        rbCustomerIntentionB = (CheckBox) view.findViewById(R.id.id_customer_intention_b);
        rbCustomerIntentionC = (CheckBox) view.findViewById(R.id.id_customer_intention_c);
        rbCustomerIntentionD = (CheckBox) view.findViewById(R.id.id_customer_intention_d);

        rbLayoutAll = (CheckBox) view.findViewById(R.id.rb_project_layout_all);
        rbLayoutOne = (CheckBox) view.findViewById(R.id.rb_project_layout_one);
        rbLayoutTwo = (CheckBox) view.findViewById(R.id.rb_project_layout_two);
        rbLayoutThree = (CheckBox) view.findViewById(R.id.rb_project_layout_three);
        rbLayoutFour = (CheckBox) view.findViewById(R.id.rb_project_layout_four);
        rbLayoutFive = (CheckBox) view.findViewById(R.id.rb_project_layout_five);
        rbLayoutFiveMore = (CheckBox) view.findViewById(R.id.rb_project_layout_five_more);
        
        rbTypeAll = (CheckBox) view.findViewById(R.id.rb_house_type_all);
        rbTypeHigh = (CheckBox) view.findViewById(R.id.rb_house_type_high);
        rbTypeMulty = (CheckBox) view.findViewById(R.id.rb_house_type_multy);
        rbTypeStack = (CheckBox) view.findViewById(R.id.rb_house_type_stack);
        rbTypeHouse = (CheckBox) view.findViewById(R.id.rb_house_type_house);
        rbTypeApart = (CheckBox) view.findViewById(R.id.rb_house_type_apart);
        rbTypeOffice = (CheckBox) view.findViewById(R.id.rb_house_type_office);
        rbTypeShop = (CheckBox) view.findViewById(R.id.rb_house_type_shop);
        
        rbCustomerStatusAll = (CheckBox) view.findViewById(R.id.rb_customer_status_all);
        rbCustomerStatusReportToBeVerify = (CheckBox) view.findViewById(R.id.rb_customer_status_report_to_be_verify);
        rbCustomerStatusReportEffect = (CheckBox) view.findViewById(R.id.rb_customer_status_effect);
        rbCustomerStatusVisitToBeVerify = (CheckBox) view.findViewById(R.id.rb_customer_visit_status_to_be_verify);
        rbCustomerStatusVisit = (CheckBox) view.findViewById(R.id.rb_customer_status_visit);
        rbCustomerStatusForm = (CheckBox) view.findViewById(R.id.rb_customer_status_form);
        rbCustomerStatusDeal = (CheckBox) view.findViewById(R.id.rb_customer_status_deal);
        rbCustomerStatusDone = (CheckBox) view.findViewById(R.id.rb_customer_status_done);

        cbCustomerEffectAll = (CheckBox) view.findViewById(R.id.rb_customer_effect_all);
        cbCustomerEffectHasOverdue = (CheckBox) view.findViewById(R.id.rb_customer_effect_yes);
        cbCustomerEffectNotOverdue = (CheckBox) view.findViewById(R.id.rb_customer_effect_no);

        btnConfirm = (Button) view.findViewById(R.id.id_confirm);
        btnConfirm.setOnClickListener(this);

        rbCustomerIntentionAll.setOnClickListener(this);
        rbCustomerIntentionA.setOnClickListener(this);
        rbCustomerIntentionB.setOnClickListener(this);
        rbCustomerIntentionC.setOnClickListener(this);
        rbCustomerIntentionD.setOnClickListener(this);
        
        rbLayoutAll.setOnClickListener(this);
        rbLayoutOne.setOnClickListener(this);
        rbLayoutTwo.setOnClickListener(this);
        rbLayoutThree.setOnClickListener(this);
        rbLayoutFour.setOnClickListener(this);
        rbLayoutFive.setOnClickListener(this);
        rbLayoutFiveMore.setOnClickListener(this);
        
        rbTypeAll.setOnClickListener(this);
        rbTypeHigh.setOnClickListener(this);
        rbTypeMulty.setOnClickListener(this);
        rbTypeStack.setOnClickListener(this);
        rbTypeHouse.setOnClickListener(this);
        rbTypeApart.setOnClickListener(this);
        rbTypeOffice.setOnClickListener(this);
        rbTypeShop.setOnClickListener(this);

        rbCustomerStatusAll.setOnClickListener(this);
        rbCustomerStatusReportToBeVerify.setOnClickListener(this);
        rbCustomerStatusReportEffect.setOnClickListener(this);
        rbCustomerStatusVisitToBeVerify.setOnClickListener(this);
        rbCustomerStatusVisit.setOnClickListener(this);
        rbCustomerStatusForm.setOnClickListener(this);
        rbCustomerStatusDeal.setOnClickListener(this);
        rbCustomerStatusDone.setOnClickListener(this);

        cbCustomerEffectAll.setOnClickListener(this);
        cbCustomerEffectHasOverdue.setOnClickListener(this);
        cbCustomerEffectNotOverdue.setOnClickListener(this);

        rbCustomerIntentionAll.setChecked(TextUtils.isEmpty(categoryId));
        rbCustomerIntentionA.setChecked(categoryId.contains("1"));
        rbCustomerIntentionB.setChecked(categoryId.contains("2"));
        rbCustomerIntentionC.setChecked(categoryId.contains("3"));
        rbCustomerIntentionD.setChecked(categoryId.contains("4"));

        rbLayoutAll.setChecked(TextUtils.isEmpty(houseType));
        rbLayoutOne.setChecked(houseType.contains("1"));
        rbLayoutTwo.setChecked(houseType.contains("2"));
        rbLayoutThree.setChecked(houseType.contains("3"));
        rbLayoutFour.setChecked(houseType.contains("4"));
        rbLayoutFive.setChecked(houseType.contains("5"));
        rbLayoutFiveMore.setChecked(houseType.contains("6"));

        rbTypeAll.setChecked(TextUtils.isEmpty(propertyType));
        rbTypeHigh.setChecked(propertyType.contains("1"));
        rbTypeMulty.setChecked(propertyType.contains("2"));
        rbTypeStack.setChecked(propertyType.contains("3"));
        rbTypeHouse.setChecked(propertyType.contains("4"));
        rbTypeApart.setChecked(propertyType.contains("5"));
        rbTypeOffice.setChecked(propertyType.contains("6"));
        rbTypeShop.setChecked(propertyType.contains("7"));

        rbCustomerStatusAll.setChecked(TextUtils.isEmpty(status));
        rbCustomerStatusReportToBeVerify.setChecked(status.contains("1"));
        rbCustomerStatusReportEffect.setChecked(status.contains("3"));
        rbCustomerStatusVisitToBeVerify.setChecked(status.contains("4"));
        rbCustomerStatusVisit.setChecked(status.contains("5"));
        rbCustomerStatusForm.setChecked(status.contains("6"));
        rbCustomerStatusDeal.setChecked(status.contains("7"));
        rbCustomerStatusDone.setChecked(status.contains("8"));

        cbCustomerEffectAll.setChecked(TextUtils.isEmpty(isDisable));
        cbCustomerEffectHasOverdue.setChecked(isDisable.contains("1"));
        cbCustomerEffectNotOverdue.setChecked(isDisable.contains("0"));
    }

    @Override
    public void onClick(View v) {
        if (v == rbCustomerIntentionAll) {
            changeCheckBoxState(rbCustomerIntentionAll);
            if (rbCustomerIntentionAll.isChecked()) {
                rbCustomerIntentionA.setChecked(false);
                rbCustomerIntentionB.setChecked(false);
                rbCustomerIntentionC.setChecked(false);
                rbCustomerIntentionD.setChecked(false);
            }
        } else if (v == rbCustomerIntentionA) {
            changeCheckBoxState(rbCustomerIntentionA);
            if (rbCustomerIntentionA.isChecked()) {
                rbCustomerIntentionAll.setChecked(false);
            }
        } else if (v == rbCustomerIntentionB) {
            changeCheckBoxState(rbCustomerIntentionB);
            if (rbCustomerIntentionB.isChecked()) {
                rbCustomerIntentionAll.setChecked(false);
            }
        } else if (v == rbCustomerIntentionC) {
            changeCheckBoxState(rbCustomerIntentionC);
            if (rbCustomerIntentionC.isChecked()) {
                rbCustomerIntentionAll.setChecked(false);
            }
        } else if (v == rbCustomerIntentionD) {
            changeCheckBoxState(rbCustomerIntentionD);
            if (rbCustomerIntentionD.isChecked()) {
                rbCustomerIntentionAll.setChecked(false);
            }
        } else if (v == rbLayoutAll) {
            changeCheckBoxState(rbLayoutAll);
            if (rbLayoutAll.isChecked()) {
                rbLayoutOne.setChecked(false);
                rbLayoutTwo.setChecked(false);
                rbLayoutThree.setChecked(false);
                rbLayoutFour.setChecked(false);
                rbLayoutFive.setChecked(false);
                rbLayoutFiveMore.setChecked(false);
            }
        } else if (v == rbLayoutOne) {
            changeCheckBoxState(rbLayoutOne);
            if (rbLayoutOne.isChecked()) {
                rbLayoutAll.setChecked(false);
            }
        } else if (v == rbLayoutTwo) {
            changeCheckBoxState(rbLayoutTwo);
            if (rbLayoutTwo.isChecked()) {
                rbLayoutAll.setChecked(false);
            }
        } else if (v == rbLayoutThree) {
            changeCheckBoxState(rbLayoutThree);
            if (rbLayoutThree.isChecked()) {
                rbLayoutAll.setChecked(false);
            }
        } else if (v == rbLayoutFour) {
            changeCheckBoxState(rbLayoutFour);
            if (rbLayoutFour.isChecked()) {
                rbLayoutAll.setChecked(false);
            }
        } else if (v == rbLayoutFive) {
            changeCheckBoxState(rbLayoutFive);
            if (rbLayoutFive.isChecked()) {
                rbLayoutAll.setChecked(false);
            }
        }  else if (v == rbLayoutFiveMore) {
            changeCheckBoxState(rbLayoutFiveMore);
            if (rbLayoutFiveMore.isChecked()) {
                rbLayoutAll.setChecked(false);
            }
        } else if (v == rbTypeAll) {
            changeCheckBoxState(rbTypeAll);
            if (rbTypeAll.isChecked()) {
                rbTypeHigh.setChecked(false);
                rbTypeMulty.setChecked(false);
                rbTypeStack.setChecked(false);
                rbTypeHouse.setChecked(false);
                rbTypeApart.setChecked(false);
                rbTypeOffice.setChecked(false);
                rbTypeShop.setChecked(false);
            }
        } else if (v == rbTypeHigh) {
            changeCheckBoxState(rbTypeHigh);
            if (rbTypeHigh.isChecked()) {
                rbTypeAll.setChecked(false);
            }
        } else if (v == rbTypeMulty) {
            changeCheckBoxState(rbTypeMulty);
            if (rbTypeMulty.isChecked()) {
                rbTypeAll.setChecked(false);
            }
        } else if (v == rbTypeStack) {
            changeCheckBoxState(rbTypeStack);
            if (rbTypeStack.isChecked()) {
                rbTypeAll.setChecked(false);
            }
        } else if (v == rbTypeHouse) {
            changeCheckBoxState(rbTypeHouse);
            if (rbTypeHouse.isChecked()) {
                rbTypeAll.setChecked(false);
            }
        } else if (v == rbTypeApart) {
            changeCheckBoxState(rbTypeApart);
            if (rbTypeApart.isChecked()) {
                rbTypeAll.setChecked(false);
            }
        } else if (v == rbTypeOffice) {
            changeCheckBoxState(rbTypeOffice);
            if (rbTypeOffice.isChecked()) {
                rbTypeAll.setChecked(false);
            }
        } else if (v == rbTypeShop) {
            changeCheckBoxState(rbTypeShop);
            if (rbTypeShop.isChecked()) {
                rbTypeAll.setChecked(false);
            }
        } else if (v == rbCustomerStatusAll) {
            changeCheckBoxState(rbCustomerStatusAll);
            if (rbCustomerStatusAll.isChecked()) {
                rbCustomerStatusReportToBeVerify.setChecked(false);
                rbCustomerStatusReportEffect.setChecked(false);
                rbCustomerStatusVisitToBeVerify.setChecked(false);
                rbCustomerStatusVisit.setChecked(false);
                rbCustomerStatusForm.setChecked(false);
                rbCustomerStatusDeal.setChecked(false);
                rbCustomerStatusDone.setChecked(false);
            }
        } else if (v == rbCustomerStatusReportToBeVerify) {
            changeCheckBoxState(rbCustomerStatusReportToBeVerify);
            if (rbCustomerStatusReportToBeVerify.isChecked()) {
                rbCustomerStatusAll.setChecked(false);
            }
        } else if (v == rbCustomerStatusReportEffect) {
            changeCheckBoxState(rbCustomerStatusReportEffect);
            if (rbCustomerStatusReportEffect.isChecked()) {
                rbCustomerStatusAll.setChecked(false);
            }
        } else if (v == rbCustomerStatusVisitToBeVerify) {
            changeCheckBoxState(rbCustomerStatusVisitToBeVerify);
            if (rbCustomerStatusVisitToBeVerify.isChecked()) {
                rbCustomerStatusAll.setChecked(false);
            }
        } else if (v == rbCustomerStatusVisit) {
            changeCheckBoxState(rbCustomerStatusVisit);
            if (rbCustomerStatusVisit.isChecked()) {
                rbCustomerStatusAll.setChecked(false);
            }
		} else if (v == rbCustomerStatusForm) {
            changeCheckBoxState(rbCustomerStatusForm);
            if (rbCustomerStatusForm.isChecked()) {
                rbCustomerStatusAll.setChecked(false);
            }
        } else if (v == rbCustomerStatusDeal) {
            changeCheckBoxState(rbCustomerStatusDeal);
            if (rbCustomerStatusDeal.isChecked()) {
                rbCustomerStatusAll.setChecked(false);
            }
        } else if (v == rbCustomerStatusDone) {
            changeCheckBoxState(rbCustomerStatusDone);
            if (rbCustomerStatusDone.isChecked()) {
                rbCustomerStatusAll.setChecked(false);
            }
        } else if (v == cbCustomerEffectAll) {
            changeCheckBoxState(cbCustomerEffectAll);
            if (cbCustomerEffectAll.isChecked()) {
                cbCustomerEffectHasOverdue.setChecked(false);
                cbCustomerEffectNotOverdue.setChecked(false);
            }
        } else if (v == cbCustomerEffectHasOverdue) {
            changeCheckBoxState(cbCustomerEffectHasOverdue);
            if (cbCustomerEffectHasOverdue.isChecked()) {
                cbCustomerEffectAll.setChecked(false);
            }
        } else if (v == cbCustomerEffectNotOverdue) {
            changeCheckBoxState(cbCustomerEffectNotOverdue);
            if (cbCustomerEffectNotOverdue.isChecked()) {
                cbCustomerEffectAll.setChecked(false);
            }
        }  else if (v == btnConfirm) {
            onConfirmClick();
        }
    }

    private void changeCheckBoxState(final CheckBox btnChange) {
        if (!btnChange.isChecked()) {
            btnChange.setChecked(false);
        } else {
            btnChange.setChecked(true);
        }
    }

    private void onConfirmClick() {
        if (rbCustomerIntentionA.isChecked()) {
            categoryIds.add("1");
        }
        if (rbCustomerIntentionB.isChecked()) {
            categoryIds.add("2");
        }
        if (rbCustomerIntentionC.isChecked()) {
            categoryIds.add("3");
        }
        if (rbCustomerIntentionD.isChecked()) {
            categoryIds.add("4");
        }
        if (rbLayoutOne.isChecked()) {
            houseTypes.add("1");
        }
        if (rbLayoutTwo.isChecked()) {
            houseTypes.add("2");
        }
        if (rbLayoutThree.isChecked()) {
            houseTypes.add("3");
        }
        if (rbLayoutFour.isChecked()) {
            houseTypes.add("4");
        }
        if (rbLayoutFive.isChecked()) {
            houseTypes.add("5");
        }
        if (rbLayoutFiveMore.isChecked()) {
            houseTypes.add("6");
        }
        if (rbTypeHigh.isChecked()) {
            propertyTypes.add("1");
        }
        if (rbTypeMulty.isChecked()) {
            propertyTypes.add("2");
        }
        if (rbTypeStack.isChecked()) {
            propertyTypes.add("3");
        }
        if (rbTypeHouse.isChecked()) {
            propertyTypes.add("4");
        }
        if (rbTypeApart.isChecked()) {
            propertyTypes.add("5");
        }
        if (rbTypeOffice.isChecked()) {
            propertyTypes.add("6");
        }
        if (rbTypeShop.isChecked()) {
            propertyTypes.add("7");
        }
        if (rbCustomerStatusReportToBeVerify.isChecked()) {
            statuses.add("1");
        }
        if (rbCustomerStatusReportEffect.isChecked()) {
            statuses.add("3");
        }
        if (rbCustomerStatusVisitToBeVerify.isChecked()) {
            statuses.add("4");
        }
        if (rbCustomerStatusVisit.isChecked()) {
            statuses.add("5");
        }
        if (rbCustomerStatusForm.isChecked()) {
            statuses.add("6");
        }
        if (rbCustomerStatusDeal.isChecked()) {
            statuses.add("7");
        }
        if (rbCustomerStatusDone.isChecked()) {
            statuses.add("8");
        }
        if (cbCustomerEffectHasOverdue.isChecked()) {
            isDisables.add("1");
        }
        if (cbCustomerEffectNotOverdue.isChecked()) {
            isDisables.add("0");
        }

        categoryId = ListUtils.sortJoin(categoryIds);
        houseType = ListUtils.sortJoin(houseTypes);
        propertyType = ListUtils.sortJoin(propertyTypes);
        status = ListUtils.sortJoin(statuses);
        isDisable = ListUtils.sortJoin(isDisables);

        ClientFilterBean bean = new ClientFilterBean();
        bean.setCategoryId(categoryId);
        bean.setHouseType(houseType);
        bean.setPropertyType(propertyType);
        bean.setStatus(status);
        bean.setIsDisable(isDisable);
        
        Intent result = new Intent();
        result.putExtra(IntentKey.INTENT_KEY_CLIENT_FILTER_ITEM, bean);
        finishActivity(result);
    }

}
