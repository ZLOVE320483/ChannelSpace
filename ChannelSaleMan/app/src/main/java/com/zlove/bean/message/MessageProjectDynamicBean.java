package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageProjectDynamicBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7225156843664142493L;
	
	private int status;
	private String message;
	private MessageProjectDynamicData data;
	private long server_time;
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public MessageProjectDynamicData getData() {
		return data;
	}
	public long getServer_time() {
		return server_time;
	}
	
}
