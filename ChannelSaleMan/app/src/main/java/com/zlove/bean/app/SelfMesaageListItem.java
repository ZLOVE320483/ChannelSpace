package com.zlove.bean.app;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfMesaageListItem implements Serializable {

    private static final long serialVersionUID = 1646628855578203304L;
    
    private String content;
    private SelfMessageListItemExtra extra;
    private String type;
    
    public String getContent() {
        return content;
    }
    
    public SelfMessageListItemExtra getExtra() {
        return extra;
    }
    
    public String getType() {
        return type;
    }
}
