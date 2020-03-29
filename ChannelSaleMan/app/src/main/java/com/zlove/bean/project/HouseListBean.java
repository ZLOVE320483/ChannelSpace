package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class HouseListBean implements Serializable {

    private static final long serialVersionUID = 5675260858483491528L;
    
    private int status;
    private String message;
    private HouseListData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public HouseListData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
}
