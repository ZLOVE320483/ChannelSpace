package com.zlove.bean.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankListItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -205434418553504874L;
	
	private String bank_name;
	private String bank_num;
	private String bank_user;
	
	public String getBank_name() {
		return bank_name;
	}
	public String getBank_num() {
		return bank_num;
	}
	public String getBank_user() {
		return bank_user;
	}
	
	

}
