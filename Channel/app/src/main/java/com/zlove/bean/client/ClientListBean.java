package com.zlove.bean.client;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientListBean implements Serializable {

    private static final long serialVersionUID = -8432591889965926992L;
    
    private int status;
    private String message;
    private ClientListData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ClientListData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
}
