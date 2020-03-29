package com.zlove.base.widget;

import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.DateFormatUtil;
import com.zlove.base.widget.wheelview.NumericWheelAdapter;
import com.zlove.base.widget.wheelview.OnWheelChangedListener;
import com.zlove.base.widget.wheelview.WheelView;
import com.zlove.channel.R;


public class CommonDateDialog extends AlertDialog implements OnClickListener, OnCancelListener {
    
    public interface OnDateSetListener {
        void onDateSet(int year, int month, int day);
    }
    
    private static int START_YEAR = 1700;
    private static int END_YEAR = 2700;
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    
    private String[] bigMonths;
    private String[] littleMonths;
    
    private WheelView yearView;
    private WheelView monthView;
    private WheelView dayView;
    
    private List<String> bigMonthsList;
    private List<String> littleMonthsList;

    private OnDateSetListener listener;

    public CommonDateDialog(Context context, int year, int month, int day, OnDateSetListener listener) {
        super(context);
        initData();
        initView(context, year, month, day);
    }

    private void initData() {
        bigMonths = ApplicationUtil.getApplicationContext().getResources().getStringArray(R.array.big_month);
        littleMonths = ApplicationUtil.getApplicationContext().getResources().getStringArray(R.array.small_month);
        bigMonthsList = Arrays.asList(bigMonths);
        littleMonthsList = Arrays.asList(littleMonths);
    }
    
    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    private void initView(Context context, int year, int month, int day) {
        setTitle("日期选择");
        setButton("确定", this);
        setButton2("取消",this);
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_view_date_dialog, null);
        yearView = (WheelView) contentView.findViewById(R.id.year);
        monthView = (WheelView) contentView.findViewById(R.id.month);
        dayView = (WheelView) contentView.findViewById(R.id.day);
        
        yearView.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));
        yearView.setCyclic(true);
        yearView.setLabel("年");
        yearView.setCurrentItem(year - START_YEAR);

        monthView.setAdapter(new NumericWheelAdapter(1, 12));
        monthView.setCyclic(true);
        monthView.setLabel("月");
        monthView.setCurrentItem(month);
        
        if (bigMonthsList.contains(String.valueOf(month + 1))) {
            dayView.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (littleMonthsList.contains(String.valueOf(month + 1))) {
            dayView.setAdapter(new NumericWheelAdapter(1 ,30));
        } else {
            if (DateFormatUtil.isLeapYear(year)) {
                dayView.setAdapter(new NumericWheelAdapter(1, 29));
            } else {
                dayView.setAdapter(new NumericWheelAdapter(1 ,28));
            }
        }
        dayView.setCyclic(true);
        dayView.setLabel("日");
        dayView.setCurrentItem(day - 1);
        
        yearView.addChangingListener(new YearWheelChangedListener());
        monthView.addChangingListener(new MonthWheelChangedListener());

        int textSize = adjustFontSize(getWindow().getWindowManager());
        yearView.TEXT_SIZE = textSize;
        monthView.TEXT_SIZE = textSize;
        dayView.TEXT_SIZE = textSize;

        setView(contentView);
    }
    
    @SuppressWarnings("deprecation")
    private int adjustFontSize(WindowManager windowManager) {
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        if (screenWidth <= 240) { // 240X320 屏幕
            return 20;
        } else if (screenWidth <= 320) { // 320X480 屏幕
            return 24;
        } else if (screenWidth <= 480) { // 480X800 或 480X854 屏幕
            return 34;
        } else if (screenWidth <= 540) { // 540X960 屏幕
            return 36;
        } else if (screenWidth <= 800) { // 800X1280 屏幕
            return 40;
        } else { // 大于 800X1280
            return 40;
        }
    }
    
    class YearWheelChangedListener implements OnWheelChangedListener {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            int year = newValue + START_YEAR;
            if (bigMonthsList.contains(String.valueOf(monthView.getCurrentItem() + 1))) {
                dayView.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (littleMonthsList.contains(String.valueOf(monthView.getCurrentItem() + 1))) {
                dayView.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                if (DateFormatUtil.isLeapYear(year)) {
                    dayView.setAdapter(new NumericWheelAdapter(1, 29));
                } else {
                    dayView.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
        }
    }

    class MonthWheelChangedListener implements OnWheelChangedListener {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            int month = newValue + 1;
            if (bigMonthsList.contains(String.valueOf(month))) {
                dayView.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (littleMonthsList.contains(String.valueOf(month))) {
                dayView.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                int year = yearView.getCurrentItem() + START_YEAR;
                if (DateFormatUtil.isLeapYear(year)) {
                    dayView.setAdapter(new NumericWheelAdapter(1, 29));
                } else {
                    dayView.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
        }
    }
    
    @Override
    public void onCancel(DialogInterface dialog) {
        dismiss();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        currentYear = yearView.getCurrentItem() + START_YEAR;
        currentMonth = monthView.getCurrentItem() + 1;
        currentDay = dayView.getCurrentItem() + 1;
        if (listener != null) {
            listener.onDateSet(currentYear, currentMonth, currentDay);
        }
        dismiss();
    }

}
