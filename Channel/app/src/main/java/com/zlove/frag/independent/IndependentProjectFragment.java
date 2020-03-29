package com.zlove.frag.independent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.zlove.act.independent.ActIndependentCityList;
import com.zlove.act.independent.ActIndependentCustomerAdd;
import com.zlove.act.independent.ActIndependentLogin;
import com.zlove.act.independent.ActIndependentProjectAdd;
import com.zlove.act.independent.ActIndependentProjectDetail;
import com.zlove.act.independent.ActProjectCustomerAdd;
import com.zlove.act.independent.ActProjectFilter;
import com.zlove.act.independent.ActProjectSearch;
import com.zlove.adapter.independent.ProjectAdapter;
import com.zlove.adapter.independent.ProjectAdapter.ProjectAddCustomerDelegate;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.DialogManager.ProjectAddCustomerListener;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.CommonConfirmDialog;
import com.zlove.base.widget.CommonConfirmDialog.ConfirmListener;
import com.zlove.base.widget.CommonDialog;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.city.CombineCityListBean;
import com.zlove.bean.city.CombineCityListData;
import com.zlove.bean.city.CombineCityListItem;
import com.zlove.bean.common.CityInfo;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.project.ProjectFilterBean;
import com.zlove.bean.project.ProjectItemBean;
import com.zlove.bean.project.ProjectItemData;
import com.zlove.bean.project.ProjectItemHouseList;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.CityHttpRequest;
import com.zlove.http.ClientHttpRequest;
import com.zlove.http.ProjectHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class IndependentProjectFragment extends BaseFragment implements
		OnClickListener, OnItemClickListener, ProjectAddCustomerListener,
		ProjectAddCustomerDelegate, AMapLocationListener, PullableViewListener {

	private LocationManagerProxy mLocationManagerProxy;
	private Dialog loadingDialog;
	private Dialog reqDialog;
	boolean isFirst = true;

	private TextView tvTitle = null;
	private TextView etSearch = null;

	private View btnAddProject = null;
	private View addView;
	private Button btnClassify = null;
	private PullListView listView = null;
	private ProjectAdapter adapter;
	private List<ProjectItemHouseList> projectInfos = new ArrayList<ProjectItemHouseList>();

	private String projectName = "";
	private String projectId = "";
	private String clientId = "";
    private String saleManName = "";
	private String saleManId = "";
	private int pageIndex = 0;

	// --------筛选条件－－－－－
	private String name = "";
	private String houseType = "";
	private String propertyType = "";
	private String areaId = "";

	// --------城市------
	private String cityName = "";
	private String defaultCityId = "1602";
	private ArrayList<CombineCityListItem> cityList = new ArrayList<CombineCityListItem>();

	/**
	 * 初始化定位
	 */
	private void init() {
		loadingDialog = DialogManager
				.getLoadingDialog(getActivity(), "正在定位...");
		if (!loadingDialog.isShowing() && isFirst) {
			loadingDialog.show();
			isFirst = false;
		}
		// 初始化定位，只采用网络定位
		mLocationManagerProxy = LocationManagerProxy.getInstance(getActivity());
		mLocationManagerProxy.setGpsEnable(false);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次,
		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);

	}

	@Override
	protected int getInflateLayout() {
		return R.layout.frag_independent_project;
	}

	@Override
	protected void setUpView(View view) {
		defaultCityId = ChannelCookie.getInstance().getCurrentCityId();
		cityName = ChannelCookie.getInstance().getCurrentCityName();
		if (TextUtils.isEmpty(defaultCityId)) {
			init();
		} else {
			CityHttpRequest.getCombineCityList(new GetCombineCityList(this));
		}
		tvTitle = (TextView) view.findViewById(R.id.id_title);
		tvTitle.setText("楼盘");
		if (!TextUtils.isEmpty(cityName)) {
            tvTitle.setText("楼盘(" + cityName + ")");
		}

		etSearch = (TextView) view.findViewById(R.id.id_search);
		etSearch.setText("搜楼盘");
		etSearch.setOnClickListener(this);

		btnAddProject = view.findViewById(R.id.id_confirm);
		addView = view.findViewById(R.id.id_add);
		btnClassify = (Button) view.findViewById(R.id.id_classify);
		listView = (PullListView) view.findViewById(R.id.id_listview);

		if (reqDialog == null) {
			reqDialog = DialogManager.getLoadingDialog(getActivity(),
					"正在加载数据...");
		}

		if (adapter == null) {
			adapter = new ProjectAdapter(getActivity(), projectInfos, this);
		}
		listView.setAdapter(adapter);

		tvTitle.setOnClickListener(this);

		btnAddProject.setOnClickListener(this);
		addView.setOnClickListener(this);
		btnClassify.setOnClickListener(this);

		listView.setOnItemClickListener(this);
		listView.setPullableViewListener(this);
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(true);
	}

	CommonDialog dialog;
    @Override
    public void onClick(View v) {
        if (v == btnAddProject) {
			if (ChannelCookie.getInstance().isLoginPass()) {
				Intent intent = new Intent(getActivity(), ActIndependentCustomerAdd.class);
				startActivityForResult(intent, IntentKey.REQUEST_CODE_ADD_CUSTOMER);
			} else {
				if (dialog == null) {
					dialog = new CommonDialog(getActivity(), new CommonDialog.ConfirmAction() {
						@Override
						public void confirm() {
							Intent intent = new Intent(getActivity(), ActIndependentLogin.class);
							startActivity(intent);
						}
					}, "温馨提示", "请先登录,再进行操作", "去登录");
				}
				dialog.showdialog();
			}
        } else if (v == btnClassify) {
            Intent intent = new Intent(getActivity(), ActProjectFilter.class);
            intent.putExtra(IntentKey.INTENT_KEY_CURRENT_CITY_ID, defaultCityId);
            ProjectFilterBean bean = new ProjectFilterBean();
    		bean.setAreaId(areaId);
    		bean.setHouseType(houseType);
    		bean.setPropertyType(propertyType);
    		intent.putExtra(IntentKey.INTENT_KEY_PROJECT_FILTER_ITEM, bean);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_PROJECT_FILTER);
        } else if (v == tvTitle) {
            Intent intent = new Intent(getActivity(), ActIndependentCityList.class);
            intent.putExtra(IntentKey.INTENT_KEY_CURRENT_CITY_ID, defaultCityId);
            intent.putExtra(IntentKey.INTENT_KEY_CURRENT_CITY_NAME, cityName);
            intent.putExtra(IntentKey.INTENT_KEY_COMBINE_CITY_LIST, cityList);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_CITY);
        } else if (v == etSearch) {
            Intent intent = new Intent(getActivity(), ActProjectSearch.class);
            intent.putExtra(IntentKey.INTENT_KEY_CURRENT_CITY_ID, defaultCityId);
            startActivity(intent);
        } else if (v == addView) {
			Intent intent = new Intent(getActivity(), ActIndependentProjectAdd.class);
			startActivityForResult(intent, IntentKey.REQUEST_CODE_PROJECT_SEARCH_BY_CODE);
		}
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    if (ListUtils.isEmpty(projectInfos)) {
            return;
        }
		Intent intent = new Intent(getActivity(),ActIndependentProjectDetail.class);
		ProjectItemHouseList data = projectInfos.get(arg2 - 1);
		if (data != null) {
			intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, data.getHouse_id());
		}
		startActivity(intent);
	}

    @Override
    public void selectAddCustomer(int pos) {
        if (pos == 0) {
            Intent intent = new Intent(getActivity(), ActIndependentCustomerAdd.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_NAME, projectName);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
            startActivity(intent);
        } else if (pos == 1) {
            Intent intent = new Intent(getActivity(), ActProjectCustomerAdd.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_SELECT_CUSTOMER);
        }
    }

    class GetProjectListHandler extends HttpResponseHandlerFragment<IndependentProjectFragment> {

		private LOAD_ACTION action;

		public GetProjectListHandler(IndependentProjectFragment context,
				LOAD_ACTION action) {
			super(context);
			this.action = action;
		}

		@Override
		public void onStart() {
			super.onStart();
			if (reqDialog != null && !reqDialog.isShowing() && isFirst) {
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
				ProjectItemBean bean = JsonUtil.toObject(new String(content),
						ProjectItemBean.class);
				if (bean != null) {
					if (bean.getStatus() == 200) {
						ProjectItemData data = bean.getData();
						if (data != null) {
							CommonPageInfo info = data.getPage_info();
							pageIndex = info.getPage_index();
							if (action == LOAD_ACTION.ONREFRESH) {
								projectInfos.clear();
							}
							List<ProjectItemHouseList> tmpList = data
									.getHouse_list();
							if (tmpList.size() < 10) {
								listView.setPullLoadEnable(false);
							} else {
								listView.setPullLoadEnable(true);
							}
							projectInfos.addAll(tmpList);
							adapter.notifyDataSetChanged();
						}

					} else {
						showShortToast(bean.getMessage());
					}
				}
				if (ListUtils.isEmpty(projectInfos)) {
					showShortToast("暂无楼盘信息");
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
			if (reqDialog != null && reqDialog.isShowing()) {
				reqDialog.dismiss();
			}
			listView.stopLoadMore();
			listView.stopRefresh();
		}

	}

	@Override
	public void addCustomerAction(String projectName, String projectId) {
		this.projectName = projectName;
		this.projectId = projectId;
		DialogManager.showProjectAddCustomerDialog(getActivity(), this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IntentKey.REQUEST_CODE_SELECT_CUSTOMER && data != null) {
				clientId = data.getStringExtra(IntentKey.INTENT_KEY_CLIENT_ID);
                saleManId = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_ID);
                saleManName = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_NAME);
                ClientHttpRequest.recommendClientById(clientId, projectId, saleManId, new RecommendClientByIdHandler(this));
            } else if (requestCode == IntentKey.REQUEST_CODE_PROJECT_SEARCH_BY_CODE) {
                if (data != null) {
                    ProjectItemHouseList item = (ProjectItemHouseList) data.getExtras().get(IntentKey.INTENT_KEY_PROJECT_ITEM_SEARCH_BY_CODE);
                    if (item != null) {
                        projectInfos.clear();
                        projectInfos.add(item);
                        adapter.notifyDataSetChanged();
                        showShortToast("添加成功,下拉可查看所有楼盘信息");
					}
				}
			} else if (requestCode == IntentKey.REQUEST_CODE_SELECT_CITY) {
			    areaId = "";
			    houseType = "";
			    propertyType = "";
			    if (data != null) {
			        CityInfo item = (CityInfo) data.getExtras().get(IntentKey.INTENT_KEY_COMBINE_CITY_ITEM);
			        if (item != null) {
                        defaultCityId = item.getCityId();
                        ChannelCookie.getInstance().saveUserCurrentCityId(defaultCityId);
                        cityName = item.getName();
                        ChannelCookie.getInstance().saveUserCurrentCityName(cityName);
                        tvTitle.setText("楼盘(" + cityName + ")");
                        requestProjectList();
                    }
			    }
            } else if (requestCode == IntentKey.REQUEST_CODE_PROJECT_FILTER) {
				if (data != null) {
					ProjectFilterBean bean = (ProjectFilterBean) data.getExtras().get(IntentKey.INTENT_KEY_PROJECT_FILTER_ITEM);
					if (bean != null) {
						areaId = bean.getAreaId();
						houseType = bean.getHouseType();
						propertyType = bean.getPropertyType();
						requestProjectList();
					}
				}
			}
		}
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		String currentCityName = amapLocation.getCity();
		if (!TextUtils.isEmpty(currentCityName)) {
			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
				showShortToast("定位成功");
				cityName = currentCityName;
				ChannelCookie.getInstance().saveUserCurrentCityName(cityName);
			}
		} else {
			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
				showShortToast("很抱歉,定位失败,请检查您的网络");
				defaultCityId = "1602";
				ChannelCookie.getInstance().saveUserCurrentCityId(defaultCityId);
				cityName = "苏州市";
				ChannelCookie.getInstance().saveUserCurrentCityName(cityName);
				requestProjectList();
			}
		}
        tvTitle.setText("楼盘(" + cityName + ")");
		CityHttpRequest.getCombineCityList(new GetCombineCityList(this));
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mLocationManagerProxy != null) {
			// 移除定位请求
			mLocationManagerProxy.removeUpdates(this);
			// 销毁定位
			mLocationManagerProxy.destroy();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if ((ListUtils.isEmpty(projectInfos) || projectInfos.size() <= 1) && !TextUtils.isEmpty(defaultCityId)) {
			requestProjectList();
		}
		if (projectInfos.size() < 10) {
			listView.setPullLoadEnable(false);
		} else {
			listView.setPullLoadEnable(true);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onRefresh() {
	    areaId = "";
	    houseType = "";
	    propertyType = "";
	    requestProjectList();
	}

	@Override
	public void onLoadMore() {
		ProjectHttpRequest.requestProjectList(name, defaultCityId,
				houseType, propertyType, "", "", String.valueOf(pageIndex), "10", areaId,
				new GetProjectListHandler(this, LOAD_ACTION.LOADERMORE));
	}

    class GetCombineCityList extends HttpResponseHandlerFragment<IndependentProjectFragment> {

		public GetCombineCityList(IndependentProjectFragment context) {
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
                CombineCityListBean bean = JsonUtil.toObject(new String(content), CombineCityListBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        CombineCityListData data = bean.getData();
                        if (data != null) {
                            List<CombineCityListItem> tmpList = data.getCombine_city_list();
                            if (!ListUtils.isEmpty(tmpList)) {
                                cityList.clear();
                                cityList.addAll(tmpList);
                            } else {
                                showShortToast("暂无合作城市");
                            }
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }

				}
                int i = 0;
				for (; i < cityList.size(); i++) {
					CombineCityListItem item = cityList.get(i);
					if (item.getCity_name().contains(cityName)) {
						defaultCityId = item.getCity_id();
						ChannelCookie.getInstance().saveUserCurrentCityId(defaultCityId);
						break;
					}
				}
				if (i == cityList.size()) {
                    showShortToast("当前城市暂无楼盘信息,您可以切换到合作城市查看楼盘信息");
                    return;
                }
                ChannelCookie.getInstance().saveUserCurrentCityId(defaultCityId);
				requestProjectList();
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
    
    class RecommendClientByIdHandler extends HttpResponseHandlerFragment<IndependentProjectFragment> {

        public RecommendClientByIdHandler(IndependentProjectFragment context) {
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
                        CommonConfirmDialog dialog = new CommonConfirmDialog(getActivity(), "业务员:" + saleManName + " 向您反馈客户是否有效", new ConfirmListener() {
                            
                            @Override
                                public void confirm() {
                                }
                            });
                        dialog.showdialog();
                    } else {
                        showShortToast(bean.getMessage());
                    }
                } else {
                    showShortToast("报备失败,请重试");
                }
            } else {
                showShortToast("报备失败,请重试");
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
	
	private void requestProjectList() {
        ProjectHttpRequest.requestProjectList(name, defaultCityId, houseType, propertyType, "", "", "0", "10", areaId, new GetProjectListHandler(
            IndependentProjectFragment.this, LOAD_ACTION.ONREFRESH));
    }
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (outState != null) {
			outState.putString("defaultCityId", defaultCityId);
			outState.putString("cityName", cityName);
		}
	}
	
	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null) {
			defaultCityId = savedInstanceState.getString("defaultCityId");
			cityName = savedInstanceState.getString("cityName");
		}
	}
}
