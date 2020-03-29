package com.zlove.bean.project;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetailData implements Serializable {

    private static final long serialVersionUID = -99414241833778257L;
    
    private String name;
    private String code;
    private String price;
    private String price_desc;
    private String location_area;
    private String house_types;
    private String property_types;
    private String lng;
    private String lat;
    private String discount_desc;
    private String house_rule;
    private String house_rule_id;
    private String address;
    private String sales_phone;
    private String cooperate_time;
    
    private List<ProjectDetailImgListItem> house_images;
    
    public String getDiscount_desc() {
        return discount_desc;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getPrice() {
        return price;
    }
    
    public String getPrice_desc() {
        return price_desc;
    }
    
    public String getLocation_area() {
        return location_area;
    }
    
    public String getHouse_types() {
        return house_types;
    }
    
    public String getProperty_types() {
        return property_types;
    }
    
    public String getLng() {
        return lng;
    }
    
    public String getLat() {
        return lat;
    }
    
    public String getHouse_rule() {
        return house_rule;
    }
    
    public String getHouse_rule_id() {
        return house_rule_id;
    }
    
    public List<ProjectDetailImgListItem> getHouse_images() {
        return house_images;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getSales_phone() {
		return sales_phone;
	}
    
    public String getCooperate_time() {
		return cooperate_time;
	}
}
