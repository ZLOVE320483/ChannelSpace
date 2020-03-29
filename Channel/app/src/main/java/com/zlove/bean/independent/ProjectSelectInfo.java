package com.zlove.bean.independent;

import java.io.Serializable;


public class ProjectSelectInfo implements Serializable {

    private static final long serialVersionUID = 4691070420955941433L;
    
    private String area;
    private String name;
    
    public String getArea() {
        return area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

}
