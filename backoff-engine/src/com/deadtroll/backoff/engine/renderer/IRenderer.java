package com.deadtroll.backoff.engine.renderer;

import org.newdawn.slick.Graphics;

import com.deadtroll.backoff.engine.model.IGameObject;

public interface IRenderer {

	public void addGameObject(IGameObject gameObject);
	
	public void removeGameObject(IGameObject gameObject);

	public void render(Graphics g);
	
}
