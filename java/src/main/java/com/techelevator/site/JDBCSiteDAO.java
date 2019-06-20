package com.techelevator.site;

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
	public List<Site> searchSiteAvailability(long campground_id, String from_date, String to_date) {
		ArrayList<Site> sites = new ArrayList<Site>();
		String sqlGetAvailableSites = "SELECT * FROM site WHERE site_id NOT IN " + 
								 	  "(SELECT site_id FROM reservation " + 
								 	  "WHERE from_date BETWEEN ? AND ? " +
								 	  "OR to_date BETWEEN ? AND ?) AND campground_id = ? LIMIT 5";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSites, from_date, to_date, from_date, to_date, campground_id);
		while(results.next()) {
			Site theSite = mapRowToCampground(results);
			sites.add(theSite);
		}
		return sites;
	}
	
	private Site mapRowToCampground(SqlRowSet results) {
		Site theSite = new Site();
		theSite.setSite_id(results.getLong("site_id"));
		theSite.setCampground_id(results.getLong("campground_id"));
		theSite.setSite_number(results.getLong("site_number"));
		theSite.setMax_occupancy(results.getLong("max_number"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMax_rv_length(results.getLong("max_rv_length"));
		theSite.setUtilities(results.getBoolean("utilities"));
		return theSite;
	}

}
