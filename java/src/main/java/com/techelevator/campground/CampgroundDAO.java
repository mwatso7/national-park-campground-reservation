package com.techelevator.campground;

import java.util.List;

public interface CampgroundDAO {
	
	public List<Campground> getCampgroundsByParkId(long park_id);
	
	public Campground getCampgroundByCampgroundId(long campground_id);
	
}
