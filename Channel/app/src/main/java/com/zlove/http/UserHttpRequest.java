package com.zlove.http;

import com.zlove.base.http.AsyncHttpResponseHandler;
import com.zlove.base.http.HttpClient;
import com.zlove.base.http.RequestParams;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.FileUtil;
import com.zlove.base.util.MD5Util;
import com.zlove.channel.R;


public class UserHttpRequest extends HttpClient {
    
    public static final String BASE_URL = ApplicationUtil.getApplicationContext().getResources().getString(R.string.channel_base_url);
    public static final String GET_VERIFY_CODE_URL = BASE_URL + "user/registerBoundPhone";
    public static final String REGISTER_URL = BASE_URL + "user/register";
    public static final String LOGIN_URL = BASE_URL + "user/login";
    public static final String FORGET_PWD_URL = BASE_URL + "user/forgetPassword";
    public static final String CHECK_PHONE_CODE_URL = BASE_URL + "user/checkPhoneCode";
    public static final String UPDATE_NEW_PWD_URL = BASE_URL + "user/updateNewPassword";
    
    public static final String MODIFY_USER_NAME_URL = BASE_URL + "user/setRealname";
    public static final String MODIFY_USER_GENDER_URL = BASE_URL + "user/setGender";
    public static final String MODIFY_USER_AVATAR_URL = BASE_URL + "user/uploadAvatar";
    public static final String MODIFY_PWD_URL = BASE_URL + "user/setNewPassword";
    public static final String LOGOUT_URL = BASE_URL + "user/logout";
    public static final String GET_CODE_FOR_UPDATE_PHONE_URL = BASE_URL + "user/getCodeForUpdatePhone";
    public static final String UPDATE_PHONE_URL = BASE_URL + "user/updateNewPhone";
    public static final String USER_FEEDBACK_URL = BASE_URL + "feedback/feedback";
    public static final String USER_ADD_BANK_CARD_URL = BASE_URL + "user/setBankInfo";
    public static final String USER_GET_BANK_CARD_URL = BASE_URL + "user/getBankInfo";
    public static final String SET_XIN_GE_TOKEN_URL = BASE_URL + "push/setXingeToken";
    
    public static void userVerifyCodeRequest(String account, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("phone", account);
        post(ApplicationUtil.getApplicationContext(), GET_VERIFY_CODE_URL, params, responseHandler);
    }
    
    public static void userRegisterRequest(String account, String password, String code, String realname, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("phone", account);
        params.put("password", MD5Util.GetMD5Code(password));
        params.put("code", code);
        params.put("realname", realname);
        post(ApplicationUtil.getApplicationContext(), REGISTER_URL, params, responseHandler);
    }
    
    public static void userLoginRequest(String account, String password, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("username", account);
        params.put("password", MD5Util.GetMD5Code(password));
        post(ApplicationUtil.getApplicationContext(), LOGIN_URL, params, responseHandler);
    }
    
    public static void userForgetPwdRequest(String account, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("phone", account);
        post(ApplicationUtil.getApplicationContext(), FORGET_PWD_URL, params, responseHandler);
    }
    
    public static void userCheckPhoneCodeRequest(String account, String code, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("phone", account);
        params.put("code", code);
        post(ApplicationUtil.getApplicationContext(), CHECK_PHONE_CODE_URL, params, responseHandler);
    }
    
    public static void userUpdateNewPwdRequest(String account, String newPwd, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("phone", account);
        params.put("new_password", MD5Util.GetMD5Code(newPwd));
        post(ApplicationUtil.getApplicationContext(), UPDATE_NEW_PWD_URL, params, responseHandler);
    }
    
    public static void userModifyPwdRequest(String old_password, String newPwd, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("old_password", MD5Util.GetMD5Code(old_password));
        params.put("new_password", MD5Util.GetMD5Code(newPwd));
        post(ApplicationUtil.getApplicationContext(), MODIFY_PWD_URL, params, responseHandler);
    }
    
    public static void userLogout(AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        post(ApplicationUtil.getApplicationContext(), LOGOUT_URL, params, responseHandler);
    }
    
    public static void userModifyNameRequest(String userName, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("realname", userName);
        post(ApplicationUtil.getApplicationContext(), MODIFY_USER_NAME_URL, params, responseHandler);
    }
    
    public static void userModifyGenderRequest(String gender, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("gender", gender);
        post(ApplicationUtil.getApplicationContext(), MODIFY_USER_GENDER_URL, params, responseHandler);
    }
    
    public static void userModifyAvatarRequest(String path, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        String avatar = FileUtil.encodeBase64File(path);
        params.put("avatar", avatar);
        post(ApplicationUtil.getApplicationContext(), MODIFY_USER_AVATAR_URL, params, responseHandler);
    }
    
    public static void getCodeForUpdatePhone(String account, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("phone", account);
        post(ApplicationUtil.getApplicationContext(), GET_CODE_FOR_UPDATE_PHONE_URL, params, responseHandler);
    }
    
    public static void userUpdatePhoneRequest(String account, String code, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("phone", account);
        params.put("code", code);
        post(ApplicationUtil.getApplicationContext(), UPDATE_PHONE_URL, params, responseHandler);
    }
    
    public static void userFeedBack(String content, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("content", content);
        post(ApplicationUtil.getApplicationContext(), USER_FEEDBACK_URL, params, responseHandler);
    }
    
    public static void userAddBankRequest(String bank_num, String bank_name, String bank_user,AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("bank_num", bank_num);
        params.put("bank_name", bank_name);
        params.put("bank_user", bank_user);
        post(ApplicationUtil.getApplicationContext(), USER_ADD_BANK_CARD_URL, params, responseHandler);
    }
    
    public static void userGetBankRequest(AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        post(ApplicationUtil.getApplicationContext(), USER_GET_BANK_CARD_URL, params, responseHandler);
    }
    
    public static void setXinGeRequest(String deviceId, String token, AsyncHttpResponseHandler responseHandler) {
        setClientHeader();
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("device_id", deviceId);
        post(ApplicationUtil.getApplicationContext(), SET_XIN_GE_TOKEN_URL, params, responseHandler);
    }
}
