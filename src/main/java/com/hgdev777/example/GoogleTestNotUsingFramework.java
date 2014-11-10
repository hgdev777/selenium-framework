package com.hgdev777.example;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.testng.Assert.*;

/**
 * 
 * Example testcase NOT using the TestCaseHelper!!!
 * 
 * Search Google.com with "selenium" keyword then verify result page.
 *  
 * @author hernan
 * @version 1.0
 * 
 */
public class GoogleTestNotUsingFramework {

	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testSeleniumIDE1() throws Exception {
		driver.get(baseUrl
				+ "/webhp?tab=ww&ei=ugBhVKDvFo3ksAT7zYKwCQ&ved=0CAcQ1S4");
		try {
			assertTrue(isElementPresent(By.id("hplogo")));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		driver.findElement(By.id("gbqfq")).clear();
		driver.findElement(By.id("gbqfq")).sendKeys("selenium");
		driver.findElement(By.id("gbqfb")).click();
		driver.findElement(By.cssSelector("a.gb_Ta.gb_Oa > span.gb_Qa"))
				.click();
		try {
			assertTrue(isElementPresent(By.id("resultStats")));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		try {
			assertTrue(isElementPresent(By
					.linkText("Selenium - Web Browser Automation")));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		try {
			assertTrue(isElementPresent(By
					.linkText("Selenium - Wikipedia, the free encyclopedia")));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
