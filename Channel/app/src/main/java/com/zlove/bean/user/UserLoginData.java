package com.zlove.bean.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginData implements Serializable {

    private static final long serialVersionUID = 4728526341171170653L;
    
    private String user_id;
    private String session_id;
    private String gender;
    private String status;
    private String email;
    private String signature;
    private String bithday;
    private String avatar;
    private String realname;
    
    public String getUser_id() {
        return user_id;
    }
    
    public String getSession_id() {
        return session_id;
    }
    
    public String getGender() {
        return gender;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public String getBithday() {
        return bithday;
    }
    
    public String getRealname() {
        return realname;
    }
    
    public void setRealname(String realname) {
        this.realname = realname;
    }

}
