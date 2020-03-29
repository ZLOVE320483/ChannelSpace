package com.zlove.bean.message;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCustomerTraceListData implements Serializable {

    private static final long serialVersionUID = 6948902324244410657L;
    
    private CommonPageInfo page_info;
    private List<MessageCustomerTraceListItem> message_list;
    
    public CommonPageInfo getPage_info() {
        return page_info;
    }
    
    public List<MessageCustomerTraceListItem> getMessage_list() {
        return message_list;
    }
    
}
