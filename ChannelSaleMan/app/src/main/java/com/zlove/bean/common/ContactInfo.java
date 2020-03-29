
package com.zlove.bean.common;

import java.io.Serializable;

import android.annotation.SuppressLint;
import android.text.TextUtils;

public class ContactInfo implements Serializable, IAlphabetSection {

    private static final long serialVersionUID = 492993321496585088L;

    private int type;

    private String sectionText;

    private String namePinyin;

    private String fullName;

    private String number;

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
        if (TextUtils.isEmpty(getNamePinyin())) {
            return 0;
        } else {
            return getNamePinyin().toUpperCase().charAt(0);
        }
    }

    @Override
    public IAlphabetSection instanceSectionItem() {
        return new ContactInfo();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
