package com.zlove.bean.message;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zlove.bean.common.CommonPageInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageContactCustomerListData implements Serializable {

    private static final long serialVersionUID = 5707123546898892312L;
    
    private CommonPageInfo page_info;
    private List<MessageContactCustomerListItem> message_list;
    
    public CommonPageInfo getPage_info() {
        return page_info;
    }
    
    public List<MessageContactCustomerListItem> getMessage_list() {
        return message_list;
    }

}
