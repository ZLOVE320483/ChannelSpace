package com.zlove.base.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.zlove.base.widget.wheelview.ArrayWheelAdapter;
import com.zlove.base.widget.wheelview.WheelView;
import com.zlove.channel.R;


public class CommonGenderDialog extends AlertDialog implements OnClickListener, OnCancelListener {
    
    public interface OnGenderSetListener {
        void onGenderSet(String gender);
    }
    
    private OnGenderSetListener listener;
    
    private String[] gender = {"男" , "女"};
    private WheelView genderView;

    public CommonGenderDialog(Context context, OnGenderSetListener listener) {
        super(context);
        this.listener = listener;
        initView(context);
    }
    
    @SuppressLint("InflateParams") 
    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        setTitle("性别选择");
        setButton("确定", this);
        setButton2("取消",this);
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_view_gender_dialog, null);
        genderView = (WheelView) contentView.findViewById(R.id.gender);
        
        genderView.setAdapter(new ArrayWheelAdapter<String>(gender));
        genderView.setCyclic(false);
        genderView.setCurrentItem(0);
        
        int textSize = adjustFontSize(getWindow().getWindowManager());
        genderView.TEXT_SIZE = textSize;
        
        setView(contentView);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dismiss();        
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int genderIndex = genderView.getCurrentItem();
        String userGender = gender[genderIndex];
        if (listener != null) {
            listener.onGenderSet(userGender);
        }
        dismiss();
    }
    
    @SuppressWarnings("deprecation")
    private int adjustFontSize(WindowManager windowManager) {
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        if (screenWidth <= 240) { // 240X320 屏幕
            return 30;
        } else if (screenWidth <= 320) { // 320X480 屏幕
            return 34;
        } else if (screenWidth <= 480) { // 480X800 或 480X854 屏幕
            return 44;
        } else if (screenWidth <= 540) { // 540X960 屏幕
            return 46;
        } else if (screenWidth <= 800) { // 800X1280 屏幕
            return 50;
        } else { // 大于 800X1280
            return 50;
        }
    }

}
