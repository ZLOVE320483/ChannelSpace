package com.zlove.bean.client;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientSelectData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5820746945645816538L;
	
	private List<ClientSelectListItem> client_list;
	
	public List<ClientSelectListItem> getClient_list() {
		return client_list;
	}

}
