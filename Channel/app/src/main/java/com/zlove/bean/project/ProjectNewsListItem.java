package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectNewsListItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8384504214847817377L;
	
	private String house_news_id;
	private String title;
	private String summary;
	private String create_time;
	
	public String getHouse_news_id() {
		return house_news_id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public String getCreate_time() {
		return create_time;
	}

}
