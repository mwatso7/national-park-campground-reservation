package com.techelevator.site;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.Campground;

public class JDBCSiteDAO implements SiteDAO{
	
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> searchSiteAvailabilityByCampground(long campground_id, String from_date, String to_date) {
		ArrayList<Site> sites = new ArrayList<Site>();
		LocalDate from = LocalDate.parse(from_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate to = LocalDate.parse(to_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		String sqlGetAvailableSites = "SELECT * FROM site WHERE site_id NOT IN " + 
			 	  					  "(SELECT site_id FROM reservation " + 
			 	  					  "WHERE from_date BETWEEN ? AND ? " +
			 	  					  "OR to_date BETWEEN ? AND ?) AND campground_id = ? LIMIT 5";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSites, from, to, from, to, campground_id);
		while(results.next()) {
			Site theSite = mapRowToCampground(results);
			sites.add(theSite);
		}
		return sites;
	}
	
	@Override
	public List<Site> searchSiteAvailabilityByPark(long park_id, String from_date, String to_date) {
		ArrayList<Site> sites = new ArrayList<Site>();
		LocalDate from = LocalDate.parse(from_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate to = LocalDate.parse(to_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		String sqlGetAvailableSites = "SELECT * FROM site WHERE site_id NOT IN " + 
									  "(SELECT site_id FROM reservation " + 
									  "WHERE from_date BETWEEN ? AND ? " +
									  "OR to_date BETWEEN ? AND ?) AND campground_id IN " +
									  "(SELECT campground_id FROM campground WHERE park_id = ?) LIMIT 5";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSites, from, to, from, to, park_id);
		while(results.next()) {
			Site theSite = mapRowToCampground(results);
			sites.add(theSite);
		}
		return sites;
	}
	
	@Override
	public Site searchSiteByCampAndNum(long campground_id, long site_number) {
		String sqlGetSite = "SELECT * FROM site WHERE campground_id = ? AND site_number = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetSite, campground_id, site_number);
		
		Site theSite = new Site();
		
		while(results.next()) {
			theSite = mapRowToCampground(results);
		}
		
		return theSite;
	}
	
	private Site mapRowToCampground(SqlRowSet results) {
		Site theSite = new Site();
		theSite.setSite_id(results.getLong("site_id"));
		theSite.setCampground_id(results.getLong("campground_id"));
		theSite.setSite_number(results.getLong("site_number"));
		theSite.setMax_occupancy(results.getLong("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMax_rv_length(results.getLong("max_rv_length"));
		theSite.setUtilities(results.getBoolean("utilities"));
		return theSite;
	}

}
