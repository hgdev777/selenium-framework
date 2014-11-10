package com.hgdev777.example;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.hgdev777.util.TestCaseHelper;

/**
 * 
 * Example test
 * 
 * @author hernan
 * @version 1.0
 * 
 */
public class GoogleTestUsingFramework extends TestCaseHelper {
	
	@BeforeClass
	public void oneTimeSetUp() throws Exception {

		fields.put(DIMENSION_Y, "600"); //just override
		if (!super.init("http://www.google.com"))
			throw new Exception();
	}

	@Test(priority = 1)
	public void verifyGoogleSearch() throws Exception {
	
		add(ID, "hplogo"); // define target
		verify(); // verify above target

		input(ID, "gbqfq", "selenium"); // enter "selenium" in textbox
		click(ID, "gbqfba"); // click

		add(XPATH, "//div[@id='resultStats']"); // define target 1
		add(CSS_SELECTOR, "h3.r > a"); // define target 2 
		add(LINK_TEXT, "Selenium - Web Browser Automation");
		add(LINK_TEXT, "Selenium - Wikipedia, the free encyclopedia");
		verify(); // verify above target

	}

}
