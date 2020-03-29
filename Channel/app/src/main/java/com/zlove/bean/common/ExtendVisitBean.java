package com.zlove.bean.common;

import java.io.Serializable;

public class ExtendVisitBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3927287472337768772L;
	
	private int status;
	private String message;
	private ExtendVisitData data;
	private long server_time;
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public ExtendVisitData getData() {
		return data;
	}
	public long getServer_time() {
		return server_time;
	}
	
}
