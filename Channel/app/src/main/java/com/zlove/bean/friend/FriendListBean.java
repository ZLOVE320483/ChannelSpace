package com.zlove.bean.friend;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendListBean implements Serializable {

    private static final long serialVersionUID = 788048140145137859L;
    
    private int status;
    private String message;
    private FriendListData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public FriendListData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
    

}
