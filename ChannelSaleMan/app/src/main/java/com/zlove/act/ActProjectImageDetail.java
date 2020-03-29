package com.zlove.act;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zlove.base.ActChannelBase;
import com.zlove.base.util.GImageLoader;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.NoScrollViewPager;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;

public class ActProjectImageDetail extends ActChannelBase implements OnClickListener {
    
    private static final int TAB_EFFECT = 1;
    private static final int TAB_MODEL = 2;
    private static final int TAB_LAYOUT = 3;
    
    private RadioButton rbEffect;
    private RadioButton rbModel;
    private RadioButton rbLayout;
    
    private ImageView ivBack;
    private NoScrollViewPager viewPager;
    private TextView tvImgType;
    private TextView tvCount;
    private int currentTab = 0;
    
    private ArrayList<String> effectUrls;
    private ArrayList<String> modelUrls;
    private ArrayList<String> layoutUrls;

    private ArrayList<String> imgUrls = new ArrayList<String>();
    private ImageView[] projectImgs;
    private int imgSize = 0;
    
    private int effectImgSize = 0;
    private int modelImgSize = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_project_image_detail);
        
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_EFFECT_URL_LIST)) {
                effectUrls = intent.getStringArrayListExtra(IntentKey.INTENT_KEY_EFFECT_URL_LIST);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_MODEL_URL_LIST)) {
            	modelUrls = intent.getStringArrayListExtra(IntentKey.INTENT_KEY_MODEL_URL_LIST);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_LAYOUT_URL_LIST)) {
                layoutUrls = intent.getStringArrayListExtra(IntentKey.INTENT_KEY_LAYOUT_URL_LIST);
            }
        }
        
        if (!ListUtils.isEmpty(effectUrls)) {
        	effectImgSize = effectUrls.size();
			imgUrls.addAll(effectUrls);
		}
        if (!ListUtils.isEmpty(modelUrls)) {
        	modelImgSize = modelUrls.size();
			imgUrls.addAll(modelUrls);
		}
        if (!ListUtils.isEmpty(layoutUrls)) {
			imgUrls.addAll(layoutUrls);
		}

	    viewPager = (NoScrollViewPager) findViewById(R.id.id_viewpager);
	    tvImgType = (TextView) findViewById(R.id.tv_img_type);
	    tvImgType.setText("效果图");
	    tvCount = (TextView) findViewById(R.id.tv_count);
        
        ivBack = (ImageView) findViewById(R.id.id_back);
        
        if (!ListUtils.isEmpty(imgUrls)) {
	        imgSize = imgUrls.size();
            viewPager.setEnableScroll(true);
            projectImgs = new ImageView[imgSize];
            for(int i = 0; i < projectImgs.length; i++){
                ImageView imageView = new ImageView(this);
                projectImgs[i] = imageView;
                GImageLoader.getInstance().getImageLoader().displayImage(imgUrls.get(i), imageView, GImageLoader.getInstance().getNormalOptions());
                tvCount.setText("1/" + imgSize);
                viewPager.setAdapter(new ProjectImgViewPageAdapter());
            }
        } else {
            tvCount.setText("0");
            viewPager.setEnableScroll(false);
        }
        
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                
                tvCount.setText((arg0 + 1) + "/" + imgSize);
                if (arg0 == effectImgSize) {
					selectTab(TAB_MODEL);
				} else if (arg0 == effectImgSize + modelImgSize) {
					selectTab(TAB_LAYOUT);
				} else if (arg0 == effectImgSize - 1) {
					selectTab(TAB_EFFECT);
				}
            }
            
        });
        
        rbEffect = (RadioButton) findViewById(R.id.id_effect);
        rbModel = (RadioButton) findViewById(R.id.id_model);
        rbLayout = (RadioButton) findViewById(R.id.id_layout);
        
        rbEffect.setOnClickListener(this);
        rbModel.setOnClickListener(this);
        rbLayout.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        
        selectTab(TAB_EFFECT);
    }
    
    @Override
    public void onClick(View v) {
        int changeIndex = 0;
        if (v == rbEffect) {
            changeIndex = TAB_EFFECT;
        } else if (v == rbModel) {
            changeIndex = TAB_MODEL;
        } else if (v == rbLayout) {
            changeIndex = TAB_LAYOUT;
        } else if (v == ivBack) {
			this.finish();
		}
        selectTab(changeIndex);
    }
    
    private void selectTab(int tab) {
        if (tab == currentTab) {
            return;
        }
        currentTab = tab;
        switch (tab) {
            case TAB_EFFECT: {
        	    tvImgType.setText("效果图");
        	    viewPager.setCurrentItem(0);
                break;
            }
            case TAB_MODEL: {
        	    tvImgType.setText("样板图");
        	    viewPager.setCurrentItem(effectImgSize);
                break;
            }
            case TAB_LAYOUT: {
        	    tvImgType.setText("户型图");
        	    viewPager.setCurrentItem(effectImgSize + modelImgSize);
                break;
            }
            default:
                break;
        }
        onTabChange();
    }
    
    private void onTabChange() {
        rbEffect.setChecked(currentTab == TAB_EFFECT);
        rbModel.setChecked(currentTab == TAB_MODEL);
        rbLayout.setChecked(currentTab == TAB_LAYOUT);
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
            if (imgUrls.size() > 0) {
                ImageView view = projectImgs[position];
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

}
