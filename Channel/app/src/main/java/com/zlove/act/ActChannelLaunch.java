package com.zlove.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.zlove.base.ActChannelBase;
import com.zlove.channel.R;


public class ActChannelLaunch extends ActChannelBase {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_app_launch);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(ActChannelLaunch.this, ActChannelIndex.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, 600);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    }
}
