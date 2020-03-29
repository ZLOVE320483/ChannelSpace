
package com.zlove.bean.independent;

import java.io.Serializable;

import android.annotation.SuppressLint;

import com.zlove.bean.common.IAlphabetSection;

public class FriendSelectInfo implements Serializable, IAlphabetSection {

    private static final long serialVersionUID = -5971190802087497734L;

    private int type;
    private String sectionText;
    private String name;
    private String phoneNum;

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
        return new FriendSelectInfo();
    }

}
