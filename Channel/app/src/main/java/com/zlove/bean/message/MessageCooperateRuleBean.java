package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCooperateRuleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -970132275474639314L;
	
	private int status;
	private String message;
	private MessageCooperateRuleData data;
	private long server_time;
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public MessageCooperateRuleData getData() {
		return data;
	}
	public long getServer_time() {
		return server_time;
	}

}
