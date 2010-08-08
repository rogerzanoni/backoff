package com.deadtroll.backoff.engine.event.input;

import com.deadtroll.backoff.engine.event.AbstractEvent;

public class KeyInputEvent extends AbstractEvent {
	private int key;
	private char character;
	private KeyEventType type;
	
	public KeyInputEvent(int key, char character, KeyEventType type) {
		this.key = key;
		this.character = character;
		this.type = type;
	}

	public int getKey() {
		return key;
	}

	public char getCharacter() {
		return character;
	}

	public KeyEventType getType() {
		return type;
	}	
}
