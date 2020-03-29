package com.zlove.bean.client;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientTraceListData implements Serializable {

    private static final long serialVersionUID = 1173303680225074357L;
    
    private CommonPageInfo page_info;
    private List<ClientTraceListItem> trace_list;
    
    public CommonPageInfo getPage_info() {
        return page_info;
    }
    
    public List<ClientTraceListItem> getTrace_list() {
        return trace_list;
    }
    
    

}
