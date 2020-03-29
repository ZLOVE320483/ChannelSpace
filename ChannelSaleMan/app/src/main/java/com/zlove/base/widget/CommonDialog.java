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
import android.widget.TextView;

import com.zlove.channelsaleman.R;


public class CommonDialog extends Dialog implements OnClickListener {

    private ConfirmAction listener;
    
    private TextView tvTitle;
    private TextView tvContent;
    private Button btnConfirm;
    private Button btnCancel;
    
    private String title;
    private String content;
    private String confirmText = "";
    
    public CommonDialog(Context context) {
        super(context, R.style.MessageBox);
    }
    
    public CommonDialog(Context context, ConfirmAction listener, String title, String content) {
        this(context);
        this.listener = listener;
        this.title = title;
        this.content = content;
    }
    
    public CommonDialog(Context context, ConfirmAction listener, String title, String content, String confirmText) {
        this(context);
        this.listener = listener;
        this.title = title;
        this.content = content;
        this.confirmText = confirmText;
    }
    
    public void showdialog() {
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
                listener.confirm();
            }
        } else if (v == btnCancel) {
            
        }
        dismiss();
    }
    
    public interface ConfirmAction {
        void confirm();
    }
}
