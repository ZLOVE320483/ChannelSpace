package com.zlove.bean.project;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectRuleData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 318397970332859171L;
	
	private CommonPageInfo page_info;
	private List<ProjectItemRuleList> house_rule_list;
	
	public List<ProjectItemRuleList> getHouse_rule_list() {
		return house_rule_list;
	}
	
    public CommonPageInfo getPage_info() {
        return page_info;
    }

}
