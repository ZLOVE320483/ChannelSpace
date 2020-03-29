package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageProjectDynamicExtra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8206148276291185401L;
	
	private String house_name;
	private String title;
	private String news_id;
	private String send_time;
	
	public String getHouse_name() {
		return house_name;
	}
	public String getTitle() {
		return title;
	}
	public String getNews_id() {
		return news_id;
	}
	public String getSend_time() {
		return send_time;
	}
	
	

}
