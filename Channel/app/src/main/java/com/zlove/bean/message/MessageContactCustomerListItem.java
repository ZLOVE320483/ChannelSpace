package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageContactCustomerListItem implements Serializable {

    private static final long serialVersionUID = 1149776361029346065L;
    
    private String status;
    private String content;
    private MessageContactCustomerListItemExtra extra;
    private String user_message_id;
    
    public String getStatus() {
        return status;
    }
    
    public String getContent() {
        return content;
    }
    
    public MessageContactCustomerListItemExtra getExtra() {
        return extra;
    }
    
    public String getUser_message_id() {
		return user_message_id;
	}
    
    public void setStatus(String status) {
        this.status = status;
    }

}
