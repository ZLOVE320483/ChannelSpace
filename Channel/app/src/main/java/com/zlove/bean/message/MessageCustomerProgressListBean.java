package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCustomerProgressListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 437599598346366794L;

	private int status;
	private String message;
	private MessageCustomerProgressListData data;
	private long server_time;
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public MessageCustomerProgressListData getData() {
		return data;
	}
	public long getServer_time() {
		return server_time;
	}
	
}
