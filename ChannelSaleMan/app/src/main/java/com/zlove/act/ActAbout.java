package com.zlove.act;

import android.os.Bundle;

import com.zlove.base.ActChannelBase;
import com.zlove.channelsaleman.R;
import com.zlove.frag.AboutFragment;


public class ActAbout extends ActChannelBase {
    
    private AboutFragment fragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_act_fragment_container);
        fragment = new AboutFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, fragment).commit();
        }
    }
}
