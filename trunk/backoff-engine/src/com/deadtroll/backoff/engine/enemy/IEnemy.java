package com.deadtroll.backoff.engine.enemy;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public interface IEnemy {

	public int getX();
	public void setX(int x);
	
	public int getY();
	public void setY(int y);
	
	public int getEnergy();
	public void setEnergy(int energy);

	public int getSpeed();
	public void setSpeed(int speed);

	public byte getStatus();
	public void setStatus(byte status);

	public int getDamage();
	public void setDamage(int damage);

	public int getScore();
	public void setScore(int score);
	
	public Image getCurrentSprite();
	public void setSpriteSheet(SpriteSheet sprite);
	
}
