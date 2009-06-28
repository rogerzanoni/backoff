package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public interface IGameObject {

	public int getLayer(); 
	
	public void update(long delta);
	
	public void render(Graphics g);
	
	public Vector2f getPosition();
	
	public void setPosition(Vector2f position); 
	
}
