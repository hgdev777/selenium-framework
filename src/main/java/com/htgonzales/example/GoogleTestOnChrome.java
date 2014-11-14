package com.htgonzales.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.htgonzales.util.SeleniumWrapper;

/**
 * Test case using chrome driver
 * 
 * @author hernan
 * @version 1.0
 * 
 */
public class GoogleTestOnChrome extends SeleniumWrapper {

	@BeforeClass
	public void oneTimeSetUp() throws Exception {
		Path path = Paths.get(GoogleTestOnChrome.class.getResource(".").toURI());
		String basePath = path.getParent().getParent().getParent().getParent()
				.getParent().toString();	
		System.setProperty("webdriver.chrome.driver", basePath + "/tools/chromedriver");
		init(new ChromeDriver(), "http://www.google.com");
	}

	@Test(priority = 1)
	public void verifyGoogleSearch() throws Exception {
		
		add(ID, "hplogo");
		verify();
		
		input(ID, "gbqfq", "selenium");
		click(ID, "gbqfba");
		add(LINK_TEXT, "Selenium - Web Browser Automation");
		add(LINK_TEXT, "Selenium - Wikipedia, the free encyclopedia");
		verify();
		
	}

}
