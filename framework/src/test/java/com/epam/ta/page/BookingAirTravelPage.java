package com.epam.ta.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class BookingAirTravelPage extends AbstractPage{

	private final String BASE_URL = "https://booking.holidayclub.by/flights/";
	private final Logger logger = LogManager.getRootLogger();
	
	public BookingAirTravelPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public BookingAirTravelPage openPage(){
		driver.navigate().to(BASE_URL);
		return this;
	}
}