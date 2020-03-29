package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectRuleDetailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7025054797098928743L;

	private int status;
	private String message;
	private ProjectRuleDetailData data;
	private long server_time;
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public ProjectRuleDetailData getData() {
		return data;
	}
	public long getServer_time() {
		return server_time;
	}
	
	
}
