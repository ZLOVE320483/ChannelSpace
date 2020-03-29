package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectNewsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1330417434003146338L;
	
	private int status;
	private String message;
	private ProjectNewsData data;
	private long server_time;
	
	public int getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public ProjectNewsData getData() {
		return data;
	}
	
	public long getServer_time() {
		return server_time;
	}

}
