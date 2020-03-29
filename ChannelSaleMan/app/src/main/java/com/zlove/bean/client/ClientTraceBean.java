package com.zlove.bean.client;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientTraceBean implements Serializable {

    private static final long serialVersionUID = 4500608364393606797L;
    
    private int status;
    private String message;
    private ClientTraceData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ClientTraceData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
}
