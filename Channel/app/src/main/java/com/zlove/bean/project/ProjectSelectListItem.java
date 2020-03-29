package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSelectListItem implements Serializable {

    private static final long serialVersionUID = -547769390928773014L;
    
    private String house_id;
    private String name;
    private String area_desc;
    private String is_recommend;
    
    public String getHouse_id() {
        return house_id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getArea_desc() {
        return area_desc;
    }
    
    public String getIs_recommend() {
		return is_recommend;
	}
}
