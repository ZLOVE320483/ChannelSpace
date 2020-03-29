package com.zlove.bean.independent;

import java.io.Serializable;


public class StationMessageInfo implements Serializable {

    private static final long serialVersionUID = 1925385765463921499L;
    
    private String title;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

}
