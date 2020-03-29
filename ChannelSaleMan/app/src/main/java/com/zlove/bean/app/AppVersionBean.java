package com.zlove.bean.app;

import java.io.Serializable;


public class AppVersionBean implements Serializable {

    private static final long serialVersionUID = 6625707164555929146L;
    
    private int status;
    private String message;
    private AppVersionData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public AppVersionData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
}
