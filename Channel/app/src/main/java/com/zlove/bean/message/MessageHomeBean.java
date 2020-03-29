package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageHomeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2388852733044120219L;
	
	private int status;
	private String message;
	private MessageHomeData data;
	private String server_time;
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public MessageHomeData getData() {
		return data;
	}
	public String getServer_time() {
		return server_time;
	}
	
	

}
