package com.zlove.bean.app;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfMessageListData implements Serializable {

    private static final long serialVersionUID = -7758719296381754186L;
    
    private CommonPageInfo page_info;
    private List<SelfMesaageListItem> message_list;
    
    public CommonPageInfo getPage_info() {
        return page_info;
    }
    
    public List<SelfMesaageListItem> getMessage_list() {
        return message_list;
    }
    
}
