package com.zlove.bean.independent;

import java.io.Serializable;

public class MessageCustomerProgressInfo implements Serializable {

	private static final long serialVersionUID = 8063150872528209990L;
	
	private String status;
	private String name;
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
