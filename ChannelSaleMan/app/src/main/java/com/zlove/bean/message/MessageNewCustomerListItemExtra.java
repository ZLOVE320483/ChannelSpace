package com.zlove.bean.message;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageNewCustomerListItemExtra implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6067815580915972865L;

	public String getCategory_id() {
		return category_id;
	}
	public String getIntent_locations_desc() {
		return intent_locations_desc;
	}
	public String getHouse_type_desc() {
		return house_type_desc;
	}
	public String getBroker_name() {
		return broker_name;
	}
	private String house_id;
    private String house_name;
	private String category_id;
	private String intent_locations_desc;
	private String house_type_desc;
	private String broker_name;
	private String intent_price_min;
	private String intent_price_max;
	private String intent_property_desc;

	public String getIntent_price_min() {
		return intent_price_min;
	}
	public String getIntent_price_max() {
		return intent_price_max;
	}
	public String getIntent_property_desc() {
		return intent_property_desc;
	}
	private String client_id;
	private String client_name;
	private String client_phone;
	private String send_time;

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

	public String getHouse_id() {
		return house_id;
	}

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }
}
