package com.techelevator.campground;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


public class JDBCCampgroundDAO implements CampgroundDAO{

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> getCampgroundsByParkId(long park_id) {
		ArrayList<Campground> parks = new ArrayList<Campground>();
		String sqlGetAllCampgrounds = "SELECT * FROM campground WHERE park_id = ? ORDER BY campground_id";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgrounds, park_id);
		while(results.next()) {
			Campground theCampground = mapRowToCampground(results);
			parks.add(theCampground);
		}
		return parks;
	}
	
	@Override
	public Campground getCampgroundByCampgroundId(long campground_id) {
		Campground theCampground = new Campground();
		String sqlGetAllCampgrounds = "SELECT * FROM campground WHERE campground_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgrounds, campground_id);
		while(results.next()) {
			theCampground = mapRowToCampground(results);
		}
		return theCampground;
	}
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground = new Campground();
		theCampground.setCampground_id(results.getLong("campground_id"));
		theCampground.setPark_id(results.getLong("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpen_from_mm(results.getString("open_from_mm"));
		theCampground.setOpen_to_mm(results.getString("open_to_mm"));
		theCampground.setDaily_fee(results.getDouble("daily_fee"));
		return theCampground;
	}
	
	
}
