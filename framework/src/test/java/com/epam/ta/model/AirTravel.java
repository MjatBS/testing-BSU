package com.epam.ta.model;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class AirTravel{
	private String originTown;
	private String destinationTown;
	private Calendar departDate;
	private Calendar returnDate;
	private int passengersNumber;

	public AirTravel(String originTown, String destinationTown,
		          Calendar departDate, Calendar returnDate, int passengersNumber){
		this.originTown = originTown;
		this.destinationTown = destinationTown;
		this.departDate = departDate;
		this.returnDate = returnDate;
		this.passengersNumber = passengersNumber;
	}

	public String getOriginTown(){
		return originTown;
	}

	public String getDestinationTown(){
		return destinationTown;
	}

	public Calendar getDepartDate(){
		return departDate;
	}

	public Calendar getReturnDate(){
		return returnDate;
	}

	public int getPassengersNumber(){
		return passengersNumber;
	}

}