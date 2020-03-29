package com.zlove.bean.common;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonPageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1797092266432162491L;
	
	private int page_index;
	private String page_size;
	
	public int getPage_index() {
		return page_index;
	}
	
	public String getPage_size() {
		return page_size;
	}

}
