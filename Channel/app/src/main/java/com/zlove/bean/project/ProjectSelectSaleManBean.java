package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSelectSaleManBean implements Serializable {

    private static final long serialVersionUID = 2215065716164059552L;
    
    private int status;
    private String message;
    private ProjectSelectSaleManData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ProjectSelectSaleManData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
    

}
