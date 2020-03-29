package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetailReportedCustomerBean implements Serializable {

    private static final long serialVersionUID = 1196977926149698550L;
    
    private int status;
    private String message;
    private ProjectDetailReportedCustomerData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ProjectDetailReportedCustomerData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
}
