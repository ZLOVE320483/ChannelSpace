package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectItemHouseList implements Serializable {

    private static final long serialVersionUID = 3323887448109954291L;
    
    private String house_id;
    private String city_id; 
    private String name;
    private String code;
    private String thumb;
    private String price;
    private String price_desc;
    private String house_types;
    private String area_desc;
    private String house_rule_desc;
    
    public String getArea_desc() {
        return area_desc;
    }
    
    public String getHouse_id() {
        return house_id;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getCity_id() {
        return city_id;
    }
    
    public String getThumb() {
        return thumb;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPrice() {
        return price;
    }
    
    public String getHouse_types() {
        return house_types;
    }
    
    public String getPrice_desc() {
        return price_desc;
    }
    
    public String getHouse_rule_desc() {
		return house_rule_desc;
	}
}
