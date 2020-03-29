package com.zlove.bean.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginBean implements Serializable {

    private static final long serialVersionUID = -5667005996708272304L;
    
    private int status;
    private String message;
    private long server_time;
    private UserLoginData data;

    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
    public UserLoginData getData() {
        return data;
    }
}
