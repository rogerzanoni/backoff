package com.deadtroll.backoff.engine.manager;

import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.viewport.ViewPort;

public class MapManager {
	protected ViewPort viewPort;
	private static MapManager instance;
	protected Map map;
	
	private MapManager() {
	}
	
	public void setMap(Map map) {
		this.map = map;
	}

	public static MapManager getInstance() {
		if (instance == null)
			instance = new MapManager();
		return instance;
	}

	public Map getMap() {
		return map;
	}

	public ViewPort getViewPort() {
		return viewPort;
	}

	public void setViewPort(ViewPort viewPort) {
		this.viewPort = viewPort;
	}
}
