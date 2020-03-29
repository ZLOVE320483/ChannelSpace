package com.zlove.bean.independent;

import java.io.Serializable;


public class CustomerDetailInfo implements Serializable {

    private static final long serialVersionUID = 6737206515722149802L;
    
    private String projectName;
    private String saleManName;
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getSaleManName() {
        return saleManName;
    }
    
    public void setSaleManName(String saleManName) {
        this.saleManName = saleManName;
    }
    
    

}
