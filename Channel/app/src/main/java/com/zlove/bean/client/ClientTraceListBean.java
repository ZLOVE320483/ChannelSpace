package com.zlove.bean.client;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientTraceListBean implements Serializable {

    private static final long serialVersionUID = 907885483193602641L;
    
    private int status;
    private String message;
    private ClientTraceListData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ClientTraceListData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
}
