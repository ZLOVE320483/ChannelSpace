package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectItemBean implements Serializable {

    private static final long serialVersionUID = 5434439154627315795L;
    
    private int status;
    private String message;
    private ProjectItemData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ProjectItemData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }

}
