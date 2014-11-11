package com.htgonzales.util;

/**
 * @author hernan
 * @version 1.0
 *
 */
public class Element {

	public static final int ELEMENT = 1;
	public static final int TEXT = 2;
	private int type;
	private String key;
	private String value;
	private String location;
	
	public Element(int type, String key, String value) {
		this.type = type;
		this.key = key;
		this.value = value;
	}

	public Element(int type, String key, String value, String location) {
		this.type = type;
		this.key = key;
		this.value = value;
		this.location = location;
	}
	
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	
}
