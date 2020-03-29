package com.zlove.bean.project;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
public class HouseListData implements Serializable {

    private static final long serialVersionUID = 8466977382465296975L;
    
    private CommonPageInfo page_info;
    private List<HouseListItem> house_list;
    
    public CommonPageInfo getPage_info() {
        return page_info;
    }
    
    public List<HouseListItem> getHouse_list() {
        return house_list;
    }
    
}
