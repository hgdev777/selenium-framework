package com.hgdev777.util;


import java.util.ArrayList;
import java.util.List;
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
	private String baseUrl;
	private int timeout;
	private Dimension dimension;
	private Point point;

	private static final String ERROR = "Error";
	private static final String SLASH = "/";

	protected static final int PASS = 1;
	protected static final int FAIL = 0;
	protected static final int ERR1 = -1;
	protected static final int ERR2 = -2;

	protected static final String LINK_TEXT = "linkText";
	protected static final String ID = "id";
	protected static final String XPATH = "xpath";
	protected static final String CSS_SELECTOR = "cssSelector";

	protected static final String ELEMENT_PRESENT = "ElementPresent";
	protected static final String ELEMENT_NOT_PRESENT = "ElementNotPresent";
	protected static final String TEXT_PRESENT = "TextPresent";
	protected static final String TEXT_NOT_PRESENT = "TextNotPresent";

	/*
	 * Constructor and Destructor
	 */

	public TestCaseExt() {
	}

	public void finalize() {
		if (_driver != null)
			_driver.quit();
	}

	/*
	 * Getters
	 */

	public WebDriver getDriver() {
		return _driver;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public int getTimeout() {
		return timeout;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Point getPoint() {
		return point;
	}

	/*
	 * Setters
	 */

	public Boolean setDriver(WebDriver driver) {
		_driver = driver;
		return true;
	}

	public Boolean setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		try {
			_driver.get(this.baseUrl + SLASH);
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	public Boolean setTimeout(int timeout) {
		this.timeout = timeout;
		try {
			_driver.manage().timeouts()
					.implicitlyWait(this.timeout, TimeUnit.SECONDS);
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	public Boolean setDimension(Dimension dimension) {
		this.dimension = dimension;
		try {
			_driver.manage().window().setSize(this.dimension);
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	public Boolean setPoint(Point point) {
		this.point = point;
		try {
			_driver.manage().window().setPosition(this.point);
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	/**
	 * Helpers
	 */

	private String convertToRegEx(String text) {
		return "^[\\s\\S]*" + text + "[\\s\\S]*$";
	}

	/*
	 * Selenium Methods
	 */

	private boolean isElementPresent(By by) {
		try {
			_driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/*
	 * Extensions
	 */

	private boolean isElementPresentById(String idStr) {
		try {
			return isElementPresent(By.id(idStr));
		} catch (Throwable e) {
			return false;
		}
	}

	private boolean isElementPresentBytLinkText(String linkTextStr) {
		try {
			return isElementPresent(By.linkText(linkTextStr));
		} catch (Throwable e) {
			return false;
		}
	}

	private boolean isElementPresentByXpath(String xpathStr) {
		try {
			return isElementPresent(By.xpath(xpathStr));
		} catch (Throwable e) {
			return false;
		}
	}

	private boolean isElementPresentByCssSelector(String cssSelectorStr) {
		try {
			return isElementPresent(By.cssSelector(cssSelectorStr));
		} catch (Throwable e) {
			return false;
		}
	}

	private boolean isTextPresentByCssSelector(String cssSelectorStr,
			String textStr) {
		try {
			return _driver.findElement(By.cssSelector(cssSelectorStr))
						.getText().matches(textStr);
		} catch (Throwable e) {
			return false;
		}
	}

	private boolean isTextPresentByXpath(String xpathStr, String textStr) {
		String textStrRegEx = this.convertToRegEx(textStr);
		try {
			return _driver.findElement(By.xpath(xpathStr)).getText()
						.matches(textStrRegEx);
		} catch (Throwable e) {
			return false;
		}
	}

	protected boolean isElementPresent(String type, String targetStr) {
		if (type.equals(LINK_TEXT)) {
			return isElementPresentBytLinkText(targetStr);
		} else if (type.equals(ID)) {
			return isElementPresentById(targetStr);
		} else if (type.equals(XPATH)) {
			return isElementPresentByXpath(targetStr);
		} else if (type.equals(CSS_SELECTOR)) {
			return isElementPresentByCssSelector(targetStr);
		} else {
			return false;
		}
	}

	protected boolean isTextPresent(String type, String targetStr, String locationStr) {
		if (type.equals(CSS_SELECTOR)) {
			return isTextPresentByCssSelector(locationStr, targetStr);
		} else if (type.equals(XPATH)) {
			return isTextPresentByXpath(locationStr, targetStr);
		}
		return false;
	}

	/*
	 * Actions
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
				return ERR2;
			}
		} catch (Throwable e) {
			return ERR1;
		}
		return PASS;
	}

	protected int select(String type, String id, String target) {
		try {
			if (type.equals(ID)) {
				new Select(_driver.findElement(By.id(id)))
						.selectByVisibleText(target);
			} else {
				return ERR2;
			}
		} catch (Throwable e) {
			return ERR1;
		}
		return PASS;
	}

	protected int input(String type, String target, String text) {
		try {
			if (type.equals(ID)) {
				_driver.findElement(By.id(target)).clear();
				_driver.findElement(By.id(target)).sendKeys(text);
			} else if (type.equals(CSS_SELECTOR)) {
				_driver.findElement(By.cssSelector(target)).clear();
				_driver.findElement(By.cssSelector(target)).sendKeys(text);
			} else {
				return ERR2;
			}
		} catch (Throwable e) {
			return ERR1;
		}
		return PASS;
	}

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
				return ERR2;
			}
		} catch (Throwable e) {
			return ERR1;
		}
		return PASS;
	}

	protected String getText(String type, String target) {
		String val = null;
		try {
			if (type.equals(XPATH)) {
				WebElement tmp = _driver.findElement(By.xpath(target));
				val = tmp.getText();
			} else {
				return ERROR;
			}
		} catch (Throwable e) {
			return ERROR;
		}
		return val;
	}

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

}
