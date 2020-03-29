package com.zlove.bean.city;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaListData implements Serializable {

    private static final long serialVersionUID = -1599030965767612347L;
    
    private List<AreaListItem> area_list;
    
    public List<AreaListItem> getArea_list() {
        return area_list;
    }

}
