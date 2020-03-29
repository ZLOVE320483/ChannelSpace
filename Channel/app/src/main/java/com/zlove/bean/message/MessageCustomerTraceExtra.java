package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCustomerTraceExtra implements Serializable {

    private static final long serialVersionUID = 9215867832954913442L;
    
    private String category_id;
    private String status;
    private String content;
    private String salesman;
    private String client_id;
    private String client_name;
    private String client_phone;
    private String send_time;
    private String status_desc;
    
    public String getCategory_id() {
        return category_id;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getContent() {
        return content;
    }
    
    public String getSalesman() {
        return salesman;
    }
    
    public String getClient_id() {
        return client_id;
    }
    
    public String getClient_name() {
        return client_name;
    }
    
    public String getClient_phone() {
        return client_phone;
    }
    
    public String getSend_time() {
        return send_time;
    }
    
    public String getStatus_desc() {
        return status_desc;
    }
    
}
