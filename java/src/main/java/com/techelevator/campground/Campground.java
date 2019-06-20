package com.techelevator.campground;

public class Campground {
	
	/*
	 * Member Data
	 */
	
	private long campground_id;
	private long park_id;
	private String name;
	private String open_from_mm;
	private String open_to_mm;
	private double daily_fee;
	
	/*
	 * Methods -- Setters
	 */
	
	public void setCampground_id(long campground_id) {
		this.campground_id = campground_id;
	}
	
	public void setPark_id(long park_id) {
		this.park_id = park_id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setOpen_from_mm(String open_from_mm) {
		this.open_from_mm = open_from_mm;
	}
	
	public void setOpen_to_mm(String open_to_mm) {
		this.open_to_mm = open_to_mm;
	}
	
	public void setDaily_fee(double daily_fee) {
		this.daily_fee = daily_fee;
	}
	
	/*
	 * Methods -- Getters
	 */
	
	public long getCampground_id() {
		return campground_id;
	}
	
	public long getPark_id() {
		return park_id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOpen_from_mm() {
		return open_from_mm;
	}
	
	public String getOpen_to_mm() {
		return open_to_mm;
	}
	
	public double getDaily_fee() {
		return daily_fee;
	}
	
	/*
	 * Methods -- Other
	 */
	
	@Override
	public String toString() {
		return "Campground [campground_id=" + campground_id + ", park_id=" + park_id + ", name=" + name
				+ ", open_from_mm=" + open_from_mm + ", open_to_mm=" + open_to_mm + ", daily_fee=" + daily_fee + "]";
	}
	
}
