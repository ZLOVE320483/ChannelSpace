package com.zlove.frag.independent;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.base.util.GImageLoader;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.NoScrollViewPager;
import com.zlove.channel.R;

public class ProjectImgDetailEffectFragment extends BaseFragment {

    private NoScrollViewPager viewPager;
    private TextView tvCount;
    
    private List<String> imgUrls;
    private ImageView[] projectImgs;
    
    private int imgSize = 0;
    
	@Override  
	protected int getInflateLayout() {
		return R.layout.frag_project_img_detail_effect;
	}

	@Override
	protected void setUpView(View view) {
	    viewPager = (NoScrollViewPager) view.findViewById(R.id.id_viewpager);
	    tvCount = (TextView) view.findViewById(R.id.tv_count);
	    if (!ListUtils.isEmpty(imgUrls)) {
	        imgSize = imgUrls.size();
            viewPager.setEnableScroll(true);
            projectImgs = new ImageView[imgSize];
            for(int i = 0; i < projectImgs.length; i++){
                ImageView imageView = new ImageView(getActivity());
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
            }
            
        });
	}
	
	public void setImageUrls(List<String> urls) {
	    this.imgUrls = urls;
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
