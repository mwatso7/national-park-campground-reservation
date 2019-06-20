package com.techelevator.reservation;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO{
	
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long save(Reservation theReservation) {
		String sqlInsertReservation = "INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date) VALUES(?, ?, ?, ?, ?, ?)";
		theReservation.setReservation_id(getNextReservationId());
		jdbcTemplate.update(sqlInsertReservation, theReservation.getReservation_id(), theReservation.getSite_id(), theReservation.getName(), theReservation.getFrom_date(), theReservation.getTo_date(), theReservation.getCreate_date());
		return theReservation.getReservation_id();
	}
	
	private Long getNextReservationId() {
		SqlRowSet getNextReservationId = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')"); 
		if (getNextReservationId.next()) {
			return getNextReservationId.getLong(1);
		} else {
			throw new RuntimeException("someting went wrong while getting the Reservation ID");
		}
	}

}
