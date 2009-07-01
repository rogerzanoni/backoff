package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class AbstractGameObject implements IGameObject {

	protected int layer;
	protected Vector2f position;
	
	@Override
	public int getLayer() {
		return this.layer;
	}

	@Override
	public Vector2f getPosition() {
		return this.position;
	}

	@Override
	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	@Override
	public Vector2f getCenter() {
		Image img = this.getCurrentSprite();
		return new Vector2f(this.position.x+(img.getWidth())/2,this.position.y+(img.getHeight())/2);
	}
	
}
