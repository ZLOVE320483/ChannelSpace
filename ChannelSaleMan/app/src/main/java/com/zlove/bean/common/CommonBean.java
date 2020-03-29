package com.zlove.bean.common;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonBean implements Serializable {

    private static final long serialVersionUID = 6390067020092002158L;
    
    private int status;
    private String message;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public long getServer_time() {
        return server_time;
    }

}
