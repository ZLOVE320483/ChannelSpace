package com.zlove.bean.client;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientListItem implements Serializable {

    private static final long serialVersionUID = 5549987390737733989L;
    
    private int status;
    private String type;
    private String client_id;
    private String category_id;
    private String phone;
    private String name;
    private String broker_name;
    private String salesman;
    private String price;
    private String intent_price_min;
    private String intent_price_max;
    private String house_name;
    private String intent_location_ids;
    private String house_area;
    private String house_types;
    private String property_types;
    private String create_time;
    private String from_type;
    private String is_expired;
    private String is_valided;
    
    public String getFrom_type() {
		return from_type;
	}
    
    public String getType() {
        return type;
    }
    
    public String getBroker_name() {
        return broker_name;
    }
    
    public String getIntent_price_max() {
        return intent_price_max;
    }
    
    public String getIntent_price_min() {
        return intent_price_min;
    }
    
    public String getHouse_area() {
        return house_area;
    }
    
    public int getStatus() {
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
    
    public String getIs_expired() {
		return is_expired;
	}
    
    public String getIs_valided() {
		return is_valided;
	}

}
