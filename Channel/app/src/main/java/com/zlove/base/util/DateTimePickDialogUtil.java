package com.zlove.base.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.zlove.channel.R;

import java.lang.reflect.Field;
import java.util.Calendar;


public class DateTimePickDialogUtil implements OnDateChangedListener, OnTimeChangedListener {
	
    private Context mContext;
    private TextView tvDateTime;
    
    private DatePicker datePicker;
    private TimePicker timePicker;
    
    private SetRevisitTimeListener listener;
    private String clientId = "";
    private String houseId = "";
    
    private String curDateTime = "";
    
    public DateTimePickDialogUtil(Context context, TextView tvDateTime, SetRevisitTimeListener listener, String clientId, String houseId) {
        this.mContext = context;
        this.tvDateTime = tvDateTime;
        this.listener = listener;
        this.clientId = clientId;
        this.houseId = houseId;
        curDateTime = DateFormatUtil.getFormateDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm");
    }
    
    @SuppressLint("InflateParams") 
    public void showDateSelectDialog(String title) {
        final Dialog dialog = new Dialog(mContext, R.style.MessageBox);
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_view_date_select, null);
        
        datePicker = (DatePicker) view.findViewById(R.id.datepicker);
        applyStyling(datePicker);
        
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(title);
        
        Button btnConfirm = (Button) view.findViewById(R.id.id_confirm);
        btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showTimeSelectDialog("设置回访时间");
				dialog.dismiss();
			}
		});
        
        Button btnCancel = (Button) view.findViewById(R.id.id_cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

        // 设置窗口弹出动画
        dialog.getWindow().setWindowAnimations(R.style.centerDialogWindowAnim);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams wl = dialog.getWindow().getAttributes();
        wl.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(wl);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(view);
        dialog.show();
    }
    
    @SuppressLint("InflateParams") 
    public void showTimeSelectDialog(String title) {
        final Dialog dialog = new Dialog(mContext, R.style.MessageBox);
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_view_time_select, null);
        
        timePicker = (TimePicker) view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        Calendar mCalendar=Calendar.getInstance();
        timePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        applyStyling(timePicker);
        timePicker.setOnTimeChangedListener(this);
        
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(title);
        
        Button btnConfirm = (Button) view.findViewById(R.id.id_confirm);
        btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
		        if (listener != null) {
                    onDateChanged(null, 0, 0, 0);
                    listener.setRevisitTime(clientId, houseId, curDateTime, tvDateTime);
                }
			}
		});
        
        Button btnCancel = (Button) view.findViewById(R.id.id_cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
        
        // 设置窗口弹出动画
        dialog.getWindow().setWindowAnimations(R.style.centerDialogWindowAnim);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams wl = dialog.getWindow().getAttributes();
        wl.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(wl);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(view);
        dialog.show();
    }
    
    
    private void applyStyling(TimePicker timePicker) {
        try {
            Field fields[] = TimePicker.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().equals(NumberPicker.class)) {
                    field.setAccessible(true);
                    applyStyling((NumberPicker) field.get(timePicker));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void applyStyling(DatePicker datePicker) {
        try {
            Field fields[] = DatePicker.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().equals(NumberPicker.class)) {
                    field.setAccessible(true);
                    applyStyling((NumberPicker) field.get(datePicker));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void applyStyling(NumberPicker numberPicker) {
        if (numberPicker == null) {
            return;
        }
        try {
            Field field = NumberPicker.class.getDeclaredField("mSelectionDivider");
            field.setAccessible(true);
            field.set(numberPicker, new ColorDrawable(mContext.getResources().getColor(R.color.common_bg_top_bar)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//        onDateChanged(null, 0, 0, 0);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
     // 获得日历实例
        Calendar calendar = Calendar.getInstance();

        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
            timePicker.getCurrentMinute());
        
        curDateTime = DateFormatUtil.getFormateDate(calendar.getTime(), "yyyy-MM-dd HH:mm");
    }
    
    public interface SetRevisitTimeListener {
        void setRevisitTime(String clientId, String houseId, String time, TextView tvDateTime);
    }
}
