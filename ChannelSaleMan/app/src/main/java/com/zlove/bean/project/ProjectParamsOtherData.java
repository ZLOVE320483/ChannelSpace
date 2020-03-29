package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectParamsOtherData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1495556910271835085L;
	
	private String traffic;
	private String hospital;
	private String school;
	private String business;
	
	public String getTraffic() {
		return traffic;
	}
	public String getHospital() {
		return hospital;
	}
	public String getSchool() {
		return school;
	}
	public String getBusiness() {
		return business;
	}

	
}
