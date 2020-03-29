package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCustomerTraceListItem implements Serializable {

    private static final long serialVersionUID = -2177890957765038057L;
    
    private String status;
    private String content;
    private MessageCustomerTraceExtra extra;
    private String user_message_id;
    
    public String getStatus() {
        return status;
    }
    
    public String getContent() {
        return content;
    }
    
    public MessageCustomerTraceExtra getExtra() {
        return extra;
    }
    
    public String getUser_message_id() {
        return user_message_id;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
