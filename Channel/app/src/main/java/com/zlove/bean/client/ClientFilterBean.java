package com.zlove.bean.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientFilterBean implements Serializable {

    private static final long serialVersionUID = -5462289834349642366L;
    
    private String categoryId;
    private String houseType;
    private String propertyType;
    private String status;
    private String isDisable;
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public String getHouseType() {
        return houseType;
    }
    
    public String getPropertyType() {
        return propertyType;
    }
    
    public String getStatus() {
        return status;
    }

    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    
    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(String isDisable) {
        this.isDisable = isDisable;
    }
    
}
