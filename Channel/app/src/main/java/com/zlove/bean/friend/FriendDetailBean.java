package com.zlove.bean.friend;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendDetailBean implements Serializable {

    private static final long serialVersionUID = -7417823694120161931L;
    
    private int status;
    private String message;
    private FriendDetailData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public FriendDetailData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
    

}
