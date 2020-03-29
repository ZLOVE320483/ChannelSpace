package com.zlove.bean.friend;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendRecommendListBean implements Serializable {

    private static final long serialVersionUID = 2515260581755051523L;
    
    private int status;
    private String message;
    private FriendRecommendListData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public FriendRecommendListData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
}
