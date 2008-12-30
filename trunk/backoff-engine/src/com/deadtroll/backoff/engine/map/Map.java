package com.deadtroll.backoff.engine.map;


public class Map {

	private String name;
	private String description;
	private MapLayer[] layers;
	private int mapWidth;
	private int mapHeight;
	private String spriteSheet;
	private int spriteSheetWidth;
	private int spriteSheetHeight;
	private int playerLayer;

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

	public String getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(String spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public int getPlayerLayer() {
		return playerLayer;
	}

	public void setPlayerLayer(int playerLayer) {
		this.playerLayer = playerLayer;
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
	
}
