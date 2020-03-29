package com.zlove.act;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.base.ActChannelBase;
import com.zlove.base.util.GImageLoader;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.NoScrollViewPager;
import com.zlove.bean.project.ProjectDetailImgListItem;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;

public class ActProjectLayoutDetail extends ActChannelBase implements OnClickListener {

	private ImageView ivBack;
    private NoScrollViewPager viewPager;
    private TextView tvLayoutDesc;
    private TextView tvArea;
    private TextView tvLayoutType;
    private TextView tvLayoutPrice;
    private ArrayList<ProjectDetailImgListItem> layoutUrls;
    private ImageView[] projectImgs;
    private int imgSize = 0;
    
    @SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_project_layout_type);
        
        Intent intent = getIntent();
        if (intent != null) {
        	if (intent.hasExtra(IntentKey.INTENT_KEY_LAYOUT_URL_LIST)) {
                layoutUrls = (ArrayList<ProjectDetailImgListItem>) intent.getExtras().get(IntentKey.INTENT_KEY_LAYOUT_URL_LIST);
            }
        }
	    viewPager = (NoScrollViewPager) findViewById(R.id.id_viewpager);
	    tvLayoutDesc = (TextView) findViewById(R.id.layout_desc);
	    tvArea = (TextView) findViewById(R.id.area);
	    tvLayoutType = (TextView) findViewById(R.id.layout_type);
	    tvLayoutPrice = (TextView) findViewById(R.id.price);
	    ivBack = (ImageView) findViewById(R.id.id_back);
	    
	    if (!ListUtils.isEmpty(layoutUrls)) {
	    	ProjectDetailImgListItem item = layoutUrls.get(0);
	    	tvLayoutDesc.setText(item.getName());
	    	if (!TextUtils.isEmpty(item.getArea())) {
	    		tvArea.setText(item.getArea() + "平米");
		    	tvLayoutType.setText(item.getDesc());
		    	tvLayoutPrice.setText(item.getPrice_desc());
			}
	    	
	        imgSize = layoutUrls.size();
            viewPager.setEnableScroll(true);
            projectImgs = new ImageView[imgSize];
            for(int i = 0; i < projectImgs.length; i++){
                ImageView imageView = new ImageView(this);
                projectImgs[i] = imageView;
                GImageLoader.getInstance().getImageLoader().displayImage(layoutUrls.get(i).getImage(), imageView, GImageLoader.getInstance().getNormalOptions());
            }
            viewPager.setAdapter(new ProjectImgViewPageAdapter());
        } else {
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
    	    	ProjectDetailImgListItem item = layoutUrls.get(arg0);
    	    	tvLayoutDesc.setText(item.getName());
    	    	if (!TextUtils.isEmpty(item.getArea())) {
    	    		tvArea.setText(item.getArea() + "平米");
    		    	tvLayoutType.setText(item.getDesc());
    		    	tvLayoutPrice.setText(item.getPrice_desc());
    			}
            }
            
        });
	    
	    ivBack.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
    	if (v == ivBack) {
			finish();
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
            if (imgSize > 0) {
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
