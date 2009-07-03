package com.deadtroll.backoff.engine.map;

import org.newdawn.slick.SpriteSheet;

public class Map {

	private String name;
	private String description;
	private MapLayer[] layers;
	private int mapWidth;
	private int mapHeight;
	private String spriteSheetPath;
	private int spriteSheetWidth;
	private int spriteSheetHeight;
	private SpriteSheet mapSpriteSheet;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MapLayer[] getLayers() {
		return layers;
	}

	public void setLayers(MapLayer[] layers) {
		this.layers = layers;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public String getSpriteSheetPath() {
		return spriteSheetPath;
	}

	public void setSpriteSheetPath(String spriteSheetPath) {
		this.spriteSheetPath = spriteSheetPath;
	}

	public int getSpriteSheetHeight() {
		return spriteSheetHeight;
	}

	public void setSpriteSheetHeight(int spriteSheetHeight) {
		this.spriteSheetHeight = spriteSheetHeight;
	}

	public int getSpriteSheetWidth() {
		return spriteSheetWidth;
	}

	public void setSpriteSheetWidth(int spriteSheetWidth) {
		this.spriteSheetWidth = spriteSheetWidth;
	}

	public void initializeMapLayers(int amount) {
		this.layers = new MapLayer[amount];
		for (int i=0; i<amount; i++) {
			this.layers[i] = new MapLayer();
			this.layers[i].setMatrix(new MapBlock[this.mapWidth][this.mapHeight]);
		}
	}

	public SpriteSheet getMapSpriteSheet() {
		return mapSpriteSheet;
	}

	public void setMapSpriteSheet(SpriteSheet mapSpriteSheet) {
		this.mapSpriteSheet = mapSpriteSheet;
	}

}