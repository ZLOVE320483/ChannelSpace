package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectItemRuleList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3483057478461704049L;
	
	private String house_rule_id;
	private String title;
	private String cooperate_time;
	
	public String getHouse_rule_id() {
		return house_rule_id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getCooperate_time() {
		return cooperate_time;
	}

}
