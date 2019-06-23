package com.techelevator;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.sql.DataSource;


import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.*;
import com.techelevator.park.*;
import com.techelevator.reservation.*;
import com.techelevator.site.*;
import com.techelevator.view.*;

public class CampgroundCLI {
	
	private static final String PARK_MENU_OPTION_CAMPGROUNDS = "View Campgrounds";
	private static final String PARK_MENU_OPTION_RESERVATION = "Search for Reservation";
	private static final String PARK_MENU_OPTION_RETURN = "Return to Previous Screen";
	private static final String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_OPTION_CAMPGROUNDS, PARK_MENU_OPTION_RESERVATION,PARK_MENU_OPTION_RETURN };
	

	private static final String CAMP_MENU_OPTION_RESERVATION = "Search for Reservation";
	private static final String CAMP_MENU_OPTION_RETURN = "Return to Previous Screen";
	private static final String[] CAMP_MENU_OPTIONS = new String[] { CAMP_MENU_OPTION_RESERVATION, CAMP_MENU_OPTION_RETURN };
	
	Park thePark;
	private Menu menu;
	private Scanner in;
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;
	private ParkDAO parkDAO;

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
		this.in = new Scanner(System.in);
		
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		siteDAO = new JDBCSiteDAO(datasource);
		parkDAO = new JDBCParkDAO(datasource);
	}

	public void run() {
		while(true) {
			String[] menuOptions = namesOfAvailableParks();
			String parkSelection = (String)menu.getChoiceFromOptions(menuOptions);
			thePark = parkDAO.getParkByName(parkSelection);

			if(parkSelection.equals("Exit")) {
				System.out.println("Thank you for visiting the Park Reservation portal. Good-bye!");
				System.exit(0);
			}else {
				displayPark();
			}
			
		}
	}
	
	private String[] namesOfAvailableParks() {
		System.out.println("\nSelect a park for further details");
		List<Park> parksToDisplay = parkDAO.getAllParks();
		String[] options = new String[parksToDisplay.size() + 1];
		int i = 1;
		for(Park aPark : parksToDisplay) {
			options[i-1] = aPark.getName();
			i++;
		}
		options[i-1] = "Exit";
		
		return options;
	}
	
	private void displayPark() {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		System.out.println("\n" + thePark.getName() + " National Park");
		System.out.printf("\n%-15s%s", "Location:", thePark.getLocation());
		System.out.printf("\n%-15s%s", "Established:", thePark.getEstablish_date());
		System.out.printf("\n%-15s%s", "Area:", numberFormat.format(thePark.getArea()) + " sq km");
		System.out.printf("\n%-15s%s", "Visitors:", numberFormat.format(thePark.getVisitors()));
		
		System.out.println("\n\n" + thePark.getDescription());
		
		String choice = (String)menu.getChoiceFromOptions(PARK_MENU_OPTIONS);
		
		if (choice.equals(PARK_MENU_OPTION_CAMPGROUNDS)){
			displayCampgroundsAndOptions();
		} else if (choice.equals(PARK_MENU_OPTION_RESERVATION)){
			parkReservationSearch();
		} else {
			run();
		}
	}
	
	private void displayCampgroundsAndOptions() {
		List<Campground> parkCampgrounds = campgroundDAO.getCampgroundsByParkId(thePark.getPark_id());
		
		displayCampgrounds(parkCampgrounds);
		
		String choice = (String)menu.getChoiceFromOptions(CAMP_MENU_OPTIONS);
		
		if (choice.equals(CAMP_MENU_OPTION_RESERVATION)){
			campgroundReservationSearch(parkCampgrounds);
		} else if (choice.equals(CAMP_MENU_OPTION_RETURN)) {
			displayPark();
		}
		
	}
	
	private void displayCampgrounds(List<Campground> parkCampgrounds) {
		System.out.printf("%10s%25s%13s%16s\n", "Name", "Open", "Close", "Daily Fee");
		for (Campground camp : parkCampgrounds) {
			System.out.printf("#%-5s%-25s%-12s%-12s$%.2f\n", camp.getCampground_id(), camp.getName(), camp.getOpen_from_mm(), camp.getOpen_to_mm(), camp.getDaily_fee());
		}
	}
	
	private void parkReservationSearch() {
		List<Site> sitesAvailable = new ArrayList<Site>();
		String arrival = new String();
		String departure = new String();
		boolean isSite = false;
		
		System.out.print("What is the arrival date (yyyy-mm-dd)? ");
		arrival = in.nextLine();
		System.out.print("What is the departure date (yyyy-mm-dd)? ");
		departure = in.nextLine();
		
		sitesAvailable = displayAvailableSites(0, arrival, departure);
		
		System.out.print("\nWhich site should be reserved (enter 0 to cancel)? ");
		long site_number = in.nextLong();
		in.nextLine();
		
		Site chosenSite = new Site();
		for (Site site : sitesAvailable) {
			if (site.getSite_number() == site_number) {
				chosenSite = site;
				isSite = true;
			}
		}
		
		if(site_number == 0) {
			System.out.println("Canceled");
			displayPark();
		} else if(isSite){
			System.out.print("What name should the reservation be made under? ");
			String name = in.nextLine();
			
			makeReservation(chosenSite.getCampground_id(), site_number, name, arrival, departure);
		} else {
			System.out.print("\n*** " + site_number + " is not a valid option ***\n");
			parkReservationSearch();
		}
	}
	
	private void campgroundReservationSearch(List<Campground> parkCampgrounds) {
		List<Site> sitesAvailable = new ArrayList<Site>();
		String arrival = new String();
		String departure = new String();
		boolean isCamp = false;
		boolean isSite = false;
		
		System.out.print("Which campground (enter 0 to cancel)? ");
		long campground_id = in.nextLong();
		in.nextLine();
		
		for (Campground camp : parkCampgrounds) {
			if (camp.getCampground_id() == campground_id) {
				isCamp = true;
			}
		}
		
		if(campground_id == 0) {
			System.out.println("Canceled");
			displayCampgroundsAndOptions();
		} else if (isCamp == true) {
			System.out.print("What is the arrival date (yyyy-mm-dd)? ");
			arrival = in.nextLine();
			System.out.print("What is the departure date (yyyy-mm-dd)? ");
			departure = in.nextLine();
			
			sitesAvailable = displayAvailableSites(campground_id, arrival, departure);
		}
		else {
			System.out.print("\n*** " + campground_id + " is not a valid option ***\n");
			displayCampgroundsAndOptions();
		}
		
		System.out.print("\nWhich site should be reserved (enter 0 to cancel)? ");
		long site_number = in.nextLong();
		in.nextLine();
		
		for (Site site : sitesAvailable) {
			if (site.getSite_number() == site_number) {
				isSite = true;
			}
		}
		
		if(site_number == 0) {
			System.out.println("Canceled");
			displayCampgroundsAndOptions();
		} else if(isSite){
			System.out.print("What name should the reservation be made under? ");
			String name = in.nextLine();
			
			makeReservation(campground_id, site_number, name, arrival, departure);
		} else {
			System.out.print("\n*** " + site_number + " is not a valid option ***\n");
			displayCampgroundsAndOptions();
		}
	}
	
	private List<Site> displayAvailableSites(long camp_id, String arrival, String departure) {
		LocalDate aDate = null;
		LocalDate dDate = null;
		try{
			aDate = LocalDate.parse(arrival, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			dDate = LocalDate.parse(departure, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}catch (Exception e) {
			System.out.println("The dates entered do not appear to be correct. Please try again.");
			if (camp_id == 0) {
				parkReservationSearch();
			} else {
				displayCampgroundsAndOptions();
			}
		}
		
		long days = ChronoUnit.DAYS.between(aDate, dDate);
		List<Site> sitesAvailable = new ArrayList<Site>();
		
		if (camp_id == 0) {
			sitesAvailable = siteDAO.searchSiteAvailabilityByPark(thePark.getPark_id(), arrival, departure);
		} else {
			sitesAvailable = siteDAO.searchSiteAvailabilityByCampground(camp_id, arrival, departure);
		}
		
		System.out.printf("\n%-10s%-15s%-15s%-15s%-10s%s\n", "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost");
		for (Site site : sitesAvailable) {
			Campground chosenCamp = campgroundDAO.getCampgroundByCampgroundId(site.getCampground_id());
			System.out.printf("#%-9s%-15s%-15s%-15s%-10s$%.2f\n", site.getSite_number(), site.getMax_occupancy(), site.isAccessible(), site.getMax_rv_length(), site.isUtilities(), chosenCamp.getDaily_fee()*days);
		}
		
		return sitesAvailable;
	}
	
	private void makeReservation(long campground_id, long site_number, String name, String arrival, String departure) {
		Site theSite = siteDAO.searchSiteByCampAndNum(campground_id, site_number);
		LocalDate aDate = LocalDate.parse(arrival, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate dDate = LocalDate.parse(departure, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		Reservation newReservation = new Reservation(theSite.getSite_id(), name, aDate, dDate);
		
		long confirmation_id = reservationDAO.save(newReservation);
		
		System.out.println("\nThe reservation has been made and the confirmation id is {" + confirmation_id + "}");
	}
}
