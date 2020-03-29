package com.zlove.bean.project;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectNewsData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4384395946102554394L;
	
	private List<ProjectNewsListItem> house_news_list;
	private CommonPageInfo page_info;
	
	public List<ProjectNewsListItem> getHouseNewsList() {
		return house_news_list;
	}
	
	public CommonPageInfo getPage_info() {
		return page_info;
	}

}
