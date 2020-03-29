package com.zlove.bean.friend;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendRecommendListItem implements Serializable {

    private static final long serialVersionUID = -510209995870294311L;
    
    private String status;
    private String client_category_id;
    private String client_id;
    private String client_name;
    private String client_phone;
    private String intent_house_types;
    private String intent_house_types_desc;
    private String intent_property_types;
    private String intent_property_types_desc;
    private String create_time;
    private String intent_price_min;
    private String intent_price_max;
    private String intent_location_desc;
    
    public String getIntent_location_desc() {
		return intent_location_desc;
	}
    
    public String getIntent_price_max() {
		return intent_price_max;
	}
    
    public String getIntent_price_min() {
		return intent_price_min;
	}
    
    public String getStatus() {
        return status;
    }
    
    public String getClient_category_id() {
        return client_category_id;
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
    
    public String getIntent_house_types() {
        return intent_house_types;
    }
    
    public String getIntent_house_types_desc() {
        return intent_house_types_desc;
    }
    
    public String getIntent_property_types() {
        return intent_property_types;
    }
    
    public String getIntent_property_types_desc() {
        return intent_property_types_desc;
    }
    
    public String getCreate_time() {
        return create_time;
    }
    
}