package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import android.annotation.SuppressLint;

import com.zlove.bean.common.IAlphabetSection;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSelectSaleManListItem implements Serializable, IAlphabetSection {

    private static final long serialVersionUID = -1671510121358462533L;

    private int type;
    private String sectionText;
    private String realname;
    private String salesman_id;
    private String phone;
    
    public String getRealname() {
        return realname;
    }
    
    public String getSalesman_id() {
        return salesman_id;
    }
    
    public String getPhone() {
        return phone;
    }

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

    @SuppressLint("DefaultLocale")
    @Override
    public int getSection() {
        return 0;
    }

    @Override
    public IAlphabetSection instanceSectionItem() {
        return new ProjectSelectSaleManListItem();
    }
}
