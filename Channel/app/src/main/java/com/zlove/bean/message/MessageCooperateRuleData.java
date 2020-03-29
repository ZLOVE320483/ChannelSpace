package com.zlove.bean.message;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCooperateRuleData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2094437453773199376L;
	
	private CommonPageInfo page_info;
	private List<MessageCooperateRuleListItem> message_list;
	
	public CommonPageInfo getPage_info() {
		return page_info;
	}
	
	public List<MessageCooperateRuleListItem> getMessage_list() {
		return message_list;
	}

}
