package com.zlove.bean.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAvatarData implements Serializable {

    private static final long serialVersionUID = 3340861894408624584L;
    
    private String url;
    
    public String getUrl() {
        return url;
    }
}
