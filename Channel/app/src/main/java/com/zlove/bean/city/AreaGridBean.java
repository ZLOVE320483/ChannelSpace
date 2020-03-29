package com.zlove.bean.city;

import java.io.Serializable;

public class AreaGridBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -418440378126505715L;
	

	private boolean isSelect;
	private AreaListItem item;
	public boolean isSelect() {
		return isSelect;
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public AreaListItem getItem() {
		return item;
	}
	public void setItem(AreaListItem item) {
		this.item = item;
	}
	
	



}
