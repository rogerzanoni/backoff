package com.deadtroll.backoff.engine.manager;

public class DebugManager {

	private static DebugManager instance;

	private DebugManager() {
	}
	
	public static DebugManager getInstance() {
		if (DebugManager.instance==null) {
			DebugManager.instance = new DebugManager();
		}
		return DebugManager.instance;
	}
}
