package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageContactCustomerListBean implements Serializable {

    private static final long serialVersionUID = -377606541784465711L;
    
    private int status;
    private String message;
    private MessageContactCustomerListData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public MessageContactCustomerListData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
}
