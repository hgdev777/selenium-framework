package com.htgonzales.example;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.htgonzales.util.SeleniumWrapper;

/**
 * 
 * Example testcase using the TestCaseHelper
 * 
 * Search Google.com with "selenium" keyword then verify result page.
 *  
 * @author hernan
 * @version 1.0
 * 
 */
public class GoogleTest extends SeleniumWrapper {

	@BeforeClass
	public void oneTimeSetUp() throws Exception {
		// example if you want to override the screen size
		//fields.put(DIMENSION_X, "300"); 
		//fields.put(DIMENSION_Y, "600"); 
		init(new FirefoxDriver(), "http://www.google.com");
	}

	@Test(priority = 1)
	public void verifyGoogleSearch() throws Exception {
		// verify google logo
		add(ID, "hplogo");
		// verify will clear the list after
		verify();
		// enter "selenium" in text box then click search
		input(ID, "gbqfq", "selenium");
		click(ID, "gbqfba");
		// verify result page
		add(LINK_TEXT, "Selenium - Web Browser Automation");
		add(LINK_TEXT, "Selenium - Wikipedia, the free encyclopedia");
		// verify will clear the list after
		verify();
	}

}
