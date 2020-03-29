package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectRuleDetailData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6980820739572650517L;
	
	private String house_rule_id;
	private String title;
	private String first_money;
	private String last_money;
	private String money_note;
	private String finish_user;
	private String cooperate_time;
	private String cooperate_rule;
	
	public String getHouse_rule_id() {
		return house_rule_id;
	}
	public String getTitle() {
		return title;
	}
	public String getFirst_money() {
		return first_money;
	}
	public String getLast_money() {
		return last_money;
	}
	public String getMoney_note() {
		return money_note;
	}
	public String getFinish_user() {
		return finish_user;
	}
	public String getCooperate_time() {
		return cooperate_time;
	}
	public String getCooperate_rule() {
		return cooperate_rule;
	}
	
}
