package com.zlove.bean.client;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRecommendHouseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6622893360831994530L;
	
	private CommonPageInfo page_info;
	private List<ClientRecommendHouseListItem> recommend_house_list;
	
	public CommonPageInfo getPage_info() {
		return page_info;
	}
	public List<ClientRecommendHouseListItem> getRecommend_house_list() {
		return recommend_house_list;
	}
	
	

}
