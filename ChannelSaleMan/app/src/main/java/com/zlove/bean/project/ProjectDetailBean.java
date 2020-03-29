package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetailBean implements Serializable {

    private static final long serialVersionUID = 3424569273113989794L;
    
    private int status;
    private String message;
    private ProjectDetailData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ProjectDetailData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
    
}
