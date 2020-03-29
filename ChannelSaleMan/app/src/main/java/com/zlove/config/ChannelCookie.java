package com.zlove.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.LogUtil;


public class ChannelCookie {
    private static final String LOG_TAG = ChannelCookie.class.getSimpleName();
    private static ChannelCookie instance;

    protected boolean isFirstStart;
    protected boolean loginPass;
    protected String account;
    protected String password;
    protected String sessionId;
    protected String userName;
    protected String userGender;
    protected String userAvatar;
    protected String currentCityId;
    protected String currentHouseId;
    protected String currentHouseName;
    protected int houseNum;
    protected String deviceId;
    protected String token;
    
    public static ChannelCookie getInstance() {
        if (instance == null) {
            synchronized (LOG_TAG) {
                if (instance == null) {
                    instance = new ChannelCookie();
                }
            }
        }
        return instance;
    }
    
    public void initCookie(Context context) {
        LogUtil.d(LOG_TAG, "initCookie");
        readUserInfo(getUserInfoSharedPre(context));
    }
    
    protected SharedPreferences getUserInfoSharedPre(Context context) {
        return context.getSharedPreferences(ChannelConstant.PRE_KEY_USER_INFO, Context.MODE_PRIVATE);
    }

    protected void readUserInfo(SharedPreferences sp) {
        this.isFirstStart = sp.getBoolean(ChannelConstant.SharePreKey.KEY_FIRST_START, true);
        this.loginPass = sp.getBoolean(ChannelConstant.SharePreKey.KEY_LOGIN_PASS, false);
        this.account = sp.getString(ChannelConstant.SharePreKey.KEY_USER_ACCOUNT, "");
        this.password = sp.getString(ChannelConstant.SharePreKey.KEY_USER_PWD,"");
        this.sessionId = sp.getString(ChannelConstant.SharePreKey.KEY_USER_SESSION_ID,"");
        this.userName = sp.getString(ChannelConstant.SharePreKey.KEY_USER_NAME,"");
        this.userGender = sp.getString(ChannelConstant.SharePreKey.KEY_USER_GENDER,"");
        this.userAvatar = sp.getString(ChannelConstant.SharePreKey.KEY_USER_AVATAR,"");
        this.currentCityId = sp.getString(ChannelConstant.SharePreKey.KEY_USER_CURRENT_CITY_ID,"1602");
        this.currentHouseId = sp.getString(ChannelConstant.SharePreKey.KEY_USER_CURRENT_HOUSE_ID,"");
        this.currentHouseName = sp.getString(ChannelConstant.SharePreKey.KEY_USER_CURRENT_HOUSE_NAME,"");
        this.houseNum = sp.getInt(ChannelConstant.SharePreKey.KEY_USER_HOUSE_NUM, 0);
        this.deviceId = sp.getString(ChannelConstant.SharePreKey.KEY_DEVICE_ID,"");
        this.token = sp.getString(ChannelConstant.SharePreKey.KEY_TOKEN,"");
    }
    
    public void saveUserInfo(String account, String password, String sessionId) {
        this.account = account;
        this.password = password;
        this.sessionId = sessionId;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putBoolean(ChannelConstant.SharePreKey.KEY_LOGIN_PASS, loginPass);
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_ACCOUNT, account);
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_PWD, password);
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_SESSION_ID, sessionId);
        editor.commit();
    }
    
    public void saveFirstStart(boolean isFirstStart) {
        this.isFirstStart = isFirstStart;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putBoolean(ChannelConstant.SharePreKey.KEY_FIRST_START, isFirstStart);
        editor.commit();
    }
    
    public void saveUserName(String userName) {
        this.userName = userName;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_NAME, userName);
        editor.commit();
    }
    
    public void saveUserGender(String gender) {
        this.userGender = gender;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_GENDER, gender);
        editor.commit();
    }
    
    public void saveUserAvatar(String avatar) {
        this.userAvatar = avatar;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_AVATAR, avatar);
        editor.commit();
    }
    
    public void saveUserCurrentCityId(String cityId) {
        this.currentCityId = cityId;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_CURRENT_CITY_ID, cityId);
        editor.commit();
    }
    
    public void saveUserCurrentHouseId(String houseId) {
        this.currentHouseId = houseId;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_CURRENT_HOUSE_ID, houseId);
        editor.commit();
    }
    
    public void saveUserCurrentHouseName(String houseName) {
        this.currentHouseName = houseName;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_CURRENT_HOUSE_NAME, houseName);
        editor.commit();
    }
    
    public void saveHouseNum(int houseNum) {
        this.houseNum = houseNum;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putInt(ChannelConstant.SharePreKey.KEY_USER_HOUSE_NUM, houseNum);
        editor.commit();
    }
    
    public void saveDeviceId(String deviceId) {
        this.deviceId = deviceId;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putString(ChannelConstant.SharePreKey.KEY_DEVICE_ID, deviceId);
        editor.commit();
    }
    
    public void saveToken(String token) {
        this.token = token;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.putString(ChannelConstant.SharePreKey.KEY_TOKEN, token);
        editor.commit();
    }
    
    public void clearUserInfo() {
        this.loginPass = false;
        this.password = "";
        this.sessionId = "";
        this.currentCityId = "";
        this.houseNum = 0;
        Editor editor = getUserInfoSharedPre(ApplicationUtil.getApplicationContext()).edit();
        editor.clear();
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_ACCOUNT, account);
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_CURRENT_HOUSE_ID, currentHouseId);
        editor.putString(ChannelConstant.SharePreKey.KEY_USER_CURRENT_HOUSE_NAME, currentHouseName);
        editor.commit();
    }
    
    public boolean isFirstStart() {
        return isFirstStart;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getAccount() {
        return account;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    
    public boolean isLoginPass() {
        return loginPass;
    }
    
    public void setLoginPass(boolean loginPass) {
        this.loginPass = loginPass;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
    
    public String getUserGender() {
        return userGender;
    }
    
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
    
    public String getUserAvatar() {
        return userAvatar;
    }
    
    public void setCurrentCityId(String currentCityId) {
        this.currentCityId = currentCityId;
    }
    
    public String getCurrentCityId() {
        return currentCityId;
    }
    
    public String getCurrentHouseId() {
        return currentHouseId;
    }
    
    public String getCurrentHouseName() {
		return currentHouseName;
	}
    
    public int getHouseNum() {
        return houseNum;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public String getToken() {
        return token;
    }
}
