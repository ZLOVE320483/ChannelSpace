package com.zlove.bean.message;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageNewCustomerListData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3051802534301618630L;
	
	private CommonPageInfo page_info;
	private List<MessageNewCustomerListItem> message_list;
	
	public CommonPageInfo getPage_info() {
		return page_info;
	}
	
	public List<MessageNewCustomerListItem> getMessage_list() {
		return message_list;
	}
	
}
