package com.hgdev777.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class GoogleTestNotUsingFramework {
	private WebDriver driver;

	@Test
	public void testGoogle() {
		driver = new FirefoxDriver();
		driver.get("http://www.google.com");
		driver.quit();
	}
}