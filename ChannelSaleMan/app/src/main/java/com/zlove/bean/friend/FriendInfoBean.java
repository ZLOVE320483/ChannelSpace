package com.zlove.bean.friend;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendInfoBean implements Serializable {

    private static final long serialVersionUID = 5433492217995337770L;

    private int status;
    private String message;
    private FriendInfoData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public FriendInfoData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
}
