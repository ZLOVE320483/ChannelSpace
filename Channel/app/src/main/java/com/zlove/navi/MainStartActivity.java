package com.zlove.navi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.zlove.channel.R;


public class MainStartActivity extends Activity implements OnClickListener {
    private TextView mComplexTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainstart);
        initView();
    }
    
    private void initView(){
        setTitle("导航SDK " + AMapNavi.getVersion());
        mComplexTextView=(TextView) findViewById(R.id.main_start_complex_text);
        mComplexTextView.setOnClickListener(this);

        TTSController ttsManager = TTSController.getInstance(this);// 初始化语音模块
        ttsManager.init();
        AMapNavi.getInstance(this).setAMapNaviListener(ttsManager);// 设置语音模块播报
    }
    
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.main_start_complex_text:
                Intent naviStartIntent=new Intent(MainStartActivity.this,ActProjectPosition.class);
                naviStartIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(naviStartIntent);
                break;

            default:
                break;
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 这是最后退出页，所以销毁导航和播报资源
        AMapNavi.getInstance(this).destroy();// 销毁导航
        TTSController.getInstance(this).stopSpeaking();
        TTSController.getInstance(this).destroy();
    }
}
