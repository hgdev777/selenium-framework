selenium-framework
========================

GUI automation framework making test case coding simple and easy using Selenium, TestNG and Maven

Dependencies
========================

1. JDK 7 or greater

2. Maven 3.0.5 or greater


How To Run
========================

Example on how run a suite
```sh
mvn test -DsuiteXmlFile=configs/suites/suiteGoogle.xml
```

How To Create New Test Case
========================

I. Create a test case

Example: Create a file `src/main/java/com/hgdev777/example/GoogleTest2.java`

```sh
package com.hgdev777.example;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.hgdev777.util.TestCaseHelper;

public class GoogleTest2 extends TestCaseHelper {

	@BeforeClass
	public void oneTimeSetUp() throws Exception {
		init("http://www.google.com");
	}

	@Test
	public void verifyGoogleSearch() throws Exception {
		input(ID, "gbqfq", "selenium");
		click(ID, "gbqfba");
		add(CSS_SELECTOR, "a.gb_Ta.gb_Oa > span.gb_Qa"); 
		add(LINK_TEXT, "Selenium - Web Browser Automation");
		add(LINK_TEXT, "Selenium - Wikipedia, the free encyclopedia");
		verify();
	}
}
```
The test case above will open google.com, enter "selenium" in the search box, click search, then verify elements on the result page.

II. Add test case to a suite.

Example: Add to `configs/suites/Google/suiteGoogle.xml` or create new suite if you want

```sh
<suite name="Test Google">
	<test name="com.hgdev777.example">
		<classes>
			<class name="com.hgdev777.example.GoogleTest" />
			**<class name="com.hgdev777.example.GoogleTest2" />**
		</classes>
	</test>
</suite>
```


