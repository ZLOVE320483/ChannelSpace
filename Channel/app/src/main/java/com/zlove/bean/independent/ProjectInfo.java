package com.zlove.bean.independent;


public class ProjectInfo {
    
    private String projectName;
    private String projectPrice;
    private boolean isPraised;
    
    public boolean isPraised() {
        return isPraised;
    }
    
    public void setPraised(boolean isPraised) {
        this.isPraised = isPraised;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectPrice(String projectPrice) {
        this.projectPrice = projectPrice;
    }
    
    public String getProjectPrice() {
        return projectPrice;
    }
}
