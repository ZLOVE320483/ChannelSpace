package com.zlove.bean.friend;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendListData implements Serializable {

    private static final long serialVersionUID = 2284239631208611867L;
    
    private CommonPageInfo page_info;
    private List<FriendListItem> friend_list;
    
    public CommonPageInfo getPage_info() {
        return page_info;
    }
    
    public List<FriendListItem> getFriend_list() {
        return friend_list;
    }
    
    

}
