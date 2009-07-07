package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.geom.Vector2f;

public interface IGameObjectAcessor {
	public int getLayer();
	public Vector2f getPosition();	
	public Vector2f getCenter();
	public boolean isDebugMode();
}
