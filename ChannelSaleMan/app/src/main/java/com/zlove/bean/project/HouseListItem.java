package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class HouseListItem implements Serializable {

    private static final long serialVersionUID = -4896304140271227360L;

    private String house_id;
    private String city_id;
    private String name;
    private String code;
    private String thumb;
    private String price;
    private String price_desc;
    private String property_types;
    private String house_types;
    
    public String getHouse_id() {
        return house_id;
    }
    
    public String getCity_id() {
        return city_id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getThumb() {
        return thumb;
    }
    
    public String getPrice() {
        return price;
    }
    
    public String getPrice_desc() {
        return price_desc;
    }
    
    public String getProperty_types() {
        return property_types;
    }
    
    public String getHouse_types() {
        return house_types;
    }
}
