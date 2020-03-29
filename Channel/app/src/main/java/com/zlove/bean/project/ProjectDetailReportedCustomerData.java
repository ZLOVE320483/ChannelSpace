package com.zlove.bean.project;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetailReportedCustomerData implements Serializable {

    private static final long serialVersionUID = 8424910851520122750L;
    
    private CommonPageInfo page_info;
    private List<ProjectDetailReportedCustomerItem> recommended_list;
    
    public CommonPageInfo getPage_info() {
        return page_info;
    }
    
    public List<ProjectDetailReportedCustomerItem> getRecommended_list() {
        return recommended_list;
    }
    
}
