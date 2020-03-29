package com.zlove.bean.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageHomeData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4097877373114190368L;
	
	private int contact_client_num;
	private int process_client_num;
	private int track_client_num;
	private int house_news_num;
	private int cooperate_num;
	
	public int getContact_client_num() {
		return contact_client_num;
	}
	public int getProcess_client_num() {
		return process_client_num;
	}
	public int getTrack_client_num() {
		return track_client_num;
	}
	public int getHouse_news_num() {
		return house_news_num;
	}
	public int getCooperate_num() {
		return cooperate_num;
	}
	
	

}
