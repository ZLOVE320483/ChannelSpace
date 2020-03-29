package com.zlove.bean.client;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientSelectListItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4796691663761976019L;
	
	private int is_recommended;
	private String client_id;
	private String category_id;
	private String phone;
	private String name;
	
	public int getIs_recommended() {
		return is_recommended;
	}
	public String getClient_id() {
		return client_id;
	}
	public String getCategory_id() {
		return category_id;
	}
	public String getPhone() {
		return phone;
	}
	public String getName() {
		return name;
	}
	
	

}
