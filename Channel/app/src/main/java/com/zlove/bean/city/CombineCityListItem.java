package com.zlove.bean.city;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CombineCityListItem implements Serializable {

    private static final long serialVersionUID = 4418241235707079523L;
    private String province_id;
    private String city_id;
    private String city_name;
    
    public String getProvince_id() {
        return province_id;
    }
    
    public String getCity_id() {
        return city_id;
    }
    
    public String getCity_name() {
        return city_name;
    }
    
    

}
