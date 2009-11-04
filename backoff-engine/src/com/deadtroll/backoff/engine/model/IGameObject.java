package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.viewport.ViewPort;

public interface IGameObject {

	public long getId();
	public void setId(long id);
	
	public float getRotation();	
	public void setRotation(float angle);
	
	public void setLayer(int layer);
	public int getLayer();

	public void setPosition(Vector2f position);
	public Vector2f getPosition();	
	public Vector2f getCenter();

	public void setSpriteSheet(SpriteSheet spriteSheet);
	public Image getCurrentSprite();
	public void render(Graphics g, ViewPort viewPort);

	public void initializeGO();
	public void finalizeGO();
	
	public void setSpeed(Vector2f speed);
	public Vector2f getSpeed();
	
	public void setAngle(Vector2f angle);
	public Vector2f getAngle();
	
	public void setScore(int score);
	public int getScore();
	
	public void setStatus(TransientStatus status);
	TransientStatus getStatus();
	
	public void setEnergy(int energy);
	public int getEnergy();
	
	public void setDamage(int damage);
	public void addDamage(int damage);
	public int getDamage();
	
	void addSoundEvents();
	void removeSoundEvents();
	
	public Shape getCollisionShape(ViewPort viewport);
}
