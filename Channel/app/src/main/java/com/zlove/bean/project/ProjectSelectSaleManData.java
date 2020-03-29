package com.zlove.bean.project;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSelectSaleManData implements Serializable {

    private static final long serialVersionUID = 7449104641264996378L;
    
    private List<ProjectSelectSaleManListItem> friend_list;
    private List<ProjectSelectSaleManListItem> unfriend_list;
    
    public List<ProjectSelectSaleManListItem> getFriend_list() {
        return friend_list;
    }
    
    public List<ProjectSelectSaleManListItem> getUnfriend_list() {
        return unfriend_list;
    }
    
}
