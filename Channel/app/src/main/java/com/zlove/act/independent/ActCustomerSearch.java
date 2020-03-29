package com.zlove.act.independent;

import android.os.Bundle;

import com.zlove.base.ActChannelBase;
import com.zlove.channel.R;
import com.zlove.frag.independent.CustomerSearchFragment;


public class ActCustomerSearch extends ActChannelBase {
    
    private CustomerSearchFragment fragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_act_fragment_container);
        fragment = new CustomerSearchFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, fragment).commit();
        }
    }
}