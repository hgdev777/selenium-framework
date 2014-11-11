selenium-framework
==================

Selenium wrapper to make selenium coding simple and easy

Dependencies
============

1. JDK 7 or greater

2. Maven 3.0.5 or greater


Instructions
============

Run all suites
```sh
mvn test -DsuiteXmlFile=configs/suites/suites.xml
```


Run a suite
```sh
mvn test -DsuiteXmlFile=configs/suites/suiteGoogle.xml
```

How To
============

To create new test case example:

```sh
package com.hgdev777.example;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.hgdev777.util.TestCaseHelper;
public class GoogleTest extends TestCaseHelper {
	@BeforeClass
	public void oneTimeSetUp() throws Exception {
		// example if you want to override the screen size
		fields.put(DIMENSION_X, "300"); 
		fields.put(DIMENSION_Y, "600"); 
		super.init("http://www.google.com");
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
		add(CSS_SELECTOR, "a.gb_Ta.gb_Oa > span.gb_Qa"); 
		add(LINK_TEXT, "Selenium - Web Browser Automation");
		add(LINK_TEXT, "Selenium - Wikipedia, the free encyclopedia");
		verify();
	}
}
```
The test case above will open google.com, verify logo, enter "selenium" in the search box, click search, then verify elements on the result page.
