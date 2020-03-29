package com.zlove.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zlove.channel.R;


public class CommonConfirmDialog extends Dialog implements OnClickListener {

    private TextView tvContent;
    private Button btnConfirm;
    private ConfirmListener listener;
    
    private String content;
    
    public CommonConfirmDialog(Context context) {
        super(context, R.style.MessageBox);
    }
    
    public CommonConfirmDialog(Context context, String content, ConfirmListener listener) {
        this(context);
        this.content = content;
        this.listener = listener;
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
        setContentView(R.layout.common_view_confirm_dialog);
        initView();
    }
    
    private void initView() {
        tvContent = (TextView) findViewById(R.id.tv_dialog_content);
        btnConfirm = (Button) findViewById(R.id.id_confirm);
        
        tvContent.setText(content);
        
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnConfirm) {
            if (listener != null) {
                listener.confirm();
            }
            dismiss();
        }
    }
    
    public interface ConfirmListener {
        void confirm();
    }
}
