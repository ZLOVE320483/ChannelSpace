package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageNewCustomerListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1120666436466044337L;
	
	private int status;
	private String message;
	private MessageNewCustomerListData data;
	private long server_time;
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public MessageNewCustomerListData getData() {
		return data;
	}
	public long getServer_time() {
		return server_time;
	}
	
	

}
