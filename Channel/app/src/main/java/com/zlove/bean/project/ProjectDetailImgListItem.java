package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetailImgListItem implements Serializable {

    private static final long serialVersionUID = 3764027493726734376L;
    
    private String type;
    private String name;
    private String image;
    private String area;
    private String price_desc;
    private String desc;
    
    public String getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public String getImage() {
        return image;
    }

	public String getArea() {
		return area;
	}

	public String getPrice_desc() {
		return price_desc;
	}

	public String getDesc() {
		return desc;
	}
    
}
