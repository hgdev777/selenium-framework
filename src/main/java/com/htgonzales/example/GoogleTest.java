package com.htgonzales.example;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.htgonzales.util.TestCaseHelper;

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
public class GoogleTest extends TestCaseHelper {

	@BeforeClass
	public void oneTimeSetUp() throws Exception {
		// example if you want to override the screen size
		fields.put(DIMENSION_X, "300"); 
		fields.put(DIMENSION_Y, "600"); 
		init("http://www.google.com");
	}

	@Test(priority = 1)
	public void verifyGoogleSearch() throws Exception {
		// verify google logo
		add(ID, "hplogo");
		verify();
		// enter "selenium" in text box then click search
		input(ID, "gbqfq", "selenium");
		click(ID, "gbqfba");
		// verify result page
		add(LINK_TEXT, "Selenium - Web Browser Automation");
		add(LINK_TEXT, "Selenium - Wikipedia, the free encyclopedia");
		verify();
	}

}
