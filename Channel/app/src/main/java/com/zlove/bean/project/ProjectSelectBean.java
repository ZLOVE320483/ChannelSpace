package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSelectBean implements Serializable {

    private static final long serialVersionUID = 5534488745254676664L;
    
    private int status;
    private String message;
    private ProjectSelectData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ProjectSelectData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
    

}
