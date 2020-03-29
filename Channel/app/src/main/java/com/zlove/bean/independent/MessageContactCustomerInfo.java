package com.zlove.bean.independent;

import java.io.Serializable;

public class MessageContactCustomerInfo implements Serializable {

	private static final long serialVersionUID = -7169775934319944644L;
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
