package com.zlove.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;
import com.zlove.act.independent.ActIndependentMain;
import com.zlove.act.independent.ActIndependentStationMessage;
import com.zlove.act.independent.ActMessageContactCustomer;
import com.zlove.act.independent.ActMessageCooperateRule;
import com.zlove.act.independent.ActMessageCustomerProgress;
import com.zlove.act.independent.ActMessageCustomerTrace;
import com.zlove.act.independent.ActMessageProjectDynamic;
import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.LogUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;
import com.zlove.http.UserHttpRequest;
import com.zlove.push.BasePushBean;

import org.apache.http.Header;


public class MessageReceiver extends XGPushBaseReceiver {
    
    @Override
    public void onDeleteTagResult(Context arg0, int arg1, String arg2) {
        Log.d("ZLiZH", "channel---onDeleteTagResult---arg1:" + arg1 + "---arg2:" + arg2);
    }

    @Override
    public void onNotifactionClickedResult(Context arg0, XGPushClickedResult arg1) {
        Log.d("ZLiZH", "channel---onNotifactionClickedResult---" + arg1.getActivityName() + "---" + arg1.getContent());
    }

    @Override
    public void onNotifactionShowedResult(Context arg0, XGPushShowedResult arg1) {
        Log.d("ZLiZH", "channel---onNotifactionShowedResult---" + arg1.getTitle() + "---" + arg1.getContent());
    }

    @Override
    public void onRegisterResult(Context arg0, int arg1, XGPushRegisterResult arg2) {
        Log.d("ZLiZH", "channel---onRegisterResult---deviceId--" + arg2.getDeviceId() + "--Token--" + arg2.getToken());
        ChannelCookie.getInstance().saveDeviceId(arg2.getDeviceId());
        ChannelCookie.getInstance().saveToken(arg2.getToken());
        if (!TextUtils.isEmpty(ChannelCookie.getInstance().getToken()) && !TextUtils.isEmpty(ChannelCookie.getInstance().getSessionId()))
            UserHttpRequest.setXinGeRequest(ChannelCookie.getInstance().getDeviceId(), ChannelCookie.getInstance().getToken(), new SetXinGeHandler());

    }

    @Override
    public void onSetTagResult(Context arg0, int arg1, String arg2) {
        Log.d("ZLiZH", "channel---onSetTagResult---" + arg2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onTextMessage(Context context, XGPushTextMessage arg1) {
        Log.d("ZLiZH", "channel---onTextMessage---" + arg1.getTitle() + "----" + arg1.getCustomContent());
        long timeMillis = System.currentTimeMillis();
        int id = (int) timeMillis % 1000;
        Intent nIntent = new Intent();
        String key = "";
        
        if (!TextUtils.isEmpty(arg1.getCustomContent())) {
            BasePushBean bean = JsonUtil.toObject(arg1.getCustomContent(), BasePushBean.class);
            if (bean == null) {
                return;
            }
            key = bean.getKey();
        }
        
        if (key.equals("contactClientBroker")) {
            nIntent.setClass(context, ActMessageContactCustomer.class);
        } else if (key.equals("clientProcessBroker")) {
            nIntent.setClass(context, ActMessageCustomerProgress.class);
        } else if (key.equals("trackClientBroker")) {
            nIntent.setClass(context, ActMessageCustomerTrace.class);
        } else if (key.equals("houseNews")) {
            nIntent.setClass(context, ActMessageProjectDynamic.class);
        } else if (key.equals("houseRule")) {
            nIntent.setClass(context, ActMessageCooperateRule.class);
        } else if (key.equals("sysNews")) {
            nIntent.setClass(context, ActIndependentStationMessage.class);
        } else {
            nIntent.setClass(context, ActIndependentMain.class);
        }
        
        // 构建Notification
        NotificationManager manager = (NotificationManager) ApplicationUtil.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建一个Notification
        Notification notification = new Notification();
        // 设置显示在手机最上边的状态栏的图标
        notification.icon = R.drawable.ic_launcher;
        // 当当前的notification被放到状态栏上的时候，提示内容
        notification.tickerText = arg1.getTitle();

        // 添加声音提示+
        notification.defaults = Notification.DEFAULT_ALL;
        // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
        notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
        
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 点击状态栏的图标出现的提示信息设置
        Notification.Builder builder = new Notification.Builder(context).setTicker(context.getString(R.string.app_name)).setSmallIcon(R.drawable.ic_launcher);
        builder.setDefaults(Notification.DEFAULT_ALL);
        notification = builder.setContentIntent(pendingIntent).setContentTitle(context.getString(R.string.app_name)).setContentText(arg1.getTitle()).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        String tag = "conversation";
        manager.cancel(tag, id);
        manager.notify(tag, id, notification);
        
    }

    @Override
    public void onUnregisterResult(Context arg0, int arg1) {
        
    }


    class SetXinGeHandler extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            if (responseBody != null) {
                CommonBean bean = JsonUtil.toObject(new String(responseBody), CommonBean.class);
                if (bean != null) {
                    LogUtil.d("ZLOVE", "SetXinGeHandler---status---" + bean.getStatus());
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }
    
}
