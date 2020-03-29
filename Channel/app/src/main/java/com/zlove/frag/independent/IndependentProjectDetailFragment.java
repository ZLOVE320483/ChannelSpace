package com.zlove.frag.independent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.act.ActProjectLayoutDetail;
import com.zlove.act.independent.ActIndependentCustomerAdd;
import com.zlove.act.independent.ActIndependentCustomerDetail;
import com.zlove.act.independent.ActIndependentLogin;
import com.zlove.act.independent.ActIndependentProjectCooperateRule;
import com.zlove.act.independent.ActIndependentProjectDynamic;
import com.zlove.act.independent.ActIndependentProjectParameter;
import com.zlove.act.independent.ActProjectCustomerAdd;
import com.zlove.act.independent.ActProjectImageDetail;
import com.zlove.adapter.independent.HouseLayoutAdapter;
import com.zlove.adapter.independent.ProjectDetailCustomerAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.http.HttpResponseHandlerFragment.LOAD_ACTION;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.DialogManager.ProjectAddCustomerListener;
import com.zlove.base.util.GImageLoader;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.util.UIUtil;
import com.zlove.base.widget.CommonConfirmDialog;
import com.zlove.base.widget.CommonConfirmDialog.ConfirmListener;
import com.zlove.base.widget.CommonDialog;
import com.zlove.base.widget.CommonGridView;
import com.zlove.base.widget.NoScrollViewPager;
import com.zlove.base.widget.pulllistview.PullListView;
import com.zlove.base.widget.pulllistview.PullableViewListener;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.common.CommonPageInfo;
import com.zlove.bean.project.ProjectDetailBean;
import com.zlove.bean.project.ProjectDetailData;
import com.zlove.bean.project.ProjectDetailImgListItem;
import com.zlove.bean.project.ProjectDetailReportedCustomerBean;
import com.zlove.bean.project.ProjectDetailReportedCustomerData;
import com.zlove.bean.project.ProjectDetailReportedCustomerItem;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ClientHttpRequest;
import com.zlove.http.ProjectHttpRequest;
import com.zlove.navi.ActProjectPosition;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class IndependentProjectDetailFragment extends BaseFragment implements OnClickListener, ProjectAddCustomerListener, OnItemClickListener, PullableViewListener {
    
    private TextView tvNoData = null;
    private PullListView listView = null;
    private List<ProjectDetailReportedCustomerItem> infos = new ArrayList<ProjectDetailReportedCustomerItem>();
    private ProjectDetailCustomerAdapter customerAdapter = null;
    private View addCustomerView = null;
    private View hotLineView = null;
    
    private View headView = null;

    private NoScrollViewPager imgViewPager = null;
    private ImageView[] projectImgs;
    private List<ProjectDetailImgListItem> imgList;
    
    private CommonGridView gvHouseLayout;
    private HouseLayoutAdapter layoutAdapter;
    private ArrayList<ProjectDetailImgListItem> data = new ArrayList<ProjectDetailImgListItem>();
    
    private View projectDynamicView = null;
    private TextView tvProjectImgNum = null;
    private View tvProjectParameter = null;
    private TextView tvProjectName;
    private TextView tvProjectPrice;
    private TextView tvCooperateTime;
    private TextView tvProjectArea;
    private TextView tvProjectTotalPrice;
    private TextView tvProjectLayout;
    private TextView tvProjectType;
    private TextView tvProjectDiscount;
    
    private View projectPositionView = null;
    private TextView tvProjectAddress;

    private View projectCooperateRuleView = null;
    private TextView tvProjectRuleTitle;
    
    private Dialog loadingDialog;
    
    private String projectName = "";
    private String projectId = "";
    private String projectRuleId = "";
    private String saleManName = "";
    private String saleManId = "";
    private String clientId = "";
    private String projectLng = "";
    private String projectLat = "";
    private String hotLine = "";
    private int imgSize = 0;
    
    private ArrayList<String> effectUrlList = new ArrayList<String>();
    private ArrayList<String> modelUrlList = new ArrayList<String>();
    private ArrayList<String> layoutUrlList = new ArrayList<String>();
    
    private int pageIndex = 0;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_project_detail;
    }

    @Override
    protected void setUpView(View view) {
        if (getActivity().getIntent() != null) {
            Intent intent = getActivity().getIntent();
            if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_ID)) {
                projectId = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_ID);
            }
        }
        listView = (PullListView) view.findViewById(R.id.id_listview);
        listView.setPullableViewListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        listView.setHeaderDividersEnabled(false);
        listView.setFooterDividersEnabled(false);
        listView.setOnItemClickListener(this);
        loadingDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        addCustomerView = view.findViewById(R.id.add_customer_view);
        addCustomerView.setOnClickListener(this);
        hotLineView = view.findViewById(R.id.call);
        hotLineView.setOnClickListener(this);
        initHeadView();
        
        if (customerAdapter == null) {
            customerAdapter = new ProjectDetailCustomerAdapter(infos, getActivity());
        }
        listView.setAdapter(customerAdapter);
        
        imgViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                tvProjectImgNum.setText((arg0 + 1) + "/" + imgSize);
            }
            
        });
        
        ProjectHttpRequest.requestProjectDetail(projectId, new GetProjectDetailHandler(this));
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	if (ListUtils.isEmpty(infos) && ChannelCookie.getInstance().isLoginPass()) {
            ProjectHttpRequest.requestProjectCustomerReportedList(projectId, "0", "10", new GetReportedCustomerListHandler(this, LOAD_ACTION.ONREFRESH));
        }
    }
    
    @SuppressLint("InflateParams") 
    private void initHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.list_project_detail_head_view, null);
        setBackButton(headView.findViewById(R.id.id_back));
        
        imgViewPager = (NoScrollViewPager) headView.findViewById(R.id.id_viewpager);
        
        tvProjectImgNum = (TextView) headView.findViewById(R.id.id_project_detail_img_num);
        
        projectDynamicView = headView.findViewById(R.id.id_project_detail_sound);
        projectDynamicView.setOnClickListener(this);
        
        tvProjectParameter = headView.findViewById(R.id.id_project_parameter);
        tvProjectParameter.setOnClickListener(this);
        
        projectCooperateRuleView = headView.findViewById(R.id.id_cooperate_rule);
        projectCooperateRuleView.setOnClickListener(this);
        
        projectPositionView = headView.findViewById(R.id.id_project_address);
        projectPositionView.setOnClickListener(this);
        
        tvProjectName = (TextView) headView.findViewById(R.id.id_project_name);
        tvProjectPrice = (TextView) headView.findViewById(R.id.id_project_price);
        tvCooperateTime = (TextView) headView.findViewById(R.id.cooperate_time);
        
        gvHouseLayout = (CommonGridView) headView.findViewById(R.id.gv_house_layout);
        layoutAdapter = new HouseLayoutAdapter(getActivity(), data);
        gvHouseLayout.setAdapter(layoutAdapter);
        
        tvProjectArea = (TextView) headView.findViewById(R.id.id_project_area);
        tvProjectTotalPrice = (TextView) headView.findViewById(R.id.id_project_total_price);
        tvProjectLayout = (TextView) headView.findViewById(R.id.id_project_house_layout);
        tvProjectType = (TextView) headView.findViewById(R.id.id_project_product);
        
        tvProjectAddress = (TextView) headView.findViewById(R.id.tv_address);
        tvProjectDiscount = (TextView) headView.findViewById(R.id.tv_discount);
        tvProjectRuleTitle = (TextView) headView.findViewById(R.id.tv_rule_title);

        tvNoData = (TextView) headView.findViewById(R.id.tv_no_data);
        tvNoData.setVisibility(View.GONE);
        
        gvHouseLayout.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(), ActProjectLayoutDetail.class);
				intent.putExtra(IntentKey.INTENT_KEY_LAYOUT_URL_LIST, data);
				startActivity(intent);
			}
		});
        
        listView.addHeaderView(headView);
    }

    CommonDialog dialog;
    @Override
    public void onClick(View v) {
        if (v == tvProjectParameter) {
            Intent intent = new Intent(getActivity(), ActIndependentProjectParameter.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
            startActivity(intent);
        } else if (v == projectCooperateRuleView) {
            Intent intent = new Intent(getActivity(), ActIndependentProjectCooperateRule.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_RULE_ID, projectRuleId);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
            startActivity(intent);
		} else if (v == projectPositionView) {
            Intent naviStartIntent=new Intent(getActivity(),ActProjectPosition.class);
            naviStartIntent.putExtra(IntentKey.INTENT_KEY_PROJECT_LNG, projectLng);
            naviStartIntent.putExtra(IntentKey.INTENT_KEY_PROJECT_LAT, projectLat);
            startActivity(naviStartIntent);
		} else if (v == projectDynamicView) {
            Intent intent = new Intent(getActivity(), ActIndependentProjectDynamic.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_ID, projectId);
            startActivity(intent);
        } else if (v == addCustomerView) {
            if (ChannelCookie.getInstance().isLoginPass()) {
                DialogManager.showProjectAddCustomerDialog(getActivity(), this);
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
        } else if (v == hotLineView) {
        	if (TextUtils.isEmpty(hotLine)) {
				showShortToast("暂未提供热线");
				return;
			}
            Uri uri = Uri.parse("tel:" + hotLine);   
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);     
            startActivity(intent); 
		}
    }
    
    private void classifyImgUrls(List<ProjectDetailImgListItem> imgUrlList) {
        for (ProjectDetailImgListItem item : imgUrlList) {
            if (item.getType().equals("0")) {
                effectUrlList.add(item.getImage());
            } else if (item.getType().equals("1")) {
                modelUrlList.add(item.getImage());
            } else if (item.getType().equals("2")) {
                layoutUrlList.add(item.getImage());
                data.add(item);
            }
        }
        layoutAdapter.notifyDataSetChanged();
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
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IntentKey.REQUEST_CODE_SELECT_CUSTOMER && data != null) {
                clientId = data.getStringExtra(IntentKey.INTENT_KEY_CLIENT_ID);
                saleManId = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_ID);
                saleManName = data.getStringExtra(IntentKey.INTENT_KEY_SALEMAN_NAME);
                ClientHttpRequest.recommendClientById(clientId, projectId, saleManId, new RecommendClientByIdHandler(this));
            }
        }
    }
    
    class GetProjectDetailHandler extends HttpResponseHandlerFragment<IndependentProjectDetailFragment> {

        public GetProjectDetailHandler(IndependentProjectDetailFragment context) {
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
                ProjectDetailBean bean = JsonUtil.toObject(new String(content), ProjectDetailBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ProjectDetailData data = bean.getData();
                        if (data != null) {
                            projectLat = data.getLat();
                            projectLng = data.getLng();
                            projectName = data.getName();
                            projectRuleId = data.getHouse_rule_id();
                            hotLine = data.getSales_phone();
                            
                            tvProjectName.setText(UIUtil.replaceNullOrEmpty(data.getName()));
                            tvProjectPrice.setText(UIUtil.replaceNullOrEmpty(data.getPrice_desc()));
                            tvCooperateTime.setText("合作时间:" + UIUtil.replaceNullOrEmpty(data.getCooperate_time()));
                            tvProjectArea.setText(UIUtil.replaceNullOrEmpty(data.getLocation_area()));
                            tvProjectTotalPrice.setText(UIUtil.replaceNullOrEmpty(data.getPrice()));
                            tvProjectLayout.setText(UIUtil.replaceNullOrEmpty(data.getHouse_types()));
                            tvProjectType.setText(UIUtil.replaceNullOrEmpty(data.getProperty_types()));
                            tvProjectAddress.setText(UIUtil.replaceNullOrEmpty(data.getAddress()));
                            tvProjectRuleTitle.setText(UIUtil.replaceNullOrEmpty(data.getHouse_rule()));
                            tvProjectDiscount.setText(UIUtil.replaceNullOrEmpty(data.getDiscount_desc()));
                            
                            imgList = data.getHouse_images();
                            if (!ListUtils.isEmpty(imgList)) {
                                classifyImgUrls(imgList);
                                imgSize = imgList.size();
                                tvProjectImgNum.setText("1/" + imgSize);
                                if (imgSize <= 1) {
                                    imgViewPager.setEnableScroll(false);
                                } else {
                                    imgViewPager.setEnableScroll(true);
                                }
                                projectImgs = new ImageView[imgSize];
                                for(int i = 0; i < projectImgs.length; i++){
                                    ImageView imageView = new ImageView(getActivity());
                                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                    projectImgs[i] = imageView;
                                    GImageLoader.getInstance().getImageLoader().displayImage(imgList.get(i).getImage(),imageView, GImageLoader.getInstance().getNormalOptions());
                                }
                                imgViewPager.setAdapter(new ProjectImgViewPageAdapter());
                            } else {
                                tvProjectImgNum.setText("0");
                                imgViewPager.setEnableScroll(false);
                            }
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
            }
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
            if (loadingDialog != null && loadingDialog.isShowing()) {
            	loadingDialog.dismiss();
            }
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }
        
    }
    
    class GetReportedCustomerListHandler extends HttpResponseHandlerFragment<IndependentProjectDetailFragment> {

    	private LOAD_ACTION action;
    	
        public GetReportedCustomerListHandler(IndependentProjectDetailFragment context, LOAD_ACTION action) {
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
            ProjectDetailReportedCustomerBean bean = JsonUtil.toObject(new String(content), ProjectDetailReportedCustomerBean.class);
            if (bean != null) {
                if (bean.getStatus() == 200) {
                    ProjectDetailReportedCustomerData data = bean.getData();
                    if (data != null) {
                    	CommonPageInfo pageInfo = data.getPage_info();
                    	if (pageInfo != null) {
							pageIndex = pageInfo.getPage_index();
						}
                    	
                        List<ProjectDetailReportedCustomerItem> tmpList = data.getRecommended_list();
                        if (tmpList.size() < 10) {
                            listView.setPullLoadEnable(false);
                        } else {
                            listView.setPullLoadEnable(true);
                        }

                        if (action == LOAD_ACTION.ONREFRESH) {
                            infos.clear();
                        }

                        tvNoData.setVisibility(View.GONE);
                        infos.addAll(tmpList);
                        customerAdapter.notifyDataSetChanged();
                    }
                    if (ListUtils.isEmpty(infos)) {
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText("您还未给该楼盘报备客户哦");
					}
                } else {
                    showShortToast(bean.getMessage());
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
    
    class RecommendClientByIdHandler extends HttpResponseHandlerFragment<IndependentProjectDetailFragment> {

        public RecommendClientByIdHandler(IndependentProjectDetailFragment context) {
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
                        CommonConfirmDialog dialog = new CommonConfirmDialog(getActivity(), "业务员:" + saleManName + " 向您反馈客户是否有效",
                            new ConfirmListener() {

                                @Override
                                public void confirm() {
                                    ProjectHttpRequest.requestProjectCustomerReportedList(projectId, "0", "10", new GetReportedCustomerListHandler(getFragment(), LOAD_ACTION.ONREFRESH));
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
    
    class ProjectImgViewPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgSize;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
          //对ViewPager页号求模取出View列表中要显示的项
            if (projectImgs.length > 0) {
                ImageView view = projectImgs[position];
                
                view.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActProjectImageDetail.class);
                        intent.putExtra(IntentKey.INTENT_KEY_EFFECT_URL_LIST, effectUrlList);
                        intent.putExtra(IntentKey.INTENT_KEY_MODEL_URL_LIST, modelUrlList);
                        intent.putExtra(IntentKey.INTENT_KEY_LAYOUT_URL_LIST, layoutUrlList);
                        startActivity(intent);
                    }
                });
                
                ViewParent vp = view.getParent();
                if (vp != null){
                    ViewGroup parent = (ViewGroup)vp;
                    parent.removeView(view);
                }
                container.addView(view);
                //add listeners here if necessary
                return view;
            }
            return null;
        }
        
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 < 2) {
			return;
		}
		ProjectDetailReportedCustomerItem item = infos.get(arg2 - 2);
		if (item == null) {
			return;
		}
		Intent intent = new Intent(getActivity(), ActIndependentCustomerDetail.class);
		intent.putExtra(IntentKey.INTENT_KEY_CLIENT_ID, item.getClient_id());
		startActivity(intent);
	}

	@Override
	public void onRefresh() {
        ProjectHttpRequest.requestProjectCustomerReportedList(projectId, "0", "10", new GetReportedCustomerListHandler(this, LOAD_ACTION.ONREFRESH));
	}

	@Override
	public void onLoadMore() {
        ProjectHttpRequest.requestProjectCustomerReportedList(projectId, String.valueOf(pageIndex), "10", new GetReportedCustomerListHandler(this, LOAD_ACTION.LOADERMORE));
	}

}
