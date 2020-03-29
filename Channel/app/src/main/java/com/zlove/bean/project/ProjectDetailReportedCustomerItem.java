package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetailReportedCustomerItem implements Serializable {

    private static final long serialVersionUID = 7642344756041734716L;
    
    private String client_recommended_id;
    private String client_id;
    private String status;
    private String client_name;
    private String client_phone;
    private String client_category_id;
    private String salesman_id;
    private String salesman_name;
    
    public String getClient_recommended_id() {
        return client_recommended_id;
    }
    
    public String getClient_id() {
        return client_id;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getClient_name() {
        return client_name;
    }
    
    public String getClient_phone() {
        return client_phone;
    }
    
    public String getClient_category_id() {
        return client_category_id;
    }
    
    public String getSalesman_id() {
        return salesman_id;
    }
    
    public String getSalesman_name() {
        return salesman_name;
    }
    
}
