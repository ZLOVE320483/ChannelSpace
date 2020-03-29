package com.zlove.bean.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginData implements Serializable {

    private static final long serialVersionUID = 4728526341171170653L;
    
    private String user_id;
    private String company_id;
    private String username;
    private String session_id;
    private String status;
    private String realname;
    private String gender;
    private String email;
    private int house_num;
    private String avatar;
    
    public int getHouse_num() {
        return house_num;
    }
    
    public String getUser_id() {
        return user_id;
    }
    
    public String getCompany_id() {
        return company_id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getSession_id() {
        return session_id;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getRealname() {
        return realname;
    }
    
    public String getGender() {
        return gender;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
}
