package com.zlove.bean.friend;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendInfoData implements Serializable {

    private static final long serialVersionUID = -1815133836694519394L;
    
    private String friend_phone;
    private String friend_name;
    private String friend_gender;
    private String category_id;
    private int total_num;
    
    public String getFriend_phone() {
        return friend_phone;
    }
    
    public String getFriend_name() {
        return friend_name;
    }
    
    public String getFriend_gender() {
        return friend_gender;
    }
    
    public String getCategory_id() {
        return category_id;
    }
    
    public int getTotal_num() {
        return total_num;
    }
    
}
