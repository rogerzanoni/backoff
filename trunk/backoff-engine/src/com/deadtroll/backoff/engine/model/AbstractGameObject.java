package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.sound.ISoundEventListener;
import com.deadtroll.backoff.engine.sound.SoundEventType;
import com.deadtroll.backoff.engine.sound.SoundSequenceType;
import com.deadtroll.backoff.engine.viewport.ViewPort;

public abstract class AbstractGameObject implements IGameObject {

	protected int layer;
	protected Vector2f position;
	protected boolean debugMode;
	protected SpriteSheet spriteSheet;
	protected float rotation;
	protected Vector2f speed;
	protected Vector2f angle;
	protected ISoundEventListener soundEventlister;
	
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
	
	public float getRotation() {
		return this.rotation;
	}
	
	public void setRotation(float angle) {
		this.rotation = angle;
	}

	public void render(Graphics g, ViewPort viewPort) {
		Image sprite = this.getCurrentSprite();
		sprite.rotate(this.getRotation());
		g.drawImage(sprite,this.position.x-viewPort.getX(), this.position.y-viewPort.getY());
	}
	
	public Vector2f getAngle() {
		return angle;
	}

	public void setAngle(Vector2f angle) {
		this.angle = angle;
	}

	public Vector2f getSpeed() {
		return speed;
	}

	public void setSpeed(Vector2f speed) {
		this.speed = speed;
	}
	
	public void playSoundEvent(SoundEventType evtType, SoundSequenceType sequenceType, boolean exclusive, boolean interrupt) {
		if (this.soundEventlister != null) {
			this.soundEventlister.playSoundEvent(this, evtType, sequenceType, exclusive, interrupt);
		}
	}

	public void setSoundEventListener(ISoundEventListener listener) {
		this.soundEventlister = listener;		
	}
}
