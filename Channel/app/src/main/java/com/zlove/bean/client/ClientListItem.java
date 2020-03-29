package com.zlove.bean.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientListItem implements Serializable {

    private static final long serialVersionUID = 5549987390737733989L;
    
    private String status;
    private String client_id;
    private String category_id;
    private String phone;
    private String name;
    private String salesman;
    private String price;
    private String intent_price_min;
    private String intent_price_max;
    private String house_name;
    private String intent_location_ids;
    private String house_types;
    private String property_types;
    private String create_time;
    private int is_disabled;

    public int getIs_disabled() {
        return is_disabled;
    }

    public String getStatus() {
        return status;
    }
    
    public String getClient_id() {
        return client_id;
    }
    
    public String getCategory_id() {
        return category_id;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIntent_location_ids() {
        return intent_location_ids;
    }
    
    public String getHouse_types() {
        return house_types;
    }
    
    public String getProperty_types() {
        return property_types;
    }
    
    public String getCreate_time() {
        return create_time;
    }

    public String getSalesman() {
        return salesman;
    }
    
    public String getPrice() {
        return price;
    }

    public String getHouse_name() {
        return house_name;
    }
    
    public String getIntent_price_max() {
        return intent_price_max;
    }
    
    public String getIntent_price_min() {
        return intent_price_min;
    }

}
