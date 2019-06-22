package com.techelevator.reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {
	
	/*
	 * Member Data
	 */
	
	private long reservation_id;
	private long site_id;
	private String name;
	private LocalDate from_date;
	private LocalDate to_date;
	private LocalDate create_date;
	
	/*
	 * Constructors
	 */
	
	public Reservation(long site_id, String name, LocalDate from_date, LocalDate to_date) {
		this.reservation_id = 0;
		this.site_id = site_id;
		this.name = name;
		this.from_date = from_date;
		this.to_date = to_date;
		this.create_date = LocalDate.now();
	}
	
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
	
	public void setFrom_date(LocalDate from_date) {
		this.from_date = from_date;
	}
	
	public void setTo_date(LocalDate to_date) {
		this.to_date = to_date;
	}
	
	public void setCreate_date(LocalDate create_date) {
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

	public LocalDate getFrom_date() {
		return from_date;
	}

	public LocalDate getTo_date() {
		return to_date;
	}

	public LocalDate getCreate_date() {
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
