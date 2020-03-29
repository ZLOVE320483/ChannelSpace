package com.zlove.bean.friend;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendListItem implements Serializable {

    private static final long serialVersionUID = -1632965946099503633L;
    
    private String friend_name;
    private String friend_phone;
    private String friend_id;
    private String category_id;
    private String rec_num;
    private String visit_num;
    private String order_num;
    private String finish_num;
    
    public String getFriend_name() {
        return friend_name;
    }
    
    public String getFriend_id() {
        return friend_id;
    }
    
    public String getRec_num() {
        return rec_num;
    }
    
    public String getVisit_num() {
        return visit_num;
    }

    public String getFriend_phone() {
        return friend_phone;
    }

    
    public String getCategory_id() {
        return category_id;
    }

    
    public String getOrder_num() {
        return order_num;
    }

    
    public String getFinish_num() {
        return finish_num;
    }

}
