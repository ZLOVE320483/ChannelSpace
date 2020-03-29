package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCustomerTraceListBean implements Serializable {

    private static final long serialVersionUID = -2415058663511151054L;
    
    private int status;
    private String message;
    private MessageCustomerTraceListData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public MessageCustomerTraceListData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
    

}
