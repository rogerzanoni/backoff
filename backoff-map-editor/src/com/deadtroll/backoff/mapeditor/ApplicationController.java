package com.deadtroll.backoff.mapeditor;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.deadtroll.backoff.engine.map.Map;

public class ApplicationController {

	private static ApplicationController instance;

	public static ApplicationController getInstance() {
		if (instance==null) {
			instance = new ApplicationController();
		}
		return instance;
	}
	
	private ApplicationController() {
	}
	
	private Map currentMap;
	private int currentSpriteIndex;
	private HashMap<Integer, BufferedImage> spriteMap;
	private int currentLayer;

	public Map getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;
	}

	public int getCurrentSpriteIndex() {
		return currentSpriteIndex;
	}

	public void setCurrentSpriteIndex(int currentSpriteIndex) {
		this.currentSpriteIndex = currentSpriteIndex;
	}

	public HashMap<Integer, BufferedImage> getSpriteMap() {
		return spriteMap;
	}

	public void setSpriteMap(HashMap<Integer, BufferedImage> spriteMap) {
		this.spriteMap = spriteMap;
	}

	public int getCurrentLayer() {
		return currentLayer;
	}

	public void setCurrentLayer(int currentLayer) {
		this.currentLayer = currentLayer;
	}

}
