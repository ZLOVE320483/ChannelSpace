package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectRuleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4603630589533376352L;
	
	private int status;
	private String message;
	private ProjectRuleData data;
	private long server_time;
	
	public int getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public ProjectRuleData getData() {
		return data;
	}
	
	public long getServer_time() {
		return server_time;
	}

}
