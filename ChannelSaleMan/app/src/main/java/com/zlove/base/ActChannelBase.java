package com.zlove.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.zlove.base.util.ApplicationUtil;


public class ActChannelBase extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    /**
     * 显示Toast
     * 
     * @param message
     */
    public void showShortToast(String message) {
        Toast.makeText(ApplicationUtil.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast
     * 
     * @param message
     */
    public void showShortToast(int message) {
        Toast.makeText(ApplicationUtil.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
