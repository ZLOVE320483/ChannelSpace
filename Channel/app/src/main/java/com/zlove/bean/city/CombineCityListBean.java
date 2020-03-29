package com.zlove.bean.city;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CombineCityListBean implements Serializable {

    private static final long serialVersionUID = 8720588655718059033L;
    
    private int status;
    private String message;
    private CombineCityListData data;
    private long server_time;
    
    public int getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public CombineCityListData getData() {
        return data;
    }
    
    public long getServer_time() {
        return server_time;
    }
    
    

}
