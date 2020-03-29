package com.zlove.act.independent;

import android.os.Bundle;

import com.zlove.base.ActChannelBase;
import com.zlove.channel.R;
import com.zlove.frag.independent.IndependentProjectDetailFragment;


public class ActIndependentProjectDetail extends ActChannelBase {
    
    private IndependentProjectDetailFragment fragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_act_fragment_container);
        fragment = new IndependentProjectDetailFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, fragment).commit();
        }

//        if (!Build.MANUFACTURER.toUpperCase().contains("HUAWEI")) {
//            TTSController ttsManager = TTSController.getInstance(this);// 初始化语音模块
//            ttsManager.init();
//            AMapNavi.getInstance(this).setAMapNaviListener(ttsManager);// 设置语音模块播报
//        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (!Build.MANUFACTURER.toUpperCase().contains("HUAWEI")) {
//            // 这是最后退出页，所以销毁导航和播报资源
//            AMapNavi.getInstance(this).destroy();// 销毁导航
//            TTSController.getInstance(this).stopSpeaking();
//            TTSController.getInstance(this).destroy();
//        }
    }
}
