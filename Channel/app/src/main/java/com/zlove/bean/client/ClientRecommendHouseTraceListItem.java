package com.zlove.bean.client;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRecommendHouseTraceListItem implements Serializable {

    private static final long serialVersionUID = -7664190386250743026L;
    
    private String user;
    private String trace_time;
    private String content;
    
    public String getUser() {
        return user;
    }
    
    public String getTrace_time() {
        return trace_time;
    }
    
    public String getContent() {
        return content;
    }
    
}
