package com.zlove.act;

import android.os.Bundle;

import com.zlove.base.ActChannelBase;
import com.zlove.channelsaleman.R;
import com.zlove.frag.CustomerChatRecordFragment;


public class ActCustomerChatRecord extends ActChannelBase {
    
    private CustomerChatRecordFragment fragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_act_fragment_container);
        fragment = new CustomerChatRecordFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, fragment).commit();
        }
    }
}
