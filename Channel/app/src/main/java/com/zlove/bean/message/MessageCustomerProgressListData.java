package com.zlove.bean.message;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCustomerProgressListData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1114478467237301897L;
	
	private CommonPageInfo page_info;
	private List<MessageCustomerProgressListItem> message_list;
	
	public CommonPageInfo getPage_info() {
		return page_info;
	}
	
	public List<MessageCustomerProgressListItem> getMessage_list() {
		return message_list;
	}

}
