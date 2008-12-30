package com.deadtroll.backoff.mapeditor;

import java.awt.image.BufferedImage;

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
	private BufferedImage currentSprite;
	
	public Map getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;
	}

	public BufferedImage getCurrentSprite() {
		return currentSprite;
	}

	public void setCurrentSprite(BufferedImage currentSprite) {
		this.currentSprite = currentSprite;
	}

	public int getCurrentSpriteIndex() {
		return currentSpriteIndex;
	}

	public void setCurrentSpriteIndex(int currentSpriteIndex) {
		this.currentSpriteIndex = currentSpriteIndex;
	}

}
