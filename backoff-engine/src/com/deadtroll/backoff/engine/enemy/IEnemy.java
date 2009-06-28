package com.deadtroll.backoff.engine.enemy;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import com.deadtroll.backoff.engine.model.IGameObject;

public interface IEnemy extends IGameObject {

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
