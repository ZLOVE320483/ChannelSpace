package com.zlove.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushServiceV3;
import com.zlove.base.ActChannelBase;
import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpResponseHandlerActivity;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.widget.HouseSelectDialog;
import com.zlove.base.widget.HouseSelectDialog.HouseSelectListener;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.project.HouseListBean;
import com.zlove.bean.project.HouseListData;
import com.zlove.bean.project.HouseListItem;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.frag.CustomerMainFragment;
import com.zlove.frag.FriendFragment;
import com.zlove.frag.MeFragment;
import com.zlove.frag.MeFragment.ChangeProjectListener;
import com.zlove.frag.MessageFragment;
import com.zlove.http.ProjectHttpRequest;
import com.zlove.http.UserHttpRequest;

import org.apache.http.Header;

import java.util.List;


public class ActSaleManMain extends ActChannelBase implements OnClickListener, ChangeProjectListener {
    
    public static final int TAB_MESSAGE = 1;
    public static final int TAB_CUSTOMER = 2;
    public static final int TAB_FRIEND = 3;
    public static final int TAB_ME = 4;
    
    private MessageFragment messageFragment;
    private CustomerMainFragment customerFragment;
    private FriendFragment friendFragment;
    private MeFragment meFragment;

    private RadioButton rbTabMessage;
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
        setContentView(R.layout.act_channel_salman_main);
        
        //*************信鸽推送**********
        XGPushConfig.enableDebug(this, true);
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);
        Intent service = new Intent(context, XGPushServiceV3.class);
        context.startService(service);

        rbTabMessage = (RadioButton) findViewById(R.id.id_todo);
        rbTabCustomer = (RadioButton) findViewById(R.id.id_customer);
        rbTabFriend = (RadioButton) findViewById(R.id.id_friend);
        rbTabMe = (RadioButton) findViewById(R.id.id_me);

        rbTabMessage.setOnClickListener(this);
        rbTabCustomer.setOnClickListener(this);
        rbTabFriend.setOnClickListener(this);
        rbTabMe.setOnClickListener(this);

        selectTab(TAB_MESSAGE);
        if (!TextUtils.isEmpty(ChannelCookie.getInstance().getToken()) && !TextUtils.isEmpty(ChannelCookie.getInstance().getSessionId()))
            UserHttpRequest.setXinGeRequest(ChannelCookie.getInstance().getDeviceId(), ChannelCookie.getInstance().getToken(), new SetXinGeHandler());
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(ChannelCookie.getInstance().getCurrentHouseId())) {
            ProjectHttpRequest.requestProjectList("", "", "0", "100", new GetHouseListHandler(this));
        }
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
        int changeIndex = 0;
        if (v == rbTabMessage) {
            changeIndex = TAB_MESSAGE;
        } else if (v == rbTabCustomer) {
            changeIndex = TAB_CUSTOMER;
        } else if (v == rbTabFriend) {
            changeIndex = TAB_FRIEND;
        } else if (v == rbTabMe) {
            changeIndex = TAB_ME;
        }
        selectTab(changeIndex);        
    }
    
    private void selectTab(int tab) {
        if (tab == currentTab) {
            return;
        }
        if (messageFragment != null && messageFragment.isRequest) {
            return;
        }
        currentTab = tab;
        switch (tab) {
            case TAB_MESSAGE: {
                showMessageFrag();
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
            
            default:
                break;
        }
        onTabChange();
    }

    private void onTabChange() {
        rbTabMessage.setChecked(currentTab == TAB_MESSAGE);
        rbTabCustomer.setChecked(currentTab == TAB_CUSTOMER);
        rbTabFriend.setChecked(currentTab == TAB_FRIEND);
        rbTabMe.setChecked(currentTab == TAB_ME);
    }
    
    private void showMessageFrag() {
        if (messageFragment == null) {
            messageFragment = new MessageFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, messageFragment).commitAllowingStateLoss();
    }

    private void showCustomerFrag() {
        if (customerFragment == null) {
            customerFragment = new CustomerMainFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, customerFragment).commitAllowingStateLoss();
    }


    private void showFriendFrag() {
        if (friendFragment == null) {
            friendFragment = new FriendFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, friendFragment).commitAllowingStateLoss();
    }

    private void showMeFrag() {
        if (meFragment == null) {
            meFragment = new MeFragment();
        }
        meFragment.setListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, meFragment).commitAllowingStateLoss();
    }
 
    class GetHouseListHandler extends HttpResponseHandlerActivity<ActSaleManMain> {

        public GetHouseListHandler(ActSaleManMain context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                HouseListBean bean = JsonUtil.toObject(new String(content), HouseListBean.class);
                if (bean != null) {
                    HouseListData data = bean.getData();
                    if (data != null) {
                        List<HouseListItem> items = data.getHouse_list();
                        if (items.size() == 1) {
                            ChannelCookie.getInstance().saveUserCurrentHouseId(items.get(0).getHouse_id());
                            ChannelCookie.getInstance().saveUserCurrentHouseName(items.get(0).getName());
                        } else {
                            if (TextUtils.isEmpty(ChannelCookie.getInstance().getCurrentHouseId())) {
                                HouseSelectDialog dialog = new HouseSelectDialog(ActSaleManMain.this, "请选择楼盘", items, new HouseSelectListener() {
                                    
                                    @Override
                                    public void selectHouse(String houseId, String houseName) {
                                        ChannelCookie.getInstance().saveUserCurrentHouseId(houseId);
                                        ChannelCookie.getInstance().saveUserCurrentHouseName(houseName);
                                    }
                                });
                                dialog.showdialog();
                            }
                        }
                        ChannelCookie.getInstance().saveHouseNum(items.size());
                    }
                }
            }
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
        }
    }

    @Override
    public void change() {
        if (customerFragment != null) {
            customerFragment.changeProject();
        }
    }
    
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
    	super.onActivityResult(arg0, arg1, arg2);
    	if (customerFragment != null) {
			customerFragment.onActivityResult(arg0, arg1, arg2);
		}
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
                    Log.d("ZLOVE", "ChannelSaleMan---SetXinGeHandler---status---" + bean.getStatus());
                } else {
                    Log.d("ZLOVE", "ChannelSaleMan---bean is null");
                }
            } else {
                Log.d("ZLOVE", "ChannelSaleMan---responseBody is null");
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            
        }
        
    }
}
