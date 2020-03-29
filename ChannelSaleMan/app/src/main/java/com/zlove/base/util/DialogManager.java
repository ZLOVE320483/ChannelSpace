
package com.zlove.base.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zlove.adapter.HouseSelectAdapter;
import com.zlove.base.widget.ProgressWheel;
import com.zlove.bean.project.HouseListItem;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;

import java.util.List;

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
    
    @SuppressLint("InflateParams")
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
    
    public static void showCustomerEffectDialog(Context context, final CustomerEffectSelectListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_view_effect_invalid_customer, null);
        Button btnInvalid = (Button) contentView.findViewById(R.id.id_customer_invalid);
        Button btnEffect = (Button) contentView.findViewById(R.id.id_customer_effect);
        Button btnCancel = (Button) contentView.findViewById(R.id.id_cancel);

        btnInvalid.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectCustomerEffect("0");
                    dialog.dismiss();
                }
            }
        });

        btnEffect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectCustomerEffect("1");
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
    
    public interface CustomerEffectSelectListener {
        void selectCustomerEffect(String effectOrInvalid);
    }
    
    public static void showDegreeDialog(Context context, final DegreeSelectListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_view_degree_select, null);
        Button degreeA = (Button) contentView.findViewById(R.id.degree_a);
        Button degreeB = (Button) contentView.findViewById(R.id.degree_b);
        Button degreeC = (Button) contentView.findViewById(R.id.degree_c);
        Button degreeD = (Button) contentView.findViewById(R.id.degree_d);
        
        
        Button btnCancel = (Button) contentView.findViewById(R.id.id_cancel);

        degreeA.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectDegree("1");
                    dialog.dismiss();
                }
            }
        });

        degreeB.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectDegree("2");
                    dialog.dismiss();
                }
            }
        });
        
        degreeC.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectDegree("3");
                    dialog.dismiss();
                }
            }
        });
        
        degreeD.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.selectDegree("4");
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
    
    public interface DegreeSelectListener {
        void selectDegree(String degree);
    }
    
    @SuppressLint("InflateParams")
    public static void showDecideVisitDialog(Context context, final DecideVisitListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_view_decide_visit, null);
        Button btnTakePhoto = (Button) contentView.findViewById(R.id.confirm_visit);
        Button btnPicPhoto = (Button) contentView.findViewById(R.id.do_not_visit);
        Button btnCancel = (Button) contentView.findViewById(R.id.id_cancel);

        btnTakePhoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.decide(1);
                    dialog.dismiss();
                }
            }
        });

        btnPicPhoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.decide(0);
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
    
    public interface DecideVisitListener {
    	void decide(int type);
    }
    

    @SuppressLint("InflateParams")
    public static void showDecideVisitFromeSelfDialog(Context context, final DecideVisitFromSelfListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_view_decide_visit_from_self, null);
        Button btnTakePhoto = (Button) contentView.findViewById(R.id.confirm_visit);
        Button btnCancel = (Button) contentView.findViewById(R.id.id_cancel);

        btnTakePhoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.decide();
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
    
    public interface DecideVisitFromSelfListener {
    	void decide();
    }

    @SuppressLint("InflateParams")
    public static void showSetOverDueDialog(Context context, final SetOverdueListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_view_set_overdue, null);
        Button btnTakePhoto = (Button) contentView.findViewById(R.id.set_is_overdue);
        Button btnPicPhoto = (Button) contentView.findViewById(R.id.set_not_overdue);
        Button btnCancel = (Button) contentView.findViewById(R.id.id_cancel);

        btnTakePhoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.setOverdue("1");
                    dialog.dismiss();
                }
            }
        });

        btnPicPhoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.setOverdue("0");
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

    public interface SetOverdueListener {
        void setOverdue(String ovderdue);
    }

    public static PopupWindow getSelectProjectWindow(final Activity context, final List<HouseListItem> houseList, final ChangeProjectListener listener) {
        final PopupWindow window;
        View view = LayoutInflater.from(context).inflate(R.layout.common_view_listview, null);
        ListView listView = (ListView) view.findViewById(R.id.id_listview);
        final HouseSelectAdapter adapter = new HouseSelectAdapter(houseList, context);
        adapter.setHouseId(ChannelCookie.getInstance().getCurrentHouseId());
        listView.setAdapter(adapter);

        window =  new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(context, 0.5f);

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(context, 1f);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HouseListItem item = houseList.get(position);
                if (item == null) {
                    return;
                }
                adapter.setHouseId(item.getHouse_id());
                adapter.notifyDataSetChanged();
                ChannelCookie.getInstance().saveUserCurrentHouseId(item.getHouse_id());
                ChannelCookie.getInstance().saveUserCurrentHouseName(item.getName());
                if (listener != null) {
                    listener.changeProject();
                }
                window.dismiss();
            }
        });

        return window;
    }

    private static void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        if (bgAlpha == 1) {
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        lp.alpha = bgAlpha;
        context.getWindow().setAttributes(lp);
    }

    public interface ChangeProjectListener {
        void changeProject();
    }

}
