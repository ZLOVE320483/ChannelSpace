package com.zlove.push;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BasePushBean implements Serializable {

    private static final long serialVersionUID = -5137381937485179343L;
    
    private String key;
    
    public String getKey() {
        return key;
    }
}
