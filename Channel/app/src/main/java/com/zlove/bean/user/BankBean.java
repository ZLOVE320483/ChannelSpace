package com.zlove.bean.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8173838816827775298L;

	private int status;
	private String message;
	private BankListItem data;
	private long server_time;
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public BankListItem getData() {
		return data;
	}
	public long getServer_time() {
		return server_time;
	}
	
	
}
