package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCustomerProgressListItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2965615121850189885L;
	
	private String status;
	private String content;
	private MessageCustomerProgressListItemExtra extra;
	private String user_message_id;
	public String getStatus() {
		return status;
	}
	public String getContent() {
		return content;
	}
	public MessageCustomerProgressListItemExtra getExtra() {
		return extra;
	}
	public String getUser_message_id() {
		return user_message_id;
	}
	
    public void setStatus(String status) {
        this.status = status;
    }
	

}
