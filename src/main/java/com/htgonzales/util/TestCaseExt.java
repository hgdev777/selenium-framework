package com.htgonzales.util;

import static org.testng.Assert.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

/**
 * @author hernan
 * @version 1.0
 * 
 */
public class TestCaseExt {

	private static WebDriver _driver;
	private static final String SLASH = "/";
	private Map<String, String> fields;
	private ArrayList<Element> list;

	protected static final int FOUND = 1;
	protected static final int NOT_FOUND = 0;
	protected static final String LINK_TEXT = "linkText";
	protected static final String ID = "id";
	protected static final String XPATH = "xpath";
	protected static final String CSS_SELECTOR = "cssSelector";

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

	/**
	 * Constructor
	 */
	public TestCaseExt() {
		list = new ArrayList<Element>();
		fields = new HashMap<String, String>();
	}

	/**
	 * Garbage Collector
	 */
	public void finalize() {
		if (_driver != null)
			_driver.quit();
	}

	/**
	 * Initializes driver's configurations
	 * 
	 * @param driver
	 * @param url
	 * @return
	 */
	public boolean init(WebDriver driver, String url) {

		// set defaults if empty
		fields.put(BASE_URL, url);
		if (!fields.containsKey(TIMEOUT))
			fields.put(TIMEOUT, "3");
		if (!fields.containsKey(DIMENSION_X))
			fields.put(DIMENSION_X, "20");
		if (!fields.containsKey(DIMENSION_Y))
			fields.put(DIMENSION_Y, "200");
		if (!fields.containsKey(POINT_X))
			fields.put(POINT_X, "0");
		if (!fields.containsKey(POINT_Y))
			fields.put(POINT_Y, "0");
		if (!fields.containsKey(SHARED_DRIVER))
			fields.put(SHARED_DRIVER, "true");

		try {

			// set driver
			// re-use browser instance if it exists
			if (Boolean.parseBoolean(fields.get(SHARED_DRIVER))) {
				WebDriver dr = _driver;
				if (dr == null) {
					_driver = driver;
				}
			} else {
				_driver = driver;
			}

			// set window size
			_driver.manage()
					.window()
					.setSize(
							new Dimension(Integer.parseInt(fields
									.get(DIMENSION_X)), Integer.parseInt(fields
									.get(DIMENSION_Y))));

			// set window location
			_driver.manage()
					.window()
					.setPosition(
							new Point(Integer.parseInt(fields.get(POINT_X)),
									Integer.parseInt(fields.get(POINT_Y))));

			// set url
			_driver.get(fields.get(BASE_URL) + SLASH);

			// set timeout
			_driver.manage()
					.timeouts()
					.implicitlyWait(Integer.parseInt(fields.get(TIMEOUT)),
							TimeUnit.SECONDS);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the driver's configurations
	 * 
	 * @return
	 */
	public Map<String, String> getParams() {
		return fields;
	}

	/**
	 * Adds item into the list
	 * 
	 * @param k
	 *            e.g. "ID", "CSS_SELECTOR", "XPATH"
	 * @param v
	 *            e.g. "username", "div.viewLogo", "//div[@id='username']/h1"
	 */
	public void add(String k, String v) {
		list.add(new Element(Element.ELEMENT, k, v));
	}

	/**
	 * Adds item into the list
	 * 
	 * @param k
	 *            e.g. "XPATH"
	 * @param v
	 *            e.g. "Enter Username"
	 * @param l
	 *            e.g. "//div[@id='username']/h1"
	 */
	public void add(String k, String v, String l) {
		list.add(new Element(Element.TEXT, k, v, l));
	}

	/**
	 * Delays the thread
	 * 
	 * @param sec
	 * @throws InterruptedException
	 */
	public void wait(int sec) throws InterruptedException {
		long msec = sec * 1000;
		Thread.sleep(msec);
	}

	/**
	 * Generates random size of 10 alpha-numeric characters
	 * 
	 * @param length
	 * @return
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
		if (!list.isEmpty()) {
			for (Element element : list) {
				if (element.getType() == Element.TEXT) {
					assertTrue(isTextPresent(element.getKey(),
							element.getValue(), element.getLocation()));
				} else {
					assertTrue(isElementPresent(element.getKey(),
							element.getValue()));
				}
			}
			list.clear();
		}
	}

	/**
	 * Returns current driver
	 * 
	 * @return
	 */
	public WebDriver getDriver() {
		return _driver;
	}

	/**
	 * Format text into regular expression
	 * 
	 * @param text
	 * @return String
	 */
	public String convertToRegEx(String text) {
		return "^[\\s\\S]*" + text + "[\\s\\S]*$";
	}

	/**
	 * Verifies element is present
	 * 
	 * @param type
	 *            "LINK_TEXT", "ID", "XPATH", "CSS_SELECTOR"
	 * @param targetStr
	 * @return
	 */
	protected boolean isElementPresent(String type, String targetStr) {
		if (type.equals(LINK_TEXT)) {
			return isElementPresent(By.linkText(targetStr));
		} else if (type.equals(ID)) {
			return isElementPresent(By.id(targetStr));
		} else if (type.equals(XPATH)) {
			return isElementPresent(By.xpath(targetStr));
		} else if (type.equals(CSS_SELECTOR)) {
			return isElementPresent(By.cssSelector(targetStr));
		} else {
			return false;
		}
	}

	/**
	 * Verifies text is present
	 * 
	 * @param type
	 *            "CSS_SELECTOR", "XPATH"
	 * @param targetStr
	 * @param locationStr
	 * @return
	 */
	protected boolean isTextPresent(String type, String targetStr,
			String locationStr) {
		if (type.equals(CSS_SELECTOR)) {
			return _driver.findElement(By.cssSelector(targetStr)).getText()
					.matches(locationStr);
		} else if (type.equals(XPATH)) {
			String textStrRegEx = convertToRegEx(targetStr);
			return _driver.findElement(By.xpath(locationStr)).getText()
					.matches(textStrRegEx);
		}
		return false;
	}

	/**
	 * Clicks an element
	 * 
	 * @param type
	 *            "LINK_TEXT", "CSS_SELECTOR", "ID"
	 * @param target
	 * @return int
	 */
	protected int click(String type, String target) {
		try {
			if (type.equals(LINK_TEXT)) {
				_driver.findElement(By.linkText(target)).click();
			} else if (type.equals(CSS_SELECTOR)) {
				_driver.findElement(By.cssSelector(target)).click();
			} else if (type.equals(ID)) {
				_driver.findElement(By.id(target)).click();
			} else {
				return NOT_FOUND;
			}
		} catch (Throwable e) {
			return ERROR;
		}
		return FOUND;
	}

	/**
	 * Selects value from select options
	 * 
	 * @param type
	 *            "ID"
	 * @param id
	 * @param target
	 * @return int
	 */
	protected int select(String type, String id, String target) {
		try {
			if (type.equals(ID)) {
				new Select(_driver.findElement(By.id(id)))
						.selectByVisibleText(target);
			} else {
				return NOT_FOUND;
			}
		} catch (Throwable e) {
			return ERROR;
		}
		return FOUND;
	}

	/**
	 * Enters value in input field
	 * 
	 * @param type
	 *            "ID", "CSS_SELECTOR"
	 * @param target
	 * @param text
	 * @return int
	 */
	protected int input(String type, String target, String text) {
		try {
			if (type.equals(ID)) {
				_driver.findElement(By.id(target)).clear();
				_driver.findElement(By.id(target)).sendKeys(text);
			} else if (type.equals(CSS_SELECTOR)) {
				_driver.findElement(By.cssSelector(target)).clear();
				_driver.findElement(By.cssSelector(target)).sendKeys(text);
			} else {
				return NOT_FOUND;
			}
		} catch (Throwable e) {
			return ERROR;
		}
		return FOUND;
	}

	/**
	 * Moves cursor to a specific element
	 * 
	 * @param type
	 *            "ID", "LINK_TEXT", "CSS_SELECTOR", "XPATH"
	 * @param target
	 * @return int
	 */
	protected int moveToElement(String type, String target) {
		Actions actions = new Actions(_driver);
		WebElement el;
		try {
			if (type.equals(ID)) {
				el = _driver.findElement(By.id(target));
				actions.moveToElement(el).perform();
			} else if (type.equals(LINK_TEXT)) {
				el = _driver.findElement(By.linkText(target));
				actions.moveToElement(el).perform();
			} else if (type.equals(CSS_SELECTOR)) {
				el = _driver.findElement(By.cssSelector(target));
				actions.moveToElement(el).perform();
			} else if (type.equals(XPATH)) {
				el = _driver.findElement(By.xpath(target));
				actions.moveToElement(el).perform();
			} else {
				return NOT_FOUND;
			}
		} catch (Throwable e) {
			return ERROR;
		}
		return FOUND;
	}

	/**
	 * Gets text from an element
	 * 
	 * @param type
	 *            "XPATH"
	 * @param target
	 * @return String
	 */
	protected String getText(String type, String target) {
		String val = null;
		try {
			if (type.equals(XPATH)) {
				WebElement tmp = _driver.findElement(By.xpath(target));
				val = tmp.getText();
			} else {
				return null;
			}
		} catch (Throwable e) {
			return null;
		}
		return val;
	}

	/**
	 * Gets all options from select menu
	 * 
	 * @param type
	 *            "ID", "XPATH"
	 * @param target
	 * @return List
	 */
	protected List<String> getSelectOptions(String type, String target) {
		WebElement el;
		List<WebElement> Options = new ArrayList<WebElement>();
		List<String> values = new ArrayList<String>();
		Select select;
		if (type.equals(ID)) {
			el = _driver.findElement(By.id(target));
			select = new Select(el);
			Options = select.getOptions();
		} else if (type.equals(XPATH)) {
			el = _driver.findElement(By.xpath(target));
			select = new Select(el);
			Options = select.getOptions();
		} else {
			return null;
		}
		for (WebElement option : Options) {
			values.add(option.getText());
		}
		return values;
	}

	/**
	 * Gets value from an element by attribute
	 * 
	 * @param type
	 *            "CSS_SELECTOR"
	 * @param target
	 * @param attribute
	 * @return String
	 */
	protected String getAttributeValue(String type, String target,
			String attribute) {
		String retVal = null;
		if (type.equals(CSS_SELECTOR)) {
			retVal = _driver.findElement(By.cssSelector(target)).getAttribute(
					attribute);
		} else {
			return retVal;
		}
		return retVal;
	}

	/**
	 * Verifies element exists
	 * 
	 * @param by
	 * @return
	 */
	private boolean isElementPresent(By by) {
		try {
			_driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
