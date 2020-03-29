
package com.zlove.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class DateFormatUtil {

    public static boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            if (year % 400 == 0) {
                return true;
            }
        } else {
            if (year % 4 == 0) {
                return true;
            }
        }
        return false;
    }

    public static String getCallLogDate(Date date) {
        if (date != null) {
            SimpleDateFormat sfd = new SimpleDateFormat("MM-dd hh:mm");
            return sfd.format(date);
        }
        return "";
    }

    public static String getFormateDate(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sfd = new SimpleDateFormat(format);
            return sfd.format(date);
        }
        return "";
    }

    public static String getFormateDate(long stamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(new Date(stamp));
        return date;
    }
}
