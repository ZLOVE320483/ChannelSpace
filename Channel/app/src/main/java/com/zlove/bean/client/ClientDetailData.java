package com.zlove.bean.client;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDetailData implements Serializable {

    private static final long serialVersionUID = 5502705942364346744L;
    
    private String client_category_id;
    private String client_category_desc;
    private String client_phone;
    private String client_name;
    private String intent_area;
    private String intent_price_min;
    private String intent_price_max;
    private String intent_location_ids;
    private String intent_location_desc;
    private String house_types;
    private String house_types_desc;
    private String property_types;
    private String property_types_desc;
    
    public String getClient_category_id() {
        return client_category_id;
    }
    
    public String getClient_category_desc() {
        return client_category_desc;
    }
    
    public String getClient_phone() {
        return client_phone;
    }
    
    public String getClient_name() {
        return client_name;
    }
    
    public String getIntent_area() {
        return intent_area;
    }
    
    public String getIntent_price_min() {
        return intent_price_min;
    }
    
    public String getIntent_price_max() {
        return intent_price_max;
    }
    
    public String getIntent_location_ids() {
        return intent_location_ids;
    }
    
    public String getIntent_location_desc() {
        return intent_location_desc;
    }
    
    public String getHouse_types() {
        return house_types;
    }
    
    public String getHouse_types_desc() {
        return house_types_desc;
    }
    
    public String getProperty_types() {
        return property_types;
    }
    
    public String getProperty_types_desc() {
        return property_types_desc;
    }
    
    

}
