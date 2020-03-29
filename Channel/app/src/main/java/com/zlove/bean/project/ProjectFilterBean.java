package com.zlove.bean.project;

import java.io.Serializable;

public class ProjectFilterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1821237730856085961L;
	
	private String areaId;
	private String houseType;
	private String propertyType;
	
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
	

}
