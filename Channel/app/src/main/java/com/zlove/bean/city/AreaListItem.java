package com.zlove.bean.city;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaListItem implements Serializable {

    private static final long serialVersionUID = -6221126441727570669L;
    
    private String id;
    private String name;
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setId(String id) {
		this.id = id;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    

}
