package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public interface IGameObject {

	public int getLayer(); 
	
	public void setLayer(int layer);
	
	public void render(Graphics g);
	
	public Vector2f getPosition();
	
	public void setPosition(Vector2f position); 
	
	public Vector2f getCenter();
	
	public Image getCurrentSprite();
	
}
