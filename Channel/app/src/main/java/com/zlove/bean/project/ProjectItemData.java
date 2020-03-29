package com.zlove.bean.project;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectItemData implements Serializable {

    private static final long serialVersionUID = 3323887448109954291L;
    
    private List<ProjectItemHouseList> house_list;
    private CommonPageInfo page_info;
    
    public List<ProjectItemHouseList> getHouse_list() {
        return house_list;
    }
    
    public CommonPageInfo getPage_info() {
		return page_info;
	}
}
