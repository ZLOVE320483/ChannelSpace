package com.zlove.bean.client;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZLOVE on 2016/12/27.
 */
public class ClientRecommendHouseListItem implements Serializable {

    private String id;
    private String is_valided;
    private String is_disabled;
    private int is_expired;
    private String expired_time;
    private String house_id;
    private String house_name;
    private String client_category_id;
    private String salesman_id;
    private String salesman_name;
    private String salesman_phone;
    private String feedback_category_id;
    private String intent_location_ids;
    private String intent_house_types;
    private String intent_property_types;
    private String intent_price_min;
    private String intent_price_max;
    private String intent_area;
    private String status;
    private int visit_refused;
    private String rec_remain_desc;
    private String visit_remain_desc;
    private String type;
    private String broker_id;
    private String broker_name;
    private String broker_phone;
    private String rec_continue_desc;
    private String visit_continue_desc;
    private String recommend_time;
    private String not_effect_time;
    private String effect_time;
    private String visit_time;
    private String intent_time;
    private String order_time;
    private String finish_time;
    private String re_visite_time;
    private List<ClientTraceListItem> trace_list;

    public String getIs_valided() {
        return is_valided;
    }

    public String getId() {
        return id;
    }

    public String getIs_disabled() {
        return is_disabled;
    }

    public int getIs_expired() {
        return is_expired;
    }

    public String getExpired_time() {
        return expired_time;
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

    public String getFeedback_category_id() {
        return feedback_category_id;
    }

    public String getIntent_location_ids() {
        return intent_location_ids;
    }

    public String getIntent_house_types() {
        return intent_house_types;
    }

    public String getIntent_property_types() {
        return intent_property_types;
    }

    public String getIntent_price_min() {
        return intent_price_min;
    }

    public String getIntent_price_max() {
        return intent_price_max;
    }

    public String getIntent_area() {
        return intent_area;
    }

    public String getStatus() {
        return status;
    }

    public int getVisit_refused() {
        return visit_refused;
    }

    public String getRec_remain_desc() {
        return rec_remain_desc;
    }

    public String getVisit_remain_desc() {
        return visit_remain_desc;
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

    public String getRec_continue_desc() {
        return rec_continue_desc;
    }

    public String getVisit_continue_desc() {
        return visit_continue_desc;
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

    public List<ClientTraceListItem> getTrace_list() {
        return trace_list;
    }

}
