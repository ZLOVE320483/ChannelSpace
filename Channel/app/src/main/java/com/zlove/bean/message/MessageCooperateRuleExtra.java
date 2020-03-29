package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCooperateRuleExtra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5848991080757985470L;

	private String house_name;
	private String title;
	private String rule_id;
	private String send_time;
	
	public String getHouse_name() {
		return house_name;
	}
	public String getTitle() {
		return title;
	}
	public String getRule_id() {
		return rule_id;
	}
	public String getSend_time() {
		return send_time;
	}
	
	

}
