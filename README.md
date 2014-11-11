selenium-framework
========================

Selenium test case implementation made easy!

GUI automation framework making test case coding simple and easy using Selenium, TestNG and Maven

Available encapsulated methods so far aside from Selenium native:

```
init(<URL-GOES-HERE>)
add(ID, <ID-GOES-HERE>)
add(LINK_TEXT, <LINK-TEXT-GOES-HERE>)
add(CSS_SELECTOR, <CSS-SELECTOR-GOES-HERE>)
add(XPATH, <XPATH-GOES-HERE>)
verify()
click("LINK_TEXT", <LINK-TEXT-GOES-HERE>)
click("CSS_SELECTOR", <CSS-SELECTOR-GOES-HERE>)
click("ID", <ID-GOES-HERE>)
select("ID", <ID-GOES-HERE>, <TEXT-GOES-HERE>)
input("ID", <ID-GOES-HERE>, <TEXT-GOES-HERE>)
input("CSS_SELECTOR", <CSS-SELECTOR-GOES-HERE>, <TEXT-GOES-HERE>)
moveToElement("ID", <ID-GOES-HERE>)
moveToElement("LINK_TEXT", <LINK-TEXT-GOES-HERE>)
moveToElement("CSS_SELECTOR", <CSS-SELECTOR-GOES-HERE>)
moveToElement("XPATH", <XPATH-GOES-HERE>)
getText("XPATH", <XPATH-GOES-HERE>)
getSelectOptions("ID", <ID-GOES-HERE>)
getSelectOptions("XPATH", <XPATH-GOES-HERE>)
getAttributeValue("CSS_SELECTOR", <CSS-SELECTOR-GOES-HERE>, <ATTRIBUTE-GOES-HERE>)
```

Dependencies
========================

1. JDK 7 or greater

2. Maven 3.0.5 or greater


How To Run
========================

Simply checkout this package then run Maven TestNG suite

Example on how run a suite
```sh
mvn test -DsuiteXmlFile=configs/suites/Google/suiteGoogle.xml
```

How To Create New Test Case
========================

I. Create a test case

Example: Create a file `src/main/java/com/htgonzales/example/GoogleTest2.java`

```sh
package com.htgonzales.example;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.htgonzales.util.TestCaseHelper;

public class GoogleTest2 extends TestCaseHelper {

	@BeforeClass
	public void oneTimeSetUp() throws Exception {
		init("http://www.google.com");
	}

	@Test
	public void verifyGoogleSearch() throws Exception {
		input(ID, "gbqfq", "selenium");
		click(ID, "gbqfba");
		add(LINK_TEXT, "Selenium - Web Browser Automation");
		add(LINK_TEXT, "Selenium - Wikipedia, the free encyclopedia");
		verify();
	}
}
```
The test case above will open google.com, enter "selenium" in the search box, click search, then verify elements on the result page.

II. Add test case to a suite.

Example: Add to `configs/suites/Google/suiteGoogle.xml` or create new suite if you want

```
<suite name="Test Google">
	<test name="com.htgonzales.example">
		<classes>
			<class name="com.htgonzales.example.GoogleTest" />
			<class name="com.htgonzales.example.GoogleTest2" />
		</classes>
	</test>
</suite>
```

Note
===========

Everyone is welcome to contribute just send a pull request
