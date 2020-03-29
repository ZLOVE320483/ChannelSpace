package com.zlove.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ChannelCrashHandler implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler mDefaultHandler;

    private static ChannelCrashHandler mInstance = new ChannelCrashHandler();

    private Context mContext;

    private Map<String, String> mLogInfo = new HashMap<String, String>();

    @SuppressLint("SimpleDateFormat") 
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");

    public static String FILE_DIR = Environment.getExternalStorageDirectory() + "/CrashInfos";

    /**
     * Creates a new instance of CrashHandler.
     */
    private ChannelCrashHandler() {
    }

    public static ChannelCrashHandler getInstance() {
        return mInstance;
    }

    public void init(Context paramContext) {
        mContext = paramContext;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    
    @Override
    public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
        if (!handleException(paramThrowable) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(paramThread, paramThrowable);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    public boolean handleException(Throwable paramThrowable) {
        if (paramThrowable == null)
            return false;
        getDeviceInfo(mContext);
        saveCrashLogToFile(paramThrowable);
        return true;
    }
    /**
     * 获取手机型号
     * @param paramContext
     */
    public void getDeviceInfo(Context paramContext) {
        try {
            PackageManager mPackageManager = paramContext.getPackageManager();
            PackageInfo mPackageInfo = mPackageManager.getPackageInfo(paramContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (mPackageInfo != null) {
                String versionName = mPackageInfo.versionName == null ? "null" : mPackageInfo.versionName;
                String versionCode = mPackageInfo.versionCode + "";
                mLogInfo.put("versionName", versionName);
                mLogInfo.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] mFields = Build.class.getDeclaredFields();
        for (Field field : mFields) {
            try {
                field.setAccessible(true);
                mLogInfo.put(field.getName(), field.get("").toString());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 写入错误日志
     * @param paramThrowable
     * @return
     */
    private String saveCrashLogToFile(Throwable paramThrowable) {
        StringBuffer mStringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : mLogInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            mStringBuffer.append(key + "=" + value + "\r\n");
        }
        Writer mWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mWriter);
        paramThrowable.printStackTrace(mPrintWriter);
        Throwable mThrowable = paramThrowable.getCause();
        while (mThrowable != null) {
            mThrowable.printStackTrace(mPrintWriter);
            mPrintWriter.append("\r\n");
            mThrowable = mThrowable.getCause();
        }
        mPrintWriter.close();
        String mResult = mWriter.toString();
        mStringBuffer.append(mResult);
        String mTime = mSimpleDateFormat.format(new Date());
        String mFileName = "CrashLog-" + mTime + ".log";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File mDirectory = new File(FILE_DIR);
                if (!mDirectory.exists()) {
                    mDirectory.mkdirs();
                }
                FileOutputStream mFileOutputStream = new FileOutputStream(mDirectory + "/" + mFileName);
                mFileOutputStream.write(mStringBuffer.toString().getBytes());
                mFileOutputStream.close();
                return mFileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }}
