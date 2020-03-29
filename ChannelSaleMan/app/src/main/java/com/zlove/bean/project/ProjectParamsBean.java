package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectParamsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 498239630782122057L;
	
	private int status;
	private String message;
	private ProjectParamsData data;
	private long server_time;
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public ProjectParamsData getData() {
		return data;
	}
	public long getServer_time() {
		return server_time;
	}

}
