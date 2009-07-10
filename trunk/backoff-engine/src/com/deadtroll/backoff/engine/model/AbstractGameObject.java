package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.viewport.ViewPort;

public abstract class AbstractGameObject implements IGameObject {

	protected int layer;
	protected Vector2f position;
	protected boolean debugMode;
	protected SpriteSheet spriteSheet;
	protected float rotation;
	
	public int getLayer() {
		return this.layer;
	}

	public Vector2f getPosition() {
		return this.position;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getCenter() {
		Image img = this.getCurrentSprite();
		return new Vector2f(this.position.x+(img.getWidth())/2,this.position.y+(img.getHeight())/2);
	}
	
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
	
	public boolean isDebugMode() {
		return this.debugMode;
	}
	
	public void setSpriteSheet(SpriteSheet sprite) {
		this.spriteSheet = sprite;
	}
	
	@Override
	public float getRotation() {
		return this.rotation;
	}
	
	@Override
	public void setRotation(float angle) {
		this.rotation = angle;
	}

	public void render(Graphics g, ViewPort viewPort) {
		Image sprite = this.getCurrentSprite();
		sprite.rotate(this.getRotation());
		g.drawImage(sprite,this.position.x-viewPort.getX(), this.position.y-viewPort.getY());
	}
}
