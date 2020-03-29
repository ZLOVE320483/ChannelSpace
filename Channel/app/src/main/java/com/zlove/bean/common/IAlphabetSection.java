package com.zlove.bean.common;


public interface IAlphabetSection {
    
    public static final int ITEM = 0;

    public static final int SECTION = 1;

    public static final int ALPHABET_START = 'A';

    public static final int ALPHABET_END = 'Z';

    public static final int ALPHABET_OTHER = '#';

    public int getSectionType();

    public void setSectionType(int type);

    public String getSectionText();

    public void setSectionText(String sectionText);

    public int getSection();

    public IAlphabetSection instanceSectionItem();


}
