package com.zlove.bean.common;

import java.io.Serializable;


public class CityInfo implements Serializable, IAlphabetSection {

    private static final long serialVersionUID = -6680297945525676850L;

    private int type;

    private String sectionText;
    
    private String name;
    
    private String cityId;
    
    @Override
    public int getSectionType() {
        return type;
    }

    @Override
    public void setSectionType(int type) {
        this.type = type;
    }

    @Override
    public String getSectionText() {
        return sectionText;
    }

    @Override
    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }

    @Override
    public int getSection() {
        return 0;
    }

    @Override
    public IAlphabetSection instanceSectionItem() {
        return new CityInfo();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCityId() {
        return cityId;
    }
    
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

}
