package com.techelevator;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.*;
import com.techelevator.park.*;
import com.techelevator.reservation.*;
import com.techelevator.site.*;
import com.techelevator.view.*;

public class CampgroundCLI {
	
	private Menu menu;
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;
	private ParkDAO parkDAO;
	
	private CampgroundApp campgroundReservationService;

	public static void main(String[] args) {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		menu = new Menu(System.in, System.out);
		
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		siteDAO = new JDBCSiteDAO(datasource);
		parkDAO = new JDBCParkDAO(datasource);
		
		campgroundReservationService = new CampgroundApp(campgroundDAO, reservationDAO, siteDAO, parkDAO);
	}

	public void run() {
		while(true) {
			String[] menuOptions = campgroundReservationService.namesOfAvailableParks();
			String parkSelection = (String)menu.getChoiceFromOptions(menuOptions);
			
			campgroundReservationService.setThePark(parkSelection);

			if(parkSelection.equals("Exit")) {
				System.out.println("Thank you for visiting the Park Reservation portal. Good-bye!");
				System.exit(0);
			}else {
				campgroundReservationService.displayPark();
				run();
			}
		}
	}
}
