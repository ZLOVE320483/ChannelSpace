package com.zlove.bean.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

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
	private String from_way;
	private String visit_time;
	private String from_type;
	private String type;
	private String broker_id;
	private String broker_name;
	private String broker_phone;
	private String status;
	private String rec_remain_desc;
	private String visit_remain_desc;
	private String recommend_time;
	private String not_effect_time;
	private String effect_time;
	private String intent_time;
	private String is_expired;
	private String is_valided;
	private String is_disabled;
	private int visit_refused;
	private String rec_continue_desc;
	private String visit_continue_desc;

	public String getRec_continue_desc() {
		return rec_continue_desc;
	}

	public String getVisit_continue_desc() {
		return visit_continue_desc;
	}

	public int getVisit_refused() {
        return visit_refused;
    }
	
	public String getIs_expired() {
		return is_expired;
	}

	public String getStatus() {
		return status;
	}

	public String getRec_remain_desc() {
		return rec_remain_desc;
	}

	public String getVisit_remain_desc() {
		return visit_remain_desc;
	}

	public String getRecommend_time() {
		return recommend_time;
	}

	public String getNot_effect_time() {
		return not_effect_time;
	}

	public String getEffect_time() {
		return effect_time;
	}

	public String getIntent_time() {
		return intent_time;
	}

	public String getOrder_time() {
		return order_time;
	}

	public String getFinish_time() {
		return finish_time;
	}

	public String getRe_visite_time() {
		return re_visite_time;
	}

	private String order_time;
	private String finish_time;
	private String re_visite_time;

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

	public String getFrom_way() {
		return from_way;
	}

	public String getVisit_time() {
		return visit_time;
	}

	public String getFrom_type() {
		return from_type;
	}

	public String getType() {
		return type;
	}

	public String getBroker_id() {
		return broker_id;
	}

	public String getBroker_name() {
		return broker_name;
	}

	public String getBroker_phone() {
		return broker_phone;
	}
	
	public String getIs_valided() {
		return is_valided;
	}

	public String getIs_disabled() {
		return is_disabled;
	}
}
