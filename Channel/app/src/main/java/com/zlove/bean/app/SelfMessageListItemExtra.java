package com.zlove.bean.app;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfMessageListItemExtra implements Serializable {

    private static final long serialVersionUID = 4332579237479916141L;
    
    private String title;
    private String summary;
    private String news_id;
    private String send_time;
    
    public String getTitle() {
        return title;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public String getNews_id() {
        return news_id;
    }
    
    public String getSend_time() {
        return send_time;
    }
    
}
