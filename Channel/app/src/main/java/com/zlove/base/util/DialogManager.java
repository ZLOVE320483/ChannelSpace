
package com.zlove.base.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.zlove.base.widget.ProgressWheel;
import com.zlove.channel.R;

@SuppressLint("InflateParams")
public class DialogManager {
    
    public static Dialog getLoadingDialog(Context context, String msg) {
        Dialog loadingDialog = new Dialog(context, R.style.Dialog);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View loadingView = inflater.inflate(R.layout.common_view_loading_dialog, null);
        TextView loadtext = (TextView) loadingView.findViewById(R.id.loading_text);
        final ProgressWheel progressWheel = (ProgressWheel) loadingView.findViewById(R.id.progressBarFour);
        if (msg != null) {
            loadtext.setText(msg);
            loadtext.setVisibility(View.VISIBLE);
        } else {
            loadtext.setText("");
            loadtext.setVisibility(View.GONE);
        }
        loadingDialog.setContentView(loadingView);
        LayoutParams lp = loadingDialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0.1F;
        lp.alpha = 1.0f;
        loadingDialog.getWindow().setAttributes(lp);
        progressWheel.spin();
        progressWheel.setVisibility(View.VISIBLE);
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;
    }

    public static void showPhotoDialog(Context context, final PhotoSelectListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_view_photo, null);
        Button btnTakePhoto = (Button) contentView.findViewById(R.id.id_photo_take);
        Button btnPicPhoto = (Button) contentView.findViewById(R.id.id_photo_pic);
        Button btnCancel = (Button) contentView.findViewById(R.id.id_cancel);

        btnTakePhoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectFromCamera();
                    dialog.dismiss();
                }
            }
        });

        btnPicPhoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectFromAlbum();
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(contentView);
        // 设置dialog在当前window中的显示方式
        Window dialogWindow = dialog.getWindow();
        LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT; // 高度
        lp.alpha = 1.0f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public interface PhotoSelectListener {

        void selectFromCamera();

        void selectFromAlbum();
    }
    
    public static void showGenderDialog(Context context, final GenderSelectListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_view_gender_select, null);
        Button btnMan = (Button) contentView.findViewById(R.id.id_gender_man);
        Button btnWoman = (Button) contentView.findViewById(R.id.id_gender_woman);
        Button btnCancel = (Button) contentView.findViewById(R.id.id_cancel);

        btnMan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectGender("1");
                    dialog.dismiss();
                }
            }
        });

        btnWoman.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectGender("2");
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(contentView);
        // 设置dialog在当前window中的显示方式
        Window dialogWindow = dialog.getWindow();
        LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT; // 高度
        lp.alpha = 1.0f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    
    public interface GenderSelectListener {

        void selectGender(String gender);
    }
    
    public static void showProjectAddCustomerDialog(Context context, final ProjectAddCustomerListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_view_add_customer_select, null);
        Button btnSelectDirect = (Button) contentView.findViewById(R.id.id_project_add_customer);
        Button btnSelectFromList = (Button) contentView.findViewById(R.id.id_project_add_customer_from_list);
        Button btnCancel = (Button) contentView.findViewById(R.id.id_cancel);

        btnSelectDirect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectAddCustomer(0);
                    dialog.dismiss();
                }
            }
        });

        btnSelectFromList.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectAddCustomer(1);
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(contentView);
        // 设置dialog在当前window中的显示方式
        Window dialogWindow = dialog.getWindow();
        LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT; // 高度
        lp.alpha = 1.0f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    
    public interface ProjectAddCustomerListener {
    	void selectAddCustomer(int pos);
    }
    
    public static void showExtendReportTimeDialog(Context context, final ExtendReportTimeListener listener, final String clientId, final String houseId, final TextView tvLeftTime) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_view_extent_report_time, null);
        Button btnMan = (Button) contentView.findViewById(R.id.id_confirm);
        Button btnCancel = (Button) contentView.findViewById(R.id.id_cancel);

        btnMan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.extendReprotTime(clientId, houseId, tvLeftTime);
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(contentView);
        // 设置dialog在当前window中的显示方式
        Window dialogWindow = dialog.getWindow();
        LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT; // 高度
        lp.alpha = 1.0f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    
    public interface ExtendReportTimeListener {
        void extendReprotTime(String clientId, String houseId, TextView tvLeftTime);
    }
    
    public static void showExtendVisitTimeDialog(Context context, final ExtendVisitTimeListener listener, final String clientId, final String houseId, final TextView tvLeftTime) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_view_extend_visit_time, null);
        Button btnMan = (Button) contentView.findViewById(R.id.id_confirm);
        Button btnCancel = (Button) contentView.findViewById(R.id.id_cancel);

        btnMan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.extendVisitTime(clientId, houseId, tvLeftTime);
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(contentView);
        // 设置dialog在当前window中的显示方式
        Window dialogWindow = dialog.getWindow();
        LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT; // 高度
        lp.alpha = 1.0f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    
    public interface ExtendVisitTimeListener {
        void extendVisitTime(String clientId, String houseId, TextView tvLeftTime);
    }
}
