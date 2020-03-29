package com.zlove.bean.message;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageProjectDynamicData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2360215589396439982L;
	
	private CommonPageInfo page_info;
	private List<MessageProjectDynamicListItem> message_list;
	
	public CommonPageInfo getPage_info() {
		return page_info;
	}
	
	public List<MessageProjectDynamicListItem> getMessage_list() {
		return message_list;
	}

}
