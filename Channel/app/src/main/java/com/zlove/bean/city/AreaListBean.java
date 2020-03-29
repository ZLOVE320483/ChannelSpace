package com.zlove.bean.city;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaListBean implements Serializable {

    private static final long serialVersionUID = -6928065782162848526L;
    
    private int status;
    private String message;
    private AreaListData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public AreaListData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
}
