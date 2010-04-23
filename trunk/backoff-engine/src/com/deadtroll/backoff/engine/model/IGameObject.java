package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.viewport.ViewPort;

public interface IGameObject extends IEntity {	
	public float getRotation();	
	public void setRotation(float angle);
	public float getPreviousRotation();
	public void setPreviousRotation(float previousRotation);
	
	public void setPreviousPosition(Vector2f position);
	public Vector2f getPreviousPosition();
	public void setPosition(Vector2f position);
	public Vector2f getPosition();	
	public Vector2f getCenter();
	
	public boolean isCollided();
	public void setCollided(boolean collided);
	
	public void setLayer(int layer);
	public int getLayer();

	public void setSpriteSheet(String spriteSheet, int sw, int sh);
	public void setSpriteSheet(SpriteSheet spriteSheet);
	public Image getCurrentSprite();
	public void render(Graphics g, ViewPort viewPort);
	
	public void setSpeed(float speed);
	public float getSpeed();
	
	public void setAngle(Vector2f angle);
	public Vector2f getAngle();
	
	public void setScore(int score);
	public int getScore();
	
	public void setEnergy(int energy);
	public int getEnergy();
	
	public void setDamage(int damage);
	public int getDamage();
	
	void addSoundEvents();
	void removeSoundEvents();
	
	public Shape getCollisionShape(ViewPort viewport);
	public Shape getCollisionShape();
}
