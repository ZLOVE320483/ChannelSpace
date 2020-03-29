package com.zlove.bean.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserChangePwdBean implements Serializable {

    private static final long serialVersionUID = -1386294253551955664L;

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
