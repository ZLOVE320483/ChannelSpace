package com.zlove.bean.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVerifyCodeData implements Serializable {

    private static final long serialVersionUID = -6206611801483978295L;
    
    private int code;
    
    public int getCode() {
        return code;
    }

}
