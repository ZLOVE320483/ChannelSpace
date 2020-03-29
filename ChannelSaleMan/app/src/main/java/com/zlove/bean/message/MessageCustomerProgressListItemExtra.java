package com.zlove.bean.message;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCustomerProgressListItemExtra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3774280938437405974L;
	
	private String status;
	private String status_desc;
	private String brokername;
	private String content;
	private String client_id;
	private String client_name;
	private String client_phone;
	private String send_time;
	private String house_id;
	private String house_name;
	private int type;
	
	public String getStatus_desc() {
		return status_desc;
	}
    public String getBrokername() {
        return brokername;
    }
	public String getContent() {
		return content;
	}
	public String getClient_id() {
		return client_id;
	}
	public String getClient_name() {
		return client_name;
	}
	public String getClient_phone() {
		return client_phone;
	}
	public String getSend_time() {
		return send_time;
	}
	
	public String getStatus() {
		return status;
	}

	public String getHouse_id() {
		return house_id;
	}
	
    public int getType() {
        return type;
    }

	public String getHouse_name() {
		return house_name;
	}
}
