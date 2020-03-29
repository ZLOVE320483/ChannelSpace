package com.zlove.bean.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRecommendHouseListItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7691229978977556682L;
	
	private String id;
    private String house_id;
    private String house_name;
    private String client_category_id;
    private String salesman_id;
    private String salesman_name;
    private String salesman_phone;
    private String status;
    private String rec_remain_desc;
    private String visit_remain_desc;
    private String recommend_time;
    private String not_effect_time;
    private String effect_time;
    private String visit_time;
    private String intent_time;
    private String order_time;
    private String finish_time;
    private String re_visite_time;
    private int visit_refused;
    private String is_expired;
    private String is_disabled;
    private String rec_continue_desc;
    private String visit_continue_desc;

    public String getRec_continue_desc() {
        return rec_continue_desc;
    }

    public String getVisit_continue_desc() {
        return visit_continue_desc;
    }

    private List<ClientRecommendHouseTraceListItem> trace_list;
    
    public String getIs_expired() {
		return is_expired;
	}
    
    public String getId() {
        return id;
    }
    
    public String getHouse_id() {
        return house_id;
    }
    
    public String getHouse_name() {
        return house_name;
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
    
    public String getSalesman_phone() {
        return salesman_phone;
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
    
    public String getVisit_time() {
        return visit_time;
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
    
    public List<ClientRecommendHouseTraceListItem> getTrace_list() {
        return trace_list;
    }
    
    public int getVisit_refused() {
		return visit_refused;
	}

    public String getIs_disabled() {
        return is_disabled;
    }

}
