package com.zlove.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

import com.zlove.base.ActChannelBase;
import com.zlove.base.widget.NoScrollViewPager;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.frag.IndexFourFragment;
import com.zlove.frag.IndexOneFragment;
import com.zlove.frag.IndexThreeFragment;
import com.zlove.frag.IndexTwoFragment;


public class ActChannelIndex extends ActChannelBase {

    private static final int INDEX_PAGE_COUNT = 4;
    
    private NoScrollViewPager viewPager;
    private ImageView[] vIndicators;
    private IndexViewPageAdapter indexViewPageAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ChannelCookie.getInstance().isFirstStart()) {
            setContentView(R.layout.act_channel_index);
            viewPager = (NoScrollViewPager) findViewById(R.id.id_viewpager);
            viewPager.setEnableScroll(true);
            vIndicators = new ImageView[INDEX_PAGE_COUNT];
            vIndicators[0] = (ImageView) findViewById(R.id.id_indicator_0);
            vIndicators[1] = (ImageView) findViewById(R.id.id_indicator_1);
            vIndicators[2] = (ImageView) findViewById(R.id.id_indicator_2);
            vIndicators[3] = (ImageView) findViewById(R.id.id_indicator_3);
            
            indexViewPageAdapter = new IndexViewPageAdapter(getSupportFragmentManager());
            viewPager.setAdapter(indexViewPageAdapter);

            viewPager.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    for (int i = 0; i < vIndicators.length; i++) {
                        vIndicators[i].setImageResource(i == arg0 ? R.drawable.ic_indicator_selected : R.drawable.ic_indicator_normal);
                    }
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            });
        } else {
            Intent intent = new Intent(ActChannelIndex.this, ActLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
    
    
    private class IndexViewPageAdapter extends FragmentPagerAdapter {

        public IndexViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0:
                    return new IndexOneFragment();
                case 1:
                    return new IndexTwoFragment();
                case 2:
                    return new IndexThreeFragment();
                case 3:
                    return new IndexFourFragment();
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return INDEX_PAGE_COUNT;
        }
    }
}
