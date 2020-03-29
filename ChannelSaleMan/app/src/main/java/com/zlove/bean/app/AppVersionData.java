package com.zlove.bean.app;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AppVersionData implements Serializable {

    private static final long serialVersionUID = -2006337053273286459L;
    
    private String version_name;
    private String is_force;
    private String url;
    private String release_note;
    
    public String getVersion_name() {
        return version_name;
    }
    
    public String getIs_force() {
        return is_force;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getRelease_note() {
        return release_note;
    }
    
}
