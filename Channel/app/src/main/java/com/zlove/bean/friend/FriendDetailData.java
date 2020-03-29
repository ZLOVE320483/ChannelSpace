package com.zlove.bean.friend;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendDetailData implements Serializable {

    private static final long serialVersionUID = 6420851864630005343L;
    
    private String name;
    private String rec_num;
    private String visit_num;
    private String intent_num;
    private String order_num;
    private String finish_num;
    private String client_num;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRec_num() {
        return rec_num;
    }
    
    public void setRec_num(String rec_num) {
        this.rec_num = rec_num;
    }
    
    public String getVisit_num() {
        return visit_num;
    }
    
    public void setVisit_num(String visit_num) {
        this.visit_num = visit_num;
    }
    
    public String getIntent_num() {
        return intent_num;
    }
    
    public void setIntent_num(String intent_num) {
        this.intent_num = intent_num;
    }
    
    public String getOrder_num() {
        return order_num;
    }
    
    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }
    
    public String getFinish_num() {
        return finish_num;
    }
    
    public void setFinish_num(String finish_num) {
        this.finish_num = finish_num;
    }
    
    public String getClient_num() {
        return client_num;
    }
    
    public void setClient_num(String client_num) {
        this.client_num = client_num;
    }
    
}
