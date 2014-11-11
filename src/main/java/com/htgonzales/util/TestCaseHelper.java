package com.htgonzales.util;

import static org.testng.Assert.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import com.htgonzales.util.Element;

/**
 * 
 * @author hernan
 * @version 1.0
 * 
 */
public class TestCaseHelper extends TestCaseExt {

	public static final int PASS = 1;
	public static final int FAIL = 0;
	public static final int ERROR = -1;
	public static final String BASE_URL = "selenium_baseurl";
	public static final String TIMEOUT = "selenium_timeout";
	public static final String DIMENSION_X = "selenium_dimensionx";
	public static final String DIMENSION_Y = "selenium_dimensiony";
	public static final String POINT_X = "selenium_pointx";
	public static final String POINT_Y = "selenium_pointy";
	public static final String SHARED_DRIVER = "selenium_sharedDriver";

	protected Map<String, String> fields;
	protected ArrayList<Element> list;
	protected boolean retval;
	protected Iterator<String> iterator;

	public TestCaseHelper() {
		super();
		this.list = new ArrayList<Element>();
		this.retval = false;
		this.fields = new HashMap<String, String>();
	}

	public void finalize() {
		if (super.getDriver() != null)
			super.finalize();
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public boolean init(String url) {
		this.fields.put(BASE_URL, url);
		if (!this.fields.containsKey(TIMEOUT))
			this.fields.put(TIMEOUT, "3");
		if (!this.fields.containsKey(DIMENSION_X))
			this.fields.put(DIMENSION_X, "20");
		if (!this.fields.containsKey(DIMENSION_Y))
			this.fields.put(DIMENSION_Y, "200");
		if (!this.fields.containsKey(POINT_X))
			this.fields.put(POINT_X, "0");
		if (!this.fields.containsKey(POINT_Y))
			this.fields.put(POINT_Y, "0");
		if (!this.fields.containsKey(SHARED_DRIVER))
			this.fields.put(SHARED_DRIVER, "true");

		try {
			// re-use browser instance if it exists
			if (Boolean.parseBoolean(this.fields.get(SHARED_DRIVER))) {
				WebDriver dr = super.getDriver();
				if (dr == null) {
					super.setDriver(new FirefoxDriver());
				}
			} else {
				super.setDriver(new FirefoxDriver());
			}
			super.setDimension(
				new Dimension(
					Integer.parseInt(this.fields.get(DIMENSION_X)), 
					Integer.parseInt(this.fields.get(DIMENSION_Y)))
				);
			super.setPoint(
				new Point(
					Integer.parseInt(this.fields.get(POINT_X)), 
					Integer.parseInt(this.fields.get(POINT_Y)))
				);
			super.setBaseUrl(this.fields.get(BASE_URL));
			super.setTimeout(Integer.parseInt(this.fields.get(TIMEOUT)));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return fields
	 */
	public Map<String, String> getParams() {
		return this.fields;
	}

	/**
	 * 
	 * @param text
	 * @return regex
	 */
	public String convertToRegEx(String text) {
		return "^[\\s\\S]*" + text + "[\\s\\S]*$";
	}

	/**
	 * 
	 * @param k e.g. "ID", "CSS_SELECTOR", "XPATH"
	 * @param v e.g. "username", "div.viewLogo", "//div[@id='username']/h1"
	 */
	public void add(String k, String v) {
		this.list.add(new Element(Element.ELEMENT, k, v));
	}

	/**
	 * 
	 * @param k e.g. "XPATH"
	 * @param v e.g. "Enter Username"
	 * @param l e.g. "//div[@id='username']/h1"
	 */
	public void add(String k, String v, String l) {
		this.list.add(new Element(Element.TEXT, k, v, l));
	}
	
	public void wait(int sec) throws InterruptedException {
		long msec = sec * 1000;
		Thread.sleep(msec);
	}

	/**
	 * 
	 * @return String random size of 10 alpha-numeric characters
	 */
	public static String rand(int length) {
		Random r = new Random();
		String alphabet = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(alphabet.charAt(r.nextInt(alphabet.length())));
		}
		return builder.toString();
	}

	/**
	 * Add at least one element first using add(k, v) before calling verify().
	 * This method clears the list right after it verifies.
	 */
	public void verify() {
		if(!this.list.isEmpty()) {
			for(Element element : this.list) {
				if(element.getType() == Element.TEXT) {
					assertTrue(super.isTextPresent(element.getKey(),
							element.getValue(), element.getLocation()));
				} else {
					assertTrue(super.isElementPresent(element.getKey(),
							element.getValue()));
				}
			}
			this.list.clear();
		}
	}

}
