package com.zlove.bean.independent;

import java.io.Serializable;

public class MessageProjectDynamicInfo implements Serializable {
	
	private static final long serialVersionUID = 467139612314020178L;
	
	private String projectName;
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectName() {
		return projectName;
	}
}
