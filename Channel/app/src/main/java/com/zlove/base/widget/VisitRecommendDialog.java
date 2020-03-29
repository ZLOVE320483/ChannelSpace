package com.zlove.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.channel.R;

public class VisitRecommendDialog extends Dialog implements OnClickListener {

    private VisitRecommendAction listener;
    private ImageView icon;
    private TextView tvDate;
    private TextView tvTime;
    
    private TextView tvTitle;
    private TextView tvContent;
    private Button btnConfirm;
    private Button btnCancel;
    
    private String title;
    private String content;
    private String confirmText = "";
    private String houseId = "";
    
    public VisitRecommendDialog(Context context) {
        super(context, R.style.MessageBox);
    }
    
    public VisitRecommendDialog(Context context, VisitRecommendAction listener, String title, String content, ImageView icon, TextView tvDate, TextView tvTime) {
        this(context);
        this.listener = listener;
        this.title = title;
        this.content = content;
        this.icon = icon;
        this.tvDate = tvDate;
        this.tvTime = tvTime;
    }
    
    public void showdialog(String houseId) {
    	this.houseId = houseId;
        // 设置窗口弹出动画
        getWindow().setWindowAnimations(R.style.centerDialogWindowAnim);
        // setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams wl = getWindow().getAttributes();
        wl.gravity = Gravity.CENTER;
        getWindow().setAttributes(wl);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        show();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_view_dialog);
        initView();
    }
    
    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_dialog_title);
        tvContent = (TextView) findViewById(R.id.tv_dialog_content);
        btnConfirm = (Button) findViewById(R.id.id_confirm);
        btnCancel = (Button) findViewById(R.id.id_cancel);
        
        tvTitle.setText(title);
        tvContent.setText(content);
        
        if (!TextUtils.isEmpty(confirmText)) {
            btnConfirm.setText(confirmText);
        }
        
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnConfirm) {
            if (listener != null) {
                listener.confirm(icon, tvDate, tvTime, houseId);
            }
        } else if (v == btnCancel) {
            
        }
        dismiss();
    }
    
    public interface VisitRecommendAction {
        void confirm(ImageView icon, TextView tvDate, TextView tvTime, String houseId);
    }
}
