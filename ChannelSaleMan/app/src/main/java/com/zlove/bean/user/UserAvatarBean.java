package com.zlove.bean.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAvatarBean implements Serializable {

    private static final long serialVersionUID = 5541510368954053533L;
    
    private int status;
    private String message;
    private UserAvatarData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public UserAvatarData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }

}
