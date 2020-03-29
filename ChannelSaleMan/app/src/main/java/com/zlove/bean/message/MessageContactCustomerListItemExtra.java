package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageContactCustomerListItemExtra implements Serializable {

    private static final long serialVersionUID = -3284259051005085988L;

    private String category_id;
    private String revisit_time;
    private String house_name;
    private String broker_name;
    private String client_name;
    private String client_phone;
    private String client_id;
    private String send_time;
    private String house_id;
    private int type;
    
    public int getType() {
        return type;
    }
    
    public String getCategory_id() {
        return category_id;
    }
    
    public String getRevisit_time() {
        return revisit_time;
    }
    
    public String getHouse_name() {
        return house_name;
    }
    
    public String getBroker_name() {
        return broker_name;
    }
    
    public String getClient_name() {
        return client_name;
    }
    
    public String getClient_phone() {
        return client_phone;
    }
    
    public String getClient_id() {
        return client_id;
    }
    
    public String getSend_time() {
        return send_time;
    }
    
    public String getHouse_id() {
		return house_id;
	}
}
