package com.zlove.bean.independent;

import java.io.Serializable;

public class MessageCustomerTraceInfo implements Serializable {

	private static final long serialVersionUID = 5966846553551989938L;
	
	private String name;
	private String phone;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
