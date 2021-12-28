package com.epam.ta.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.epam.ta.model.AirTravel;
import com.epam.ta.page.BookingAirTravelPage;
import com.epam.ta.page.AirTravelPage;
import com.epam.ta.exceptions.BadUserDataException;


public class AirTravelTest extends CommonConditions {

    @Test
    public void testFindAirTravels(){
    	AirTravel searchFlight = new AirTravel("Москва", "Минск", new GregorianCalendar(2022, 0, 1), new GregorianCalendar(2022, 0, 25), 2);
    	try{
    		BookingAirTravelPage a = new AirTravelPage(driver).openPage().findAirTravels(searchFlight);
    		Assert.assertTrue(true);
    	} catch (BadUserDataException e){
    		Assert.assertTrue(false);
    	}
    }

    //@Test(expectedExceptions = BadUserDataException.class)
    public void testSrcAndDstMustBeDifferent() throws BadUserDataException{
    	AirTravel searchFlight = new AirTravel("Москва", "Москва", new GregorianCalendar(2022, 0, 1), new GregorianCalendar(2022, 0, 25), 2);
    	BookingAirTravelPage a = new AirTravelPage(driver).openPage().findAirTravels(searchFlight);
    }

    @Test
    public void testSwitch(){
    	String srcTown = "Москва";
    	String dstTown = "Минск";
    	AirTravelPage page = new AirTravelPage(driver)
    	                         .openPage()
    	                         .selectOriginTown(srcTown)
						    	 .selectDestinationTown(dstTown)
						    	 .switchNames();
    	Assert.assertEquals(page.getNameOriginTown(), dstTown);
    }

    @Test
    public void testReturnDateAlwaysMoreDepartDate(){
    	Calendar departDate = new GregorianCalendar(2022, 0, 20);
    	Calendar returnDate = new GregorianCalendar(2022, 0, 11);
    	AirTravelPage page = new AirTravelPage(driver)
    	                         .openPage()
    	                         .selectDepartDate(departDate)
    	                         .selectReturnDate(returnDate);
		String rightDepartDate = page.getStrDepartDate();
		Assert.assertEquals("2022-01-10", rightDepartDate);
    }

    @Test
    public void testMaxPassengerLessThan10(){
    	AirTravelPage page = new AirTravelPage(driver)
    								.openPage()
    								.selectNumberOfPassengers(25);
    	Assert.assertEquals(page.getNumberOfPassengers(), 9);
    }

    @Test
    public void testMustBeAtLeastOnPessenger(){
    	AirTravelPage page = new AirTravelPage(driver)
    								.openPage()
    								.selectNumberOfPassengers(0);
    	Assert.assertEquals(page.getNumberOfPassengers(), 1);
    }

    @Test
    public void testAutoCompleteTownName(){
    	String townName = new AirTravelPage(driver)
    								.openPage()
    								.selectOriginTown("Моск")
    								.getNameOriginTown();
    	Assert.assertEquals(townName, "Москва");
    }
}
