package com.zlove.bean.app;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfMessageListBean implements Serializable {

    private static final long serialVersionUID = 4842309254512169300L;
    
    private int status;
    private String message;
    private SelfMessageListData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public SelfMessageListData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
    

}
