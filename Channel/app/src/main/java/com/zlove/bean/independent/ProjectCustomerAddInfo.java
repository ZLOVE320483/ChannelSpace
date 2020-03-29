package com.zlove.bean.independent;

import java.io.Serializable;


public class ProjectCustomerAddInfo implements Serializable {

    private static final long serialVersionUID = 5876952130150569555L;
    
    private String name;
    private String phoneNum;
    private boolean isReport;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhoneNum() {
        return phoneNum;
    }
    
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    public boolean isReport() {
        return isReport;
    }
    
    public void setReport(boolean isReport) {
        this.isReport = isReport;
    }
    
    

}
