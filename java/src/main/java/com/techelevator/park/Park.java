package com.techelevator.park;

import java.util.Date;

public class Park {
	
	/*
	 * Member Data
	 */

	private long park_id;
	private String name;
	private String location;
	private String establish_date;
	private long area;
	private long visitors;
	private String description;
	
	/*
	 * Methods -- Setters
	 */
	
	public void setPark_id(long park_id) {
		this.park_id = park_id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setEstablish_date(String establish_date) {
		this.establish_date = establish_date;
	}
	
	public void setArea(long area) {
		this.area = area;
	}
	
	public void setVisitors(long visitors) {
		this.visitors = visitors;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	/*
	 * Methods -- Getters
	 */
	
	public long getPark_id() {
		return park_id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getEstablish_date() {
		return establish_date;
	}
	
	public long getArea() {
		return area;
	}
	
	public long getVisitors() {
		return visitors;
	}
	
	public String getDescription() {
		return description;
	}
	
	/*
	 * Methods -- Other
	 */
	
	@Override
	public String toString() {
		return "Park [park_id=" + park_id + ", name=" + name + ", location=" + location + ", establish_date="
				+ establish_date + ", area=" + area + ", visitors=" + visitors + ", description=" + description + "]";
	}
	
}
