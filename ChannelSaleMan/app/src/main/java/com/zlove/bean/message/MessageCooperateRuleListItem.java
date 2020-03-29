package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCooperateRuleListItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9037662435196111196L;
	
	private String status;
	private String content;
	private MessageCooperateRuleExtra extra;
	private String user_message_id;
	
	public String getStatus() {
		return status;
	}
	public String getContent() {
		return content;
	}
	public MessageCooperateRuleExtra getExtra() {
		return extra;
	}

    public String getUser_message_id() {
        return user_message_id;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
