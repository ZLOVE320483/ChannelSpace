package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectParamsData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 818657611781565887L;
	
	private ProjectParamsBaseData base_data;
	private ProjectParamsOtherData other;
	private String summary;
	
	public ProjectParamsBaseData getBase_data() {
		return base_data;
	}
	public ProjectParamsOtherData getOther() {
		return other;
	}
	public String getSummary() {
		return summary;
	}
	
}
