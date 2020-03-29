package com.zlove.bean.client;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDetailBean implements Serializable {

    private static final long serialVersionUID = 8387079391282917492L;
    
    private int status;
    private String message;
    private ClientDetailData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ClientDetailData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
    

}
