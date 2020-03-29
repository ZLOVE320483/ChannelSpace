package com.zlove.bean.common;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtendVisitData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1852320283492432536L;

	private String expire_time;
	private String rec_remain_desc;
	private String visit_remain_desc;
	
	public String getExpire_time() {
		return expire_time;
	}
	
    public String getRec_remain_desc() {
        return rec_remain_desc;
    }
    
    public String getVisit_remain_desc() {
        return visit_remain_desc;
    }
	
}
