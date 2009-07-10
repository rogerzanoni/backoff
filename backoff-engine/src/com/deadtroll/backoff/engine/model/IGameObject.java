package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.viewport.ViewPort;

public interface IGameObject extends IGameObjectAcessor {

	public float getRotation();
	
	public void setRotation(float angle);
	
	public void setLayer(int layer);

	public void setPosition(Vector2f position);

	public void setDebugMode(boolean debugMode);

	public void setSpriteSheet(SpriteSheet spriteSheet);

	public Image getCurrentSprite();

	public void render(Graphics g, ViewPort viewPort);

	public void initializeGO();

	public void finalizeGO();

}
