package com.deadtroll.backoff.engine.manager;

import java.util.HashMap;


public class KeyStateManager {
	private HashMap<Integer, Boolean> keyStateMap;
	
	private static KeyStateManager instance;
	
	private KeyStateManager() {
		this.keyStateMap = new HashMap<Integer, Boolean>();
	}
	
	public static KeyStateManager getInstance() {
		if (instance == null)
			instance = new KeyStateManager();
		return instance;
	}
	
	public void clear() {
		this.keyStateMap.clear();
	}
	
	public void updateState(int key, boolean pressed) {
		this.keyStateMap.put(key, pressed);
	}
	
	public boolean isKeyPressed(int key) {
		if (this.keyStateMap.containsKey(key))
			return this.keyStateMap.get(key);
		return false;
	}
	
	public boolean anyKeyPressed() {
		for(boolean pressed : this.keyStateMap.values()) {
			if (pressed)
				return true;
		}
		return false;
	}
}