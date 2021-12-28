package com.epam.ta.page;

import org.openqa.selenium.WebDriver;

public abstract class AbstractPage
{
	protected WebDriver driver;

	protected final int WAIT_TIMEOUT_SECONDS = 25;
	public abstract AbstractPage openPage();
	protected AbstractPage(WebDriver driver)
	{
		this.driver = driver;
	}
}
