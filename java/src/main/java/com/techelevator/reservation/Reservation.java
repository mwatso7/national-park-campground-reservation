package com.techelevator.reservation;

public class Reservation {
	
	/*
	 * Member Data
	 */
	
	private long reservation_id;
	private long site_id;
	private String name;
	private String from_date;
	private String to_date;
	private String create_date;
	
	/*
	 * Methods -- Setters
	 */
	
	public void setReservation_id(long reservation_id) {
		this.reservation_id = reservation_id;
	}
	
	public void setSite_id(long site_id) {
		this.site_id = site_id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	/*
	 * Methods -- Getters
	 */
	
	public long getReservation_id() {
		return reservation_id;
	}

	public long getSite_id() {
		return site_id;
	}

	public String getName() {
		return name;
	}

	public String getFrom_date() {
		return from_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public String getCreate_date() {
		return create_date;
	}

	/*
	 * Methods -- Other
	 */
	
	@Override
	public String toString() {
		return "Reservation [reservation_id=" + reservation_id + ", site_id=" + site_id + ", name=" + name
				+ ", from_date=" + from_date + ", to_date=" + to_date + ", create_date=" + create_date + "]";
	}	
	
}
