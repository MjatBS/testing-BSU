package com.epam.ta.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.Keys;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import com.epam.ta.model.AirTravel;
import com.epam.ta.exceptions.BadUserDataException;


public class AirTravelPage extends AbstractPage
{
	private final String BASE_URL = "https://www.holidayclub.by/";
	private final Logger logger = LogManager.getRootLogger();

	private By airTravelTapLocator = By.xpath("/html/body/div[2]/div[2]/div/div/div[1]/ul/li[2]/a");

	@FindBy(id = "flights-origin-prepop-dc8ed062124ab91504c1cd6c79dd4f06")
	private WebElement originTown;
	private By nameOriginTownLocator = By.xpath("/html/body/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[1]/section/div/form/div[2]/div[1]/span[1]");
	private By autocompleteTownLocator = By.className("mewtwo-autocomplete-list-item");

	@FindBy(id = "flights-destination-prepop-dc8ed062124ab91504c1cd6c79dd4f06")
	private WebElement destinationTown;
	private By nameDestinationTownLocator = By.xpath("/html/body/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[1]/section/div/form/div[3]/div[1]/span[1]");

	@FindBy(className = "mewtwo-swap_button")
	private WebElement townSwitch;

	@FindBy(id = "flights-dates-depart-prepop-dc8ed062124ab91504c1cd6c79dd4f06")
	private WebElement departCalendar;

	@FindBy(id = "flights-dates-return-prepop-dc8ed062124ab91504c1cd6c79dd4f06")
	private WebElement returnCalendar;

	@FindBy(xpath = "/html/body/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[1]/section/div/form/div[5]/div")
	private WebElement choosePassengers;
	private By passengersLocator = By.className("mewtwo-popup-ages-counter");
	private By numberOfPassengersLocator = By.className("mewtwo-popup-ages-counter__amount");
	private By increasePassengerLocator = By.className("mewtwo-popup-ages-counter__plus");
	private By decreasePassengerLocator = By.className("mewtwo-popup-ages-counter__minus");
	private By readyPassengerButtonLocator = By.className("mewtwo-passengers-ready__button");

	@FindBy(xpath = "/html/body/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[1]/section/div/form/div[6]/button")
	private WebElement findTickets;


	public AirTravelPage(WebDriver driver){
		super(driver);
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, WAIT_TIMEOUT_SECONDS);
		PageFactory.initElements(factory, this);
	}

	public BookingAirTravelPage findAirTravels(AirTravel airTravel) throws BadUserDataException{
		return
		this.selectOriginTown(airTravel.getOriginTown())
		    .selectDestinationTown(airTravel.getDestinationTown())
		    .selectDepartDate(airTravel.getDepartDate())
		    .selectReturnDate(airTravel.getReturnDate())
		    .selectNumberOfPassengers(airTravel.getPassengersNumber())
			.clickToFindAirTravels();
	}
	public AirTravelPage selectOriginTown(String town){
		originTown.clear();                             
		originTown.sendKeys(town);
		waitUntilElementToBeClickableAndClick(autocompleteTownLocator);
		return this;
	}
	public AirTravelPage selectDestinationTown(String town){
		destinationTown.clear();
		destinationTown.sendKeys(town);
		waitUntilElementToBeClickableAndClick(autocompleteTownLocator);
		return this;
	}

	public AirTravelPage selectDepartDate(Calendar departDate){
		departCalendar.click();
		System.out.println(""+getDateId(departDate));
		waitUntilElementToBeClickableAndClick(By.id(getDateId(departDate)));
		return this;
	}
	public AirTravelPage selectReturnDate(Calendar returnDate){
		returnCalendar.click();
		waitUntilElementToBeClickableAndClick(By.id(getDateId(returnDate)));
		return this;
	}
	private String getDateId(Calendar date){
		return "mewtwo-datepicker" + 
		       "-" + date.get(Calendar.YEAR) + 
		       "-" + (date.get(Calendar.MONTH) + 1) + 
		       "-" + date.get(Calendar.DAY_OF_MONTH);
	}

	public AirTravelPage selectNumberOfPassengers(int number){
		choosePassengers.click();
		waitUntilElementToBeClickableAndClick(passengersLocator);
		int nowPassengers = Integer.parseInt(driver.findElement(numberOfPassengersLocator).getText());
		if(number >= nowPassengers){
			WebElement increase = driver.findElement(increasePassengerLocator); // Попробовать разместить в начале
			for(int i=nowPassengers; i<number; i++){
				increase.click();
			}
		} else {
			WebElement decrease = driver.findElement(decreasePassengerLocator);
			for(int i=nowPassengers; i>number; i--){
				decrease.click();
			}
		}
		driver.findElement(readyPassengerButtonLocator).click();
		return this;
	}
	public int getNumberOfPassengers(){
		choosePassengers.click();
		waitUntilElementToBeClickableAndClick(passengersLocator);
		return Integer.parseInt(driver.findElement(numberOfPassengersLocator).getText());
	}
	public BookingAirTravelPage clickToFindAirTravels() throws BadUserDataException{
		Set<String> windowsBeforeClick = driver.getWindowHandles();
		findTickets.click();
		String newWindowHandle = (new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS))
	        .until(new ExpectedCondition<String>() {
	            public String apply(WebDriver driver) {
	                Set<String> windowsAfterClick = driver.getWindowHandles();
		            windowsAfterClick.removeAll(windowsBeforeClick);
		            return windowsAfterClick.size() > 0 ? 
	                    windowsAfterClick.iterator().next() : null;
	            }
	        });
	    if(newWindowHandle == null){
	    	throw new BadUserDataException();
	    }
	    driver.switchTo().window(newWindowHandle);
	    return new BookingAirTravelPage(driver);
	}
	public AirTravelPage switchNames(){
		townSwitch.click();
		return this;
	}
	public String getNameOriginTown(){
		return driver.findElement(nameOriginTownLocator).getText();
	}
	public String getStrDepartDate(){
		return driver.findElement(By.id("flights-dates-depart-dc8ed062124ab91504c1cd6c79dd4f06")).getAttribute("value");
	}

	public void waitUntilElementToBeClickableAndClick(By locator){
		new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
		            .until(ExpectedConditions.visibilityOfElementLocated(locator))
		            .click();
	}
	public void waitUntilElementToBeVisible(By locator){
		new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
		            .until(ExpectedConditions.visibilityOfElementLocated(locator));
	
	}

	@Override
	public AirTravelPage openPage(){
		driver.get(BASE_URL);
		boolean ok = false;
		while (!ok) {
			try{
				waitUntilElementToBeClickableAndClick(airTravelTapLocator);
				ok = true;
			} catch (Exception e){
				logger.error("Failed to click flight tap: " + e.getLocalizedMessage());
			}
		}
		return this;
	}
}
