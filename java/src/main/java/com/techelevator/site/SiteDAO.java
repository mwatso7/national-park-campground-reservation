package com.techelevator.site;

import java.util.List;

public interface SiteDAO {

	public List<Site> searchSiteAvailabilityByCampground(long campground_id, String from_date, String to_date);
	
	public List<Site> searchSiteAvailabilityByPark(long park_id, String from_date, String to_date);
	
	public Site searchSiteByCampAndNum(long campground_id, long site_number);
}
