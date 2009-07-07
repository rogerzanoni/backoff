package com.deadtroll.backoff.engine.model;

public enum Heading {
	UP(0),
	RIGHT(1),
	DOWN(2),
	LEFT(3);
	
	private Heading(int headingValue) {
		value = headingValue;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}
