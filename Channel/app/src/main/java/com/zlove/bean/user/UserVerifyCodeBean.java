package com.zlove.bean.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVerifyCodeBean implements Serializable {

    private static final long serialVersionUID = -5681114339529348193L;
    
    private int status;
    private String message;
    private UserVerifyCodeData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public UserVerifyCodeData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }

}
