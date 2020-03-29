package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSerchByCodeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2312848857578239890L;
	
	private int status;
	private String message;
	private ProjectItemHouseList data;
	private long server_time;
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public ProjectItemHouseList getData() {
		return data;
	}
	public long getServer_time() {
		return server_time;
	}

}
