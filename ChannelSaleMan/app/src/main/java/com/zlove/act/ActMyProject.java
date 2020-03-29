package com.zlove.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zlove.base.ActChannelBase;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;
import com.zlove.frag.MyProjectFragment;


public class ActMyProject extends ActChannelBase {
    
    private MyProjectFragment fragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_act_fragment_container);
        fragment = new MyProjectFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, fragment).commit();
        }
    }
    
    @Override
    public void onBackPressed() {
        Intent result = new Intent();
        result.putExtra(IntentKey.INTENT_KEY_CHANGE_PROJECT, true);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
