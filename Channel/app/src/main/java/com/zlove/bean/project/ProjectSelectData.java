package com.zlove.bean.project;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSelectData implements Serializable {

    private static final long serialVersionUID = 7671717220879942045L;
    
    private List<ProjectSelectListItem> house_list;
    
    public List<ProjectSelectListItem> getHouse_list() {
        return house_list;
    }

}
