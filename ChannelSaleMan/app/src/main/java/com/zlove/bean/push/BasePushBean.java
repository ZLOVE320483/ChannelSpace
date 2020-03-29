package com.zlove.bean.push;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BasePushBean implements Serializable {

    private static final long serialVersionUID = 6750538299148965844L;
    
    private String key;

    
    public String getKey() {
        return key;
    }

}
