package com.zlove.bean.project;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectParamsBaseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4559683731107246438L;
	
	private String price_desc;
	private String property_types;
	private String decoration_types;
	private String property_costs;
	private String cover_area;
	private String house_types;
	private String own_rate;
	private String volume_rate;
	private String building_area;
	private String total_area;
	private String family_num;
	private String sold_num;
	private String parking_rate;
	private String open_time;
	private String entry_time;
	private String developer;
	private String property_company;
	private String address;
	private String company_name;
	public String getPrice_desc() {
		return price_desc;
	}
	public String getProperty_types() {
		return property_types;
	}
	public String getDecoration_types() {
		return decoration_types;
	}
	public String getProperty_costs() {
		return property_costs;
	}
	public String getCover_area() {
		return cover_area;
	}
	public String getHouse_types() {
		return house_types;
	}
	public String getOwn_rate() {
		return own_rate;
	}
	public String getVolume_rate() {
		return volume_rate;
	}
	public String getBuilding_area() {
		return building_area;
	}
	public String getTotal_area() {
		return total_area;
	}
	public String getFamily_num() {
		return family_num;
	}
	public String getSold_num() {
		return sold_num;
	}
	public String getParking_rate() {
		return parking_rate;
	}
	public String getOpen_time() {
		return open_time;
	}
	public String getEntry_time() {
		return entry_time;
	}
	public String getDeveloper() {
		return developer;
	}
	public String getProperty_company() {
		return property_company;
	}
	public String getAddress() {
		return address;
	}
	public String getCompany_name() {
		return company_name;
	}
	
}
