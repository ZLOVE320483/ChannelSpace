package com.zlove.bean.city;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CombineCityListData implements Serializable {

    private static final long serialVersionUID = -4223313473899289424L;
    
    private List<CombineCityListItem> combine_city_list;
    
    public List<CombineCityListItem> getCombine_city_list() {
        return combine_city_list;
    }

}
