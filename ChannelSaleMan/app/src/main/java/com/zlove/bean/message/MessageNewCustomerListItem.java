package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageNewCustomerListItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5634136514508585372L;
	
	private String status;
	private String content;
	private MessageNewCustomerListItemExtra extra;
	private String user_message_id;
	
	public String getStatus() {
		return status;
	}
	public String getContent() {
		return content;
	}
	public MessageNewCustomerListItemExtra getExtra() {
		return extra;
	}
	public String getUser_message_id() {
		return user_message_id;
	}
	
    public void setStatus(String status) {
        this.status = status;
    }
}
