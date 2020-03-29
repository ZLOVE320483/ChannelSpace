package com.zlove.frag.independent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zlove.adapter.independent.AreaGridAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.CommonGridView;
import com.zlove.bean.city.AreaGridBean;
import com.zlove.bean.city.AreaListBean;
import com.zlove.bean.city.AreaListData;
import com.zlove.bean.city.AreaListItem;
import com.zlove.bean.project.ProjectFilterBean;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.CityHttpRequest;


public class IndependentProjectFilterFragment extends BaseFragment implements OnClickListener, OnItemClickListener {
	
    private AreaGridAdapter adapter;
    private CommonGridView gridView;
    List<AreaGridBean> areas = new ArrayList<AreaGridBean>();
    List<AreaListItem> areaList = new ArrayList<AreaListItem>();

    private RadioButton rbLayoutAll;
    private RadioButton rbLayoutOne;
    private RadioButton rbLayoutTwo;
    private RadioButton rbLayoutThree;
    private RadioButton rbLayoutFour;
    private RadioButton rbLayoutFive;
    private RadioButton rbLayoutFiveMore;

    private RadioButton rbTypeAll;
    private RadioButton rbTypeHigh;
    private RadioButton rbTypeMulty;
    private RadioButton rbTypeStack;
    private RadioButton rbTypeHouse;
    private RadioButton rbTypeApart;
    private RadioButton rbTypeOffice;
    private RadioButton rbTypeShop;

	private Button btnConfirm;
	
	private String cityId = "";
	private Dialog loadDialog;
	
	// 区域
	private String areaId = "";
	// 户型
	private String houseType = "";
	// 类型
	private String propertyId = "";
	
	
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_project_filter;
    }

    @Override
    protected void setUpView(View view) {
    	Intent intent = getActivity().getIntent();
    	if (intent != null) {
			if (intent.hasExtra(IntentKey.INTENT_KEY_CURRENT_CITY_ID)) {
				cityId = intent.getStringExtra(IntentKey.INTENT_KEY_CURRENT_CITY_ID);
			}
			if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_FILTER_ITEM)) {
				ProjectFilterBean bean = (ProjectFilterBean) intent.getSerializableExtra(IntentKey.INTENT_KEY_PROJECT_FILTER_ITEM);
				areaId = bean.getAreaId();
				houseType = bean.getHouseType();
				propertyId = bean.getPropertyType();
			}
		}
    	
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("楼盘筛选");
        
        loadDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        
        gridView = (CommonGridView) view.findViewById(R.id.gridview);
        adapter = new AreaGridAdapter(getActivity(), areas);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        
        rbLayoutAll = (RadioButton) view.findViewById(R.id.rb_project_layout_all);
        rbLayoutOne = (RadioButton) view.findViewById(R.id.rb_project_layout_one);
        rbLayoutTwo = (RadioButton) view.findViewById(R.id.rb_project_layout_two);
        rbLayoutThree = (RadioButton) view.findViewById(R.id.rb_project_layout_three);
        rbLayoutFour = (RadioButton) view.findViewById(R.id.rb_project_layout_four);
        rbLayoutFive = (RadioButton) view.findViewById(R.id.rb_project_layout_five);
        rbLayoutFiveMore = (RadioButton) view.findViewById(R.id.rb_project_layout_five_more);
        
        rbTypeAll = (RadioButton) view.findViewById(R.id.rb_house_type_all);
        rbTypeHigh = (RadioButton) view.findViewById(R.id.rb_house_type_high);
        rbTypeMulty = (RadioButton) view.findViewById(R.id.rb_house_type_multy);
        rbTypeStack = (RadioButton) view.findViewById(R.id.rb_house_type_stack);
        rbTypeHouse = (RadioButton) view.findViewById(R.id.rb_house_type_house);
        rbTypeApart = (RadioButton) view.findViewById(R.id.rb_house_type_apart);
        rbTypeOffice = (RadioButton) view.findViewById(R.id.rb_house_type_office);
        rbTypeShop = (RadioButton) view.findViewById(R.id.rb_house_type_shop);
        
        btnConfirm = (Button) view.findViewById(R.id.id_confirm);
        
        rbLayoutAll.setChecked(houseType.equals(""));
        rbLayoutOne.setChecked(houseType.equals("1"));
        rbLayoutTwo.setChecked(houseType.equals("2"));
        rbLayoutThree.setChecked(houseType.equals("3"));
        rbLayoutFour.setChecked(houseType.equals("4"));
        rbLayoutFive.setChecked(houseType.equals("5"));
        rbLayoutFiveMore.setChecked(houseType.equals("6"));
        
        rbTypeAll.setChecked(propertyId.equals(""));
        rbTypeHigh.setChecked(propertyId.equals("1"));
        rbTypeMulty.setChecked(propertyId.equals("2"));
        rbTypeStack.setChecked(propertyId.equals("3"));
        rbTypeHouse.setChecked(propertyId.equals("4"));
        rbTypeApart.setChecked(propertyId.equals("5"));
        rbTypeOffice.setChecked(propertyId.equals("6"));
        rbTypeShop.setChecked(propertyId.equals("7"));
        
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
        
        btnConfirm.setOnClickListener(this);
    }

	@Override
	public void onClick(View view) {
		if (view == rbLayoutAll) {
			houseType = "";
            rbLayoutAll.setChecked(true);
            rbLayoutOne.setChecked(false);
            rbLayoutTwo.setChecked(false);
            rbLayoutThree.setChecked(false);
            rbLayoutFour.setChecked(false);
            rbLayoutFive.setChecked(false);
            rbLayoutFiveMore.setChecked(false);
        } else if (view == rbLayoutOne) {
        	houseType = "1";
            rbLayoutAll.setChecked(false);
            rbLayoutOne.setChecked(true);
            rbLayoutTwo.setChecked(false);
            rbLayoutThree.setChecked(false);
            rbLayoutFour.setChecked(false);
            rbLayoutFive.setChecked(false);
            rbLayoutFiveMore.setChecked(false);
        } else if (view == rbLayoutTwo) {
        	houseType = "2";
            rbLayoutAll.setChecked(false);
            rbLayoutOne.setChecked(false);
            rbLayoutTwo.setChecked(true);
            rbLayoutThree.setChecked(false);
            rbLayoutFour.setChecked(false);
            rbLayoutFive.setChecked(false);
            rbLayoutFiveMore.setChecked(false);
        } else if (view == rbLayoutThree) {
        	houseType = "3";
            rbLayoutAll.setChecked(false);
            rbLayoutOne.setChecked(false);
            rbLayoutTwo.setChecked(false);
            rbLayoutThree.setChecked(true);
            rbLayoutFour.setChecked(false);
            rbLayoutFive.setChecked(false);
            rbLayoutFiveMore.setChecked(false);
        } else if (view == rbLayoutFour) {
        	houseType = "4";
            rbLayoutAll.setChecked(false);
            rbLayoutOne.setChecked(false);
            rbLayoutTwo.setChecked(false);
            rbLayoutThree.setChecked(false);
            rbLayoutFour.setChecked(true);
            rbLayoutFive.setChecked(false);
            rbLayoutFiveMore.setChecked(false);
        } else if (view ==rbLayoutFive) {
        	houseType = "5";
            rbLayoutAll.setChecked(false);
            rbLayoutOne.setChecked(false);
            rbLayoutTwo.setChecked(false);
            rbLayoutThree.setChecked(false);
            rbLayoutFour.setChecked(false);
            rbLayoutFive.setChecked(true);
            rbLayoutFiveMore.setChecked(false);
        } else if (view == rbLayoutFiveMore) {
        	houseType = "6";
            rbLayoutAll.setChecked(false);
            rbLayoutOne.setChecked(false);
            rbLayoutTwo.setChecked(false);
            rbLayoutThree.setChecked(false);
            rbLayoutFour.setChecked(false);
            rbLayoutFive.setChecked(false);
            rbLayoutFiveMore.setChecked(true);
        } else if (view == rbTypeAll) {
        	propertyId = "";
            rbTypeAll.setChecked(true);
            rbTypeHigh.setChecked(false);
            rbTypeMulty.setChecked(false);
            rbTypeStack.setChecked(false);
            rbTypeHouse.setChecked(false);
            rbTypeApart.setChecked(false);
            rbTypeOffice.setChecked(false);
            rbTypeShop.setChecked(false);
        } else if (view == rbTypeHigh) {
        	propertyId = "1";
            rbTypeAll.setChecked(false);
            rbTypeHigh.setChecked(true);
            rbTypeMulty.setChecked(false);
            rbTypeStack.setChecked(false);
            rbTypeHouse.setChecked(false);
            rbTypeApart.setChecked(false);
            rbTypeOffice.setChecked(false);
            rbTypeShop.setChecked(false);
        } else if (view == rbTypeMulty) {
        	propertyId = "2";
            rbTypeAll.setChecked(false);
            rbTypeHigh.setChecked(false);
            rbTypeMulty.setChecked(true);
            rbTypeStack.setChecked(false);
            rbTypeHouse.setChecked(false);
            rbTypeApart.setChecked(false);
            rbTypeOffice.setChecked(false);
            rbTypeShop.setChecked(false);
        } else if (view == rbTypeStack) {
        	propertyId = "3";
            rbTypeAll.setChecked(false);
            rbTypeHigh.setChecked(false);
            rbTypeMulty.setChecked(false);
            rbTypeStack.setChecked(true);
            rbTypeHouse.setChecked(false);
            rbTypeApart.setChecked(false);
            rbTypeOffice.setChecked(false);
            rbTypeShop.setChecked(false);
        } else if (view == rbTypeHouse) {
        	propertyId = "4";
            rbTypeAll.setChecked(false);
            rbTypeHigh.setChecked(false);
            rbTypeMulty.setChecked(false);
            rbTypeStack.setChecked(false);
            rbTypeHouse.setChecked(true);
            rbTypeApart.setChecked(false);
            rbTypeOffice.setChecked(false);
            rbTypeShop.setChecked(false);
        } else if (view == rbTypeApart) {
        	propertyId = "5";
            rbTypeAll.setChecked(false);
            rbTypeHigh.setChecked(false);
            rbTypeMulty.setChecked(false);
            rbTypeStack.setChecked(false);
            rbTypeHouse.setChecked(false);
            rbTypeApart.setChecked(true);
            rbTypeOffice.setChecked(false);
            rbTypeShop.setChecked(false);
        } else if (view == rbTypeOffice) {
        	propertyId = "6";
            rbTypeAll.setChecked(false);
            rbTypeHigh.setChecked(false);
            rbTypeMulty.setChecked(false);
            rbTypeStack.setChecked(false);
            rbTypeHouse.setChecked(false);
            rbTypeApart.setChecked(false);
            rbTypeOffice.setChecked(true);
            rbTypeShop.setChecked(false);
        } else if (view == rbTypeShop) {
        	propertyId = "7";
            rbTypeAll.setChecked(false);
            rbTypeHigh.setChecked(false);
            rbTypeMulty.setChecked(false);
            rbTypeStack.setChecked(false);
            rbTypeHouse.setChecked(false);
            rbTypeApart.setChecked(false);
            rbTypeOffice.setChecked(false);
            rbTypeShop.setChecked(true);
        } else if (view == btnConfirm) {
			onConfirmClick();
		}
	}
	
	private void onConfirmClick() {
		ProjectFilterBean bean = new ProjectFilterBean();
		bean.setAreaId(areaId);
		bean.setHouseType(houseType);
		bean.setPropertyType(propertyId);
		Intent data = new Intent();
		data.putExtra(IntentKey.INTENT_KEY_PROJECT_FILTER_ITEM, bean);
		finishActivity(data);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (ListUtils.isEmpty(areas)) {
		    CityHttpRequest.getAreaList(cityId, new GetAreaListHandler(this));
        }
	}
	
	class GetAreaListHandler extends HttpResponseHandlerFragment<IndependentProjectFilterFragment> {

		public GetAreaListHandler(IndependentProjectFilterFragment context) {
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
				AreaListItem areaItem = new AreaListItem();
				areaItem.setId("");
				areaItem.setName("全部");
				AreaGridBean item = new AreaGridBean();
				item.setItem(areaItem);
				if (areaId.equals("")) {
					item.setSelect(true);
				} else {
					item.setSelect(false);
				}
				areas.add(item);
				if (!ListUtils.isEmpty(areaList)) {
					for(AreaListItem areaListItem : areaList) {
						AreaGridBean gridBean = new AreaGridBean();
						gridBean.setItem(areaListItem);
						if (areaId.equals(areaListItem.getId())) {
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (ListUtils.isEmpty(areas)) {
			return;
		}
		for(int i = 0; i < areas.size(); i++) {
			AreaGridBean bean = adapter.getItem(i);
			if (i == position) {
				areaId = bean.getItem().getId();
				bean.setSelect(true);
			} else {
				bean.setSelect(false);
			}
		}
		adapter.notifyDataSetChanged();
	}

}
