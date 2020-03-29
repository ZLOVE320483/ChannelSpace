package com.zlove.act.independent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushServiceV3;
import com.zlove.app.NetWorkDaemon;
import com.zlove.base.ActChannelBase;
import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.LogUtil;
import com.zlove.base.widget.CommonDialog;
import com.zlove.bean.common.CommonBean;
import com.zlove.channel.R;
import com.zlove.config.ChannelCookie;
import com.zlove.frag.independent.IndependentCustomerFragment;
import com.zlove.frag.independent.IndependentFriendFragment;
import com.zlove.frag.independent.IndependentMeFragment;
import com.zlove.frag.independent.IndependentProjectFragment;
import com.zlove.frag.independent.IndependentToDoFragment;
import com.zlove.http.UserHttpRequest;

import org.apache.http.Header;


public class ActIndependentMain extends ActChannelBase implements OnClickListener {
    private NetWorkDaemon networkDaemon;

    public static final int TAB_PROJECT = 1;
    public static final int  TAB_CUSTOMER= 2;
    public static final int TAB_FRIEND = 3;
    public static final int TAB_ME = 4;
    public static final int TAB_TODO = 5;
    
    private IndependentProjectFragment projectFragment;
    private IndependentToDoFragment todoFragment;
    private IndependentCustomerFragment customerFragment;
    private IndependentFriendFragment friendFragment;
    private IndependentMeFragment meFragment;
    
    private RadioButton rbTabProject;
    private RadioButton rbTabToDo;
    private RadioButton rbTabCustomer;
    private RadioButton rbTabFriend;
    private RadioButton rbTabMe;
    
    private int currentTab = 0;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (!TextUtils.isEmpty(ChannelCookie.getInstance().getToken()) && !TextUtils.isEmpty(ChannelCookie.getInstance().getSessionId()))
            UserHttpRequest.setXinGeRequest(ChannelCookie.getInstance().getDeviceId(), ChannelCookie.getInstance().getToken(), new SetXinGeHandler());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_channel_independent_main);


        //*************信鸽推送**********
        XGPushConfig.enableDebug(this, true);
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);
        Intent service = new Intent(context, XGPushServiceV3.class);
        context.startService(service);
        
        networkDaemon = new NetWorkDaemon();

        rbTabProject = (RadioButton) findViewById(R.id.id_project);
        rbTabToDo = (RadioButton) findViewById(R.id.id_todo);
        rbTabCustomer = (RadioButton) findViewById(R.id.id_customer);
        rbTabFriend = (RadioButton) findViewById(R.id.id_friend);
        rbTabMe = (RadioButton) findViewById(R.id.id_me);

        rbTabProject.setOnClickListener(this);
        rbTabToDo.setOnClickListener(this);
        rbTabCustomer.setOnClickListener(this);
        rbTabFriend.setOnClickListener(this);
        rbTabMe.setOnClickListener(this);

        selectTab(TAB_PROJECT);
        if (!TextUtils.isEmpty(ChannelCookie.getInstance().getToken()) && !TextUtils.isEmpty(ChannelCookie.getInstance().getSessionId()))
            UserHttpRequest.setXinGeRequest(ChannelCookie.getInstance().getDeviceId(), ChannelCookie.getInstance().getToken(), new SetXinGeHandler());

    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        networkDaemon.unregisterReceiver();
        super.onDestroy();
    }
    
    long lastPressedTime = 0;
    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if ((now - lastPressedTime) > 1000) {
            lastPressedTime = System.currentTimeMillis();
            showShortToast("再按一次退出程序");
        } else {
            this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        int changeIndex;
        if (v == rbTabProject) {
            changeIndex = TAB_PROJECT;
            selectTab(changeIndex);
        } else if (v == rbTabCustomer) {
            if (ChannelCookie.getInstance().isLoginPass()) {
                changeIndex = TAB_CUSTOMER;
                selectTab(changeIndex);
            } else {
                gotoLoginFirst();
            }
        } else if (v == rbTabFriend) {
            if (ChannelCookie.getInstance().isLoginPass()) {
                changeIndex = TAB_FRIEND;
                selectTab(changeIndex);
            } else {
                gotoLoginFirst();
            }
        } else if (v == rbTabMe) {
            if (ChannelCookie.getInstance().isLoginPass()) {
                changeIndex = TAB_ME;
                selectTab(changeIndex);
            } else {
                gotoLoginFirst();
            }
        } else if (v == rbTabToDo) {
            if (ChannelCookie.getInstance().isLoginPass()) {
                changeIndex = TAB_TODO;
                selectTab(changeIndex);
            } else {
                gotoLoginFirst();
            }
        }
        onTabChange();
    }

    CommonDialog dialog;
    private void gotoLoginFirst() {
        if (dialog == null) {
            dialog = new CommonDialog(this, new CommonDialog.ConfirmAction() {
                @Override
                public void confirm() {
                    Intent intent = new Intent(ActIndependentMain.this, ActIndependentLogin.class);
                    startActivity(intent);
                }
            }, "温馨提示", "请先登录,再进行操作", "去登录");
        }
        dialog.showdialog();
    }

    private void selectTab(int tab) {
        if (tab == currentTab) {
            return;
        }
        if (todoFragment != null && todoFragment.isRequest) {
            return;
        }
        currentTab = tab;
        switch (tab) {
            case TAB_PROJECT: {
                showProjectFrag();
                break;
            }
            case TAB_CUSTOMER: {
                showCustomerFrag();
                break;
            }
            case TAB_FRIEND: {
                showFriendFrag();
                break;
            }
            case TAB_ME: {
                showMeFrag();
                break;
            }
            case TAB_TODO: {
                showToDoFrag();
                break;
            }
            default:
                break;
        }
        onTabChange();
    }

    private void onTabChange() {
        rbTabProject.setChecked(currentTab == TAB_PROJECT);
        rbTabCustomer.setChecked(currentTab == TAB_CUSTOMER);
        rbTabFriend.setChecked(currentTab == TAB_FRIEND);
        rbTabMe.setChecked(currentTab == TAB_ME);
        rbTabToDo.setChecked(currentTab == TAB_TODO);
    }

    private void showCustomerFrag() {
        if (customerFragment == null) {
            customerFragment = new IndependentCustomerFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, customerFragment).commitAllowingStateLoss();
    }

    private void showProjectFrag() {
        if (projectFragment == null) {
            projectFragment = new IndependentProjectFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, projectFragment).commitAllowingStateLoss();
    }

    private void showFriendFrag() {
        if (friendFragment == null) {
            friendFragment = new IndependentFriendFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, friendFragment).commitAllowingStateLoss();
    }

    private void showMeFrag() {
        if (meFragment == null) {
            meFragment = new IndependentMeFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, meFragment).commitAllowingStateLoss();
    }
    
    private void showToDoFrag() {
        if (todoFragment == null) {
            todoFragment = new IndependentToDoFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, todoFragment).commitAllowingStateLoss();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putInt("currentTab", currentTab);
        }
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            currentTab = savedInstanceState.getInt("currentTab");
            selectTab(currentTab);
        }
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
