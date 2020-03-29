package com.zlove.bean.client;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientSelectBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2321152365255221341L;
	
	private int status;
	private String message;
	private ClientSelectData data;
	private long server_time;
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public ClientSelectData getData() {
		return data;
	}
	public long getServer_time() {
		return server_time;
	}
	
	

}
