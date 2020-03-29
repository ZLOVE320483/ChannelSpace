package com.zlove.frag;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.act.ActHouseSelect;
import com.zlove.act.ActProjectCooperateRule;
import com.zlove.act.ActProjectDynamic;
import com.zlove.act.ActProjectImageDetail;
import com.zlove.act.ActProjectLayoutDetail;
import com.zlove.act.ActProjectParameter;
import com.zlove.act.ActProjectPosition;
import com.zlove.adapter.HouseLayoutAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.GImageLoader;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.util.UIUtil;
import com.zlove.base.widget.CommonGridView;
import com.zlove.base.widget.NoScrollViewPager;
import com.zlove.bean.project.ProjectDetailBean;
import com.zlove.bean.project.ProjectDetailData;
import com.zlove.bean.project.ProjectDetailImgListItem;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ProjectHttpRequest;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class MyProjectFragment extends BaseFragment  implements OnClickListener {
    
    private NoScrollViewPager viewPager;
    private ImageView[] projectImgs;
    private List<ProjectDetailImgListItem> imgList;

    private CommonGridView gvHouseLayout;
    private HouseLayoutAdapter layoutAdapter;
    private ArrayList<ProjectDetailImgListItem> projectDetailImgListItems = new ArrayList<>();
    
    private ImageView ivProjectDynamic;
    private TextView tvProjectImgNum;

    private TextView tvProjectName;
    private TextView tvProjectPrice;
    private TextView tvCooperateTime;
    private TextView tvProjectArea;
    private TextView tvProjectTotalPrice;
    private TextView tvProjectLayout;
    private TextView tvProjectType;
    
    private View projectParameterView;
    private View projectPositionView;
    private TextView tvProjectAddress;
    private View projectCooperateRuleView;
    private TextView tvProjectRule;
    private TextView tvProjectDiscount;
    
    private Button btnChangeProject;
    
    private ArrayList<String> effectUrlList = new ArrayList<>();
    private ArrayList<String> modelUrlList = new ArrayList<>();
    private ArrayList<String> layoutUrlList = new ArrayList<>();

    private String projectLng = "";
    private String projectLat = "";
    private int imgSize = 0;
    private String projectRuleId = "";
    
    private boolean isChange = false;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_my_project;
    }

    @Override
    protected void setUpView(View view) {
        view.findViewById(R.id.id_back).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra(IntentKey.INTENT_KEY_CHANGE_PROJECT, true);
                finishActivity(result);
            }
        });
        
        viewPager = (NoScrollViewPager) view.findViewById(R.id.id_viewpager);
        
        ivProjectDynamic = (ImageView) view.findViewById(R.id.id_project_detail_sound);
        ivProjectDynamic.setOnClickListener(this);
        
        tvProjectImgNum = (TextView) view.findViewById(R.id.id_project_detail_img_num);
        
        tvProjectName = (TextView) view.findViewById(R.id.id_project_name);
        tvProjectPrice = (TextView) view.findViewById(R.id.id_project_price);
        tvCooperateTime = (TextView) view.findViewById(R.id.cooperate_time);
        tvProjectArea = (TextView) view.findViewById(R.id.id_project_area);
        tvProjectTotalPrice = (TextView) view.findViewById(R.id.id_project_total_price);
        tvProjectLayout = (TextView) view.findViewById(R.id.id_project_house_layout);
        tvProjectType = (TextView) view.findViewById(R.id.id_project_product);

        tvProjectDiscount = (TextView) view.findViewById(R.id.tv_discount);
        projectParameterView = view.findViewById(R.id.id_project_parameter);
        projectParameterView.setOnClickListener(this);
        
        projectPositionView = view.findViewById(R.id.id_project_address);
        projectPositionView.setOnClickListener(this);
        tvProjectAddress = (TextView) view.findViewById(R.id.tv_address);
        
        projectCooperateRuleView = view.findViewById(R.id.id_cooperate_rule);
        projectCooperateRuleView.setOnClickListener(this);
        tvProjectRule = (TextView) view.findViewById(R.id.rule);
        btnChangeProject = (Button) view.findViewById(R.id.btn_change_project);
        btnChangeProject.setOnClickListener(this);
        
        gvHouseLayout = (CommonGridView) view.findViewById(R.id.gv_house_layout);
        layoutAdapter = new HouseLayoutAdapter(getActivity(), projectDetailImgListItems);
        gvHouseLayout.setAdapter(layoutAdapter);
        
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

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
        
        gvHouseLayout.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(), ActProjectLayoutDetail.class);
				intent.putExtra(IntentKey.INTENT_KEY_LAYOUT_URL_LIST, projectDetailImgListItems);
				startActivity(intent);
			}
		});
        
        if (ChannelCookie.getInstance().getHouseNum() <= 1) {
            btnChangeProject.setVisibility(View.GONE);
        } else {
            btnChangeProject.setVisibility(View.VISIBLE);
        }
        ProjectHttpRequest.requestProjectInfo(ChannelCookie.getInstance().getCurrentHouseId(), new GetProjectInfoHandler(this));
    }
    
    @Override
    public void onClick(View v) {
        if (v == projectParameterView) {
            Intent intent = new Intent(getActivity(), ActProjectParameter.class);
            startActivity(intent);
        } else if (v == projectPositionView) {
            Intent naviStartIntent=new Intent(getActivity(),ActProjectPosition.class);
            naviStartIntent.putExtra(IntentKey.INTENT_KEY_PROJECT_LNG, projectLng);
            naviStartIntent.putExtra(IntentKey.INTENT_KEY_PROJECT_LAT, projectLat);
            startActivity(naviStartIntent);
		} else if (v == projectCooperateRuleView) {
            Intent intent = new Intent(getActivity(), ActProjectCooperateRule.class);
            intent.putExtra(IntentKey.INTENT_KEY_PROJECT_RULE_ID, projectRuleId);
            startActivity(intent);
		} else if (v == ivProjectDynamic) {
		    Intent intent = new Intent(getActivity(), ActProjectDynamic.class);
		    startActivity(intent);
		} else if (v == btnChangeProject) {
            Intent intent = new Intent(getActivity(), ActHouseSelect.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_CHANGE_PROJECT);
        }
    }
    
    class GetProjectInfoHandler extends HttpResponseHandlerFragment<MyProjectFragment> {

		public GetProjectInfoHandler(MyProjectFragment context) {
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
                ProjectDetailBean bean = JsonUtil.toObject(new String(content), ProjectDetailBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ProjectDetailData data = bean.getData();
                        if (data != null) {
                            projectLat = data.getLat();
                            projectLng = data.getLng();
                            projectRuleId = data.getHouse_rule_id();
                            
                            tvProjectName.setText(UIUtil.replaceNullOrEmpty(data.getName()));
                            tvProjectPrice.setText(UIUtil.replaceNullOrEmpty(data.getPrice_desc()));
                            tvProjectArea.setText(UIUtil.replaceNullOrEmpty(data.getLocation_area()));
                            tvProjectTotalPrice.setText(UIUtil.replaceNullOrEmpty(data.getPrice()));
                            tvProjectLayout.setText(UIUtil.replaceNullOrEmpty(data.getHouse_types()));
                            tvProjectType.setText(UIUtil.replaceNullOrEmpty(data.getProperty_types()));
                            tvProjectAddress.setText(UIUtil.replaceNullOrEmpty(data.getAddress()));
                            tvProjectRule.setText(UIUtil.replaceNullOrEmpty(data.getHouse_rule()));
                            tvProjectDiscount.setText(UIUtil.replaceNullOrEmpty(data.getDiscount_desc()));
                            tvCooperateTime.setText("合作时间:" + UIUtil.replaceNullOrEmpty(data.getCooperate_time()));
                            
                            imgList = data.getHouse_images();
                            if (!ListUtils.isEmpty(imgList)) {
                                classifyImgUrls(imgList);
                                imgSize = imgList.size();
                                tvProjectImgNum.setText("1/" + imgSize);
                                if (imgSize <= 1) {
                                    viewPager.setEnableScroll(false);
                                } else {
                                	viewPager.setEnableScroll(true);
                                }
                                projectImgs = new ImageView[imgSize];
                                for(int i = 0; i < projectImgs.length; i++){
                                    ImageView imageView = new ImageView(getActivity());
                                    projectImgs[i] = imageView;
                                    GImageLoader.getInstance().getImageLoader().displayImage(imgList.get(i).getImage(),imageView, GImageLoader.getInstance().getNormalOptions());
                                }
                                viewPager.setAdapter(new ProjectImgViewPageAdapter());
                            } else {
                                tvProjectImgNum.setText("0");
                                viewPager.setEnableScroll(false);
                            }
                        }
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
    
    private void classifyImgUrls(List<ProjectDetailImgListItem> imgUrlList) {
        for (ProjectDetailImgListItem item : imgUrlList) {
            if (item.getType().equals("0")) {
                effectUrlList.add(item.getImage());
            } else if (item.getType().equals("1")) {
                modelUrlList.add(item.getImage());
            } else if (item.getType().equals("2")) {
                layoutUrlList.add(item.getImage());
                projectDetailImgListItems.add(item);
            }
        }
        layoutAdapter.notifyDataSetChanged();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IntentKey.REQUEST_CODE_CHANGE_PROJECT && data != null) {
                isChange = data.getBooleanExtra(IntentKey.INTENT_KEY_CHANGE_PROJECT, false);
                if (isChange) {
                    imgList.clear();
                    effectUrlList.clear();
                    modelUrlList.clear();
                    layoutUrlList.clear();
                    projectDetailImgListItems.clear();
                    ProjectHttpRequest.requestProjectInfo(ChannelCookie.getInstance().getCurrentHouseId(), new GetProjectInfoHandler(this));
                }
            }
        }
    }
}
