package com.zlove.bean.client;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientListData implements Serializable {

    private static final long serialVersionUID = -3670983301623298702L;
    
    private CommonPageInfo page_info;
    private List<ClientListItem> client_list;
    
    public CommonPageInfo getPage_info() {
        return page_info;
    }
    
    public List<ClientListItem> getClient_list() {
        return client_list;
    }
    
    
}
