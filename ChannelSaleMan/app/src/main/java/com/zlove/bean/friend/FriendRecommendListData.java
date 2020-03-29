package com.zlove.bean.friend;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendRecommendListData implements Serializable {

    private static final long serialVersionUID = -4008562538532477091L;
    
    private CommonPageInfo page_info;
    private List<FriendRecommendListItem> recommend_list;
    
    public CommonPageInfo getPage_info() {
        return page_info;
    }
    
    public List<FriendRecommendListItem> getRecommend_list() {
        return recommend_list;
    }
    
}
